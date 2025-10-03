package fr.uvsq.cprog.dns.commandes;

import fr.uvsq.cprog.dns.AdresseIP;
import fr.uvsq.cprog.dns.Commande;
import fr.uvsq.cprog.dns.Dns;
import fr.uvsq.cprog.dns.DnsItem;

public class CommandeRechercheNom implements Commande {
    private final Dns dns;
    private final String adresseIp;

    public CommandeRechercheNom(Dns dns, String adresseIp) {
        this.dns = dns;
        this.adresseIp = adresseIp;
    }

    @Override
    public String execute() {
        try {
            AdresseIP ip = new AdresseIP(adresseIp);
            DnsItem item = dns.getItem(ip);

            if (item != null) {
                return item.getNomMachine().getNomComplet();
            } else {
                return "ERREUR : Adresse IP non trouvée : " + adresseIp;
            }
        } catch (IllegalArgumentException e) {
            return "ERREUR : Adresse IP invalide : " + adresseIp;
        }
    }
}