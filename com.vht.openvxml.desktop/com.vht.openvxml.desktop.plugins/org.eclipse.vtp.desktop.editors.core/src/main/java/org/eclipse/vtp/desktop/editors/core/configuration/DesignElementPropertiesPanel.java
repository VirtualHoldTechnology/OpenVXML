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
package org.eclipse.vtp.desktop.editors.core.configuration;

import com.openmethods.openvxml.desktop.model.workflow.design.IDesignElement;

public abstract class DesignElementPropertiesPanel extends
		ComponentPropertiesPanel {
	IDesignElement pe = null;

	public DesignElementPropertiesPanel(String name, IDesignElement element) {
		super(name);
		pe = element;
	}

	protected IDesignElement getElement() {
		return pe;
	}
}
