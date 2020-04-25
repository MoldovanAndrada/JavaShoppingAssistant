/**
 * Clasa are rolul de a stoca si a gestiona 2 sau mai multi clienti
 *
 * @author Andrada
 * @since 2020-04-23
 */
package Repositories;

import Domain.Client;
import IOfiles.*;

import java.io.File;
import java.util.ArrayList;

public class ClientiRepo {
    private ArrayList<Client> clients = new ArrayList<Client>();

    public ArrayList<Client> getClients(){
        return this.clients;
    }

    public void adaugaClient(Client c){
        clients.add(c);
    }

    /**
     * Functia extrage toate fisierele care apartin comenzilor clientilor din directorul de resurse, si citeste continutul fiecaruie, dupa
     * care il adauga in Repository-ul de clienti
     */
    public void populeaza(){
        File dir = new File("src/resources");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (isClient(child)) {
                    CitireClient cc = new CitireClient(child);
                    Client c = cc.readLines();
                    adaugaClient(c);
                }
            }
        }
    }

    /**
     * functia verifica daca numele unui fisier apartine comenzii unui client
     * @param f: fisierul
     * @return true, daca fisierul apartine comenzii unui client
     *         fals, altfel
     */
    public boolean isClient(File f){
        String[] s = f.toString().split("\\.");
        String[] ss = s[0].split("_");
        return(ss[0].substring(14,21).equals("comanda"));
    }
}