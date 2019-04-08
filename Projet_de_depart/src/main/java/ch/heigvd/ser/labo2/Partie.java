package ch.heigvd.ser.labo2;

import java.util.List;

public class Partie {
    private String numTour;
    private String coupBlanc;
    private String coupNoir;

    private List<String> coups;

    public String getNumTour() {
        return numTour;
    }

    public void setNumTour(String numTour) {
        this.numTour = numTour;
    }

    public String getCoupBlanc() {
        return coupBlanc;
    }

    public void setCoupBlanc(String coupBlanc) {
        this.coupBlanc = coupBlanc;
    }

    public String getCoupNoir() {
        return coupNoir;
    }

    public void setCoupNoir(String coupNoir) {
        this.coupNoir = coupNoir;
    }

    public List<String> getCoups() {
        return coups;
    }

    public void setCoups(List<String> coups) {
        this.coups = coups;
    }
}
