package WorkingCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImportExtractor {
	
	 public static void  main(String arg[]) {

	        Map<String, Integer> termsFreqMap = new HashMap<>();
	            String editorText = "\n" + 
	            		"import java.util.HashSet;\n" + 
	            		"import java.util.Set;\n" + 
	            		"import java.util.regex.Matcher;\n" + 
	            		"import java.util.regex.Pattern;\n" + 
	            		"\n" + 
	            		"public class StringMatchUtils {\n" + 
	            		"\n" + 
	            		"    public static final Pattern IMPORT_STATEMENT_PATTERN = Pattern.compile(\"import\\\\s+([\\\\w\\\\.]*?(\\\\w+));\");\n" + 
	            		"    public static final Pattern TERM_PATTERN = Pattern.compile(\"\\\\b([A-Z]\\\\w+)\\\\b\");\n" + 
	            		"\n" + 
	            		"    public static String removeComments(String s) {\n" + 
	            		"        return s.replaceAll(\"(//.*?\\\\n)|(/\\\\*(.|\\\\n)*?\\\\*/)\", \"\");\n" + 
	            		"    }\n" + 
	            		"\n" + 
	            		"    public static Set<String> extractImports(String s) {\n" + 
	            		"        Matcher matcher = IMPORT_STATEMENT_PATTERN.matcher(s);\n" + 
	            		"\n" + 
	            		"        Set<String> imports = new HashSet<>();\n" + 
	            		"\n" + 
	            		"        while (matcher.find()) {\n" + 
	            		"            imports.add(matcher.group(1));\n" + 
	            		"        }\n" + 
	            		"\n" + 
	            		"        return imports;\n" + 
	            		"    }\n" + 
	            		"}\n" + 
	            		"";

	            Set<String> imports = StringMatchUtils.extractImports(editorText);

	            imports.forEach(i -> Arrays.stream(i.toLowerCase().split("\\."))
	                    .forEach(t -> termsFreqMap.put(t, 1 + termsFreqMap.getOrDefault(t, 0))));
	            
	            System.out.println(imports);

	            String[] lines = editorText.split("\\n");
	 }

}
