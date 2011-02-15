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

package es.udc.cartolab.gvsig.eielutils;

import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.gui.ChangePassDialog;

public class ChangePassExtension extends
		es.udc.cartolab.gvsig.users.ChangePassExtension {

	public void execute(String actionCommand) {

		ChangePassDialog dialog = new ChangePassDialog(EIELValues.getHeader(), EIELValues.HEADER_COLOR);
		dialog.openWindow();
	}

}
