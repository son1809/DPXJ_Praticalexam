package org.aptech.t2311e;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Patient> list = new ArrayList<>();

    public static void main(String[] args) {
        inputData();

        writeDataToFileXML("patients.xml");
        writeDataToFileJSON("patients.json");

        readDataFromFileXML("patients.xml");
        readDataFromFileJSON("patients.json");
    }

    public static void inputData() {
        list.add(new Patient(1, "Nguyen Van A", 60, 1.7f, 'A', true, LocalDate.of(2000,1,1)));
        list.add(new Patient(2, "Tran Thi B", 50, 1.6f, 'B', false, LocalDate.of(1999,5,10)));
        list.add(new Patient(3, "Le Van C", 70, 1.75f, 'O', true, LocalDate.of(2001,3,15)));
    }


    public static void writeDataToFileXML(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Patients");
            doc.appendChild(root);

            for (Patient p : list) {
                Element patient = doc.createElement("Patient");

                patient.appendChild(createNode(doc, "Id", String.valueOf(p.getId())));
                patient.appendChild(createNode(doc, "Name", p.getName()));
                patient.appendChild(createNode(doc, "Weight", String.valueOf(p.getWeight())));
                patient.appendChild(createNode(doc, "Height", String.valueOf(p.getHeight())));
                patient.appendChild(createNode(doc, "BloodType", String.valueOf(p.getBloodType())));
                patient.appendChild(createNode(doc, "Gender", String.valueOf(p.isGender())));
                patient.appendChild(createNode(doc, "BirthDate", p.getBirthDate().toString()));

                root.appendChild(patient);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));

            System.out.println("Ghi XML thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Element createNode(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }


    public static void writeDataToFileJSON(String filename) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            mapper.writeValue(new File(filename), list);
            System.out.println("Ghi JSON thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void readDataFromFileXML(String filename) {
        try {
            File file = new File(filename);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodes = doc.getElementsByTagName("Patient");

            System.out.println("\n--- Đọc XML ---");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);

                Patient p = new Patient(
                        Integer.parseInt(getTag(e, "Id")),
                        getTag(e, "Name"),
                        Integer.parseInt(getTag(e, "Weight")),
                        Float.parseFloat(getTag(e, "Height")),
                        getTag(e, "BloodType").charAt(0),
                        Boolean.parseBoolean(getTag(e, "Gender")),
                        LocalDate.parse(getTag(e, "BirthDate"))
                );

                list.add(p);

                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTag(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }


    public static void readDataFromFileJSON(String filename) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, Patient.class);

            List<Patient> data = mapper.readValue(new File(filename), listType);

            list = data;

            System.out.println("\n--- Đọc JSON ---");

            for (Patient p : data) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}