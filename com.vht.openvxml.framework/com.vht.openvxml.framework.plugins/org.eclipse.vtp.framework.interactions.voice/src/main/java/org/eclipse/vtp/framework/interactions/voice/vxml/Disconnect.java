/*--------------------------------------------------------------------------
 * Copyright (c) 2004, 2006 OpenMethods, LLC
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Trip Gilman (OpenMethods), Lonnie G. Pryor (OpenMethods),
 *    T.D. Barnes (OpenMethods) - initial API and implementation
 -------------------------------------------------------------------------*/
package org.eclipse.vtp.framework.interactions.voice.vxml;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * The <code>Disconnect</code> class represents the &lt;disconnect&gt; VXML
 * element. This action will cause the VXML interpreter to disconnect the
 * physical call and stop execution of the VXML application.
 * 
 * @author Trip Gilman
 * @author Lonnie Pryor
 * @version 2.0
 */
public class Disconnect extends Action {
	/**
	 * Creates a new instance of <code>Disconnect</code>.
	 */
	public Disconnect() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.framework.spi.voice.output.VXMLWidget#writeWidget(
	 * org.xml.sax.ContentHandler)
	 */
	@Override
	public void writeWidget(ContentHandler outputHandler)
			throws NullPointerException, SAXException {
		if (outputHandler == null) {
			throw new NullPointerException("outputHandler"); //$NON-NLS-1$
		}
		// Start and end the element.
		AttributesImpl attributes = new AttributesImpl();
		writeAttributes(attributes);
		outputHandler.startElement(NAMESPACE_URI_VXML, NAME_DISCONNECT,
				NAME_DISCONNECT, attributes);
		outputHandler.endElement(NAMESPACE_URI_VXML, NAME_DISCONNECT,
				NAME_DISCONNECT);
	}

	/**
	 * Write the attribute members of this action to the supplied set.
	 * 
	 * @param attributes
	 *            The attribute set to write to.
	 * @throws NullPointerException
	 *             If the supplied attribute set is <code>null</code>.
	 */
	protected void writeAttributes(AttributesImpl attributes) {
	}
}
