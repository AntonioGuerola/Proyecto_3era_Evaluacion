package org.example.test;

import org.example.model.connection.ConnectionProperties;
import org.example.model.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(), "connection.xml");
        System.out.println(c);
    }
}
