package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JCheckBox;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.elle.gui.ElleWizard;

public class EielWizard extends ElleWizard {

	private JCheckBox ctesCHB;

	@Override
	public void initWizard() {

		super.initWizard();
		setTabName("EIEL");

		ctesCHB = new JCheckBox("Utilizar constantes seleccionadas");

		Constants constants = Constants.getCurrentConstants();
		ctesCHB.setSelected(constants.constantsSelected());
		ctesCHB.setEnabled(constants.constantsSelected());

		add(ctesCHB, BorderLayout.SOUTH);
	}

	@Override
	protected String getWhereClause() {
		//		return "";
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
