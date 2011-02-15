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

package es.udc.cartolab.gvsig.eielutils.constants;

import java.util.ArrayList;
import java.util.List;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiFrame.NewStatusBar;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;

public class Constants {

	private enum Field {
		FASE, PROVINCIA, MUNICIPIO, ENTIDAD, NUCLEO, POBLAMIENTO, NOVALUE;

		public static Field toField(String str) {
			try {
				return valueOf(str.toUpperCase());
			} catch (Exception e) {
				return NOVALUE;
			}
		}
	}

	private static Constants instance = null;

	private List<String> municipios;
	private String munCod;
	private String entCod;
	private String nucCod;
	private boolean constatsSelected = false;
	private String fase = EIELValues.FASE;
	private String provincia = EIELValues.PROVINCIA;

	private Constants() {
	}

	/**
	 * @return user defined constants, or null if they're not defined yet.
	 */
	public static Constants getCurrentConstants() {
		if (instance == null) {
			instance = new Constants();
		}
		return instance;
	}

	public static Constants newConstants(String munCod, String entCod,
			String nucCod, List<String> municipios) {
		if (instance == null) {
			instance = new Constants();
		}
		instance.munCod = munCod;
		instance.entCod = entCod;
		instance.nucCod = nucCod;
		if (entCod == null) {
			instance.entCod = "";
		}
		if (nucCod == null) {
			instance.nucCod = "";
		}
		instance.municipios = municipios;
		if (!municipios.contains(munCod)) {
			instance.municipios.add(munCod);
		}
		instance.changeStatusBar();
		instance.constatsSelected = true;
		return instance;
	}

	public static Constants newConstants(String munCod, String entCod,
			String nucCod) {
		ArrayList<String> municipios = new ArrayList<String>();
		municipios.add(munCod);
		return newConstants(munCod, entCod, nucCod, municipios);
	}

	public String getMunCod() {
		return munCod;
	}

	public String getEntCod() {
		return entCod;
	}

	public String getNucCod() {
		return nucCod;
	}

	public List<String> getMunicipios() {
		return municipios;
	}

	private void changeStatusBar() {
		MDIFrame mF = (MDIFrame) PluginServices.getMainFrame();
		NewStatusBar footerStatusBar = mF.getStatusBar();
		String nuc = nucCod;
		if (nucCod == null || nucCod.equals("")) {
			nuc = "-";
		}
		String ent = entCod;
		if (entCod == null || entCod.equals("")) {
			ent = "-";
		}
		String text = PluginServices.getText(this, "status_mun_ent_nuc");
		text = String.format(text, munCod, ent, nuc);
		footerStatusBar.setMessage("constants", text);
	}

	public static void removeConstants() {
		if (instance.constatsSelected) {
			instance.constatsSelected = false;
			MDIFrame mF = (MDIFrame) PluginServices.getMainFrame();
			NewStatusBar footerStatusBar = mF.getStatusBar();
			footerStatusBar.setMessage("constants",
					PluginServices.getText(null, "all_prov"));
		}
	}

	public boolean constantsSelected() {
		return constatsSelected;
	}

	/**
	 * It tells if the council it's the current one
	 * 
	 * @param cod
	 * @return
	 */
	public boolean isSelectedCouncil(String cod) {
		if (instance.constantsSelected()) {
			return cod.equals(instance.getMunCod());
		}
		return false;
	}

	/**
	 * Gets current constant value.
	 * 
	 * @param constant
	 *            should be one of fase, provincia, municipio or
	 *            nucleo/poblamiento.
	 * @return the constant value, null if it's not set
	 */
	public String getValue(String constant) {
		switch (Field.toField(constant)) {
		case FASE:
			return fase;
		case PROVINCIA:
			return provincia;
		case MUNICIPIO:
			return munCod;
		case ENTIDAD:
			return entCod;
		case NUCLEO:
		case POBLAMIENTO:
			return nucCod;
		default:
			return null;
		}
	}

}
