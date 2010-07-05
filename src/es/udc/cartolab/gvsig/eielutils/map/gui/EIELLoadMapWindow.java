package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.util.List;

import com.iver.cit.gvsig.fmap.layers.FLayer;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;
import es.udc.cartolab.gvsig.elle.gui.LoadMapWindow;
import es.udc.cartolab.gvsig.elle.utils.LoadMap;

public class EIELLoadMapWindow extends LoadMapWindow {

	@Override
	protected void loadMap() {
		super.loadMap();
		//si hay constantes hacer zoom
	}

	@Override
	protected void callLoadMap() throws Exception {
		Constants constants = Constants.getCurrentConstants();
		String whereClause = "";
		String munField = EIELValues.FIELD_COD_MUN;
		if (constants!=null) {
			whereClause = "WHERE ";
			List<String> municipios = constants.getMunicipios();
			for (int j=0; j<municipios.size()-1; j++) {
				whereClause = whereClause.concat(munField + "='" + municipios.get(j) +
				"' OR ");
			}
			whereClause = whereClause.concat(munField + "='" + municipios.get(municipios.size()-1) + "'");
		}
		LoadMap.loadMap(view, mapList.getSelectedValue().toString(), crsPanel.getCurProj(), whereClause);
		Constants ctes = Constants.getCurrentConstants();
		if (ctes!=null) {
			if (ctes.getNucCod() != null) {
				FLayer layer = view.getMapControl().getMapContext().getLayers().getLayer(EIELValues.LAYER_NUCLEO);
				if (layer!=null) {
					LayerOperations.zoomToNucleo(layer, ctes.getMunCod(), ctes.getEntCod(), ctes.getNucCod());
				}
			} else if (ctes.getMunCod() != null) {
				FLayer layer = view.getMapControl().getMapContext().getLayers().getLayer(EIELValues.LAYER_MUNICIPIO);
				if (layer!=null) {
					LayerOperations.zoomToMunicipio(layer, ctes.getMunCod());
				}
			}
		}
	}

}
