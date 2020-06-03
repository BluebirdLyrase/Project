package prototype1.NewViews;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import javax.inject.Inject;
import javax.swing.text.LabelView;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;

/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class NewViews extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "prototype1.NewViews.NewViews";
	
    
	@Override	
	public void createPartControl(Composite parent) {
    	Label[] collection = new Label[5];
    	Label[] sap = new Label[5];
    	String[] content = {"Title","I fuck up my codeplz help","code code code","d","d"};
    	GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		parent.setLayout(gridLayout);
    	for(int i=0 ;i<5;i++) {
    		collection[i] = new Label(parent,SWT.NONE);
    		collection[i].setText(content[i]);
    		Color col2 = new Color(null, 150,100,0+(i*20));
    		collection[i].setBackground(col2);
    		collection[i].setSize(100,30);
    		collection[i].setLocation(50, 50);
    	}
    	
    	
    	
    	
    	
    	
    	}
        
	

	@Override
	public void setFocus() {
	}
}