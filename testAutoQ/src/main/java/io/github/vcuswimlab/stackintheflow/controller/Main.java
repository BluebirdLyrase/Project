package io.github.vcuswimlab.stackintheflow.controller;
import com.intellij.openapi.editor.*;
import io.github.vcuswimlab.stackintheflow.controller.info.match.StringMatchUtils;
import io.github.vcuswimlab.stackintheflow.model.score.combiner.Combiner;

import java.util.*;
import java.util.stream.Collectors;
publi class Main {
    public static void main(String[] args) {
     //   AutoQueryGenerator aqg = new AutoQueryGenerator();
        String selectedText = "out of bound expression";
        Map<String, Integer> termsFreqMap = new HashMap<>();
        if (selectedText == null || selectedText.trim().isEmpty()) {
            String editorText = document.getText();

            Set<String> imports = "bruhhhhhhhhhhhhhhhhhhhh bruhh";

            imports.forEach(i -> Arrays.stream(i.toLowerCase().split("\\."))
                    .forEach(t -> termsFreqMap.put(t, 1 + termsFreqMap.getOrDefault(t, 0))));

            String[] lines = editorText.split("\\n");

            int linePos = logicalPosition.line;
            if (linePos < lines.length) {
                String currentLine = "Brah ? brah";
                Arrays.stream(currentLine.toLowerCase().split("\\b"))
                        .forEach(t -> termsFreqMap.put(t, 2 + termsFreqMap.getOrDefault(t, 0)));
            }
        } else { // The user has highlighted as selection, pull our terms from that
            Arrays.stream(selectedText.toLowerCase().split("\\b"))
                    .forEach(t -> termsFreqMap.put(t, 2 + termsFreqMap.getOrDefault(t, 0)));
        }
        System.out.println(selectedText);
    }
}