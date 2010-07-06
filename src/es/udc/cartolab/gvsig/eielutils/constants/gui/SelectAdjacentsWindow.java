package es.udc.cartolab.gvsig.eielutils.constants.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.constants.ConstantsUtils;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.eielutils.misc.LayerOperations;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class SelectAdjacentsWindow extends JPanel implements IWindow, ActionListener {

	private class CheckBoxItem {

		private final String mun;
		private final JCheckBox checkBox;

		public CheckBoxItem(String mun, JCheckBox chb) {
			this.mun = mun;
			checkBox = chb;
		}

		public String getMun() {
			return mun;
		}

		public JCheckBox getCheckBox() {
			return checkBox;
		}

	}

	private final String municipio, entidad, nucleo;
	private WindowInfo viewInfo = null;
	private JPanel northPanel = null;
	private JPanel southPanel = null;
	private JPanel centerPanel = null;
	private JButton okButton, cancelButton;
	private final ArrayList<CheckBoxItem> checkBoxes;
	private JButton allCouncilsButton;
	private JButton cleanCouncilsButton;
	private View view;



	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG | WindowInfo.PALETTE);
			viewInfo.setTitle(PluginServices.getText(this, "select_adjacents"));
			viewInfo.setWidth(800);
			viewInfo.setHeight(400);
		}
		return viewInfo;
	}

	public SelectAdjacentsWindow(View view, String munCod, String entCod, String nucCod) {
		municipio = munCod;
		entidad = entCod;
		nucleo = nucCod;
		checkBoxes = new ArrayList<CheckBoxItem>();
		this.view = view;
		init();
	}

	public SelectAdjacentsWindow(String munCod, String entCod, String nucCod) {
		this(null, munCod, entCod, nucCod);
	}

	private void init() {

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		add(getNorthPanel(), new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		add(getCenterPanel(), new GridBagConstraints(0, 1, 1, 1, 0, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 0, 0), 0, 0));

		add(getSouthPanel(), new GridBagConstraints(0, 2, 1, 1, 10, 0,
				GridBagConstraints.SOUTH, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		//enables tabbing navigation
		setFocusCycleRoot(true);
	}

	protected JPanel getNorthPanel() {

		//Set header if any
		//Current header (Pontevedra) size: 425x79
		if (northPanel == null) {
			northPanel = new JPanel();
			File iconPath = new File("gvSIG/extensiones/es.udc.cartolab.gvsig.elle/images/header.png");
			if (iconPath.exists()) {
				FlowLayout layout = new FlowLayout();
				layout.setAlignment(FlowLayout.LEFT);
				northPanel.setBackground(new Color(36, 46, 109));
				northPanel.setLayout(layout);
				ImageIcon logo = new ImageIcon(iconPath.getAbsolutePath());
				JLabel icon = new JLabel();
				icon.setIcon(logo);
				northPanel.add(icon);
			}
		}
		return northPanel;
	}

	protected JPanel getSouthPanel() {

		if (southPanel == null) {
			southPanel = new JPanel();
			MigLayout layout = new MigLayout("", "[][grow][]", "[]");
			southPanel.setLayout(layout);

			//left
			JPanel left = new JPanel();
			allCouncilsButton = new JButton(PluginServices.getText(this, "all"));
			cleanCouncilsButton = new JButton(PluginServices.getText(this, "clean"));
			allCouncilsButton.addActionListener(this);
			cleanCouncilsButton.addActionListener(this);
			left.add(allCouncilsButton);
			left.add(cleanCouncilsButton);

			//right
			JPanel right = new JPanel();
			okButton = new JButton(PluginServices.getText(this, "ok"));
			cancelButton = new JButton(PluginServices.getText(this, "cancel"));
			okButton.addActionListener(this);
			cancelButton.addActionListener(this);
			right.add(okButton);
			right.add(cancelButton);



			southPanel.add(left, "shrink, align left");
			southPanel.add(new JPanel());
			//			southPanel.add(cleanCouncilsButton, "shrink, align left");
			southPanel.add(right, "shrink, align right");
			//			southPanel.add(cancelButton, "shrink, align right");
		}
		return southPanel;
	}

	protected JPanel getCenterPanel() {

		if (centerPanel == null) {
			centerPanel = new JPanel();
			DBSession dbs = DBSession.getCurrentSession();
			String munField = EIELValues.FIELD_COD_MUN;
			String denomField = EIELValues.FIELD_DENOM;
			if (dbs != null) {
				GridLayout layout = new GridLayout(13,5);
				centerPanel.setLayout(layout);
				ArrayList<String> adjacents = new ArrayList<String>();
				String query = "SELECT " + munField + " FROM %s WHERE ST_Touches(the_geom, (SELECT the_geom FROM %s WHERE " + munField + "='" +
				municipio + "')) ORDER BY " + denomField;
				String table = dbs.getSchema() + "." + EIELValues.TABLE_MUNICIPIO;
				try {
					Statement stat = dbs.getJavaConnection().createStatement();

					query = String.format(query, table, table);

					ResultSet rs = stat.executeQuery(query);

					while (rs.next()) {
						String text = rs.getString(munField);
						adjacents.add(text);
					}
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				query = "SELECT " + munField + ", " + denomField + " FROM %s ORDER BY " + munField;
				query = String.format(query, table);
				try {
					Statement stat = dbs.getJavaConnection().createStatement();

					query = String.format(query, table, table);

					ResultSet rs = stat.executeQuery(query);

					while (rs.next()) {
						String mun = rs.getString(munField);
						String name = rs.getString(denomField);
						JCheckBox chb = new JCheckBox(name);
						chb.setToolTipText(name);
						if (adjacents.contains(mun)) {
							chb.setSelected(true);
							chb.setForeground(Color.blue);
						}
						if (mun.equals(municipio)) {
							chb.setSelected(true);
							chb.setEnabled(false);
						}
						CheckBoxItem cbi = new CheckBoxItem(mun, chb);
						checkBoxes.add(cbi);
						centerPanel.add(chb);
					}
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
		}
		return centerPanel;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cancelButton) {
			PluginServices.getMDIManager().closeWindow(this);
		}
		if (event.getSource() == okButton) {
			ArrayList<String> municipios = new ArrayList<String>();
			for (int i=0; i<checkBoxes.size(); i++) {
				if (checkBoxes.get(i).getCheckBox().isSelected()) {
					municipios.add(checkBoxes.get(i).getMun());
				}
			}
			Constants ctes = Constants.getCurrentConstants();
			boolean changeMunicipio = !ctes.isSelectedCouncil(municipio);
			ctes = Constants.newConstants(municipio, entidad, nucleo, municipios);
			if (changeMunicipio) {
				//call function that checks councils and reload view if necessary
				try {
					ConstantsUtils.reloadView(view);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				LayerOperations.zoomToConstant(view);
			}
			PluginServices.getMDIManager().closeWindow(this);
		}
		if (event.getSource() == allCouncilsButton) {
			for (CheckBoxItem cbi : checkBoxes) {
				JCheckBox cb = cbi.getCheckBox();
				if (cb.isEnabled()) {
					cbi.getCheckBox().setSelected(true);
				}
			}
		}
		if (event.getSource() == cleanCouncilsButton) {
			for (CheckBoxItem cbi : checkBoxes) {
				JCheckBox cb = cbi.getCheckBox();
				if (cb.isEnabled()) {
					cbi.getCheckBox().setSelected(false);
				}
			}
		}
	}

	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}

}
