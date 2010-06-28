package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;

import es.udc.cartolab.gvsig.eielutils.map.gui.EIELLoadMapWindow;
import es.udc.cartolab.gvsig.elle.gui.LoadMapWindow;

public class LoadMapExtension extends es.udc.cartolab.gvsig.elle.LoadMapExtension  {

	@Override
	public void execute(String actionCommand) {
		LoadMapWindow window = new EIELLoadMapWindow();
		PluginServices.getMDIManager().addCentredWindow(window);
	}

	@Override
	public void initialize() {
		//		AddLayer.addWizard(EielWizard.class);
	}

}
