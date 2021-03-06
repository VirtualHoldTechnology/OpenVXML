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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.vtp.framework.common.IScriptable;
import org.eclipse.vtp.framework.interactions.core.ILanguageRegistry;
import org.eclipse.vtp.framework.interactions.core.configurations.LanguageConfiguration;

/**
 * Implementation of {@link ILanguageRegistry}.
 * 
 * @author Lonnie Pryor
 */
public class LanguageRegistry implements ILanguageRegistry, IScriptable {
	/** The available languages. */
	private final Map<String, List<String>> languageMapping;

	/**
	 * Creates a new LanguageRegistry.
	 * 
	 * @param configurations
	 *            The configurations to use.
	 */
	public LanguageRegistry(LanguageConfiguration[] configurations) {
		Map<String, List<String>> mapping = new HashMap<String, List<String>>();
		for (LanguageConfiguration configuration : configurations) {
			List<String> languages = mapping.get(configuration
					.getInteractionType());
			if (languages == null) {
				languages = new ArrayList<String>();
				mapping.put(configuration.getInteractionType(), languages);
			}
			languages.add(configuration.getID());
		}
		this.languageMapping = Collections.unmodifiableMap(mapping);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.framework.interactions.core.ILanguageRegistry#
	 * getLanguageIDs()
	 */
	@Override
	public String[] getLanguageIDs(String interactionType) {
		List<String> languages = languageMapping.get(interactionType);
		if (languages == null) {
			return new String[0];
		}
		return languages.toArray(new String[languages.size()]);
	}

	@Override
	public boolean clearEntry(String name) {
		return false;
	}

	@Override
	public boolean clearItem(int index) {
		return false;
	}

	@Override
	public Object getEntry(String name) {
		return null;
	}

	@Override
	public String[] getFunctionNames() {
		return new String[] { "getLanguage" };
	}

	@Override
	public Object getItem(int index) {
		if (index > -1 && index < languageMapping.size()) {
			return languageMapping.get(index);
		}
		return null;
	}

	@Override
	public String getName() {
		return "Languages";
	}

	@Override
	public String[] getPropertyNames() {
		return new String[0];
	}

	@Override
	public boolean hasEntry(String name) {
		return false;
	}

	@Override
	public boolean hasItem(int index) {
		return index > -1 && index < languageMapping.size();
	}

	@Override
	public boolean hasValue() {
		return false;
	}

	@Override
	public Object invokeFunction(String name, Object[] arguments) {
		if ("getLanguage".equals(name)) {
			if (arguments.length > 0) {
				return arguments[0];
			}
		}
		return null;
	}

	@Override
	public boolean setEntry(String name, Object value) {
		return false;
	}

	@Override
	public boolean setItem(int index, Object value) {
		return false;
	}

	@Override
	public Object toValue() {
		return null;
	}

	@Override
	public boolean isMutable() {
		return false;
	}
}
