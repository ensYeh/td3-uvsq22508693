package fr.uvsq.cprog.collex.commandes;

import fr.uvsq.cprog.collex.Commande;
import fr.uvsq.cprog.collex.Dns;
import fr.uvsq.cprog.collex.DnsItem;
import fr.uvsq.cprog.collex.NomMachine;

public class CommandeRechercheIP implements Commande {
    private final Dns dns;
    private final String NomMachine;

    public CommandeRechercheIP(Dns dns, String NomMachine) {
        this.dns = dns;
        this.NomMachine = NomMachine;
    }

    @Override
    public String execute() {
        try {
            NomMachine nom = new NomMachine(this.NomMachine);
            DnsItem item = dns.getItem(nom);

            if (item != null) {
                return item.getAdresseIp().getIp();
            } else {
                return "ERREUR : Nom de machine non trouvé : " + this.NomMachine;
            }
        } catch (IllegalArgumentException e) {
            return "ERREUR : Nom de machine invalide : " + this.NomMachine;
        }
    }

}
