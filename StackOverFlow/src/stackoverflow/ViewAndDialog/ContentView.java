package stackoverflow.ViewAndDialog;
import org.eclipse.swt.dnd.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.*;
import org.json.JSONException;
import stackoverflow.APIConnecter.AllContent;
import stackoverflow.APIConnecter.AllContentStub;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ContentView extends ViewPart {

	@Inject
	IWorkbench workbench;

	Composite parent;
	public static final String ID = "stackoverflow.ViewAndDialog.ContentView";
	
	public void setContent(String id) {
		
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(sc, SWT.BORDER | SWT.WRAP  | SWT.MULTI);
		
		sc.setContent(composite);
		
		composite.setLayout(new GridLayout(2, false));
//		AllContentStub content;
		AllContent content;
		try {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;

			composite.setLayout(gridLayout);
			content = new AllContent(id);
//			content = new AllContentStub("no");

			Question q = content.getAllConetent();
			System.out.println(q.getBody());
			System.out.println(q.getTitle());

			Text qTitle = new Text(composite, SWT.MULTI | SWT.READ_ONLY);

			Text qBody = new Text(composite, SWT.MULTI | SWT.READ_ONLY);
			
			Text selectedText = new Text(composite, SWT.MULTI | SWT.READ_ONLY);

			qTitle.setText(q.getTitle());
			 
			qTitle.setFont(new Font(null, "Times New Roman", 15, SWT.BOLD | SWT.ITALIC));
			Label separator = new Label(composite, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
			
			
			//Text selectedText =new Text(composite, SWT.MULTI | SWT.READ_ONLY);
			
			
//			 + content1.substring(selection.y, content1.length());
			//selectedText.setText(qTitle.getSelectionText());
			
			
			
			int operations = DND.DROP_MOVE | DND.DROP_COPY;
			DragSource source = new DragSource(qTitle, operations);

			Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
			source.setTransfer(types);
			source.addDragListener(new DragSourceListener() {
					   public void dragStart(DragSourceEvent event) {
					      // Only start the drag if there is actually text in the
					      // label - this text will be what is dropped on the target.
						   
						   
							String content1 = qTitle.getText();
							Point selection = qTitle.getSelection();
							
							content1 = content1.substring(selection.x, selection.y);
							
						//	System.out.println("content1= "+content1);	
							selectedText.setText(content1);
						//	System.out.println("selectedText = "+selectedText.getText());
						  
					      if (selectedText.getText().length() == 0) {
					          event.doit = false;
					      }
					   }
					   public void dragSetData(DragSourceEvent event) {
					     // Provide the data of the requested type.
					     if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					    	 
					          event.data = selectedText.getText();
					     }
					   }
					   public void dragFinished(DragSourceEvent event) {
					     // If a move operation has been performed, remove the data
					     // from the source

					     }
					   }
					);
			
			
			
			separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			qBody.setText(q.getBody());
			qBody.setFont(new Font(null, "Times New Roman", 12, SWT.WRAP| SWT.READ_ONLY));
			qBody.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ));
			Text commentHeader = new Text(composite, SWT.BOLD|SWT.READ_ONLY);
			commentHeader.setText("Comment is here");

			Color commentColor = new Color(null, 197, 197, 197);

			if (q.isHaveComment()) {

				String[] comment = q.getComment();
				System.out.println(comment.length);
				Text[] lComment = new Text[comment.length];
				for (int i = 0; i < comment.length; i++) {
					System.out.println(comment[i]);
					lComment[i] = new Text(composite, SWT.MULTI | SWT.READ_ONLY);
					lComment[i].setText(comment[i]);
					lComment[i].setBackground(commentColor);
				}

			}

			if (q.isHaveAnswer()) {

				Answer[] answers = q.getAnswer();
				Text[] lAnswers = new Text[answers.length];
				Text[] lAnswersHeader = new Text[answers.length];

				for (int i = 0; i < answers.length; i++) {
					System.out.println("Loop i : " + i);
					System.out.println(answers[i].getBody());
					System.out.println(answers[i].getScore());
					lAnswersHeader[i] = new Text(composite,SWT.MULTI | SWT.READ_ONLY);
					lAnswersHeader[i].setText("Answer index " + i);
					lAnswers[i] = new Text(composite,SWT.MULTI | SWT.READ_ONLY);
					lAnswers[i].setText(answers[i].getBody());

					if (answers[i].isHaveComment()) {

						String[] aComment = answers[i].getComment();
						Text[] lAComment = new Text[answers[i].getComment().length];

						for (int j = 0; j < answers[i].getComment().length; j++) {
							System.out.println("Loop j : " + j);
							System.out.println(aComment[j]);
							lAComment[j] = new Text(composite, SWT.NONE| SWT.MULTI | SWT.READ_ONLY);
							lAComment[j].setText(aComment[j]);
							lAComment[j].setBackground(commentColor);
						}
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
	}

	@Override
	public void setFocus() {
	}
	

}
