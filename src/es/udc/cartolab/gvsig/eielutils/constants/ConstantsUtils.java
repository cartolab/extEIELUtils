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

package es.udc.cartolab.gvsig.eielutils.constants;

import java.util.List;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.iver.utiles.XMLEntity;

import es.udc.cartolab.gvsig.eielutils.misc.EIELMap;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.elle.gui.EllePreferencesPage;
import es.udc.cartolab.gvsig.elle.utils.ELLEMap;
import es.udc.cartolab.gvsig.elle.utils.MapDAO;

public class ConstantsUtils {

	/**
	 * Reloads maps on the view if the area of the current loaded ones doesn't contain
	 * the new ones.
	 *
	 * It doesn't remove non-related layers from the view, but the order can change.
	 * This should be used <strong>after</strong> calling <i>createNewConstants</i>.
	 * @param view the view to reload
	 * @param loadedCouncils the previously loaded council, it is obtained by constant.getMunicipios(),
	 * <strong>before calling createNewContants</strong>.
	 */
	public static boolean reloadView(View view) throws Exception {

		boolean reloaded = false;
		if (view != null) {
			Constants constants = Constants.getCurrentConstants();

			//where clause for the new load
			String whereClause = "";
			List<String> ctsMuns = null;
			if (constants.constantsSelected()) {
				ctsMuns = constants.getMunicipios();
				String munField = EIELValues.FIELD_COD_MUN;
				if (constants.constantsSelected()) {
					whereClause = "WHERE ";
					for (int j=0; j<ctsMuns.size()-1; j++) {
						whereClause = whereClause.concat(munField + "='" + ctsMuns.get(j) +
						"' OR ");
					}
					whereClause = whereClause.concat(munField + "='" + ctsMuns.get(ctsMuns.size()-1) + "'");
				}
			}

			for (ELLEMap map : MapDAO.getInstance().getLoadedMaps()) {
				if (map instanceof EIELMap) {
					EIELMap eielmap = (EIELMap) map;
					if (eielmap.reloadNeeded(ctsMuns)) {
						eielmap.setMunicipios(ctsMuns);
						eielmap.setWhereClause(whereClause);
						map.reload();
						reloaded = true;
					}
				}
			}

//			for (MapView map : maps) {
//				if (map.reloadNeeded(ctsMuns)) {
//					//remove map
//					LoadEIELMap.getInstance().removeMap(view, map.getMap());
//					constants.removeMap(map.getMap(), view);
//
//					//load the map again
//					LoadEIELMap.getInstance().getMap(view, map.getMap(), whereClause, map.getLegendType(), map.getLegend());
//					constants.addMap(map.getMap(), view, ctsMuns, map.getLegendType(), map.getLegend());
//
//					reloaded = true;
//				}
//			}
		}

		return reloaded;
	}


	public static boolean reloadNeeded(View view, List<String> munCodes) {

		for (ELLEMap map : MapDAO.getInstance().getLoadedMaps()) {
			if (map instanceof EIELMap) {
				EIELMap eielmap = (EIELMap) map;
				if (eielmap.reloadNeeded(munCodes)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getLegendDir() {

		XMLEntity xml = PluginServices.getPluginServices("es.udc.cartolab.gvsig.elle").getPersistentXML();
		if (xml.contains(EllePreferencesPage.DEFAULT_LEGEND_DIR_KEY_NAME)) {
			return xml.getStringProperty(EllePreferencesPage.DEFAULT_LEGEND_DIR_KEY_NAME);
		}

		return null;
	}

}
