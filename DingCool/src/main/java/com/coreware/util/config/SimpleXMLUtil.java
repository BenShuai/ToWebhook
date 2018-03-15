package com.coreware.util.config;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.springframework.util.ResourceUtils;
import org.xml.sax.InputSource;

@SuppressWarnings("unchecked")
final public class SimpleXMLUtil {
	private SimpleXMLUtil() {
	}

	public static Document file2Doc(String xmlPath) {
		return file2Doc(xmlPath, false);
	}

	public static Document file2Doc(String xmlPath, boolean validate) {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(ResourceUtils.getFile(xmlPath.replace("%20"," ")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static Document file2Doc(InputStream stream, boolean validate) {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static Document file2Doc(InputStream stream) {
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = null;
		try {
			doc = builder.build(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static String doc2String(final Document doc) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(baos);
			Format format = Format.getCompactFormat();
			format.setEncoding("UTF-8");
			XMLOutputter xmlop = new XMLOutputter();
			xmlop.setFormat(format);
			xmlop.output(doc, pw);

			return baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document string2Doc(final String xml) {
		Document doc = null;
		try {
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			doc = (new SAXBuilder()).build(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void updateXML(String xmlFullPath, Document doc) {
		try {
			Format format = Format.getCompactFormat();
			format.setEncoding("utf-8");
			format.setIndent("	");
			XMLOutputter serializer = new XMLOutputter(format);

			FileOutputStream fos = new FileOutputStream(
					ResourceUtils.getFile(xmlFullPath.replace("%20", " ")));

			serializer.output(doc, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Element> getElements(Element root, String path) {
		try {
			return XPath.selectNodes(root, path);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Element getElement(Element root, String path) {
		try {
			return (Element) XPath.selectSingleNode(root, path);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}
}