package ch.heigvd.ser.labo2;

import org.jdom2.*; // Librairie à utiliser !
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

class Main {

    private static final String xmlFilePath = "tournois_fse.xml";

    public static void main(String ... args) {

        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(xmlFilePath);
        //List listPartie;

        try {
            Document document = saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();

            Element Tournois = rootElement.getChild("tournois");
            List listTournoi = Tournois.getChildren("tournoi");
            for (int iTournoi = 0; iTournoi < listTournoi.size(); ++iTournoi) {

                Element tournoi = (Element) listTournoi.get(iTournoi);

                Element parties = (Element) tournoi.getChild("parties");
                List listPartie = parties.getChildren("partie");
                for (int iPartie = 0; iPartie < listPartie.size(); ++iPartie) {
                    Element partie = (Element) listPartie.get(iPartie);
                    //System.out.println("option : ");
                    Element coups = (Element) partie.getChild("coups");
                    List listCoup = coups.getChildren("coup");
                    for (int iCoup = 0; iCoup < listCoup.size(); ++iCoup) {
                        Element coup = (Element) listCoup.get(iCoup);

                        Element deplacement;
                        if((deplacement = (Element) coup.getChild("deplacement")) == null){
                            deplacement = (Element) coup.getChild("roque");
                            
                        } else {

                        }

                        ++iCoup;
                        coup = (Element) listCoup.get(iCoup);


                        System.out.println("option : " + iCoup);
                    }


                }


               /* List listOption = menu.getChildren("option");
                for (int iOption = 0; iOption < listOption.size(); ++iOption) {
                    Element option = (Element) listOption.get(iOption);
                    System.out.println("option : "  + option.getText());
                }*/
            }
        }

        catch (IOException io) {
            System.out.println(io.getMessage());
        }

        catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

    }

}
