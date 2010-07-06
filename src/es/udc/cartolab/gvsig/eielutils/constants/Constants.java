package es.udc.cartolab.gvsig.eielutils.constants;

import java.util.ArrayList;
import java.util.List;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiFrame.NewStatusBar;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielutils.misc.MapView;

public class Constants {

	private ArrayList<MapView> loadedMaps;

	private static Constants instance = null;

	private List<String> municipios;
	private String munCod;
	private String entCod;
	private String nucCod;
	private boolean constatsSelected = false;

	private Constants() {
		loadedMaps = new ArrayList<MapView>();
	}
	/**
	 * @return user defined constants, or null if they're not defined yet.
	 */
	public static Constants getCurrentConstants() {
		if (instance == null) {
			instance = new Constants();
		}
		return instance;
	}

	public static Constants newConstants(String munCod, String entCod, String nucCod,
			List<String> municipios) {
		if (instance == null) {
			instance = new Constants();
		}
		instance.munCod = munCod;
		instance.entCod = entCod;
		instance.nucCod = nucCod;
		if (entCod==null) {
			instance.entCod="";
		}
		if (nucCod==null) {
			instance.nucCod="";
		}
		instance.municipios = municipios;
		if (!municipios.contains(munCod)) {
			instance.municipios.add(munCod);
		}
		instance.changeStatusBar();
		instance.constatsSelected = true;
		return instance;
	}

	public static Constants newConstants(String munCod, String entCod, String nucCod) {
		ArrayList<String> municipios = new ArrayList<String>();
		municipios.add(munCod);
		return newConstants(munCod, entCod, nucCod, municipios);
	}

	public String getMunCod() {
		return munCod;
	}

	public String getEntCod() {
		return entCod;
	}

	public String getNucCod() {
		return nucCod;
	}

	public List<String> getMunicipios() {
		return municipios;
	}

	private void changeStatusBar() {
		MDIFrame mF = (MDIFrame) PluginServices.getMainFrame();
		NewStatusBar footerStatusBar = mF.getStatusBar();
		String nuc = nucCod;
		if (nucCod==null || nucCod.equals("")) {
			nuc = "-";
		}
		String ent = entCod;
		if (entCod==null || entCod.equals("")) {
			ent = "-";
		}
		String text = PluginServices.getText(this, "status_mun_ent_nuc");
		text = String.format(text, munCod, ent, nuc);
		footerStatusBar.setMessage("constants", text);
	}

	public static void removeConstants() {
		if (instance.constatsSelected) {
			instance.constatsSelected = false;
			MDIFrame mF = (MDIFrame) PluginServices.getMainFrame();
			NewStatusBar footerStatusBar = mF.getStatusBar();
			footerStatusBar.setMessage("constants", PluginServices.getText(null, "all_prov"));
		}
	}

	public void addMap(String map, View view, List<String> municipios) {
		MapView mv = new MapView(map, view, municipios);
		loadedMaps.add(mv);
	}

	public void removeMap(String map, View view) {
		for (MapView mv : loadedMaps) {
			if (mv.getView() == view) {
				if (mv.getMap().equals(map)) {
					loadedMaps.remove(mv);
					break;
				}
			}
		}
	}

	public boolean constantsSelected() {
		return constatsSelected;
	}

	public List<MapView> getLoadedMaps(View view) {
		ArrayList<MapView> mapsInView = new ArrayList<MapView>();
		for (MapView mv : loadedMaps) {
			if (mv.getView() == view) {
				mapsInView.add(mv);
			}
		}
		return mapsInView;
	}

	/**
	 * It tells if the council it's the current one
	 * @param cod
	 * @return
	 */
	public boolean isSelectedCouncil(String cod) {
		if (instance.constantsSelected()) {
			return cod.equals(instance.getMunCod());
		}
		return false;
	}

}
