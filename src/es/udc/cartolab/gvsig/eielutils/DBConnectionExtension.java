package es.udc.cartolab.gvsig.eielutils;

import java.util.ArrayList;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.ExtensionDecorator;
import com.iver.cit.gvsig.About;
import com.iver.cit.gvsig.gui.panels.FPanelAbout;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.gui.DBConnectionDialog;


public class DBConnectionExtension extends es.udc.cartolab.gvsig.users.DBConnectionExtension {

	@Override
	public void execute(String actionCommand) {

		//with header image (Pontevedra)
		DBConnectionDialog dialog = new DBConnectionDialog(EIELValues.getHeader(), EIELValues.HEADER_COLOR);
		dialog.openWindow();
	}

	@Override
	public void initialize() {
		super.initialize();

		About about=(About)PluginServices.getExtension(About.class);
		FPanelAbout panelAbout=about.getAboutPanel();
		java.net.URL aboutURL = this.getClass().getResource("/about.htm");
		panelAbout.addAboutUrl("OGE", aboutURL);

		//remove every reference from users and elle
		ArrayList<ExtensionDecorator> decorators = new ArrayList<ExtensionDecorator>();
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.users.DBConnectionExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.users.ChangePassExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.users.CloseSessionExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.users.CreateUserExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.users.DropUserExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.elle.LoadMapExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.elle.LoadAllLegendsExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.elle.SaveAllLegendsExtension.class));
		decorators.add(PluginServices.getDecoratedExtension(es.udc.cartolab.gvsig.elle.SaveMapExtension.class));

		for(ExtensionDecorator decorator : decorators) {
			decorator.setVisibility(ExtensionDecorator.ALWAYS_INVISIBLE);
		}

	}

}
