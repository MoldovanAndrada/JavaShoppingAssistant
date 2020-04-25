/**
 * <p>
 *     Clasa MagazinPret
 * </p>
 */
package Domain;

public class MagazinPret {
    private String numeMagazin;
    private double pret;

    public MagazinPret(String numeMagazin, double pret){
        this.numeMagazin=numeMagazin;
        this.pret=pret;
    }

    public String getNumeMagazin() {
        return numeMagazin;
    }

    public double getPret() {
        return pret;
    }

}
