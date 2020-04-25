/**
 * <p>
 *     Clasa ComandaClient
 * </p>
 */
package Domain;

import java.util.ArrayList;

public class ComandaClient implements Comparable{
    ArrayList<ProdusMagazinPret> pmp = new ArrayList<ProdusMagazinPret>();
    double total;
    String numeClient;

    public ComandaClient(ArrayList<ProdusMagazinPret> pmp, double total, String numeClient){
        this.pmp=pmp;
        this.total=total;
        this.numeClient=numeClient;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<ProdusMagazinPret> getPmp() {
        return pmp;
    }

    @Override
    public int compareTo(Object o){
        ComandaClient that = (ComandaClient) o;
        if(this.getTotal()>that.getTotal())
            return -1;
        return 1;
    }

    @Override
    public String toString() {
        String s = "Comanda clientului " + this.numeClient + ": \n";
        for(ProdusMagazinPret p : this.pmp){
            if(p.getPret()!=-1)
                s+=p.toString()+"\n";
            else
                s+="Produsul " + p.getNume() + " nu a putut fi achizitionat." + "\n";
        }
        s+="Total: " + String.format( "%.2f", this.total );
        return s;
    }
}
