package es.udc.cartolab.gvsig.eielutils;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.gui.ConstantLabel;
import es.udc.cartolab.gvsig.eielutils.constants.gui.ConstantSelectionWindow;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class ConstantSelectionExtension extends Extension {

	private ConstantLabel label = new ConstantLabel();

	public void execute(String actionCommand) {
		// TODO Auto-generated method stub
		IWindow w = PluginServices.getMDIManager().getActiveWindow();
		ConstantSelectionWindow cswindow;
		if (w instanceof View) {
			cswindow = new ConstantSelectionWindow((View) w);
		} else {
			cswindow = new ConstantSelectionWindow();
		}
		PluginServices.getMDIManager().addCentredWindow(cswindow);


	}

	public void initialize() {
		// TODO Auto-generated method stub
		PluginServices.getMainFrame().addStatusBarControl(ConstantSelectionExtension.class, label);
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isVisible() {
		// TODO Auto-generated method stub
		DBSession dbs = DBSession.getCurrentSession();
		return dbs != null;
	}

}
