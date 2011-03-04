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
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;

public class ZoomToConstantExtension extends Extension {

	public void execute(String actionCommand) {
		View view = (View) PluginServices.getMDIManager().getActiveWindow();
		LayerOperations.zoomToConstant(view);
	}

	public void initialize() {
		PluginServices.getIconTheme().registerDefault(
				"zoomnuc",
				this.getClass().getClassLoader()
						.getResource("images/zoomnuc.png"));
	}

	public boolean isEnabled() {
		Constants c = Constants.getCurrentConstants();
		boolean enabled = false;
		if (c.constantsSelected()) {
			FLayer layerM = ((View) PluginServices.getMDIManager()
					.getActiveWindow()).getMapControl().getMapContext()
					.getLayers().getLayer(EIELValues.LAYER_MUNICIPIO);
			enabled = layerM != null;
		}
		return enabled;
	}

	public boolean isVisible() {
		return PluginServices.getMDIManager().getActiveWindow() instanceof View;
	}

}
