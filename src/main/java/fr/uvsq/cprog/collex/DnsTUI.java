package fr.uvsq.cprog.collex;

import java.util.List;
import java.util.Scanner;

public class DnsTUI {
    private final Scanner scanner;
    private final Dns dns;

    public DnsTUI(final Dns dns) {
        this.scanner = new Scanner(System.in);
        this.dns = dns;
    }

    public Commande nextCommande() {
        System.out.print("> ");

        if (!scanner.hasNextLine()) {
            return null;
        }

        String ligne = scanner.nextLine().trim();

        if (ligne.isEmpty()) {
            return null;
        }

        return analyserCommande(ligne);
    }

    private Commande analyserCommande(final String ligne) {
        String[] parties = ligne.split("\\s+");

        if (parties[0].equals("ls")) {
            if (parties.length == 2) {
                return new CommandeLs(dns, parties[1], false);
            } else if (parties.length == 3 && parties[1].equals("-a")) {
                return new CommandeLs(dns, parties[2], true);
            } else {
                return new CommandeErreur("Format incorrect. Usage: ls [-a] domaine");
            }
        }

        if (parties[0].equals("add")) {
            if (parties.length == 3) {
                return new CommandeAdd(dns, parties[1], parties[2]);
            } else {
                return new CommandeErreur("Format incorrect. Usage: add adresse.ip nom.machine");
            }
        }

        if (parties[0].equals("quit") || parties[0].equals("exit")) {
            return new CommandeQuitter();
        }

        if (parties.length == 1) {
            String param = parties[0];

            if (AdresseIP.estValide(param)) {
                return new CommandeRechercheIP(dns, param);
            }

            if (NomMachine.estValide(param)) {
                return new CommandeRechercheNom(dns, param);
            }

            return new CommandeErreur("Format invalide: ni adresse IP ni nom de machine valide");
        }

        return new CommandeErreur("Commande non reconnue: " + parties[0]);
    }

    public void affiche(final String resultat) {
        if (resultat != null && !resultat.isEmpty()) {
            System.out.println(resultat);
        }
    }

    public void affiche(final List<DnsItem> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("Aucun résultat");
            return;
        }

        for (DnsItem item : items) {
            System.out.println(item.toString());
        }
    }

    public void affiche(final DnsItem item) {
        if (item == null) {
            System.out.println("Aucun résultat trouvé");
        } else {
            System.out.println(item.toString());
        }
    }

    public void fermer() {
        scanner.close();
    }
}