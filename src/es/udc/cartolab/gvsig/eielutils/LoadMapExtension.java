/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of extUtilsEIEL
 *
 * extUtilsEIEL is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extUtilsEIEL is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extUtilsEIEL.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.map.gui.EIELLoadMapWizard;

public class LoadMapExtension extends
		es.udc.cartolab.gvsig.elle.LoadMapExtension {

	public void execute(String actionCommand) {
		EIELLoadMapWizard wizard = new EIELLoadMapWizard((View) PluginServices
				.getMDIManager().getActiveWindow());
		wizard.open();
	}

	public void initialize() {

	}
}
