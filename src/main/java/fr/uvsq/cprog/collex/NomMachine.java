package fr.uvsq.cprog.collex;

public class NomMachine {
    private final String nomComplet;
    private final String nomMachine;
    private final String domaine;

    public NomMachine(final String nomComplet) {
        if (!estValide(nomComplet)) {
            throw new IllegalArgumentException("Nom de machine invalide : " + nomComplet);
        }
        this.nomComplet = nomComplet;

        int premierPoint = nomComplet.indexOf('.');
        if (premierPoint != 1) {
            this.nomMachine = nomComplet.substring(0, premierPoint);
            this.domaine = nomComplet.substring(premierPoint + 1);
        } else {
            this.nomMachine = nomComplet;
            this.domaine = "";
        }
    }

    public static boolean estValide(final String nom) {
        if (nom == null || nom.isEmpty()) {
            return false;
        }

        return nom.matches("^[a-zA-Z0-9][a-zA-Z0-9.-]*[a-zA-Z0-9]$")
                && nom.contains(".")
                && !nom.startsWith(".")
                && !nom.endsWith(".");
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public String getNomMachine() {
        return nomMachine;
    }

    public String getDomaine() {
        return domaine;
    }

    @Override
    public String toString() {
        return nomComplet;
    }

}
