package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;

public class ZoomToConstantExtension extends Extension {

	public void execute(String actionCommand) {
		View view = (View) PluginServices.getMDIManager().getActiveWindow();
		Constants c = Constants.getCurrentConstants();
		if (c.getNucCod() != null && !c.getNucCod().equals("")) {
			String codNuc = c.getNucCod();
			String codMun = c.getMunCod();
			String codEnt = c.getEntCod();
			FLayer nucLayer = view.getMapControl().getMapContext().getLayers().getLayer(EIELValues.LAYER_NUCLEO);
			if (nucLayer != null && nucLayer instanceof FLyrVect) {
				LayerOperations.zoomToNucleo(nucLayer, codMun, codEnt, codNuc);
			}
		} else {
			String codMun = c.getMunCod();
			FLayer munLayer = view.getMapControl().getMapContext().getLayers().getLayer(EIELValues.LAYER_MUNICIPIO);
			if (munLayer != null && munLayer instanceof FLyrVect) {
				LayerOperations.zoomToMunicipio(munLayer, codMun);
			}
		}
	}

	public void initialize() {

	}

	public boolean isEnabled() {
		Constants c = Constants.getCurrentConstants();
		return c!=null;
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
