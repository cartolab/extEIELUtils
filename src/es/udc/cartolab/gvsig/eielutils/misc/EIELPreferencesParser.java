/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 * 
 * This file is part of extEIELUtils
 * 
 * extEIELUtils is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 * 
 * extEIELUtils is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with extEIELUtils.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielutils.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.iver.andami.Launcher;

public class EIELPreferencesParser {

	private final static String preferencesFile = Launcher.getAppHomeDir()
			+ File.separator + "eiel.xml";

	private static File getFile() {
		File f = new File(preferencesFile);
		if (f.exists()) {
			return f;
		} else {
			return null;
		}
	}

	private static String getXML(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void getEIELValues() {
		EIELValues ev = EIELValues.getInstance();
		try {
			File f = getFile();
			if (f != null) {
				String xmlDef = getXML(f);
				if (xmlDef != null) {
					DOMParser parser = new DOMParser();
					StringReader sr = new StringReader(xmlDef);
					InputSource is = new InputSource(sr);
					parser.parse(is);

					Node rootNode = parser.getDocument();
					Node attributes = rootNode.getFirstChild();
					while (attributes != null) {
						if (attributes.getNodeName().equalsIgnoreCase(
								"eielpreferences")) {
							Node attributes2 = attributes.getFirstChild();
							while (attributes2 != null) {
								if (attributes2.getNodeName().equalsIgnoreCase(
										"windowspreferences")) {
									processWindowsPreferences(ev, attributes2);
								} else if (attributes2.getNodeName()
										.equalsIgnoreCase("eielvalues")) {
									processEIELValues(ev, attributes2);
								}
								attributes2 = attributes2.getNextSibling();
							}
						}
						attributes = attributes.getNextSibling();
					}
				}
			}
		} catch (IOException e) {
		} catch (SAXException e) {
		}
		// nothing to do in case of an exception (it'll store default values or
		// file values got before the exception).
	}

	private static void processEIELValues(EIELValues ev, Node eielNode) {
		Node attributes = eielNode.getFirstChild();
		while (attributes != null) {
			if (attributes.getNodeName().equalsIgnoreCase("fase")) {
				String fase = attributes.getFirstChild().getNodeValue();
				ev.setFase(fase);
			} else if (attributes.getNodeName().equalsIgnoreCase("provincia")) {
				String provincia = attributes.getFirstChild().getNodeValue();
				ev.setProvincia(provincia);
			}
			attributes = attributes.getNextSibling();
		}
	}

	private static void processWindowsPreferences(EIELValues ev,
			Node windowsPrefNode) {
		Node attributes = windowsPrefNode.getFirstChild();
		while (attributes != null) {
			if (attributes.getNodeName().equalsIgnoreCase("ntformsheader")) {
				processNTHeader(ev, attributes);
			} else if (attributes.getNodeName()
					.equalsIgnoreCase("windowheader")) {
				Node attributes2 = attributes.getFirstChild();
				while (attributes2 != null) {
					if (attributes2.getNodeName().equalsIgnoreCase("img")) {
						String path = attributes2.getAttributes()
								.getNamedItem("src").getNodeValue();
						ev.setHeader(path);
					} else if (attributes2.getNodeName().equalsIgnoreCase(
							"bgcolor")) {
						processBGColor(ev, attributes2);
					}
					attributes2 = attributes2.getNextSibling();
				}
			}
			attributes = attributes.getNextSibling();
		}
	}

	private static void processNTHeader(EIELValues ev, Node ntNode) {
		Node attributes = ntNode.getFirstChild();
		while (attributes != null) {
			if (attributes.getNodeName().equalsIgnoreCase("img")) {
				String path = attributes.getAttributes().getNamedItem("src")
						.getNodeValue();
				ev.setHeaderNT(path);
			}
			attributes = attributes.getNextSibling();
		}
	}

	private static void processBGColor(EIELValues ev, Node bgColorNode) {
		Node attributes = bgColorNode.getFirstChild();
		String red = null, green = null, blue = null;
		boolean foundRed = false, foundGreen = false, foundBlue = false;
		while (attributes != null) {
			if (attributes.getNodeName().equalsIgnoreCase("red")) {
				red = attributes.getFirstChild().getNodeValue();
				foundRed = true;
			}
			if (attributes.getNodeName().equalsIgnoreCase("green")) {
				green = attributes.getFirstChild().getNodeValue();
				foundGreen = true;
			}
			if (attributes.getNodeName().equalsIgnoreCase("blue")) {
				blue = attributes.getFirstChild().getNodeValue();
				foundBlue = true;
			}
			attributes = attributes.getNextSibling();
		}
		if (foundRed && foundGreen && foundBlue) {
			try {
				int iRed = Integer.parseInt(red);
				int iGreen = Integer.parseInt(green);
				int iBlue = Integer.parseInt(blue);
				ev.setHeaderColor(iRed, iGreen, iBlue);
			} catch (NumberFormatException e) {
				// nothing to do, no color is the default option
			}
		}
	}

}
