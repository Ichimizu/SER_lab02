package ch.heigvd.ser.labo2;


import java.io.*;

/**
 * Classe permettant de créer et d'ecrire un fichier PGN
 *
 * @author Benoit Julien, Catel Torres A. Gabriel
 */
public class JDomWriter {

    /**
     * permet de créer et d'ecrire un fichier PGN
     * @param partie la partie à écrire sur le fichier PGN
     * @param compteurPartie Le numéro de la partie pour diférencier les fichier
     */
    public void writePgnFile(String partie, int compteurPartie) {
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("PGN-File-" + compteurPartie + ".pgn"));
            pw.print(partie);
            pw.close();

        } catch (IOException io){
            System.out.println(io.getMessage());
        }

    }

}
