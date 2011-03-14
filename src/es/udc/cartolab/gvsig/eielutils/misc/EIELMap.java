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

import java.util.List;

import com.iver.cit.gvsig.project.documents.view.gui.BaseView;

import es.udc.cartolab.gvsig.elle.utils.ELLEMap;
import es.udc.cartolab.gvsig.elle.utils.MapDAO;

public class EIELMap extends ELLEMap {

	public EIELMap(String name, BaseView view) {
		super(name, view);
	}

	private List<String> municipios;

	public List<String> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<String> municipios) {
		this.municipios = municipios;
	}

	public boolean reloadNeeded(List<String> munCodes) {
		boolean reload = false;
		if (municipios != null) {
			if (munCodes != null) {
				for (String mun : munCodes) {
					if (!municipios.contains(mun)) {
						reload = true;
						break;
					}
				}
			} else {
				// there were constants, but now there aren't
				reload = true;
			}
		}
		return reload;
	}

	public MapDAO getMapDAO() {
		return EIELMapDAO.getInstance();
	}

}
