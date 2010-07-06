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
