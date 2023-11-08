package a1;

public class Gracz {
    private int punkty;
    private String imie;

    public Gracz(String imie) {
        this.imie = imie;
        punkty = 0;
    }
    public Gracz(String imie, int punkty) {
        this.imie = imie;
        this.punkty = punkty;
    }

    public int getPunkty() {
        return punkty;
    }

    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    public String getImie() {
        return imie;
    }

    @Override
    public String toString() {
        if(imie != null)
            return punkty + "\t " + imie;
        return "";
    }

}
