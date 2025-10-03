package fr.uvsq.cprog.dns.commandes;

import fr.uvsq.cprog.dns.Commande;

public class CommandeQuitter implements Commande {

    @Override
    public String execute() {
        System.exit(0);
        return "Au revoir !";
    }
}