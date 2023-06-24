package br.com.uniriotec.sagui.svgio;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.XMLConstants;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@Slf4j
public class SvgIo {
	private Document doc;
	/**
	 * Throws:
	 * NullPointerException     - if file argument is null
	 * IOException              - If any IO errors occur.
	 * SAXException             - If any parse errors occur.
	 * IllegalArgumentException - When f is null
	 */
	public SvgIo(String fileName){
		try {
			File file = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

			//dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			//dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			dbFactory.setExpandEntityReferences(false);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			document.getDocumentElement().normalize();
			this.doc = document;
		}catch(NullPointerException | IOException | SAXException | ParserConfigurationException npe ) {
			npe.printStackTrace();
		}
	}
	
	public NodeList getNodeListByTag(String name) {
		return doc.getElementsByTagName(name);
	}
	
	public Element getElementPerTagLine(String name, int index) {
		return (Element) getNodeListByTag(name).item(index);
	}
	
	public void setNewElementStyle(String name, int index, String colorNew) {
		Element pathElement = getElementPerTagLine(name, index);
		pathElement.setAttribute(SvgUsedElements.STYLE.getElement() , colorNew);
	}
	
	public void setNewElementText(String name, int index, String textNew) {
		Element pathElement = getElementPerTagLine(name, index);
		pathElement.getFirstChild().setTextContent(textNew);
	}

	@Override
	public String toString() {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();

		//try {
			//tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		//} catch (TransformerConfigurationException e) {
			//log.info("Imposssivel abriri o svg");
		//}
		//tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");

		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException tfce) {
			tfce.printStackTrace();
			log.info("Não foi possível configurar interface transform para o documento SVG passado");
		} catch (TransformerException te) {
			te.printStackTrace();
			log.info("Não foi possível transformar o documento sgv através da interface transformation");
		}
		return writer.toString();
	}
	
}
