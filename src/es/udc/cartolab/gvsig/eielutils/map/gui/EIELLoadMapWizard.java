package es.udc.cartolab.gvsig.eielutils.map.gui;

import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.elle.gui.wizard.load.LoadMapWizard;

public class EIELLoadMapWizard extends LoadMapWizard {

	public EIELLoadMapWizard(View view) {
		super(view);

		views.remove(0);
		views.add(0, new EIELLoadMapWizardComponent(properties));

	}

}
