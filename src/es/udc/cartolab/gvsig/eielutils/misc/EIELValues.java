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

import javax.swing.ImageIcon;

public class EIELValues {

	public final static String HEADER_PATH = "gvSIG/extensiones/es.udc.cartolab.gvsig.eielutils/images/header_pontevedra.png";

	public final static Color HEADER_COLOR = new Color(36, 46, 109);

	public final static String FASE = "2008";
	public final static String PROVINCIA = "36";

	/**
	 * EIEL layers and fields
	 */
	public final static String TABLE_MUNICIPIO = "municipio";
	public final static String TABLE_NUCLEO = "nucleo_poblacion";
	public final static String TABLE_ENTIDAD = "entidad_singular";
	public final static String FIELD_COD_MUN = "municipio";
	public final static String FIELD_COD_ENT = "entidad";
	public final static String FIELD_COD_POB = "nucleo";
	public final static String FIELD_DENOM = "denominaci";
	public final static String LAYER_MUNICIPIO = "Municipio";
	public final static String LAYER_NUCLEO = "Nucleo población";


	public static ImageIcon getHeader() {
		return new ImageIcon(HEADER_PATH);
	}


}
