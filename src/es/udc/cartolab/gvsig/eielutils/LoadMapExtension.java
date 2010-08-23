package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.map.gui.EIELLoadMapWizard;

public class LoadMapExtension extends es.udc.cartolab.gvsig.elle.LoadMapExtension  {

	@Override
	public void execute(String actionCommand) {
		EIELLoadMapWizard wizard = new EIELLoadMapWizard((View) PluginServices.getMDIManager().getActiveWindow());
		wizard.open();
	}

	@Override
	public void initialize() {
		//		AddLayer.addWizard(EielWizard.class);
	}

}
