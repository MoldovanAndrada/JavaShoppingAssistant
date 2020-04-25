/**
 * Clasa responsabila de citirea din fisier a tuturor informatiilor corespunzatoare unui magazin, precum si de stocarea acestora
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

public class CitireMagazin {
    private File filename;

    public CitireMagazin(File filename){this.filename=filename;}

    /**
     *      Se citesc toate liniile dintr-un fisier care contine informatii despre un magazin.
     *      Se verifica la fiecare pas daca linia citita reprezinta: inceputul/finalul unei promotii, inceputul/finalul fisierului, lista de produse
     * nereduse, o data/doua dati calendaristice, etc..
     *
     * @exception IOException - se proceseaza cazul in care fisierul pentru citire nu poate fi deschis, se afiseaza un mesaj pentru instiintarea utilizatorului
     * @exception ArrayIndexOutOfBoundsException - in cazul in care o linie citita nu corespunde niciunui format care poate fi gestionat,
     *  aceasta va fi ignorata si se va citi in continuare pana la finalul fisierului
     * @return (Magazin) m - toate informatiile referitoare la un magazin pe care le contine fisierul, sub forma clasei Magazin, definita in pachetul Domain
     */
    public Magazin readLines() {

        /**urmatoarele variable sunt auxiliare si ne ajuta sa procesam informatiie citite din fisier inspre a le construi in memorie sub forma de obiecte */
        int index=0;
        Boolean duminica=false;
        String currentDate;
        Boolean startOfList=false;
        Produs p;
        Promotie pro;
        Date startData;
        Date endData;
        ArrayList<Produs> produseAux;
        ArrayList<Promotie> promotii=new ArrayList<Promotie>();
        ArrayList<Produs> produseNereduse=new ArrayList<Produs>();



        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(index==0){ // daca linia citita este prima din fisier, stim ca ea numeste disponibilitatea magazinului in ziua de duminica
                    if(line.equals("Inchis"))
                        duminica=false;
                    else
                        duminica=true;
                }

                /**incepem o noua promotie de fiecare data cand depistam o data calendaristica in input: */

                if(startOfList && isIndataFormat(line)) { // we are dealing with the last list: the one of WHOLE PRICES
                    /**
                     * in continuare este nevoie sa stabilim daca se precizeaza una sau 2 date calendaristice si sa le stocam in functie de aceasta informatie */
                    if(isDoubleDate(line)){
                        String s[] = line.split(" ");
                        startData = convertStringDate(s[0]);
                        endData = convertStringDate(s[1]);

                    }
                    else{
                        startData = convertStringDate(line);
                        endData = null;
                    }

                    /** linia pe care ne aflam reprezinta primul produs dintr-o promotie */

                    produseAux=new ArrayList<Produs>();

                    while (!(line = br.readLine()).equals("**********") && isIndataFormat(line)==false && !(line).equals("")){
                            try{
                            p = lineToProdus(line);
                            produseAux.add(p);}catch(ArrayIndexOutOfBoundsException e){}

                    }
                    pro = new Promotie(getNumeMagazin(), startData, endData, produseAux);
                    promotii.add(pro);
                }


                /**
                 *  In continuare ne ocupam de produsele nereduse ale magazinului */
                if(startOfList && !(isIndataFormat(line)) && !(line.equals("") || line.equals("**********"))) { // we are dealing with the last list: the one of WHOLE PRICES

                        p = lineToProdus(line); //first element
                        produseNereduse.add(p);

                        while ((line = br.readLine()) != null){ //all other elements until the end of the file
                        p = lineToProdus(line);
                        produseNereduse.add(p);
                    }
                    break;
                }

                if(line.equals("**********") || line.equals("")) // keeping info for when we process the following line
                    startOfList=true;
                else startOfList=false;



            index++;
            }
        }catch (IOException e){System.out.println("An error occurred while trying to open the file.");}
        Magazin magazin = new Magazin(duminica, getNumeMagazin(), promotii, produseNereduse);
        return magazin;
    }

    /**
     * @param s e un string
     * @return 'true' daca el reprezinta o data de incepere, impreuna cu una de final
     * @return 'false' daca data reprezinta o singura zi
     */
    public boolean isDoubleDate(String s){
        return s.length()==21;
    }

    /**
     * Se verifica daca un string din fisier reprezinta o data calendaristica
     *
     * @param s, un string care ar putea reprezenta data extrasa din fisier
     * @return true daca s reprezinta o data calendaristica
     * @return false daca s nu reprezinta o data calendaristica
     */
    public boolean isIndataFormat(String s){
        if (s.length() == 10 || s.length() == 21) {
            if (s.charAt(7)=='-')
                if (s.charAt(4)=='-')
                    return true;
        }
        return false;
    }

    /**
     * Pentru a lucra ulterior cu produsele listate in fisier, ele vor fi stocate sub forma clasei Produs, definita in pachetul Domain
     *
     * @param s, forma unui produs asa cum este numit de o linie din fisier
     * @return un produs listat in fisier
     */
    public Produs lineToProdus(String s){
        String[] parts = s.split(":");
        Produs p = new Produs(Double.parseDouble(parts[1]), parts[0]);
        return p;
    }

    /**
     * Se extrage numele unui Magazin direct din numele fisierului care il descrie
     * @return String s: numele magazinului
     */
    public String getNumeMagazin(){
        String f = filename.toString();
        String[] delimitat = f.split("_");
        String s[] = delimitat[1].split("\\.");
        return s[0];
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

