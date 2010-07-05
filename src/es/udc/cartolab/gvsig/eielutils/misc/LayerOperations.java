package es.udc.cartolab.gvsig.eielutils.misc;

import java.awt.geom.Rectangle2D;

import com.hardcode.gdbms.driver.exceptions.InitializeDriverException;
import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.hardcode.gdbms.engine.values.Value;
import com.hardcode.gdbms.engine.values.ValueWriter;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.fmap.layers.ReadableVectorial;
import com.iver.cit.gvsig.fmap.layers.SelectableDataSource;
import com.iver.cit.gvsig.fmap.layers.layerOperations.AlphanumericData;

public class LayerOperations {

	public static void zoomToNucleo (FLayer layer, String codMun, String codEnt, String codNuc) {


		if (layer instanceof AlphanumericData) {
			int pos = -1;

			SelectableDataSource recordset;
			try {
				recordset = ((FLyrVect) layer).getRecordset();


				int munIdx = recordset.getFieldIndexByName(EIELValues.FIELD_COD_MUN);
				int entIdx = recordset.getFieldIndexByName(EIELValues.FIELD_COD_ENT);
				int nucIdx = recordset.getFieldIndexByName(EIELValues.FIELD_COD_POB);

				if (munIdx > -1 && entIdx > -1 && nucIdx > -1) {
					for (int i=0; i<recordset.getRowCount(); i++) {
						Value val = recordset.getFieldValue(i, munIdx);
						String cod = val.getStringValue(ValueWriter.internalValueWriter);
						cod = cod.replaceAll("'", "");
						if (cod.equals(codMun)) {
							val = recordset.getFieldValue(i, entIdx);
							cod = val.getStringValue(ValueWriter.internalValueWriter);
							cod = cod.replaceAll("'", "");
							if (cod.equals(codEnt)) {
								val = recordset.getFieldValue(i, nucIdx);
								cod = val.getStringValue(ValueWriter.internalValueWriter);
								cod = cod.replaceAll("'", "");
								if (cod.equals(codNuc)) {
									pos = i;
									break;
								}
							}
						}
					}
				}
			} catch (ReadDriverException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			if (pos > -1) {
				zoom((FLyrVect) layer, pos);
			}
		}
	}

	public static void zoomToMunicipio (FLayer layer, String codMun) {


		if (layer instanceof AlphanumericData) {
			int pos = -1;

			SelectableDataSource recordset;
			try {
				recordset = ((FLyrVect) layer).getRecordset();


				int munIdx = recordset.getFieldIndexByName(EIELValues.FIELD_COD_MUN);

				if (munIdx > -1) {
					for (int i=0; i<recordset.getRowCount(); i++) {
						Value val = recordset.getFieldValue(i, munIdx);
						String cod = val.getStringValue(ValueWriter.internalValueWriter);
						cod = cod.replaceAll("'", "");
						if (cod.equals(codMun)) {
							pos = i;
							break;
						}
					}
				}
			} catch (ReadDriverException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			if (pos > -1) {
				zoom((FLyrVect) layer, pos);
			}
		}
	}

	private static void zoom(FLyrVect layer, int pos) {

		Rectangle2D rectangle = null;
		//		int pos = Long.valueOf(currentPosition).intValue();
		if (layer instanceof AlphanumericData) {
			//TODO gvSIG comment: Esta comprobacion se hacia con Selectable
			try {
				IGeometry g;
				ReadableVectorial source = (layer).getSource();
				source.start();
				g = source.getShape(pos);
				source.stop();

				/* fix to avoid zoom problems when layer and view
				 * projections aren't the same. */
				if (layer.getCoordTrans() != null) {
					g.reProject(layer.getCoordTrans());
				}

				rectangle = g.getBounds2D();

				if (rectangle.getWidth() < 200){
					rectangle.setFrameFromCenter(rectangle.getCenterX(),
							rectangle.getCenterY(),
							rectangle.getCenterX()+100,
							rectangle.getCenterY()+100);
				}

				if (rectangle != null) {
					layer.getMapContext().getViewPort().setExtent(rectangle);
				}

			} catch (InitializeDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
