package es.udc.cartolab.gvsig.eielutils;

import es.udc.cartolab.gvsig.eielutils.constants.gui.EIELCreateUserWindow;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.gui.CreateUserWindow;

public class CreateUserExtension extends es.udc.cartolab.gvsig.users.CreateUserExtension {

	@Override
	public void execute(String actionCommand) {

		CreateUserWindow window = new EIELCreateUserWindow(EIELValues.getHeader(), EIELValues.color);
		window.openWindow();
	}

}
