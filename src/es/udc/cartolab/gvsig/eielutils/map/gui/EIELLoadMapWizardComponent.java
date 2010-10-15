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

package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.util.List;
import java.util.Map;

import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;
import es.udc.cartolab.gvsig.elle.gui.wizard.WizardException;
import es.udc.cartolab.gvsig.elle.gui.wizard.load.LoadMapWizardComponent;
import es.udc.cartolab.gvsig.elle.utils.LoadMap;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class EIELLoadMapWizardComponent extends LoadMapWizardComponent {

	public EIELLoadMapWizardComponent(Map properties) {
		super(properties);

	}

	@Override
	public void finish() throws WizardException {
		Object aux = properties.get(PROPERTY_VEW);
		if (aux!=null && aux instanceof View) {
			View view = (View) aux;
			Constants constants = Constants.getCurrentConstants();
			String whereClause = "";
			String munField = EIELValues.FIELD_COD_MUN;
			DBSession dbs = DBSession.getCurrentSession();
			String munTable = "\"" + dbs.getSchema() + "\".\"" + EIELValues.TABLE_MUNICIPIO + "\"";
			List<String> municipios = null;
			if (constants.constantsSelected()) {
				whereClause = "WHERE ";
				municipios = constants.getMunicipios();
				for (int j=0; j<municipios.size(); j++) {
					String subquery = "(SELECT the_geom FROM " + munTable + " WHERE " + munField + "='" + municipios.get(j) + "' LIMIT 1)";
					whereClause = whereClause.concat("ST_Intersects(the_geom, " + subquery + ") OR ");
				}
				whereClause = whereClause.substring(0, whereClause.length()-3);
				System.out.println(whereClause);
			}
			try {
				LoadMap.loadMap(view, mapList.getSelectedValue().toString(), crsPanel.getCurProj(), whereClause);
				constants.addMap(mapList.getSelectedValue().toString(), view, municipios);
				LayerOperations.zoomToConstant(view);
			} catch (Exception e) {
				throw new WizardException(e);
			}
		} else {
			throw new WizardException("Couldn't retrieve the view");
		}
	}

}
