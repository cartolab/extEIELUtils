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

import com.iver.cit.gvsig.project.documents.view.gui.View;

public class MapView {

	private String map;
	private View view;
	private List<String> municipios;

	public MapView(String map, View view, List<String> municipios) {
		this.map = map;
		this.view = view;
		this.municipios = municipios;
	}

	public String getMap() {
		return map;
	}

	public View getView() {
		return view;
	}

	public List<String> getMunicipios() {
		return municipios;
	}

}
