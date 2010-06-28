package es.udc.cartolab.gvsig.eielutils.db.gui;

import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import com.iver.andami.PluginServices;

import es.udc.cartolab.gvsig.users.gui.CreateUserWindow;
import es.udc.cartolab.gvsig.users.utils.DBAdminUtils;

public class EIELCreateUserWindow extends CreateUserWindow {

	private TreeMap<String, String> items = new TreeMap<String, String>();;

	public EIELCreateUserWindow() {
		this(null, null);
	}

	public EIELCreateUserWindow(ImageIcon headerImg, Color headerBgColor) {
		super(headerImg, headerBgColor);
		items.put(PluginServices.getText(this, "create_eiel"), "eiel");
		for (String item : items.keySet()) {
			typeCB.addItem(item);
		}
	}

	@Override
	protected void grantRole(Connection con, String username) throws SQLException {
		if (items.containsKey(typeCB.getSelectedItem())) {
			DBAdminUtils.grantRole(con, username, items.get(typeCB.getSelectedItem()));
		} else {
			super.grantRole(con, username);
		}
	}

}
