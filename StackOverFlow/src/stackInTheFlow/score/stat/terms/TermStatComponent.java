package stackInTheFlow.score.stat.terms;

import com.ctc.wstx.stax.WstxInputFactory;
//import com.intellij.openapi.components.ApplicationComponent;
//import com.intellij.openapi.editor.Editor;
//import io.github.vcuswimlab.stackintheflow.controller.AutoQueryGenerator;
import stackInTheFlow.score.stat.Stat;
import stackInTheFlow.score.*;
//import stackInTheFlow.score.combiner.SumCombiner;
//import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class TermStatComponent {

    public static final String COMPONENT_ID = "StackInTheFlow.TermStatComponent";
    private long termCount;
    private long docCount;
    private Map<String, Stat> termStatMap;
    private Collection<Scorer> scorers;


    public Collection<Scorer> getScorer() {
        termStatMap = new HashMap<>();
        loadTermStats();
         scorers = Arrays.asList(new ScqScorer(this),
                new VarScorer(this),
                new IctfScorer(this),
                new IdfScorer(this));
         
         return scorers;
    }


    public Optional<Stat> getTermStat(String term) {
        return Optional.ofNullable(termStatMap.get(term));
    }

    public long getTermCount() {
        return termCount;
    }

    public long getDocCount() {
        return docCount;
    }

    private void loadTermStats() {
    	
        XMLInputFactory inputFactory = new WstxInputFactory();
		String path = "\\resources\\Terms.xml";
		Bundle bundle = Platform.getBundle("StackOverFlow");
		String url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(path), null).toString();
//		System.out.println("url:"+url);
		
  
        try {
        	InputStream input = new URL(url).openStream();
//            XMLEventReader eventReader = inputFactory.createXMLEventReader(
//                    this.getClass().getClassLoader().getResourceAsStream("Terms.xml"));
            XMLEventReader eventReader = inputFactory.createXMLEventReader(input);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "Collection":
                            termCount = Long.parseLong(startElement.getAttributeByName(QName.valueOf("termCount")).getValue());
                            docCount = Long.parseLong(startElement.getAttributeByName(QName.valueOf("docCount")).getValue());
                            break;
                        case "Term":
                            String name = startElement.getAttributeByName(QName.valueOf("name")).getValue();
                            long ctf = Long.parseLong(startElement.getAttributeByName(QName.valueOf("ctf")).getValue());
                            long df = Long.parseLong(startElement.getAttributeByName(QName.valueOf("ctf")).getValue());
                            double idf = Double.parseDouble(startElement.getAttributeByName(QName.valueOf("idf")).getValue());
                            double ictf = Double.parseDouble(startElement.getAttributeByName(QName.valueOf("ictf")).getValue());

                            termStatMap.put(name, new Stat(ctf, df, idf, ictf));
                            break;
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}
