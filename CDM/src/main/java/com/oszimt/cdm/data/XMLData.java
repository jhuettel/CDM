package com.oszimt.cdm.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

/**
 * Data layer that gets and modifies the data stored in the XML file.
 * @author milo.gruettner
 */
public class XMLData implements IData {
    private String filepath = "xml/db.xml";

    private final String ID = "id";

    private final String INDENTAMOUNT = "{http://xml.apache.org/xslt}indent-amount";

    private final String NAME = "name";

    private final String CITY = "city";

    private final String DISTRICT = "district";

    private Document doc;

    private Document getXMLFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(filepath);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean saveXMLFile() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(INDENTAMOUNT, "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Constructs a new instance
     */
    public XMLData() {

    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#addCity(com.oszimt.cdm.model.City)
     */
    @Override
    public boolean addCity(City city) {
        String cityID = city.getID();
        doc = getXMLFile();
        String cityName = city.getName();
        List<String> ids = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        Node cities = doc.getFirstChild();
        NodeList allCities = doc.getElementsByTagName(CITY);
        for (int i = 0; i < allCities.getLength(); i++) {
            if (allCities.item(i) instanceof Element == false)
                continue;
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            ids.add(nodeID.getTextContent());
            Node nodeName = attribute.getNamedItem(NAME);
            names.add(nodeName.getTextContent());
        }
        Element newCity = doc.createElement(CITY);
        if (names.contains(cityName)) {
            return false;
        } else {
            newCity.setAttribute(NAME, cityName);
        }
        if (cityID == null) {
            String newID;
            NodeList citiesLast = cities.getChildNodes();
            System.out.println("The length of citiesLast is " + citiesLast.getLength());
            Node currentCity = citiesLast.item(citiesLast.getLength() - 2);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            System.out.println("Current ID is: " + nodeID.getTextContent());
            cityID = nodeID.getTextContent();
            int temp = Integer.parseInt(cityID);
            temp++;
            newID = Integer.toString(temp);
            newCity.setAttribute(ID, newID);
        } else if (cityID != null && ids.contains(cityID)) {
            return false;
        } else {
            newCity.setAttribute(ID, cityID);
        }
        cities.appendChild(newCity);
        return saveXMLFile();
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#addDistrict(com.oszimt.cdm.model.City,
     *      com.oszimt.cdm.model.District)
     */
    @Override
    public boolean addDistrict(City city, District district) {
        String cityID = city.getID();
        String districtID = district.getID();
        doc = getXMLFile();
        String districtName = district.getName();
        List<String> ids = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        NodeList allCities = doc.getElementsByTagName(CITY);
        for (int i = 0; i < allCities.getLength(); i++) {
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            NodeList currentCitiesDistricts = currentCity.getChildNodes();
            Node nodeID = attribute.getNamedItem(ID);
            if (nodeID.getTextContent().equals(cityID)) {
                Element newDistrict = doc.createElement(DISTRICT);
                for (int j = 0; j < currentCitiesDistricts.getLength(); j++) {
                    if (currentCitiesDistricts.item(j) instanceof Element == false)
                        continue;
                    Node currentDistrict = currentCitiesDistricts.item(j);
                    attribute = currentDistrict.getAttributes();
                    Node crrntID = attribute.getNamedItem(ID);
                    ids.add(crrntID.getTextContent());
                    Node crrntName = attribute.getNamedItem(NAME);
                    names.add(crrntName.getTextContent());
                }
                if (names.contains(districtName)) {
                    return false;
                } else {
                    newDistrict.setAttribute(NAME, districtName);
                }
                if (districtID == null && ids.isEmpty()) {
                    attribute = currentCity.getAttributes();
                    nodeID = attribute.getNamedItem(ID);
                    newDistrict.setAttribute(ID, nodeID.getTextContent() + "01");
                } else if (districtID == null && !ids.isEmpty()) {
                    String newID;
                    NodeList districtsLast = currentCity.getChildNodes();
                    Node currentDistrict = districtsLast.item(districtsLast.getLength() - 2);
                    attribute = currentDistrict.getAttributes();
                    nodeID = attribute.getNamedItem(ID);
                    districtID = nodeID.getTextContent();
                    int temp = Integer.parseInt(districtID);
                    temp++;
                    newID = Integer.toString(temp);
                    newDistrict.setAttribute(ID, newID);
                } else if (districtID != null && ids.contains(districtID)) {
                    return false;
                } else {
                    newDistrict.setAttribute(ID, districtID);
                }
                currentCity.appendChild(newDistrict);
                return saveXMLFile();
            }
        }
        return false;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#editCity(com.oszimt.cdm.model.City)
     */
    @Override
    public boolean editCity(City city) {
        String cityID = city.getID();
        doc = getXMLFile();
        String cityName = city.getName();
        doc = getXMLFile();
        NodeList allCities = doc.getElementsByTagName(CITY);
        for (int i = 0; i < allCities.getLength(); i++) {
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            if (nodeID.getTextContent().equals(cityID)) {
                Node nodeName = attribute.getNamedItem(NAME);
                nodeName.setTextContent(cityName);
                return saveXMLFile();
            }
        }
        return false;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#editDistrict(com.oszimt.cdm.model.District)
     */
    @Override
    public boolean editDistrict(District district) {
        String districtID = district.getID();
        doc = getXMLFile();
        String districtName = district.getName();
        NodeList allDistricts = doc.getElementsByTagName(DISTRICT);
        for (int i = 0; i < allDistricts.getLength(); i++) {
            Node currentDistrict = allDistricts.item(i);
            NamedNodeMap attribute = currentDistrict.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            if (nodeID.getTextContent().equals(districtID)) {
                Node nodeName = attribute.getNamedItem(NAME);
                nodeName.setTextContent(districtName);
                return saveXMLFile();
            }
        }
        return false;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#deleteCity(com.oszimt.cdm.model.City)
     */
    @Override
    public boolean deleteCity(City city) {
        String cityID = city.getID();
        doc = getXMLFile();
        NodeList allCities = doc.getElementsByTagName(CITY);
        Node cities = doc.getFirstChild();
        for (int i = 0; i < allCities.getLength(); i++) {
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            if (nodeID.getTextContent().equals(cityID)) {
                cities.removeChild(currentCity);
                return saveXMLFile();
            }
        }
        return false;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#deleteDistrict(com.oszimt.cdm.model.District)
     */
    @Override
    public boolean deleteDistrict(District district) {
        String districtID = district.getID();
        doc = getXMLFile();
        NodeList allDistricts = doc.getElementsByTagName(DISTRICT);
        for (int i = 0; i < allDistricts.getLength(); i++) {
            Node currentDistrict = allDistricts.item(i);
            NamedNodeMap attribute = currentDistrict.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            if (nodeID.getTextContent().equals(districtID)) {
                Node currentCity = currentDistrict.getParentNode();
                currentCity.removeChild(currentDistrict);
                return saveXMLFile();
            }
        }
        return false;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#getAllCities()
     */
    @Override
    public List<City> getAllCities() {
        doc = getXMLFile();
        List<District> districts = new ArrayList<District>();
        List<City> foundCities = new ArrayList<City>();
        NodeList allCities = doc.getElementsByTagName(CITY);
        for (int i = 0; i < allCities.getLength(); i++) {
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            Node nodeName = attribute.getNamedItem(NAME);
            NodeList currentCitiesDistricts = currentCity.getChildNodes();
            String cityID = nodeID.getTextContent();
            String cityName = nodeName.getTextContent();
            for (int j = 0; j < currentCitiesDistricts.getLength(); j++) {

                if (currentCitiesDistricts.item(j) instanceof Element == false)
                    continue;

                Node currentDistrict = currentCitiesDistricts.item(j);
                attribute = currentDistrict.getAttributes();
                nodeID = attribute.getNamedItem(ID);
                nodeName = attribute.getNamedItem(NAME);
                String districtID = nodeID.getTextContent();
                String districtName = nodeName.getTextContent();
                districts.add(new District(districtID, districtName));
            }
            foundCities.add(new City(cityID, cityName, districts));
            districts = new ArrayList<District>();
        }
        return foundCities;
    }

    /**
     * 
     * @see com.oszimt.cdm.data.IData#findCitiesByName(java.lang.String)
     */
    @Override
    public List<City> findCitiesByName(String name) {
        doc = getXMLFile();
        List<District> districts = new ArrayList<District>();
        List<City> foundCities = new ArrayList<City>();
        NodeList allCities = doc.getElementsByTagName(CITY);
        for (int i = 0; i < allCities.getLength(); i++) {
            Node currentCity = allCities.item(i);
            NamedNodeMap attribute = currentCity.getAttributes();
            Node nodeID = attribute.getNamedItem(ID);
            Node nodeName = attribute.getNamedItem(NAME);
            NodeList currentCitiesDistricts = currentCity.getChildNodes();
            if (nodeName.getTextContent().equals(name)) {
                String cityID = nodeID.getTextContent();
                String cityName = nodeName.getTextContent();
                for (int j = 0; j < currentCitiesDistricts.getLength(); j++) {

                    if (currentCitiesDistricts.item(j) instanceof Element == false)
                        continue;

                    Node currentDistrict = currentCitiesDistricts.item(j);
                    attribute = currentDistrict.getAttributes();
                    nodeID = attribute.getNamedItem(ID);
                    nodeName = attribute.getNamedItem(NAME);
                    String districtID = nodeID.getTextContent();
                    String districtName = nodeName.getTextContent();
                    districts.add(new District(districtID, districtName));
                }
                foundCities.add(new City(cityID, cityName, districts));
                districts = new ArrayList<District>();
            }
        }
        return foundCities;
    }
}
