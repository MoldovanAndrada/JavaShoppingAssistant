/**
 * <p>
 *     Clasa MagazinPrioritate
 * </p>
 */
package Domain;

import java.util.Objects;

public class MagazinPrioritate implements Comparable{
    private String nume;
    private int prioritate;

    public MagazinPrioritate(String nume, int prioritate){
        this.nume=nume;
        this.prioritate=prioritate;
    }

    public int getPrioritate(){return this.prioritate;}
    public String getNume() {return this.nume;}

    @Override
    public int compareTo(Object o){
        MagazinPrioritate that = (MagazinPrioritate) o;
        if(this.getPrioritate()<that.getPrioritate())
            return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagazinPrioritate that = (MagazinPrioritate) o;
        return prioritate == that.prioritate &&
                Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prioritate);
    }

    @Override
    public String toString() {
        return "Domain.MagazinPrioritate{" +
                "nume='" + nume + '\'' +
                ", prioritate=" + prioritate +
                '}';
    }
}
