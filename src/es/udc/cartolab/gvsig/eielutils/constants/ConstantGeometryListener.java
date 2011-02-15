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

package es.udc.cartolab.gvsig.eielutils.constants;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.fmap.layers.SelectableDataSource;
import com.iver.cit.gvsig.listeners.EndGeometryListener;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.navtable.ToggleEditing;

public class ConstantGeometryListener implements EndGeometryListener {

	public void endGeometry(FLayer layer) {

		Constants ctes = Constants.getCurrentConstants();

		if (layer instanceof FLyrVect && ctes.constantsSelected()) {

			FLyrVect lyr = (FLyrVect) layer;

			ToggleEditing te = new ToggleEditing();

			try {
				SelectableDataSource recordSet = lyr.getRecordset();
				int pos = (int) recordSet.getRowCount() - 1;

				int[] attPos = new int[3];
				attPos[0] = recordSet.getFieldIndexByName(EIELValues.FIELD_FASE);
				attPos[1] = recordSet.getFieldIndexByName(EIELValues.FIELD_COD_PRO);
				attPos[2] = recordSet.getFieldIndexByName(EIELValues.FIELD_COD_MUN);

				boolean foundAll = true;
				for (int aux : attPos) {
					if (aux < 0) {
						foundAll = false;
					}
				}

				if (foundAll) {
					String[] attStringValues = new String[3];
					attStringValues[0] = EIELValues.FASE;
					attStringValues[1] = EIELValues.PROVINCIA;
					attStringValues[2] = ctes.getMunCod();

					boolean lyrEditing = lyr.isEditing();
					if (!lyrEditing) {
						te.startEditing(layer);
					}

					te.modifyValues(lyr, pos, attPos, attStringValues);

					if (!lyrEditing) {
						te.stopEditing(layer, false);
					}
				}
			} catch (ReadDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
