package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
//import javax.xml.namespace.QName;
//import javax.xml.stream.XMLEventReader;
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamConstants;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.StartElement;
//import javax.xml.stream.events.XMLEvent;
//import java.util.Collection;
//import java.util.Map;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.StatusTextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;

public class AutoQHandler extends AbstractHandler {

	private IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		
		IEditorPart	part = ((IWorkbenchPage) PlatformUI.getWorkbench()
	            .getActiveWorkbenchWindow().getActivePage()).getActiveEditor();
		
//		IEditorPart editor = ((IWorkbenchPage) PlatformUI.getWorkbench()
//	            .getActiveWorkbenchWindow().getActivePage()).getActiveEditor();
//
	    IEditorInput input = (IEditorInput) part.getEditorInput();     // accessing code from eclipse editor
	    System.out.print(input.getAdapter(IEditorInput.class));
		if (part instanceof ITextEditor) {
			ITextEditor editor = (ITextEditor) part;
			IDocumentProvider provider = editor.getDocumentProvider();
			IDocument document = provider.getDocument(editor.getEditorInput());

			MessageDialog.openInformation(window.getShell(), "StackOverFlow", document.get());

			

		}else {System.out.print("nope it's false");}
		//MessageDialog.openInformation(window.getShell(), "StackOverFlow", "Brah");
		return null;
	}
	
}

//	public String getCurrentEditorContent() {
//
//	}
