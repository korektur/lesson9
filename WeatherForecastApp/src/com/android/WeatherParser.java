package com.android;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Руслан
 * Date: 21.11.13
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class WeatherParser {

    public static ArrayList<Map<String, Object>> Parse(InputStream inputStream,String tagName,  String[] keys) {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        try {
            DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element elem = (Element) nodeList.item(i);
                    Element[] elements = new Element[keys.length];
                    for(int j = 0; j < keys.length; j++){
                        elements[j] = (Element) elem.getElementsByTagName(keys[j]).item(0);
                    }
                    HashMap<String, Object> parsedNode = new HashMap<String, Object>();
                    for(int j = 0; j < keys.length; j++){
                        parsedNode.put(keys[j], elements[j].getFirstChild().getNodeValue());
                    }
                    data.add(parsedNode);
                }
            }
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        } catch (SAXException e) {
        }
        ;
        return data;
    }
}