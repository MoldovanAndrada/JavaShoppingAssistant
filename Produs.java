/**
 * <p>
 *     Clasa Produs
 * </p>
 */
package Domain;

public class Produs {
    private double pret;
    private String nume;

    public Produs(double pret, String nume){
        this.pret=pret;
        this.nume=nume;
    }

    public String getNume(){return this.nume;}
    public double getPret(){return this.pret;}

    @Override
    public String toString() {
        return "Domain.Produs{" +
                "pret=" + pret +
                ", nume='" + nume + '\'' +
                '}';
    }
}
