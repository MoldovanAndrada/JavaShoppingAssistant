/**
 *<p>Clasa client. Parametrii: String nume, String prenume, Date data, ArrayList<MagazinPrioritate> PP, String[] lista</></p>
 */

package Domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Client {
    private String nume;
    private String prenume;
    private Date data;
    private ArrayList<MagazinPrioritate> pp;
    private String[] lista;

    public Client(String n, String p, Date d, ArrayList<MagazinPrioritate> pp, String[] lista){
        this.nume=n;
        this.prenume=p;
        this.data=d;
        this.pp=pp;
        this.lista=lista;
    }

    public Date getData(){return this.data;}
    public String[] getLista(){return this.lista;}
    public ArrayList<MagazinPrioritate> getMagazinePrioritati(){return this.pp;}
    public String getNume(){return this.nume;}
    public String getPrenume() {return this.prenume;}

    @Override
    public String toString() {
        String s="";
        for(MagazinPrioritate mp : this.pp)
            s+=mp.toString();
        return "Domain.Client{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", data=" + data +
                ", pp=" + s +
                ", lista=" + Arrays.toString(lista) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(nume, client.nume) &&
                Objects.equals(prenume, client.prenume) &&
                Objects.equals(data, client.data) &&
                Objects.equals(pp, client.pp) &&
                Arrays.equals(lista, client.lista);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nume, prenume, data, pp);
        result = 31 * result + Arrays.hashCode(lista);
        return result;
    }
}
