package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;

public class ZoomToConstantExtension extends Extension {

	public void execute(String actionCommand) {
		View view = (View) PluginServices.getMDIManager().getActiveWindow();
		LayerOperations.zoomToConstant(view);
	}

	public void initialize() {

	}

	public boolean isEnabled() {
		Constants c = Constants.getCurrentConstants();
		return c.constantsSelected();
	}

	public boolean isVisible() {
		//		if (PluginServices.getMDIManager().getActiveWindow() instanceof View) {
		//			View view = (View) PluginServices.getMDIManager().getActiveWindow();
		//			return LoadMap.isLayer(view.getMapControl().getMapContext().getLayers(), EIELValues.LAYER_MUNICIPIO);
		//		}
		//		return false;
		return PluginServices.getMDIManager().getActiveWindow() instanceof View;
	}

}
