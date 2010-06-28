package es.udc.cartolab.gvsig.eielutils.map.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JCheckBox;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.elle.gui.ElleWizard;

public class EielWizard extends ElleWizard {

	private JCheckBox ctesCHB;

	@Override
	public void initWizard() {

		super.initWizard();
		setTabName("EIEL");

		ctesCHB = new JCheckBox("Utilizar constantes seleccionadas");

		Constants constants = Constants.getCurrentConstants();
		if (constants != null) {
			ctesCHB.setSelected(true);
		} else {
			ctesCHB.setSelected(false);
			ctesCHB.setEnabled(false);
		}

		add(ctesCHB, BorderLayout.SOUTH);
	}

	@Override
	protected String getWhereClause() {
		//		return "";
		String whereClause = "";
		if (ctesCHB.isSelected()) {
			Constants constants = Constants.getCurrentConstants();
			if (constants!=null) {
				whereClause = "WHERE ";
				List<String> councils = constants.getMunicipios();
				for (int j=0; j<councils.size()-1; j++) {
					whereClause = whereClause.concat("municipio ='" + councils.get(j) +
					"' OR ");
				}
				whereClause = whereClause.concat("municipio ='" + councils.get(councils.size()-1) + "'");

			}
		}
		return whereClause;
	}


}
