package ch.heigvd.ser.labo2;

import java.io.File;
import java.util.LinkedList;

/**
 * programme permettant de récupérer un fichier xml d'une saison d'echec et de fournir en sortie autant de fichier
 * qu'il y a eu de parties au totale en notation PGN
 *
 * @author Benoit Julien, Catel Torres A. Gabriel
 */
class Main {

    private static final String xmlFilePath = "tournois_fse.xml";

    public static void main(String ... args) {
        JDomReader jDomReader = new JDomReader();
        JDomWriter jdomWriter = new JDomWriter();

        File xmlFile = new File(xmlFilePath);

        LinkedList<String> partiesEchec = jDomReader.readXmlFile(xmlFile);

        for (int i = 1; i <= partiesEchec.size(); i++){
            jdomWriter.writePgnFile(partiesEchec.get(i - 1), i);
        }
    }
}
