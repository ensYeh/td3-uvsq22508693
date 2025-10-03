package fr.uvsq.cprog.collex.commandes;

import fr.uvsq.cprog.collex.AdresseIP;
import fr.uvsq.cprog.collex.Commande;
import fr.uvsq.cprog.collex.Dns;
import fr.uvsq.cprog.collex.NomMachine;

public class CommandeAjout implements Commande {
    private final Dns dns;
    private final String adresseIp;
    private final String nomMachine;

    public CommandeAjout(Dns dns, String adresseIp, String nomMachine) {
        this.dns = dns;
        this.adresseIp = adresseIp;
        this.nomMachine = nomMachine;
    }

    @Override
    public String execute() {
        try {
            AdresseIP ip = new AdresseIP(adresseIp);
            NomMachine nom = new NomMachine(nomMachine);

            dns.addItem(ip, nom);
            return "Entrée ajoutée avec succès : " + nomMachine + " -> " + adresseIp;

        } catch (IllegalArgumentException e) {
            return "ERREUR : " + e.getMessage();
        }
    }
}