/**
 * <p>
 *     Clasa ProdusMagazinPret
 * </p>
 */
package Domain;

public class ProdusMagazinPret {
    private String nume;
    private String magazin;
    private double pret;

    public ProdusMagazinPret(String n, String m, double pret){
        this.nume=n;
        this.magazin=m;
        this.pret=pret;
    }

    public double getPret() {
        return pret;
    }

    public String getNume() {
        return this.nume;
    }

    @Override
    public String toString() {
        return "Produsul" +
                " " + nume  +
                " a fost achizitionat din magazinul " + magazin +
                " la pretul: " + pret;
    }
}
