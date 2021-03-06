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
package org.eclipse.vtp.framework.spi;

/**
 * A process that can be stepped through.
 * 
 * @author Lonnie Pryor
 */
public interface IProcess {
	/**
	 * Returns the ID of this process.
	 * 
	 * @return The ID of this process.
	 */
	String getProcessID();

	/**
	 * Looks up the service selected for the specified identifier or
	 * <code>null</code> if no such service exists.
	 * 
	 * @param identifier
	 *            The identifier of the service to look up.
	 * @return The service selected for the specified identifier or
	 *         <code>null</code> if no such service exists.
	 * @throws NullPointerException
	 *             If the supplied identifier is <code>null</code>.
	 */
	Object lookupService(String identifier) throws NullPointerException;

	/**
	 * Returns an array containing all the services registered under the
	 * specified identifier.
	 * 
	 * @param identifier
	 *            The identifier of the services to look up.
	 * @return An array containing all the services registered under the
	 *         specified identifier.
	 * @throws NullPointerException
	 *             If the supplied identifier is <code>null</code>.
	 */
	Object[] lookupAllServices(String identifier) throws NullPointerException;

	/**
	 * Creates a new process engine session from the specified descriptor.
	 * 
	 * @param descriptor
	 *            The descriptor of the session to create.
	 * @return A new process engine session from the specified descriptor.
	 * @throws NullPointerException
	 *             If the specified descriptor is <code>null</code>.
	 */
	ISession createSession(ISessionDescriptor descriptor)
			throws NullPointerException;
}
