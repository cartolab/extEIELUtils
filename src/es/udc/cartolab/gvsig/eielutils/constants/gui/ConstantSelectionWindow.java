/*
 * Copyright (c) 2010. Cartolab (Universidade da Coru�a)
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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.jeta.forms.components.panel.FormPanel;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.constants.ConstantsUtils;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class ConstantSelectionWindow extends JPanel implements IWindow,
		ActionListener {

	private WindowInfo viewInfo = null;
	private JComboBox municipioCB, entidadCB, nucleoCB;
	private JCheckBox municipioCHB;
	private JPanel northPanel = null;
	private JPanel centerPanel = null;
	private JPanel southPanel = null;
	private JButton okButton;
	private JButton cancelButton;
	private View view;

	private final String municipioTable = EIELValues.TABLE_MUNICIPIO;
	private final String munCodField = EIELValues.FIELD_COD_MUN;
	private final String entidadTable = EIELValues.TABLE_ENTIDAD;
	private final String entCodField = EIELValues.FIELD_COD_ENT;
	private final String nucleoTable = EIELValues.TABLE_NUCLEO;
	private final String nucCodField = EIELValues.FIELD_COD_POB;
	private final String denominacion = EIELValues.FIELD_DENOM;
	private final String fase = EIELValues.getInstance().getFase();

	private static Logger logger = Logger
			.getLogger(ConstantSelectionWindow.class);

	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG
					| WindowInfo.PALETTE);
			viewInfo.setTitle(PluginServices.getText(this, "select_constants"));
			viewInfo.setWidth(425);
			viewInfo.setHeight(265);
		}
		return viewInfo;
	}

	public ConstantSelectionWindow() {
		this(null);
	}

	public ConstantSelectionWindow(View view) {
		init();
		this.view = view;
	}

	protected JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			FormPanel form = new FormPanel("forms/constantSelection.jfrm");
			form.setFocusTraversalPolicyProvider(true);
			centerPanel.add(form);

			JLabel municipioLabel = form.getLabel("municipioLabel");
			municipioLabel.setText(PluginServices.getText(this, "municipio"));
			JLabel entidadLabel = form.getLabel("entidadLabel");
			entidadLabel.setText(PluginServices.getText(this, "entidad"));
			JLabel nucleoLabel = form.getLabel("nucleoLabel");
			nucleoLabel.setText(PluginServices.getText(this, "nucleo"));

			municipioCB = form.getComboBox("municipioCB");
			entidadCB = form.getComboBox("entidadCB");
			nucleoCB = form.getComboBox("nucleoCB");

			municipioCHB = form.getCheckBox("municipioCHB");
			municipioCHB.setText(PluginServices.getText(this,
					"adjacent_councils"));

			try {
				String text = PluginServices.getText(this, "all_prov");
				fillComboBox(text, municipioTable, munCodField, "WHERE fase='"
						+ fase + "'", municipioCB);
				municipioCB.addActionListener(this);
				municipioCB.setSelectedIndex(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		setDefaultValues();

		return centerPanel;
	}

	protected JPanel getNorthPanel() {

		// Set header if any
		if (northPanel == null) {
			northPanel = new JPanel();
			ImageIcon logo = EIELValues.getInstance().getHeader();
			northPanel.setBackground(EIELValues.getInstance().getHeaderColor());
			JLabel icon = new JLabel();
			icon.setIcon(logo);
			northPanel.add(icon, BorderLayout.WEST);
		}
		return northPanel;
	}

	protected JPanel getSouthPanel() {

		if (southPanel == null) {
			southPanel = new JPanel();
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.RIGHT);
			southPanel.setLayout(layout);
			okButton = new JButton(PluginServices.getText(this, "ok"));
			cancelButton = new JButton(PluginServices.getText(this, "cancel"));
			okButton.addActionListener(this);
			cancelButton.addActionListener(this);
			southPanel.add(okButton);
			southPanel.add(cancelButton);
		}
		return southPanel;
	}

	private void fillComboBox(String firstItem, String tableName,
			String codField, String whereClause, JComboBox comboBox)
			throws SQLException {

		DBSession dbs = DBSession.getCurrentSession();
		if (dbs != null) {

			Connection con = dbs.getJavaConnection();
			Statement stat = con.createStatement();
			String schema = dbs.getSchema();
			String query = "SELECT " + codField + ", " + denominacion
					+ " FROM " + schema + "." + tableName;
			query = query + " " + whereClause + " ORDER BY " + codField + ";";

			comboBox.removeAllItems();

			if (firstItem != null && !firstItem.equals("")) {
				comboBox.addItem(firstItem);
			}

			ResultSet rs = stat.executeQuery(query);

			while (rs.next()) {
				String text = rs.getString(codField) + " - "
						+ rs.getString("denominaci");
				comboBox.addItem(text);
			}
			rs.close();

		}
	}

	private String getCode(JComboBox cb, int index) {
		String text = "";
		if (index > 0 && index < cb.getItemCount()) {
			text = cb.getItemAt(index).toString();
			text = text.substring(0, text.indexOf(" - "));
		}
		return text;
	}

	private String getCode(JComboBox cb) {
		return getCode(cb, cb.getSelectedIndex());
	}

	private void init() {

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		add(getNorthPanel(), new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		add(getCenterPanel(), new GridBagConstraints(0, 1, 1, 1, 0, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		add(getSouthPanel(), new GridBagConstraints(0, 2, 1, 1, 10, 0,
				GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		// enables tabbing navigation
		setFocusCycleRoot(true);
	}

	private List<String> getMunicipiosToLoad(String munCod, String entCod,
			String nucCod) {
		List<String> municipios;
		if (municipioCHB.isSelected()) {
			SelectAdjacentsWindow win = new SelectAdjacentsWindow(view, munCod,
					entCod, nucCod);
			win.open();
			municipios = win.getSelectedMuns();
		} else {
			municipios = new ArrayList<String>();
			municipios.add(munCod);
		}
		return municipios;
	}

	private void setConstants() {
		// Get codes
		String munCod = getCode(municipioCB);
		String entCod = "";
		String nucCod = "";
		if (entidadCB.getSelectedIndex() != 0) {
			entCod = getCode(entidadCB);
		}
		if (nucleoCB.getSelectedIndex() != 0) {
			nucCod = getCode(nucleoCB);
		}
		// Close window
		PluginServices.getMDIManager().closeWindow(this);

		List<String> municipios = getMunicipiosToLoad(munCod, entCod, nucCod);

		/*
		 * If municipios == null, then user has canceled at Select Adjacents
		 * Window...
		 */
		if (municipios != null) {
			if (ConstantsUtils.reloadNeeded(view, municipios)) {
				int answer = JOptionPane.showConfirmDialog(this,
						PluginServices.getText(this, "maps_will_be_reloaded"),
						"", JOptionPane.YES_NO_OPTION);
				if (answer == 0) {
					Constants.newConstants(munCod, entCod, nucCod, municipios);
					// call function that checks councils and reload view if
					// necessary
					try {
						ConstantsUtils.reloadView(view);
					} catch (Exception e) {
						String msg = e.getMessage();
						JOptionPane.showMessageDialog(this, String.format(
								PluginServices.getText(this,
										"error_loading_layers"), msg), "",
								JOptionPane.ERROR_MESSAGE, null);
						logger.error(msg, e);
					}
					LayerOperations.zoomToConstant(view);
				}
			} else {
				Constants.newConstants(munCod, entCod, nucCod, municipios);
			}
		}
	}

	private void setDefaultValues() {

		Constants ctes = Constants.getCurrentConstants();

		if (ctes.constantsSelected()) {

			String mun = ctes.getMunCod();
			String ent = ctes.getEntCod();
			String nuc = ctes.getNucCod();

			setCombobox(municipioCB, mun);
			setCombobox(entidadCB, ent);
			setCombobox(nucleoCB, nuc);

		}

	}

	private void setCombobox(JComboBox cb, String code) {

		int index = 0;
		if (code != null && !code.equals("")) {
			for (int i = 0; i < cb.getItemCount(); i++) {
				String auxCode = getCode(cb, i);
				if (auxCode.equals(code)) {
					index = i;
					break;
				}
			}
		}

		if (index < cb.getItemCount()) {
			cb.setSelectedIndex(index);
		}

	}

	private void noConstants() {
		PluginServices.getMDIManager().closeWindow(this);
		if (ConstantsUtils.reloadNeeded(view, null)) {
			int answer = JOptionPane.showConfirmDialog(this,
					PluginServices.getText(this, "maps_will_be_reloaded"), "",
					JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				Constants.removeConstants();
				// call function that checks councils and reload view if
				// necessary
				try {
					ConstantsUtils.reloadView(view);
				} catch (Exception e) {
					String msg = e.getMessage();
					JOptionPane.showMessageDialog(this,
							String.format(PluginServices.getText(this,
									"error_loading_layers"), msg), "",
							JOptionPane.ERROR_MESSAGE, null);
					logger.error(msg, e);
				}
			}
		} else {
			Constants.removeConstants();
		}
	}

	private void onOk() {
		if (municipioCB.getSelectedIndex() != 0) {
			setConstants();
		} else {
			noConstants();
		}
		PluginServices.getMainFrame().enableControls();
	}

	private void onMunSelected() {
		if (municipioCB.getSelectedIndex() != 0) {
			String cod = getCode(municipioCB);
			try {
				entidadCB.removeActionListener(this);
				String text = PluginServices.getText(this, "all_ent");
				fillComboBox(text, entidadTable, entCodField, "WHERE fase='"
						+ fase + "' AND " + munCodField + "='" + cod + "'",
						entidadCB);
				entidadCB.addActionListener(this);
				entidadCB.setSelectedIndex(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			municipioCHB.setEnabled(true);
		} else {
			entidadCB.removeActionListener(this);
			entidadCB.removeAllItems();
			entidadCB.addActionListener(this);
			nucleoCB.removeActionListener(this);
			nucleoCB.removeAllItems();
			nucleoCB.addActionListener(this);
			municipioCHB.setEnabled(false);
		}
	}

	private void onEntSelected() {
		if (entidadCB.getSelectedIndex() != 0) {
			String entCod = getCode(entidadCB);
			String munCod = getCode(municipioCB);
			System.out.println("WHERE fase='" + fase + "' AND " + munCodField
					+ "='" + munCod + "' AND " + entCodField + "='" + entCod
					+ "'");
			try {
				String text = PluginServices.getText(this, "all_nuc");
				fillComboBox(text, nucleoTable, nucCodField, "WHERE fase='"
						+ fase + "' AND " + munCodField + "='" + munCod
						+ "' AND " + entCodField + "='" + entCod + "'",
						nucleoCB);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			nucleoCB.removeAllItems();
		}
	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == cancelButton) {
			PluginServices.getMDIManager().closeWindow(this);
		}
		if (event.getSource() == okButton) {
			onOk();
		}
		if (event.getSource() == municipioCB) {
			onMunSelected();
		}
		if (event.getSource() == entidadCB) {
			onEntSelected();
		}
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}
}
