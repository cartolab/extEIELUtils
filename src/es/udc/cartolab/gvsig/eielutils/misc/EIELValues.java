package es.udc.cartolab.gvsig.eielutils.misc;

import java.awt.Color;

import javax.swing.ImageIcon;

public class EIELValues {

	public final static String HEADER_PATH = "gvSIG/extensiones/es.udc.cartolab.gvsig.eielutils/images/header_pontevedra.png";

	public final static Color HEADER_COLOR = new Color(36, 46, 109);

	public final static String FASE = "2008";

	/**
	 * EIEL layers and fields
	 */
	public final static String TABLE_MUNICIPIO = "municipio";
	public final static String TABLE_NUCLEO = "nucleo_poblacion";
	public final static String TABLE_ENTIDAD = "entidad_singular";
	public final static String FIELD_COD_MUN = "municipio";
	public final static String FIELD_COD_ENT = "entidad";
	public final static String FIELD_COD_POB = "poblamiento";
	public final static String FIELD_DENOM = "denominaci";
	public final static String LAYER_MUNICIPIO = "Municipio";
	public final static String LAYER_NUCLEO = "Nucleo población";


	public static ImageIcon getHeader() {
		return new ImageIcon(HEADER_PATH);
	}


}
