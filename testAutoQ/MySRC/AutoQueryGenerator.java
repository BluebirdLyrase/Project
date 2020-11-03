//package io.github.vcuswimlab.stackintheflow.controller;
//
//import com.intellij.openapi.editor.*;
//import io.github.vcuswimlab.stackintheflow.controller.info.match.StringMatchUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Chase on 1/7/2017.
 */
public class AutoQueryGenerator {

    private static final int MAX_QUERY_TERMS = 4;

    public static void main(String args[]) {

      Map<String, Integer> termsFreqMap = new HashMap<>();

              String currentLine = "prog.java:10: error: ')' expected";
                Arrays.stream(currentLine.toLowerCase().split("\\b"))
                        .forEach(t -> termsFreqMap.put(t, 2 + termsFreqMap.getOrDefault(t, 0)));
                
            Combiner combiner1 = new Combiner() {
				@Override
				public double generateCumulativeScore(String term) {
					// TODO Auto-generated method stub
					return 0;
				}
            };
            
      Map<String, Double> scores =
                termsFreqMap.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> combiner1.generateCumulativeScore(e.getKey())));

      //  Collects the MAX_QUERY_TERMS most frequent elements in the list
        List<String> top = scores
                .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(MAX_QUERY_TERMS)
               .map(Map.Entry::getKey).collect(Collectors.toList());

       System.out.print( top.stream().collect(Collectors.joining(" ")));
    }
}
