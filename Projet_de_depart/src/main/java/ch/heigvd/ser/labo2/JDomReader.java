package ch.heigvd.ser.labo2;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JDomReader {

    public LinkedList<String> readXmlFile(File xmlFile) {
        LinkedList<String> toutesLesParties = new LinkedList<String>();

        SAXBuilder saxBuilder = new SAXBuilder();

        try {
            // Mise en place du document a lire
            Document document = saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();

            // Recuperation de l'element tournois
            Element Tournois = rootElement.getChild("tournois");

            // Recuperation de tous les tournoi dans tournois
            List listTournoi = Tournois.getChildren("tournoi");

            // Boucle qui itere sur chaque tournoi de la liste de tournoi
            for (int iTournoi = 0; iTournoi < listTournoi.size(); ++iTournoi) {

                Element tournoi = (Element) listTournoi.get(iTournoi);

                // Recuperation de l'element parties
                Element parties = (Element) tournoi.getChild("parties");

                // Recuperation de tous les tournoi dans tournois
                List listPartie = parties.getChildren("partie");

                // Boucle qui itere sur chaque partie de la liste de partie
                for (int iPartie = 0; iPartie < listPartie.size(); ++iPartie) {

                    Element partie = (Element) listPartie.get(iPartie);

                    // Recuperation de l'element coups d'une partie
                    Element coups = (Element) partie.getChild("coups");

                    // Recuperation de tous les coup dans coups
                    List listCoup = coups.getChildren("coup");

                    // Variable pour avoir le coup de chaque joueur dans un tour
                    int compteurDeCoup = 1;

                    String partieStr = "";

                    // Boucle qui itere sur chaque coup de la liste de coup
                    for (int iCoup = 0; iCoup < listCoup.size(); ++iCoup) {

                        // Ecriture du numero du coup
                        partieStr += (compteurDeCoup++ + " ");

                        // Recuperation du coup blanc
                        Element coup = (Element) listCoup.get(iCoup);

                        // Transformation du coup en String et ajout au coup final
                        partieStr += (parseReadCoup(coup) + " ");

                        iCoup++;

                        // Si le joueur noir a encore joue un coup
                        if(iCoup < listCoup.size()) {
                            coup = (Element) listCoup.get(iCoup);
                            partieStr += parseReadCoup(coup);
                        }
                        partieStr += "\n";
                    }
                    toutesLesParties.add(partieStr);
                }
            }
        }

        catch (IOException io) {
            System.out.println(io.getMessage());
        }

        catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

        return toutesLesParties;
    }


    private String parseReadCoup(Element coup){
        String result = "";
        Element deplacement;

        // Cas d'un deplacement normal
        if((deplacement = (Element) coup.getChild("deplacement")) != null){
            result += choixPiece(deplacement.getAttributeValue("piece"));

            // S'il y a une case de depart renseignee
            if(deplacement.getAttributeValue("case_depart") != null) {
                result += deplacement.getAttributeValue("case_depart");
            }

            // S'il y a une elimination
            if(deplacement.getAttributeValue("elimination") != null) {
                result += "x";
            }

            // Ecriture de la case d'arrivee
            result += deplacement.getAttributeValue("case_arrivee");

            if(deplacement.getAttributeValue("promotion") != null) {
                result += "=" + choixPiece(deplacement.getAttributeValue("promotion"));
            }
        }

        // Cas d'un roque
        else if((deplacement = (Element) coup.getChild("roque")) != null) {
            //Petit roque
            if(deplacement.getAttributeValue("type").equals("petit_roque")){
                result += "O-O";
            } else {
                result += "O-O-O";
            }
        }

        // Cas des coups speciaux
        if(coup.getAttributeValue("coup_special") != null) {
            if (coup.getAttributeValue("coup_special").equals("echec")) {
                result += "+";
            } else if (coup.getAttributeValue("coup_special").equals("mat")) {
                result += "#";
            }
        }
        return result;
    }

    private String choixPiece(String s){
        switch(s) {
            case "Roi" : return "K";
            case "Dame" : return "Q";
            case "Tour" : return "R";
            case "Cavalier" : return "N";
            case "Fou" : return "B";
            case "Pion" : return "";
        }
        return null;
    }
}
