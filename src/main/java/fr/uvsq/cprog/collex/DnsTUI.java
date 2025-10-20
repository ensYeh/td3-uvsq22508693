package fr.uvsq.cprog.collex;

import fr.uvsq.cprog.collex.commandes.CommandeRechercheIP;
import fr.uvsq.cprog.collex.commandes.CommandeRechercheNom;
import fr.uvsq.cprog.collex.commandes.CommandeListeDomaine;
import fr.uvsq.cprog.collex.commandes.CommandeAjout;
import fr.uvsq.cprog.collex.commandes.CommandeQuitter;

public class DnsTUI {
    private final Dns dns;

    public DnsTUI(Dns dns) {
        this.dns = dns;
    }

    public Commande nextCommande(String saisie) {
        if (saisie == null || saisie.trim().isEmpty()) {
            return new CommandeVide();
        }

        String[] parties = saisie.trim().split("\\s+");
        String premierePartie = parties[0].toLowerCase();

        try {

            if (estAdresseIP(premierePartie)) {
                return new CommandeRechercheNom(dns, saisie.trim());
            }

            if (premierePartie.contains(".") && !premierePartie.equals("ls") && !premierePartie.equals("add")) {
                return new CommandeRechercheIP(dns, saisie.trim());
            }

            if (premierePartie.equals("ls")) {
                return traiterCommandeLs(parties);
            }

            if (premierePartie.equals("add")) {
                return traiterCommandeAdd(parties);
            }

            if (premierePartie.equals("quit") || premierePartie.equals("exit")) {
                return new CommandeQuitter();
            }

            return new CommandeInconnue(saisie);

        } catch (Exception e) {
            return new CommandeErreur("Erreur d'analyse : " + e.getMessage());
        }
    }

    private Commande traiterCommandeLs(String[] parties) {
        if (parties.length < 2) {
            return new CommandeErreur("Usage: ls [-a] domaine");
        }

        boolean trierParIp = false;
        String domaine = "";

        if (parties[1].equals("-a")) {
            trierParIp = true;
            if (parties.length >= 3) {
                domaine = parties[2];
            } else {
                return new CommandeErreur("Usage: ls [-a] domaine");
            }
        } else {
            domaine = parties[1];
        }

        return new CommandeListeDomaine(dns, domaine, trierParIp);
    }

    private Commande traiterCommandeAdd(String[] parties) {
        if (parties.length != 3) {
            return new CommandeErreur("Usage: add adresse_ip nom_machine");
        }

        String adresseIp = parties[1];
        String nomMachine = parties[2];

        return new CommandeAjout(dns, adresseIp, nomMachine);
    }

    private boolean estAdresseIP(String texte) {
        try {
            new AdresseIP(texte);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void affiche(String resultat) {
        System.out.println(resultat);
    }

    public void affichePrompt() {
        System.out.print("> ");
    }

    private static class CommandeVide implements Commande {
        @Override
        public String execute() {
            return "";
        }
    }

    private static class CommandeInconnue implements Commande {
        private final String commande;

        public CommandeInconnue(String commande) {
            this.commande = commande;
        }

        @Override
        public String execute() {
            return "Commande inconnue : " + commande;
        }
    }

    private static class CommandeErreur implements Commande {
        private final String message;

        public CommandeErreur(String message) {
            this.message = message;
        }

        @Override
        public String execute() {
            return message;
        }
    }
}