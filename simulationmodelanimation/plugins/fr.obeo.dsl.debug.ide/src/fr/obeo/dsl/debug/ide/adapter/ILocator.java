package fr.obeo.dsl.debug.ide.adapter;

import org.eclipse.emf.ecore.EObject;

/*******************************************************************************
 * Copyright (c) 2015 Obeo. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Obeo - initial API and implementation
 *******************************************************************************/

/**
 * a.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ILocator {

	/**
	 * The type locator (either xtext or sirius).
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	enum Type {
		/**
		 * Xtext locator type.
		 */
		XTEXT_LOCATION("Xtext"),

		/**
		 * Sirius locator type.
		 */
		SIRIUS_LOCATION("Sirius");

		/**
		 * Name of the locator type.
		 */
		private final String typeName;

		/**
		 * Constructs a locator type based on a type name.
		 * 
		 * @param typeName
		 *            The locator type name.
		 */
		Type(final String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return typeName;
		}
	}

	/**
	 * Gets the location of a given model element.
	 * 
	 * @param eObject
	 *            The model element.
	 * @return The location (eg. the file and line number).
	 */
	Location getLocation(EObject eObject);

	/**
	 * The location of a model element (eg. in an xmi or xtext file).
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	class Location {

		/**
		 * The location type.
		 */
		final Type type;

		/**
		 * The location data (depends on the type).
		 */
		final Object data;

		/**
		 * Constructs a location based on type anf location data.
		 * 
		 * @param type
		 *            The location type.
		 * @param data
		 *            The location data.
		 */
		public Location(final Type type, final Object data) {
			this.type = type;
			this.data = data;
		}
	}
}
