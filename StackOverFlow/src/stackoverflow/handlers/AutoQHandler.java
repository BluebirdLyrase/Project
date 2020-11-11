package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.text.IDocument;

public class AutoQHandler extends AbstractHandler {

//    public static final String COMPONENT_ID = "StackInTheFlow.TermStatComponent";
//    private long termCount;
//    private long docCount;
//    private Map<String, Stat> termStatMap;
//    private Collection<Scorer> scorers;

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String currentEditorText;
		currentEditorText = getCurrentEditorContent();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"StackOverFlow",
				currentEditorText);
		return null;
		
	}
	
	public String getCurrentEditorContent() {
	    final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
	            .getActiveEditor();
	    if (activeEditor == null)
	        return null;
	    final IDocument doc = (IDocument) activeEditor.getAdapter(IDocument.class);
	    if (doc == null) return null;

	    return doc.get();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
	

	
}
