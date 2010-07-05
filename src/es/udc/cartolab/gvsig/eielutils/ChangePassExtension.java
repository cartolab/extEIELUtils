package es.udc.cartolab.gvsig.eielutils;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.gui.ChangePassDialog;

public class ChangePassExtension extends es.udc.cartolab.gvsig.users.ChangePassExtension {

	@Override
	public void execute(String actionCommand) {

		ChangePassDialog dialog = new ChangePassDialog(EIELValues.getHeader(), EIELValues.HEADER_COLOR);
		dialog.openWindow();
	}


}
