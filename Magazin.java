/**
 * <p>Clasa Magazin</p>
 */
package Domain;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Magazin {
    private boolean duminica;
    private String nume;
    private ArrayList<Promotie> promotii;
    private ArrayList<Produs> produsePretIntreg;

    public Magazin(Boolean duminica, String nume, ArrayList<Promotie> promotii, ArrayList<Produs> produse){
        this.duminica=duminica;
        this.nume=nume;
        this.promotii=promotii;
        this.produsePretIntreg=produse;
    }

    @Override
    public String toString() {
        return "Magazin{" +
                "duminica=" + duminica +
                ", nume='" + nume + '\'' +
                ", promotii=" + promotii +
                ", produsePretIntreg=" + produsePretIntreg +
                '}';
    }

    public boolean isDuminica() {
        return duminica;
    }

    public boolean getDuminica() {return this.duminica;}

    public String getNume() {
        return nume;
    }

    public ArrayList<Promotie> getPromotii() {
        return promotii;
    }

    public ArrayList<Produs> getProdusePretIntreg() {
        return produsePretIntreg;
    }

}
