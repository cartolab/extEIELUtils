package es.udc.cartolab.gvsig.eielutils.misc;

import java.awt.Color;

import javax.swing.ImageIcon;

public class EIELValues {

	public final static String headerPath = "gvSIG/extensiones/es.udc.cartolab.gvsig.eielutils/images/header_pontevedra.png";

	public final static Color color = new Color(36, 46, 109);

	public static ImageIcon getHeader() {
		return new ImageIcon(headerPath);
	}

}
