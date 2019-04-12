package ch.heigvd.ser.labo2;

import java.util.List;

public class Partie {
    private List<String> coups;

    public List<String> getCoups() {
        return coups;
    }

    public void setCoups(List<String> coups) {
        this.coups = coups;
    }

    public void addCoup(String coup) {
        coups.add(coup);
    }
}
