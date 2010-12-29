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

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JCheckBox;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.elle.gui.ElleWizard;

public class EielWizard extends ElleWizard {

	private JCheckBox ctesCHB;

	public void initWizard() {

		super.initWizard();
		setTabName("EIEL");

		ctesCHB = new JCheckBox("Utilizar constantes seleccionadas");

		Constants constants = Constants.getCurrentConstants();
		ctesCHB.setSelected(constants.constantsSelected());
		ctesCHB.setEnabled(constants.constantsSelected());

		add(ctesCHB, BorderLayout.SOUTH);
	}

	protected String getWhereClause() {
		String whereClause = "";
		String munField = EIELValues.FIELD_COD_MUN;
		if (ctesCHB.isSelected()) {
			Constants constants = Constants.getCurrentConstants();
			if (constants.constantsSelected()) {
				whereClause = "WHERE ";
				List<String> councils = constants.getMunicipios();
				for (int j=0; j<councils.size()-1; j++) {
					whereClause = whereClause.concat(munField + "='" + councils.get(j) +
					"' OR ");
				}
				whereClause = whereClause.concat(munField + "='" + councils.get(councils.size()-1) + "'");

			}
		}
		return whereClause;
	}


}
