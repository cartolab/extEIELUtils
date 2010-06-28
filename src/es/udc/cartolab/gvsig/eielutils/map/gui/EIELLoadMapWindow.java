package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.util.List;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
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
		if (constants!=null) {
			whereClause = "WHERE ";
			List<String> municipios = constants.getMunicipios();
			for (int j=0; j<municipios.size()-1; j++) {
				whereClause = whereClause.concat("municipio ='" + municipios.get(j) +
				"' OR ");
			}
			whereClause = whereClause.concat("municipio ='" + municipios.get(municipios.size()-1) + "'");
		}
		LoadMap.loadMap(view, mapList.getSelectedValue().toString(), crsPanel.getCurProj(), whereClause);
	}

}
