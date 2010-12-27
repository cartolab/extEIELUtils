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

import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LoadEIELMap;
import es.udc.cartolab.gvsig.eielutils.misc.MapView;

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
			List<MapView> maps = constants.getLoadedMaps(view);

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
			for (MapView map : maps) {
				if (map.reloadNeeded(ctsMuns)) {
					//remove map
					LoadEIELMap.getInstance().removeMap(view, map.getMap());
					constants.removeMap(map.getMap(), view);

					//load the map again
					LoadEIELMap.getInstance().loadMap(view, map.getMap(), view.getProjection(), whereClause);
					constants.addMap(map.getMap(), view, ctsMuns);

					reloaded = true;
				}
			}
		}

		return reloaded;
	}


	public static boolean reloadNeeded(View view, List<String> munCodes) {

		Constants constants = Constants.getCurrentConstants();

		List<MapView> maps = constants.getLoadedMaps(view);
		for (MapView map : maps) {
			if (map.reloadNeeded(munCodes)) {
				return true;
			}
		}
		return false;
	}

}
