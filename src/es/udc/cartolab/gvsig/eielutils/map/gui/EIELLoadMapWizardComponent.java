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
			List<String> municipios = null;
			if (constants.constantsSelected()) {
				whereClause = "WHERE ";
				municipios = constants.getMunicipios();
				for (int j=0; j<municipios.size()-1; j++) {
					whereClause = whereClause.concat(munField + "='" + municipios.get(j) +
					"' OR ");
				}
				whereClause = whereClause.concat(munField + "='" + municipios.get(municipios.size()-1) + "'");
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
