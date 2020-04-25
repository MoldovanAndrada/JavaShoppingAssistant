/**
 * Clasa are rolul de a stoca si a gestiona 2 sau mai multe magazine
 *
 * @author Andrada
 * @since 2020-04-23
 */
package Repositories;

import Domain.Magazin;
import IOfiles.*;

import java.io.File;
import java.util.ArrayList;

public class MagazineRepo {
    private ArrayList<Magazin> magazine = new ArrayList<Magazin>();

    public void adaugaMagazin(Magazin m){
        magazine.add(m);
    }

    public ArrayList<Magazin> getMagazine(){return this.magazine;}

    /**
     *  functia extrage toate fisierele care reprezinta magazine din directorul de resurse, dupa care le citeste si retine
     *  informatiile corespunzatoare fiecarui magazin in Repository-ul de magazine
     */
    public void populeaza(){
        File dir = new File("src/resources");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (isMagazin(child)) {
                    CitireMagazin cm = new CitireMagazin(child);
                    Magazin m = cm.readLines();
                    adaugaMagazin(m);
                }
            }
        }
    }

    /**
     * @param f: fisier
     * @return true - daca fisierul dat reprezinta un fisier cu informatiile unui magazin
     *         fals- altfel
     */
    public boolean isMagazin(File f){
        String[] s = f.toString().split("\\.");
        String[] ss = s[0].split("_");
        return(ss[0].substring(14,21).equals("magazin"));
    }

}
