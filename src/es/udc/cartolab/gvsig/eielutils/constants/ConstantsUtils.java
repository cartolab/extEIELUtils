package es.udc.cartolab.gvsig.eielutils.constants;

import java.util.List;

import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.MapView;
import es.udc.cartolab.gvsig.elle.utils.LoadMap;

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
				List<String> mapMuns = map.getMunicipios();
				boolean allInside = mapMuns == null;
				if (mapMuns != null && ctsMuns!=null) {
					allInside = true;
					for (String mun : ctsMuns) {
						if (!mapMuns.contains(mun)) {
							allInside = false;
							break;
						}
					}
				}
				if (!allInside) {
					//remove map
					LoadMap.removeMap(view, map.getMap());
					constants.removeMap(map.getMap(), view);

					//load the map again
					LoadMap.loadMap(view, map.getMap(), view.getProjection(), whereClause);
					constants.addMap(map.getMap(), view, ctsMuns);

					reloaded = true;
				}
			}
		}

		return reloaded;
	}

}
