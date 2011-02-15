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

package es.udc.cartolab.gvsig.eielutils.constants.gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeMap;

import com.iver.andami.PluginServices;

import es.udc.cartolab.gvsig.users.gui.CreateUserWindow;
import es.udc.cartolab.gvsig.users.utils.DBAdminUtils;

public class EIELCreateUserWindow extends CreateUserWindow {

	private TreeMap<String, String> items = new TreeMap<String, String>();;

	public EIELCreateUserWindow() {
		super();
		items.put(PluginServices.getText(this, "create_eiel"), "eiel");
		for (String item : items.keySet()) {
			typeCB.addItem(item);
		}
	}

	protected void grantRole(Connection con, String username)
			throws SQLException {
		if (items.containsKey(typeCB.getSelectedItem())) {
			DBAdminUtils.grantRole(con, username,
					items.get(typeCB.getSelectedItem()));
		} else {
			super.grantRole(con, username);
		}
	}

}
