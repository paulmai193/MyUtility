package logia.utility.readfile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class XmlUtil.
 * 
 * @author Paul Mai
 */
public class XmlUtil {

	/** The logger. */
	private final Logger LOGGER = Logger.getLogger(getClass());

	/** The xml file path. */
	String               xmlFilePath;

	/**
	 * Instantiates a new xml ultility.
	 */
	public XmlUtil() {

	}

	/**
	 * Instantiates a new xml reader.
	 * 
	 * @param xmlFilePath the xml file path
	 */
	public XmlUtil(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}

	/**
	 * Gets the attribute.
	 * 
	 * @param node the node
	 * @param attName the att name
	 * @return the attribute. Null if the given node is Null or not have any attribute
	 */
	public String getAttribute(Node node, String attName) {
		String attValue;
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			attValue = element.getAttribute(attName);
		}
		else {
			attValue = null;
		}
		return attValue;
	}

	/**
	 * Gets the list node in specific element by tag name.
	 * 
	 * @param tagName the tag name
	 * @param element the element
	 * @return the list node. NULL if cannot file any node given by tag name.
	 */
	public NodeList getListNode(String tagName, Element element) {
		if (element == null) {
			return null;
		}
		else {
			NodeList nodeList = element.getElementsByTagName(tagName);
			return nodeList;
		}
	}

	/**
	 * Gets the root element.
	 * 
	 * @return the root. NULL if document given by file path not xml format.
	 */
	public Element getRoot() {
		Element root = null;
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(this.xmlFilePath);
			doc.getDocumentElement().normalize();
			root = doc.getDocumentElement();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return root;
	}

	/**
	 * Gets the value.
	 * 
	 * @param node the node
	 * @return the value. Null if the given node is Null or not have any content.
	 */
	public String getValue(Node node) {
		if (node == null) {
			return null;
		}
		else {
			return node.getTextContent();
		}

	}
}
