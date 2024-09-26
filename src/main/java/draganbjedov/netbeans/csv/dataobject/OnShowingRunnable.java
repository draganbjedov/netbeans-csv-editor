package draganbjedov.netbeans.csv.dataobject;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.windows.OnShowing;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/*
 * OnShowingRunnable.java
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
public class OnShowingRunnable implements Runnable {

    @Override
    public void run() {
        WindowManager.getDefault().getRegistry().addPropertyChangeListener((var evt) -> {
            if (evt.getPropertyName().equals(TopComponent.Registry.PROP_OPENED)) {
                HashSet<TopComponent> newHashSet = (HashSet<TopComponent>) evt.getNewValue();
                HashSet<TopComponent> oldHashSet = (HashSet<TopComponent>) evt.getOldValue();
                for (TopComponent topComponent : newHashSet) {
                    if (!oldHashSet.contains(topComponent)) {
                        DataObject dataObject = topComponent.getLookup().lookup(DataObject.class);
                        if (dataObject != null) {
                            FileObject currentFile = dataObject.getPrimaryFile();
                            if (currentFile != null && currentFile.getMIMEType().equals("text/csv")) {
                                if (dataObject instanceof CSVDataObject csvDataObject) {
                                    csvDataObject.initDocument();
                                } else {
                                    try {
                                        dataObject.setValid(false);
                                    } catch (PropertyVetoException ex) {
                                        Exceptions.printStackTrace(ex);
                                    }

                                    topComponent.close();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

}
