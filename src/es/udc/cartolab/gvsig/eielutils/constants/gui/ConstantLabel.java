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

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import org.gvsig.gui.beans.controls.IControl;

import com.iver.andami.PluginServices;

public class ConstantLabel extends JLabel implements IControl {

	private ArrayList<ActionListener> actionCommandListeners = new ArrayList<ActionListener>();

	public ConstantLabel() {
		super();
		setText(PluginServices.getText(this, "all_prov"));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}

	public void addActionListener(ActionListener listener) {
		actionCommandListeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) {
		actionCommandListeners.remove(listener);
	}

	public Object setValue(Object value) {
		String text = value.toString() + " ";
		setText(text);
		return text;
	}

	public String getName() {
		return "constants";
	}

	public void setName(String name) {

	}

}
