/**
 * <p>
 * Clasa Promotie. Parametrii: String numemagazin, Date startData, Date endData, ArrayList<Produs> produse
 *</p>
 */
package Domain;

import java.util.ArrayList;
import java.util.Date;

public class Promotie {
    private String numeMagazin;
    private Date startData;
    private Date endData;
    private ArrayList<Produs> produse;

    public Promotie(String numeMagazin, Date sd, Date ed, ArrayList<Produs> produse){
        this.numeMagazin=numeMagazin;
        this.startData=sd;
        this.endData=ed;
        this.produse=produse;
    }

    @Override
    public String toString() {
        return "Domain.Promotie{" +
                "startData='" + startData + '\'' +
                ", endData='" + endData + '\'' +
                ", produse=" + produse +
                '}';
    }

    public Date getStartData() {
        return startData;
    }

    public Date getEndData(){return endData;}

    public ArrayList<Produs> getProduse() {
        return produse;
    }
}
