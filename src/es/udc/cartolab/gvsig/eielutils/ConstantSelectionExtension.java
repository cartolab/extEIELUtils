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
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.listeners.CADListenerManager;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.ConstantGeometryListener;
import es.udc.cartolab.gvsig.eielutils.constants.gui.ConstantLabel;
import es.udc.cartolab.gvsig.eielutils.constants.gui.ConstantSelectionWindow;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class ConstantSelectionExtension extends Extension {

	private ConstantLabel label = new ConstantLabel();

	public void execute(String actionCommand) {
		IWindow w = PluginServices.getMDIManager().getActiveWindow();
		ConstantSelectionWindow cswindow;
		if (w instanceof View) {
			cswindow = new ConstantSelectionWindow((View) w);
		} else {
			cswindow = new ConstantSelectionWindow();
		}
		PluginServices.getMDIManager().addCentredWindow(cswindow);


	}

	public void initialize() {
		PluginServices.getMainFrame().addStatusBarControl(ConstantSelectionExtension.class, label);

		PluginServices.getIconTheme().registerDefault(
				"constants",
				this.getClass().getClassLoader().getResource("images/ctes.png")
			);

		CADListenerManager.addEndGeometryListener(this.getClass().getName(), new ConstantGeometryListener());
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		DBSession dbs = DBSession.getCurrentSession();
		return dbs != null;
	}

}
