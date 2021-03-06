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
package com.openmethods.openvxml.desktop.model.dependencies.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.vtp.desktop.model.core.internal.WorkflowResource;

import com.openmethods.openvxml.desktop.model.dependencies.IDependency;

/**
 * Dependency.
 *
 * @author Lonnie Pryor
 */
public class Dependency extends WorkflowResource implements IDependency {
	private static final String HASHPREFIX = "Dependency";
	/** Comment for parent. */
	private final DependencySet parent;
	/** Comment for file. */
	private final IFile file;

	/**
	 * Creates a new Dependency.
	 *
	 * @param parent
	 * @param file
	 */
	public Dependency(DependencySet parent, IFile file) {
		this.parent = parent;
		this.file = file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.desktop.core.project.internals.VoiceResource#
	 * getObjectId()
	 */
	@Override
	protected String getObjectId() {
		return file.getFullPath().toPortableString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.desktop.core.project.IVoiceResource#getName()
	 */
	@Override
	public String getName() {
		return file.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.desktop.core.project.IVoiceResource#getParent()
	 */
	@Override
	public DependencySet getParent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.desktop.projects.core.IDependency#delete()
	 */
	@Override
	public void delete() throws CoreException {
		parent.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.vtp.desktop.projects.core.IDependency#exists()
	 */
	@Override
	public boolean exists() {
		return file.exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.vtp.desktop.model.core.internal.WorkflowResource#getAdapter
	 * (java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterClass) {
		if (IResource.class.isAssignableFrom(adapterClass)
				&& adapterClass.isAssignableFrom(file.getClass())) {
			return file;
		}
		if (Dependency.class.isAssignableFrom(adapterClass)) {
			return this;
		}
		return super.getAdapter(adapterClass);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dependency) {
			return file.equals(((Dependency) obj).file);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (HASHPREFIX + file.toString()).hashCode();
	}

}
