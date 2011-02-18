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

package es.udc.cartolab.gvsig.eielutils.misc;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;

public class EIELValues {

	// public final static String HEADER_PATH =
	// "gvSIG/extensiones/es.udc.cartolab.gvsig.eielutils/images/header_pontevedra.png";
	// public final static String HEADER_PATH_NT =
	// "gvSIG/extensiones/es.udc.cartolab.gvsig.eielutils/images/navtable_header_pontevedra.png";
	//
	// public final static Color HEADER_COLOR = new Color(36, 46, 109);

	private static EIELValues instance = null;

	private String fase = "2010";

	private String provincia = "36";
	private Color headerColor;
	private String headerPath;
	private String headerNTPath;

	private EIELValues() {
	}

	private synchronized static EIELValues createInstance() {
		if (instance == null) {
			instance = new EIELValues();
		}
		return instance;
	}

	public synchronized static EIELValues getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	/**
	 * EIEL layers and fields
	 */
	public final static String TABLE_MUNICIPIO = "municipio";
	public final static String TABLE_NUCLEO = "nucleo_poblacion";
	public final static String TABLE_ENTIDAD = "entidad_singular";
	public final static String FIELD_FASE = "fase";
	public final static String FIELD_COD_PRO = "provincia";
	public final static String FIELD_COD_MUN = "municipio";
	public final static String FIELD_COD_ENT = "entidad";
	public final static String FIELD_COD_POB = "nucleo";
	public final static String FIELD_DENOM = "denominaci";
	public final static String LAYER_MUNICIPIO = "Municipio";
	public final static String LAYER_NUCLEO = "Núcleos";

	public void setHeader(String path) {
		headerPath = path;
	}

	public void setHeaderNT(String path) {
		headerNTPath = path;
	}

	public void setHeaderColor(int red, int green, int blue) {
		this.headerColor = new Color(red, green, blue);
	}

	public Color getHeaderColor() {
		return headerColor;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public ImageIcon getHeader() {
		if (headerPath != null && getHeaderFile().exists()) {
			return new ImageIcon(headerPath);
		} else {
			return null;
		}
	}

	public File getHeaderFile() {
		return new File(headerPath);
	}

	public ImageIcon getHeaderNT() {
		if (headerNTPath != null && getHeaderFileNT().exists()) {
			return new ImageIcon(headerNTPath);
		} else {
			return null;
		}
	}

	public File getHeaderFileNT() {
		return new File(headerNTPath);
	}

}
