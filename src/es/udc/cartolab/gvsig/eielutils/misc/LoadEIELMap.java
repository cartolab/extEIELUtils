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

import org.cresques.cts.IProjection;

import com.iver.cit.gvsig.fmap.drivers.DBException;
import com.iver.cit.gvsig.fmap.layers.FLayer;

import es.udc.cartolab.gvsig.elle.utils.LoadMap;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class LoadEIELMap extends LoadMap {

	private static LoadEIELMap instance = null;

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new LoadEIELMap();
		}
	}

	public static LoadEIELMap getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	public  FLayer getLayer(String layerName, String tableName,
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
