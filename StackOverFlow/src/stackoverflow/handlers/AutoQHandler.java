package stackoverflow.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import stackoverflow.stat.*;

public class AutoQHandler extends AbstractHandler {
    private static final int MAX_QUERY_TERMS = 4;
	private IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Map<String, Integer> termsFreqMap = new HashMap<>();
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		String editorText;
		IEditorPart part = ((IWorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage())
				.getActiveEditor();

		IEditorInput input = (IEditorInput) part.getEditorInput(); // accessing code from eclipse editor
		System.out.print(input.getAdapter(IEditorInput.class));
		if (part instanceof ITextEditor) {
			ITextEditor editor = (ITextEditor) part;
			IDocumentProvider provider = editor.getDocumentProvider();
			IDocument document = provider.getDocument(editor.getEditorInput());
			editorText = document.get();
			Set<String> imports = StringMatchUtils.extractImports(editorText);

			imports.forEach(i -> Arrays.stream(i.toLowerCase().split("\\."))
					.forEach(t -> termsFreqMap.put(t, 1 + termsFreqMap.getOrDefault(t, 0))));

			System.out.println(imports);

			String[] lines = editorText.split("\\n");
			//System.out.print(lines.length);
			Combiner combiner1 = new Combiner() {
				@Override
				public double generateCumulativeScore(String term) {
					// TODO Auto-generated method stub
					return 0;
				}
			};
			/*
			 * Map<String, Double> scores = termsFreqMap.entrySet().stream()
			 * .collect(Collectors.toMap(Map.Entry::getKey, e ->
			 * combiner1.generateCumulativeScore(e.getKey())));
			 * 
			 * // Collects the MAX_QUERY_TERMS most frequent elements in the list
			 * List<String> top =
			 * scores.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.
			 * comparingByValue()))
			 * .limit(MAX_QUERY_TERMS).map(Map.Entry::getKey).collect(Collectors.toList());
			 */

			

            String currentLine = document.get();
            Arrays.stream(currentLine.toLowerCase().split("\\b"))
                    .forEach(t -> termsFreqMap.put(t, 2 + termsFreqMap.getOrDefault(t, 0)));
            
            Map<String, Double> scores =
                    termsFreqMap.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> combiner1.generateCumulativeScore(e.getKey())));

            //Collects the MAX_QUERY_TERMS most frequent elements in the list
            List<String> top = scores
                    .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(MAX_QUERY_TERMS)
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            
            System.out.print(top.stream().collect(Collectors.joining(" ")));
			
			
			MessageDialog.openInformation(window.getShell(), "StackOverFlow", top.stream().collect(Collectors.joining(" ")));

		} else {
			System.out.print("nope it's false");
		}

		return null;
	}

}

//	public String getCurrentEditorContent() {
//
//	}
