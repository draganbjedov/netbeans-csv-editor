package draganbjedov.netbeans.csv.dataobject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.windows.OnShowing;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/*
 * Installer.java
 *
 * Created on Nov 29, 2013, 10:22:18 PM
 *
 * @author Dragan Bjedov
 */
/**
 * Class for adding listeners when csv file opens
 *
 */
@OnShowing
public class Installer implements Runnable {

	@Override
	public void run() {
		WindowManager.getDefault().getRegistry().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(TopComponent.Registry.PROP_OPENED)) {
					HashSet<TopComponent> newHashSet = (HashSet<TopComponent>) evt.getNewValue();
					HashSet<TopComponent> oldHashSet = (HashSet<TopComponent>) evt.getOldValue();
					for (TopComponent topComponent : newHashSet) {
						if (!oldHashSet.contains(topComponent)) {
							DataObject dObj = topComponent.getLookup().lookup(DataObject.class);
							if (dObj != null) {
								FileObject currentFile = dObj.getPrimaryFile();
								if (currentFile != null && currentFile.getMIMEType().equals("text/csv")) {
									CSVDataObject csvDataObject = (CSVDataObject) dObj;
									csvDataObject.initDocument();
//									currentFile.addFileChangeListener(new FileChangeAdapter() {
//										@Override
//										public void fileChanged(FileEvent fe) {
//											StatusDisplayer.getDefault().setStatusText("Hurray! "
//													+ "Saved " + fe.getFile().getNameExt(), 1);
//										}
//									});
									StatusDisplayer.getDefault().setStatusText("Hurray! " + "Opened " + currentFile.getNameExt(), 1);
								}
							}
						}
					}
				}
			}
		});
	}

}
