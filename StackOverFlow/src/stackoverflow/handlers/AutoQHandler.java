package stackoverflow.handlers;

import java.io.IOException;
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
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.json.JSONException;

import stackoverflow.APIConnecter.SearchResult;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.ViewAndDialog.SearchResultView;
import stackoverflow.ViewAndDialog.UserInputDialog;
import stackoverflow.stat.*;

public class AutoQHandler extends AbstractHandler {
	private static final int MAX_QUERY_TERMS = 4;
	private IWorkbenchWindow window;
	private String intitle;
	private String order;
	private String sort;
	private String site;
	private String tagged;

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

			// String[] lines = editorText.split("\\n");
			// System.out.print(lines.length);
			Combiner combiner1 = new Combiner() {
				@Override
				public double generateCumulativeScore(String term) {
					// TODO Auto-generated method stub
					return 0;
				}
			};

			String currentLine = document.get();
			Arrays.stream(currentLine.toLowerCase().split("\\b"))
					.forEach(t -> termsFreqMap.put(t, 2 + termsFreqMap.getOrDefault(t, 0)));

			Map<String, Double> scores = termsFreqMap.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> combiner1.generateCumulativeScore(e.getKey())));

			// Collects the MAX_QUERY_TERMS most frequent elements in the list
			List<String> top = scores.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					.limit(MAX_QUERY_TERMS).map(Map.Entry::getKey).collect(Collectors.toList());

			System.out.print(top.stream().collect(Collectors.joining(" ")));
			String editorContent = top.stream().collect(Collectors.joining(" "));

			UserInputDialog dialog = new UserInputDialog(window.getShell());
			dialog.setText(editorContent);
			dialog.create();
			if (dialog.open() == Window.OK) {

				intitle = dialog.getSearchText();
				order = dialog.getOrder();
				sort = dialog.getSort();
				site = dialog.getSite();
				tagged = dialog.getTagsText();
				System.out.println(intitle);
				// filter non english intitle and tagged to prevent bug in saveSearchTextHistory
				// (too large data)
				boolean intitleIsValid = intitle.matches("[\\p{Graph}\\p{Space}]+") || intitle.isEmpty();
				boolean taggedIsValid = tagged.matches("[\\p{Graph}\\p{Space}]+") || tagged.isEmpty();
				boolean isEnglish = intitleIsValid && taggedIsValid;
				if (isEnglish) {
					createSearchResult();
				} else {
					MessageDialog.openError(window.getShell(), "Error", "We currently support only English searching");
				}

			}

		}

		return null;
	}

	private void createSearchResult() {
		try {
			String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
			SearchResult searchResult;
			searchResult = new SearchResult(intitle, 1, 40, order, sort, site, tagged);
			new SearchingWriter().saveSearchTextHistory(intitle, order, sort, site, tagged);

			if (searchResult.haveResult()) {

				String[] titleList = searchResult.getTitleList();
				String[] questionIdList = searchResult.getQuestionIdList();
				String site = searchResult.getSite();

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				window.getActivePage().showView(viewerID);

				IViewPart viewPart = page.findView(viewerID);

				SearchResultView myView = (SearchResultView) viewPart;

				myView.setSearchResult(titleList, questionIdList, site);

			} else {
				MessageDialog.openError(window.getShell(), "Error", "Can not find any result");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error",
					"There is a problem occur on API Connection. please check your internet Connection and try agian or email us your Log folder");
		} catch (IOException | PartInitException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error",
					"There is a problem occur. please email us your Log folder");
		}
	}

}
