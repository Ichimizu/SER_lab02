package ch.heigvd.ser.labo2;

import org.jdom2.*; // Librairie à utiliser !
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

class Main {

    private static final String xmlFilePath = "tournois_fse.xml";

    public static void main(String ... args) {
        JDomReader jDomReader = new JDomReader();

        File xmlFile = new File(xmlFilePath);

        LinkedList<String> partiesEchec = jDomReader.readXmlFile(xmlFile);

        // Montre la bonne recuperation des parties
        for(String partie : partiesEchec) {
            System.out.println(partie);
        }
    }
}
