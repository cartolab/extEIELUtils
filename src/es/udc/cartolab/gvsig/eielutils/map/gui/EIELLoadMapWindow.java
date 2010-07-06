package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.util.List;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;
import es.udc.cartolab.gvsig.elle.gui.LoadMapWindow;
import es.udc.cartolab.gvsig.elle.utils.LoadMap;

public class EIELLoadMapWindow extends LoadMapWindow {

	@Override
	protected void loadMap() {
		super.loadMap();
	}

	@Override
	protected void callLoadMap() throws Exception {
		Constants constants = Constants.getCurrentConstants();
		String whereClause = "";
		String munField = EIELValues.FIELD_COD_MUN;
		List<String> municipios = null;
		if (constants.constantsSelected()) {
			whereClause = "WHERE ";
			municipios = constants.getMunicipios();
			for (int j=0; j<municipios.size()-1; j++) {
				whereClause = whereClause.concat(munField + "='" + municipios.get(j) +
				"' OR ");
			}
			whereClause = whereClause.concat(munField + "='" + municipios.get(municipios.size()-1) + "'");
		}
		LoadMap.loadMap(view, mapList.getSelectedValue().toString(), crsPanel.getCurProj(), whereClause);
		constants.addMap(mapList.getSelectedValue().toString(), view, municipios);

		LayerOperations.zoomToConstant(view);
	}

}
