/*--------------------------------------------------------------------------
 * Copyright (c) 2004, 2006-2007 OpenMethods, LLC
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Trip Gilman (OpenMethods), Lonnie G. Pryor (OpenMethods)
 *    - initial API and implementation
 -------------------------------------------------------------------------*/
package org.eclipse.vtp.framework.engine.runtime;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.vtp.framework.core.IContext;

/**
 * A factory class that can construct and configure instances of a component.
 * 
 * @author Lonnie Pryor
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Builder {
	/** The registry created for the scope. */
	private IContext registry = null;
	/** The currently active component instance. */
	private Object instance = null;

	/**
	 * Creates a new Builder.
	 */
	protected Builder() {
	}

	/**
	 * Creates an instance of the component.
	 * 
	 * @return The new component instance or <code>null</code> if it could not
	 *         be created.
	 * @throws IllegalStateException
	 *             If a previously created component has not been configured.
	 */
	public Object create() throws IllegalStateException {
		if (instance != null) {
			throw new IllegalStateException();
		}
		Constructor[] constructors = getConstructors();
		List arguments = new ArrayList();
		for (int i = 0; instance == null && i < constructors.length; ++i) {
			Class[] parameterTypes = constructors[i].getParameterTypes();
			if (parameterTypes.length == 0) {
				instance = RuntimeUtils.createInstance(constructors[i],
						new Object[0]);
			} else {
				for (Class parameterType : parameterTypes) {
					Object value = resolveDependency(parameterType);
					if (value == null) {
						System.err
								.println("Could not locate: "
										+ parameterType.getName()
										+ " while resolving "
										+ constructors[0].getDeclaringClass()
												.getName());
						arguments.clear();
						break;
					}
					arguments.add(value);
				}
				if (arguments.isEmpty()) {
					continue;
				}
				instance = RuntimeUtils.createInstance(constructors[i],
						arguments.toArray());
			}
		}
		if (instance == null) {
			if (registry != null) {
				registry.error("Unable to create: "
						+ constructors[0].getDeclaringClass().getName());
			} else {
				System.err.println("Unable to create: "
						+ constructors[0].getDeclaringClass().getName());
			}
		}
		return instance;
	}

	/**
	 * Configures the currently active component instance.
	 * 
	 * @throws IllegalStateException
	 *             If there is no currently active component instance.
	 */
	public void configure() throws IllegalStateException {
		if (instance == null) {
			throw new IllegalStateException();
		}
		Method[] mutators = getMutators();
		for (Method mutator : mutators) {
			Object value = resolveDependency(mutator.getParameterTypes()[0]);
			if (value != null
					&& (!value.getClass().isArray() || ((Object[]) value).length > 0)) {
				RuntimeUtils.setProperty(instance, mutator, value);
			}
		}
		instance = null;
	}

	/**
	 * Returns the constructors for the component.
	 * 
	 * @return The constructors for the component.
	 */
	protected abstract Constructor[] getConstructors();

	/**
	 * Returns the mutator methods of the component.
	 * 
	 * @return The mutator methods of the component.
	 */
	protected abstract Method[] getMutators();

	/**
	 * Creates a service registry for the current scope.
	 * 
	 * @return A new service registry for the current scope.
	 */
	protected abstract IContext createServiceRegistry();

	/**
	 * Resolves a dependency on the specified type.
	 * 
	 * @param type
	 *            The type to find.
	 * @return The value of the resolved dependency or <code>null</code> if the
	 *         dependency could not be resolved.
	 */
	private Object resolveDependency(Class type) {
		if (registry == null) {
			registry = createServiceRegistry();
		}
		Object value = null;
		if (type.isArray()) {
			Object[] values = registry.lookupAll(type.getComponentType()
					.getName());
			List results = new ArrayList(values.length);
			for (Object value2 : values) {
				if (type.getComponentType().isInstance(value2)) {
					results.add(value2);
				}
			}
			value = results.toArray((Object[]) Array.newInstance(
					type.getComponentType(), results.size()));
		} else {
			value = registry.lookup(type.getName());
			if (value != null && !type.isInstance(value)) {
				value = null;
			}
		}
		return value;
	}
}
