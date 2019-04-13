package ch.heigvd.ser.labo2;

import ch.heigvd.ser.labo2.coups.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe permettant de lire un fichier xml d'echec avec JDOM2
 *
 * @author Benoit Julien, Catel Torres A. Gabriel
 */
public class JDomReader {

    /**
     * permet de lire un fichier xml d'echec avec JDOM2
     * @param xmlFile un fichier xml d'echec
     * @return une liste de parties d'echec (chaque parties contient tous les coups de celle-ci)
     */
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
                Element parties = tournoi.getChild("parties");

                // Recuperation de tous les tournoi dans tournois
                List listPartie = parties.getChildren("partie");

                // Boucle qui itere sur chaque partie de la liste de partie
                for (int iPartie = 0; iPartie < listPartie.size(); ++iPartie) {

                    Element partie = (Element) listPartie.get(iPartie);

                    // Recuperation de l'element coups d'une partie
                    Element coups = partie.getChild("coups");

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


    /**
     * Permet de parser le fichier xml et de récupérer le coup en notation PGN
     * @param coup le coup a transformer en notation PGN
     * @return le coup en notation PGN
     */
    private String parseReadCoup(Element coup){
        String result = "";
        Element deplacement;
        Coup unCoup = null;

        try {
            // Cas d'un deplacement normal
            if ((deplacement = coup.getChild("deplacement")) != null) {
                TypePiece piece = TypePiece.valueOf(deplacement.getAttributeValue("piece"));
                Case depart = null;
                CoupSpecial coupSpecial = null;
                TypePiece pieceElimine = null;
                TypePiece piecePromu = null;

                // Ecriture de la case d'arrivee
                Case arrivee = new Case(deplacement.getAttributeValue("case_arrivee").charAt(0),
                        Character.getNumericValue(deplacement.getAttributeValue("case_arrivee").charAt(1)));

                // Cas des coups speciaux
                if (coup.getAttribute("coup_special") != null) {
                    coupSpecial = CoupSpecial.valueOf(coup.getAttributeValue("coup_special").toUpperCase());
                }

                // S'il y a une elimination
                if (deplacement.getAttribute("elimination") != null) {
                    pieceElimine = TypePiece.valueOf(deplacement.getAttributeValue("elimination"));
                }

                // S'il y a une case de depart renseignee
                if (deplacement.getAttribute("case_depart") != null) {
                    depart = new Case(
                            coup.getChild("deplacement").getAttribute("case_depart").getValue().charAt(0),
                            Character.getNumericValue(deplacement.getAttributeValue("case_depart").charAt(1))
                    );
                }

                //S'il y a une promotion
                if (deplacement.getAttribute("promotion") != null) {
                    piecePromu = TypePiece.valueOf(deplacement.getAttributeValue("promotion"));
                }

                unCoup = new Deplacement(piece, pieceElimine, piecePromu, coupSpecial, depart, arrivee);
            }

            // Cas d'un roque
            else if ((deplacement = coup.getChild("roque")) != null) {
                TypeRoque type = TypeRoque.valueOf(deplacement.getAttributeValue("type").split("_")[0].toUpperCase());
                unCoup = new Roque(null, type);
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        } catch (Exception e) {
        e.printStackTrace();
        }
        return unCoup.notationPGN();
    }
}
