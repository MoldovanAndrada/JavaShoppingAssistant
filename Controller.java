/**
 *  Clasa conecteaza si gestioneaza toate utilitatile definite in celelalte pachete
 *
 * @author Andrada
 * @since 2020-04-23
 */
package Controller;

import Domain.*;
import IOfiles.ScriereFisier;
import Repositories.ClientiRepo;
import Repositories.MagazineRepo;

import java.io.File;
import java.util.*;


public class Controller {
    private ClientiRepo clienti = new ClientiRepo();
    private MagazineRepo magazine = new MagazineRepo();

    public ClientiRepo getClienti() {
        return clienti;
    }


    /**
     * Se apeleaza functia din MagazineRepo
     */

    public void populeazaMagazine(){
        magazine.populeaza();
    }

    /**
     * Se apeleaza functia din ClientiRepo
     */
    public void populeazaClienti(){

        clienti.populeaza();
    }


    /**
     * Functia care preia din Repository-ul de magazine toate magazinele mentionate de client, in ordinea prioritatilor lor, le compara cu
     * cele specificate de un client, apoi le returneaza pe cele care au fost asociate
     * @param  c - clientul prin ale carui magazine se itereaza
     * @return ArrayList<Magazin></> - magazinele care se gasesc atat in Repository-ul de magazine, cat si in lista clientului
     */
    public ArrayList<Magazin> createMagazineClient(Client c){
        ArrayList<MagazinPrioritate> magazinePrioritare = c.getMagazinePrioritati();
        ArrayList<Magazin> magazineClient = new ArrayList<Magazin>();
        Collections.sort(magazinePrioritare, (MagazinPrioritate m1, MagazinPrioritate m2) -> m1.compareTo(m2));

        for (MagazinPrioritate mp : magazinePrioritare) { // se creeaza o lista doar cu magazinele specificate de client
            for (Magazin m : magazine.getMagazine()) {
                if (m.getNume().equals(mp.getNume())) {
                    if(c.getData().getDay()!=0 || m.getDuminica()==true) { //daca in care clientul merge la cumparaturi NU e duminica SAU magazinul e deschis duminica
                        magazineClient.add(m);
                    }
                }
            }
        }
        return magazineClient;
    }



    /**
     * Acesta este algoritmul principal de selectie a produselor din magazine pentru un client
     *
     * @return: (ComandaClient) - toata informatia despre cumparaturile unui client, care va fi scrisa in fisier ulterior
     * @param  : (Client) c - clientul caruia i se onoreaza comanda
     */
    public ComandaClient onoreazaComanda(Client c) {
        double sumatotala=0;

        ArrayList<Magazin> magazineClient = createMagazineClient(c);
        ArrayList<ProdusMagazinPret> rezultat= new ArrayList<ProdusMagazinPret>();


        for (String cautat : c.getLista()) { // se parcurge lista de cumparaturi a clientului
                MagazinPret mag1=cautaInPromotii(c.getData(),cautat, magazineClient);

                if (mag1.getNumeMagazin()==(null)) { //daca nu s-a gasit in nicio promotie
                    MagazinPret mag2=cautaInNereduse(cautat, magazineClient); // se va cauta in listele de produse nereduse ale magazinelor specificate de client

                    if(mag2.getNumeMagazin()!=(null)) { // produsul a fost gasit in lista de produse nereduse
                        sumatotala+=mag2.getPret();
                        ProdusMagazinPret pmp=new ProdusMagazinPret(cautat, mag2.getNumeMagazin(),mag2.getPret());
                        rezultat.add(pmp);
                    }
                    else { //produsul nu se poate achizitiona
                        ProdusMagazinPret pmp=new ProdusMagazinPret(cautat, null, -1);
                        rezultat.add(pmp);
                    }
                }
                else { //produsul a fost achizitionat dintr-o promotie
                    sumatotala+=mag1.getPret();
                    ProdusMagazinPret pmp = new ProdusMagazinPret(cautat, mag1.getNumeMagazin(), mag1.getPret());
                    rezultat.add(pmp);
                }
            }
            ComandaClient cc = new ComandaClient(rezultat, sumatotala, c.getNume() + " " + c.getPrenume());
            return cc;
        }


