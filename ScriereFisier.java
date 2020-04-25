/**
 * Clasa este folosita pentru ca mentine o separare intre prelucrarea datelor si scrierea lor in fisier
 * Singurul rol al clasei este sa deschida un fisier si sa scrie un String dat in el
 *
 * @author Andrada
 * @since 2020-04-23
 */
package IOfiles;

import java.io.*;

public class ScriereFisier {
    private File file;

    /**
     * constructor
     * @param f - fisierul in care se va scrie
     */
    public ScriereFisier(File f) {this.file=f;}

    /**
     * Functia de scriere in fisier
     *
     * @exception IOException - in cazul in care fisierul pentru scriere nu poate fi descris, un mesaj se afiseaza in consola pentru
     *                          notificarea utilizatorului
     * @param s - sirul de caractere care va fi scris
     */
    public void scrieInFisier(String s){
        try {
            FileWriter writer = new FileWriter(file.toString());
            writer.write(s);
            writer.close();

        }catch(IOException e){System.out.println("An error occurred while trying to open the file.");}
    }
}
