package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Dns {
    private final List<DnsItem> items;
    private final String fichierBase;
    private static final String FICHIER_CONFIG = "config.properties";

    public Dns() {
        this.items = new ArrayList<>();
        this.fichierBase = chargerFichierBase();
        chargerBaseDeDonnees();
    }

    private String chargerFichierBase() {
        try {
            Properties proprietes = new Properties();
            proprietes.load(getClass().getClassLoader().getResourceAsStream(FICHIER_CONFIG));
            return proprietes.getProperty("dns.file", "dnstable.txt");
        } catch (IOException e) {
            System.err.println("Erreur de chargement de la configuration: " + e.getMessage());
            return "dnstable.txt";
        }
    }

    private void chargerBaseDeDonnees() {
        try {
            Path chemin = Paths.get(fichierBase);

            if (!Files.exists(chemin)) {
                Files.createFile(chemin);
                System.out.println("Fichier de base créé: " + fichierBase);
                return;
            }

            List<String> lignes = Files.readAllLines(chemin);

            for (String ligne : lignes) {
                if (ligne.trim().isEmpty()) {
                    continue;
                }

                String[] parties = ligne.split("\\s+");
                if (parties.length == 2) {
                    try {
                        NomMachine nom = new NomMachine(parties[0]);
                        AdresseIP ip = new AdresseIP(parties[1]);
                        items.add(new DnsItem(ip, nom));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ligne ignorée (format invalide): " + ligne);
                    }
                } else {
                    System.err.println("Ligne ignorée (format incorrect): " + ligne);
                }
            }

            System.out.println("Base DNS chargée: " + items.size() + " entrées");

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier DNS: " + e.getMessage());
        }
    }

    public DnsItem getItem(AdresseIP ip) {
        for (DnsItem item : items) {
            if (item.getAdresseIp().getIp().trim().equals(ip.getIp().trim())) {
                return item;
            }
        }
        return null;
    }

    public DnsItem getItem(NomMachine nom) {
        for (DnsItem item : items) {
            // Comparaison directe des noms de machines
            if (item.getNomMachine().getNomComplet().equals(nom.getNomComplet())) {
                return item;
            }
        }
        return null;
    }

    public List<DnsItem> getItems(String domaine) {
        List<DnsItem> resultats = new ArrayList<>();
        for (DnsItem item : items) {
            if (item.appartientAuDomaine(domaine)) {
                resultats.add(item);
            }
        }
        return resultats;
    }

    public void addItem(AdresseIP ip, NomMachine nom) {
        // Vérifie si l'adresse IP existe déjà
        if (getItem(ip) != null) {
            throw new IllegalArgumentException("L'adresse IP existe déjà: " + ip.getIp());
        }

        // Vérifie si le nom de machine existe déjà
        if (getItem(nom) != null) {
            throw new IllegalArgumentException("Le nom de machine existe déjà: " + nom.getNomComplet());
        }

        // Ajoute le nouvel item
        DnsItem nouvelItem = new DnsItem(ip, nom);
        items.add(nouvelItem);

        // Met à jour le fichier
        sauvegarderBaseDeDonnees();
    }

    private void sauvegarderBaseDeDonnees() {
        try {
            Path chemin = Paths.get(fichierBase);
            List<String> lignes = new ArrayList<>();

            for (DnsItem item : items) {
                String ligne = item.getNomMachine().getNomComplet() + " " + item.getAdresseIp().getIp();
                lignes.add(ligne);
            }

            Files.write(chemin, lignes);

        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde du fichier DNS: " + e.getMessage());
            throw new RuntimeException("Impossible de sauvegarder les données", e);
        }
    }

    public int getTaille() {
        return items.size();
    }
}