    /**
     * Functia cauta un produs in listele de produse din promotiile magazinelor dintr-un Array dat
     * @param : d (Date) - data in care clientul isi face cumparaturile
     * @param : magazineClient(ArrayList) - lista de magazine in care se cauta produsul
     * @param : cautat (String) - numele produsului ce se doreste a fi cumparat
     *                              magaineClient(ArrayList) - lista de magazine in care se cauta produsul
     *@return: obiect de tip MagazinPret reprezentand numele magazinului de la care se va achizitiona obiectul, precum si pretul lui
     */
        public MagazinPret cautaInPromotii(Date d, String cautat, ArrayList<Magazin> magazineClient) {
            MagazinPret mp = new MagazinPret(null, -1);
            double pretMinim=10000;
            for (Magazin m : magazineClient) { // se cauta in promotia fiecarui magazin specificat
            for (Promotie p : m.getPromotii()) {
                for (Produs produs : p.getProduse()) {
                    if (produs.getNume().equals(cautat)) {
                        if(seIncadreazaData(d, p.getStartData(), p.getEndData(), m) && produs.getPret()<pretMinim) {
                             mp = new MagazinPret(m.getNume(), produs.getPret());
                            pretMinim=produs.getPret();
                        }
                    }
                }
            }
            if(mp.getNumeMagazin()!= null)
                return mp;
        }
            return mp;
    }


    /**
     * Functia cauta un produs in listele de produse care NU fac parte din promotii, ale magazinelor dintr-un Array dat
     * @parameters: cautat (String) - numele produsului ce se doreste a fi cumparat
     *              magaineClient(ArrayList) - lista de magazine in care se cauta produsul
     *@return: obiect de tip MagazinPret reprezentand numele magazinului de la care se va achizitiona obiectul, precum si pretul lui
     **/
    public MagazinPret cautaInNereduse(String cautat, ArrayList<Magazin> magazineClient){
        for(Magazin m : magazineClient){
            for(Produs p : m.getProdusePretIntreg()){
                if(p.getNume().equals(cautat)){
                    MagazinPret mp = new MagazinPret(m.getNume(), p.getPret());
                    return mp;
                }
            }
        }
        MagazinPret mp = new MagazinPret(null,-1);
        return mp;
    }


    /**
     * Functia determina daca data in care clientul isi face cumparaturile coincide cu datele de incepere si de final ale unei promotii
     * se gestioneaza iin primul 'if' cazul zilelor de duminica
     *
     * @return: true, cand data cumparaturile se incadreaza intre datele de inceput si de final ale promotiei
     *          fals, altfel
     * @parametrii : data efectuarii cumparaturilor, data de inceput a promotiei, data de final a promotiei, magazinul (pentru a extrage disponibilitatea in ziua de duminica)
     */
    public boolean seIncadreazaData(Date clientd, Date startd, Date endd, Magazin m){
        if(clientd.getDay()==0 && !m.isDuminica()) // Clientul merge la cumparaturi intr-o zi de duminica SI magazinul e inchis Duminica
            return false;

        if(endd==null)
            return (!(clientd.after(startd)) && !(clientd.before(startd))); //the client is shopping during the only day of the promotion

        return(!startd.after(clientd) && !endd.before(clientd)); // startd <= clientd <= endd
    }

    /**
     * Functia preia comenzile fiecarui client, le adauga intr-un nou ArrayList, le ordoneaza folosind tool-ul de colectii
     * si le scrie in fisierul "comenzi.txt" din directorul "/src", apeland functia de scriere in fisier, definita in clasa ScriereFisier
     */
    public void scrieInFisier(){
        populeazaMagazine();
        populeazaClienti();

        ArrayList<ComandaClient> cc = new ArrayList<ComandaClient>(); // toate comenzile ce urmeaza sa fie sortate

        for(Client c : this.clienti.getClients()){
            cc.add(onoreazaComanda(c));
        }

        Collections.sort(cc, (ComandaClient c1, ComandaClient c2) -> c1.compareTo(c2));

        File f= new File("src/comenzi.txt");
        ScriereFisier sf = new ScriereFisier(f);

        String s="";
        for(ComandaClient comanda : cc){
            s+=comanda;
            s+="\n \n";
        }

        sf.scrieInFisier(s);
    }

}