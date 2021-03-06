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
package org.eclipse.vtp.framework.interactions.core.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.vtp.framework.interactions.core.media.IFormatter;
import org.eclipse.vtp.framework.interactions.core.media.IFormatterRegistry;
import org.osgi.framework.Bundle;

/**
 * Implementation of {@link IFormatterRegistry}.
 * 
 * @author Lonnie Pryor
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class FormatterRegistry implements IFormatterRegistry {
	/** The formatters by ID. */
	private final Map formatters;

	/**
	 * Creates a new FormatterRegistry.
	 * 
	 * @param registry
	 *            The extension registry to load from.
	 */
	public FormatterRegistry(IExtensionRegistry registry) {
		IExtensionPoint point = registry.getExtensionPoint(//
				"org.eclipse.vtp.framework.interactions.core.formatterTypes"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		Map formatters = new HashMap(extensions.length);
		for (IExtension extension : extensions) {
			Bundle bundle = Platform.getBundle(extension.getContributor()
					.getName());
			IConfigurationElement[] elements = extension
					.getConfigurationElements();
			for (IConfigurationElement element : elements) {
				try {
					formatters
							.put(element.getAttribute("id"), bundle //$NON-NLS-1$
											.loadClass(
													element.getAttribute("class")).newInstance()); //$NON-NLS-1$
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		this.formatters = Collections.unmodifiableMap(formatters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.vtp.framework.interactions.core.media.IFormatterRegistry#
	 * getFormatterIDs()
	 */
	@Override
	public String[] getFormatterIDs() {
		return (String[]) formatters.keySet().toArray(
				new String[formatters.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.vtp.framework.interactions.core.media.IFormatterRegistry#
	 * getFormatter(java.lang.String)
	 */
	@Override
	public IFormatter getFormatter(String formatterID) {
		return (IFormatter) formatters.get(formatterID);
	}
}
