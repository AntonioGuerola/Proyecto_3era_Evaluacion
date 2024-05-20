package org.example.test;

import org.example.model.connection.ConnectionProperties;
import org.example.model.utils.XMLManager;

/**
 * A test class to save connection properties to an XML file.
 */
public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost", "3306", "G3D", "root", "root");
        XMLManager.writeXML(c, "connection.xml");
    }
}
