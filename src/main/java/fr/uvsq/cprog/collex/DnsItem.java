package fr.uvsq.cprog.collex;

import java.util.Objects;

public class DnsItem {
    private final AdresseIP adresseIp;
    private final NomMachine nomMachine;

    public DnsItem(final AdresseIP adresseIp, final NomMachine nomMachine) {
        if (adresseIp == null || nomMachine == null) {
            throw new IllegalArgumentException("L'adresse IP et le nom de machine ne peuvent pas être null");
        }
        this.adresseIp = adresseIp;
        this.nomMachine = nomMachine;
    }

    public AdresseIP getAdresseIp() {
        return adresseIp;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }

    public boolean appartientAuDomaine(final String domaine) {
        return nomMachine.getDomaine().equals(domaine);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        DnsItem dnsItem = (DnsItem) obj;

        return Objects.equals(adresseIp, dnsItem.adresseIp)
                && Objects.equals(nomMachine, dnsItem.nomMachine);
    }

    @Override
    public String toString() {
        return adresseIp.toString() + " " + nomMachine.toString();
    }
}
