package fr.uvsq.cprog.dns.commandes;

import fr.uvsq.cprog.dns.Commande;
import fr.uvsq.cprog.dns.Dns;
import fr.uvsq.cprog.dns.DnsItem;
import java.util.List;

public class CommandeListeDomaine implements Commande {
    private final Dns dns;
    private final String domaine;
    private final boolean trierParIp;

    public CommandeListeDomaine(Dns dns, String domaine, boolean trierParIp) {
        this.dns = dns;
        this.domaine = domaine;
        this.trierParIp = trierParIp;
    }

    @Override
    public String execute() {
        List<DnsItem> items = dns.getItems(domaine);

        if (items.isEmpty()) {
            return "Aucune machine trouvée pour le domaine : " + domaine;
        }

        StringBuilder resultat = new StringBuilder();
        for (DnsItem item : items) {
            if (trierParIp) {
                resultat.append(item.getAdresseIp().getIp())
                        .append(" ")
                        .append(item.getNomMachine().getNomComplet())
                        .append("\n");
            } else {
                resultat.append(item.getNomMachine().getNomComplet())
                        .append(" ")
                        .append(item.getAdresseIp().getIp())
                        .append("\n");
            }
        }

        return resultat.toString().trim();
    }
}