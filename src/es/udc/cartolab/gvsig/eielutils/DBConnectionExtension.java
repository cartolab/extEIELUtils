/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of extUtilsEIEL
 *
 * extUtilsEIEL is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extUtilsEIEL is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extUtilsEIEL.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielutils;

import java.util.ArrayList;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.ExtensionDecorator;
import com.iver.cit.gvsig.About;
import com.iver.cit.gvsig.gui.panels.FPanelAbout;

import es.udc.cartolab.gvsig.eielutils.misc.EIELPreferencesParser;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.gui.AbstractGVWindow;
import es.udc.cartolab.gvsig.users.gui.DBConnectionDialog;

public class DBConnectionExtension extends
		es.udc.cartolab.gvsig.users.DBConnectionExtension {

	public void execute(String actionCommand) {

		// with header image (Pontevedra)
		DBConnectionDialog dialog = new DBConnectionDialog();
		dialog.openWindow();
	}

	public void initialize() {
		super.initialize();

		About about = (About) PluginServices.getExtension(About.class);
		FPanelAbout panelAbout = about.getAboutPanel();
		java.net.URL aboutURL = this.getClass().getResource("/about.htm");
		panelAbout.addAboutUrl("gvSIG-EIEL", aboutURL);

		// remove every reference from users and elle
		ArrayList<ExtensionDecorator> decorators = new ArrayList<ExtensionDecorator>();
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.users.DBConnectionExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.users.ChangePassExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.users.CloseSessionExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.users.CreateUserExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.users.DropUserExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.elle.LoadMapExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.elle.LoadAllLegendsExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.elle.SaveAllLegendsExtension.class));
		decorators
				.add(PluginServices
						.getDecoratedExtension(es.udc.cartolab.gvsig.elle.SaveMapExtension.class));

		for (ExtensionDecorator decorator : decorators) {
			decorator.setVisibility(ExtensionDecorator.ALWAYS_INVISIBLE);
		}

		// initialize brand image in dbconnection windows

		// fpuga. EIELValues and EIELPrerencesParser should be rationalized
		EIELPreferencesParser.getEIELValues();
		EIELValues v = EIELValues.getInstance();
		AbstractGVWindow.setHeader(v.getHeader());
		AbstractGVWindow.setHeaderColor(v.getHeaderColor());

	}

	protected void registerIcons() {

	}

}
