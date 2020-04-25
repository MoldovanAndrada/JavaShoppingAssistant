/**
 * Clasa responsabila de citirea din fisier a tuturor informatiilor corespunzatoare unui client, precum si de stocarea acestora
 *
 * @author @Andrada
 * @since 2020-04-23
 */
package IOfiles;

import Domain.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CitireClient {
    private File filename;

    /**
     * constructor
     * @param f - fisierul din care se vor citi informatiile corespunzatoare clientului
     */
    public CitireClient(File f) {
        this.filename = f;
    }

    /**
     * Se citesc, pe rand, toate liniile din fisier, verificandu-se pentru fiecare daca descrie o lista de magazine si prioritati,
     * o data calendaristica sau o lista de cumparaturi
     *
     * @exception IOException - if the file could not be open, se afiseaza un mesaj pentru instiintarea utilizatorului
     * @return c - obiect din clasa 'Client'
     */
    public Client readLines() {
        ArrayList<MagazinPrioritate> mps = new ArrayList<MagazinPrioritate>();
        String[] lista=new String[0];
        Date d = getDateFromFilename();
        String nume = getNameFromFilename();
        String prenume = getPrenumeFromFilename();
        MagazinPrioritate mp;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line=br.readLine(); // procesam prima linie
            lista = line.split(";");

            while ((line = br.readLine()) != null) {
                String[] mag = line.split(":");
                mp=new MagazinPrioritate(mag[0], Integer.parseInt(mag[1]));
                mps.add(mp);
            }
        } catch (IOException e) {System.out.println("An error occurred while trying to open the file.");
        }

        Client c = new Client(nume,prenume,d,mps,lista);
        return c;
    }

    /**
     * Data corespunzatoare comenzii unui client se extrage direct din numele fisierului asociat informatiilor despre un client si se
     * returneaza pentru a putea fi folosita ulterior
     * @return (Date) d
     */
    public Date getDateFromFilename(){
        Date d;
        String[] s = this.filename.toString().split("_");
        String[] ss = s[2].split("\\.");
        d = convertStringDate(ss[0]);
        return d;
    }

    /**
     * Numele unui client se extrage direct din numele unui fisier si se returneaza sub forma unui 'String' pentru a putea fi
     * folosit ulterior
     * @return (String) s - numele clientului caruia ii corespund informatiile dintr-un fisier
     */
    public String getNameFromFilename(){
        String[] s = this.filename.toString().split("\\.");
        String[] ss = s[0].split("_");
        String[] sss = ss[1].split(" ");
        return sss[0];
    }

    /**
     * Prenumele unui client se extrage direct din numele unui fisier si se returneaza sub forma unui 'String' pentru a putea fi
     * folosit ulterior
     * @return (String) s - prenumele clientului caruia ii corespund informatiile dintr-un fisier
     */
    public String getPrenumeFromFilename(){
        String[] s = this.filename.toString().split("\\.");
        String[] ss = s[0].split("_");
        String[] sss = ss[1].split(" ");
        return sss[1];
    }

    /**
     * Pentru a usura operatiile ulterioare in care se foloseste data calendaristica, ea va fi transformata din format de tip String,
     * in format de tip 'Date'
     * @param s - forma initiala a unei date calendaristice: "yyyy-mm-dd"
     * @return d - aceeasi data calendaristica, in format Date
     */
    public Date convertStringDate(String s) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        Date d=new Date();
        try {
            d = formater.parse(s);
        } catch (ParseException e) {
        }
        return d;
    }
}
