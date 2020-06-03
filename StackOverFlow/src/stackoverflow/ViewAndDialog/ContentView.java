package stackoverflow.ViewAndDialog;


import org.eclipse.ui.part.*;
//import org.eclipse.jface.viewers.*;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.jface.action.*;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.ui.*;
//import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;



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

public class ContentView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.ContentView";
	 @Override
	    public void createPartControl(Composite parent) {
		 

	        StyledText sText = new StyledText(parent, SWT.CENTER);
	    	sText.setText("0123456789 ABCDEFGHIJKLM NOPQRSTUVWXYZ");
	    	// make 0123456789 appear bold
	    	StyleRange style1 = new StyleRange();
	    	style1.start = 0;
	    	style1.length = 10;
	    	style1.fontStyle = SWT.BOLD;
	    	sText.setStyleRange(style1);

	        Label label = new Label(parent,SWT.CENTER);
	        Color col = new Color(null, 150,100,100);
	        parent.setLayout (new FillLayout ());
	        
	    	new Label (parent, SWT.SEPARATOR | SWT.VERTICAL);
	    	parent.setSize (200, 200);
	    	label.setText("LABEL");
	    	
	    	label.setBackground(col);
	    	
	    	for(int i=0 ;i<5;i++) {
	    		
	    	}
	    	
	    	}
	 		

	         
	    
	 

	    @Override
	    public void setFocus() {
	    }
	
}
