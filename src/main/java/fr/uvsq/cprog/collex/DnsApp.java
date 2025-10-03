package fr.uvsq.cprog.collex;

import java.util.Scanner;

public class DnsApp {
    private final Dns dns;
    private final DnsTUI dnsTui;
    private final Scanner scanner;

    public DnsApp() {
        this.dns = new Dns();
        this.dnsTui = new DnsTUI(dns);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        DnsApp app = new DnsApp();
        app.run();
    }

    public void run() {
        System.out.println("=== Système DNS ===");
        System.out.println("Commandes disponibles :");
        System.out.println("  nom.machine.domaine    - Recherche l'IP d'une machine");
        System.out.println("  adresse.ip             - Recherche le nom d'une machine");
        System.out.println("  ls [-a] domaine        - Liste les machines d'un domaine");
        System.out.println("  add ip nom             - Ajoute une entrée DNS");
        System.out.println("  quit ou exit           - Quitte l'application");
        System.out.println("=========================");

        boolean running = true;

        while (running) {
            try {
                dnsTui.affichePrompt();

                String saisie = scanner.nextLine();

                Commande commande = dnsTui.nextCommande(saisie);

                String resultat = commande.execute();

                if (!resultat.isEmpty()) {
                    dnsTui.affiche(resultat);
                }

                if (commande instanceof fr.uvsq.cprog.collex.commandes.CommandeQuitter) {
                    running = false;
                }

            } catch (Exception e) {
                System.err.println("Erreur inattendue : " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
        System.out.println("Application DNS arrêtée.");
    }

    public Dns getDns() {
        return dns;
    }

    public DnsTUI getDnsTui() {
        return dnsTui;
    }
}