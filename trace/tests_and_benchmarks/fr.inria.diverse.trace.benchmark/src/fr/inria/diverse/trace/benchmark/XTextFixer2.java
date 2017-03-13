package fr.inria.diverse.trace.benchmark;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XTextFixer2 {

	public static void main(String[] args) {
		try {
			String path = "/home/dorian/workspace/ModelDebugging/trace/tests_and_benchmarks/fr.inria.diverse.trace.benchmark/models/hireV4.xmi";
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
				dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("edges");
			for (int i = 0; i < nList.getLength(); i++) {
				((Element) nList.item(i)).setAttribute("name", "e"+i);
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(path));
			Source input = new DOMSource(doc);

			transformer.transform(input, output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
