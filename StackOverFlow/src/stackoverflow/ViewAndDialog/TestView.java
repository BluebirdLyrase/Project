package stackoverflow.ViewAndDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.APIConnecter.AllContentStub;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

import java.io.IOException;

import javax.inject.Inject;

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

public class TestView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.TestView";

	@Inject
	IWorkbench workbench;

	Composite parent;

	public void setContent(String id) {

	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;

		// Create scroll container.
		final ScrolledComposite scrollContainer = new ScrolledComposite(parent,
				SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
		scrollContainer.setExpandHorizontal(true);
		scrollContainer.setExpandVertical(true);

		// Create scroll area.
		final Composite scrollArea = new Composite(scrollContainer, SWT.MULTI);
		final GridLayout scrollLayout = new GridLayout();
		scrollLayout.marginWidth = 5;
		scrollLayout.marginHeight = 5;
		scrollLayout.verticalSpacing = 0;
		scrollLayout.horizontalSpacing = 0;
		scrollArea.setLayout(scrollLayout);

		GridData paragraphData = new GridData();

		// Create paragraph.
		AllContentStub content;
//		AllContent content;
		try {

//		content = new AllContent(id);
			content = new AllContentStub("no");
			Question q = content.getAllConetent();

			// test
//		Text paragraph = new Text(scrollArea, SWT.READ_ONLY |SWT.WRAP);
//		paragraph.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
//		paragraph.setText(q.getTitle()+"\r\n");
//		paragraph.setLayoutData(paragraphData);

//		Text paragraph2 = new Text(scrollArea, SWT.READ_ONLY |SWT.WRAP);
//		paragraph2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
//		paragraph2.setText(q.getBody());
//		paragraph2.setLayoutData(paragraphData);		

			Text qTitle = new Text(scrollArea, SWT.READ_ONLY | SWT.WRAP);
			qTitle.setText(q.getTitle());
//		qTitle.setLayoutData(paragraphData);

			Text qBody = new Text(scrollArea, SWT.READ_ONLY | SWT.WRAP);
			qBody.setText(q.getBody());
			qBody.setLayoutData(paragraphData);

//		qTitle.setFont(new Font(null, "Times New Roman", 15, SWT.BOLD |SWT.WRAP| SWT.ITALIC));
//		qBody.setFont(new Font(null, "Times New Roman", 12, SWT.WRAP| SWT.READ_ONLY));
//		
//		Label separator = new Label(scrollArea, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
//		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			Color commentColor = new Color(null, 197, 197, 197);

			if (q.isHaveComment()) {

				// Create scroll container.
				GridData paragraphDataComment = new GridData();
				final ScrolledComposite scrollContainerComment = new ScrolledComposite(scrollArea,
						SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
				scrollContainerComment.setExpandHorizontal(true);
				scrollContainerComment.setExpandVertical(true);

				// Create scroll area.
				final Composite scrollAreaComment = new Composite(scrollContainerComment, SWT.MULTI);
				
				final GridLayout scrollLayoutComment = new GridLayout();
				scrollLayout.marginWidth = 5;
				scrollLayout.marginHeight = 5;
				scrollLayout.verticalSpacing = 0;
				scrollLayout.horizontalSpacing = 0;
				scrollArea.setLayout(scrollLayout);

				String[] comment = q.getComment();
				System.out.println(comment.length);
				Text[] lComment = new Text[comment.length];
				for (int i = 0; i < comment.length; i++) {
					System.out.println(comment[i]);
					lComment[i] = new Text(scrollArea, SWT.MULTI | SWT.READ_ONLY);
					lComment[i].setText(comment[i]);
					lComment[i].setBackground(commentColor);
					lComment[i].setLayoutData(paragraphDataComment);
				}
				scrollContainerComment.addListener(SWT.Resize, (event) -> {
					int width = scrollContainerComment.getClientArea().width;
					paragraphDataComment.widthHint = width - scrollLayoutComment.marginWidth; // Add padding to right.
					Point size = scrollAreaComment.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					scrollContainerComment.setMinSize(size);
				});

				scrollContainer.setContent(scrollArea);

			}
//		
//		
//		
//
//		Text commentHeader = new Text(scrollArea, SWT.BOLD|SWT.READ_ONLY);
//		commentHeader.setText("Comment is here");

			// Setup scrolling.
			scrollContainer.addListener(SWT.Resize, (event) -> {
				int width = scrollContainer.getClientArea().width;
				paragraphData.widthHint = width - scrollLayout.marginWidth; // Add padding to right.
				Point size = scrollArea.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrollContainer.setMinSize(size);
			});

			scrollContainer.setContent(scrollArea);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL|SWT.WRAP | SWT.LEFT);
//		Composite composite = new Composite(sc, SWT.BORDER | SWT.WRAP  | SWT.MULTI);
//		sc.setContent(composite);
//
//		composite.setLayout(new GridLayout(2, false));
//		AllContentStub content;
////		AllContent content;
//		try {
//			GridLayout gridLayout = new GridLayout(1, false);
//			gridLayout.marginWidth = 5;
//			gridLayout.marginHeight = 5;
//			gridLayout.verticalSpacing = 0;
//			gridLayout.horizontalSpacing = 0;
//
//			composite.setLayout(gridLayout);
////			content = new AllContent(id);
//			content = new AllContentStub("no");
//
//			Question q = content.getAllConetent();
//			System.out.println(q.getBody());
//			System.out.println(q.getTitle());
//
//			Text qTitle = new Text(composite, SWT.MULTI | SWT.READ_ONLY|SWT.WRAP | SWT.LEFT);
//
//			Text qBody = new Text(composite, SWT.MULTI | SWT.READ_ONLY|SWT.WRAP | SWT.LEFT);
//
//			qTitle.setText(q.getTitle());

//			qTitle.setFont(new Font(null, "Times New Roman", 15, SWT.BOLD | SWT.ITALIC));
//			Label separator = new Label(composite, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
//			separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//			qBody.setText(q.getBody());
//			qBody.setFont(new Font(null, "Times New Roman", 12, SWT.WRAP| SWT.READ_ONLY));
//			qBody.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ));
//			Text commentHeader = new Text(composite, SWT.BOLD|SWT.READ_ONLY);
//			commentHeader.setText("Comment is here");
//
//			Color commentColor = new Color(null, 197, 197, 197);
//
//			if (q.isHaveComment()) {
//
//				String[] comment = q.getComment();
//				System.out.println(comment.length);
//				Text[] lComment = new Text[comment.length];
//				for (int i = 0; i < comment.length; i++) {
//					System.out.println(comment[i]);
//					lComment[i] = new Text(composite, SWT.MULTI | SWT.READ_ONLY);
//					lComment[i].setText(comment[i]);
//					lComment[i].setBackground(commentColor);
//				}
//
//			}
//
//			if (q.isHaveAnswer()) {
//
//				Answer[] answers = q.getAnswer();
//				Text[] lAnswers = new Text[answers.length];
//				Text[] lAnswersHeader = new Text[answers.length];
//
//				for (int i = 0; i < answers.length; i++) {
//					System.out.println("Loop i : " + i);
//					System.out.println(answers[i].getBody());
//					System.out.println(answers[i].getScore());
//					lAnswersHeader[i] = new Text(composite,SWT.MULTI | SWT.READ_ONLY);
//					lAnswersHeader[i].setText("Answer index " + i);
//					lAnswers[i] = new Text(composite,SWT.MULTI | SWT.READ_ONLY);
//					lAnswers[i].setText(answers[i].getBody());
//
//					if (answers[i].isHaveComment()) {
//
//						String[] aComment = answers[i].getComment();
//						Text[] lAComment = new Text[answers[i].getComment().length];
//
//						for (int j = 0; j < answers[i].getComment().length; j++) {
//							System.out.println("Loop j : " + j);
//							System.out.println(aComment[j]);
//							lAComment[j] = new Text(composite, SWT.NONE| SWT.MULTI | SWT.READ_ONLY);
//							lAComment[j].setText(aComment[j]);
//							lAComment[j].setBackground(commentColor);
//						}
//					}
//				}
//
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		sc.setExpandHorizontal(true);
//		sc.setExpandVertical(true);
//		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		parent.pack();

	}

	@Override
	public void setFocus() {

	}
}
