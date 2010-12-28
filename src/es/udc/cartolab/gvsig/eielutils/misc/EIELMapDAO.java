/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of extEIELForms
 *
 * extEIELForms is based on the forms application of GisEIEL <http://giseiel.forge.osor.eu/>
 * devoloped by Laboratorio de Bases de Datos (Universidade da Coruña)
 *
 * extEIELForms is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extEIELForms is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extEIELForms.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielutils.misc;

import java.sql.SQLException;
import java.util.List;

import org.cresques.cts.IProjection;

import com.iver.cit.gvsig.fmap.drivers.DBException;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.elle.gui.wizard.save.LayerProperties;
import es.udc.cartolab.gvsig.elle.utils.ELLEMap;
import es.udc.cartolab.gvsig.elle.utils.MapDAO;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class EIELMapDAO extends MapDAO {

	private static EIELMapDAO instance = null;

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new EIELMapDAO();
		}
	}

	public static EIELMapDAO getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	public ELLEMap getMap(View view, String mapName,
			String whereClause, int stylesSource, String stylesName) throws Exception {

		if (whereClause == null) {
			whereClause = "";
		}

		EIELMap map = new EIELMap(mapName, view);
		map.setWhereClause(whereClause);

		List<LayerProperties> viewLayers = getViewLayers(mapName);
		for (LayerProperties lp : viewLayers) {
			map.addLayer(lp);
		}

			/////////////// MapOverview
		List<LayerProperties> overviewLayers = getOverviewLayers(mapName);
		for (LayerProperties lp : overviewLayers) {
			map.addOverviewLayer(lp);
		}

		map.setStyleSource(stylesSource);
		map.setStyleName(stylesName);

		return map;

	}

	protected FLayer getLayer(String layerName, String tableName,
			String schema, String whereClause, IProjection proj,
			boolean visible) throws SQLException, DBException {
		DBSession dbs = DBSession.getCurrentSession();
		FLayer layer = null;

		if (dbs != null) {

			if (whereClause.equals("") || hasMunicipio(tableName, schema)) {
				if (schema!=null) {
					layer = dbs.getLayer(layerName, tableName, schema, whereClause, proj);
				} else {
					layer = dbs.getLayer(layerName, tableName, whereClause, proj);
				}
				layer.setVisible(visible);
			}
		}
		return layer;
	}


	private boolean hasMunicipio(String table, String schema) throws SQLException {

		DBSession dbs = DBSession.getCurrentSession();
		if (dbs != null) {
			String[] columns;
			if (schema!=null) {
				columns = dbs.getColumns(table);
			} else {
				columns = dbs.getColumns(schema, table);
			}

			for (String column : columns) {
				if (column.equals(EIELValues.FIELD_COD_MUN)) {
					return true;
				}
			}
		}

		return false;

	}


}
