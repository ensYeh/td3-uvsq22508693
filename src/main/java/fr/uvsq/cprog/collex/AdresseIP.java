package fr.uvsq.cprog.collex;

import java.util.regex.Pattern;

public class AdresseIP {
    private final String ip;

    public AdresseIP(String ip) {
        if (!estValide(ip)) {
            throw new IllegalArgumentException("Adresse IP invalide : " + ip);
        }
        this.ip = ip;
    }

    public static boolean estValide(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return Pattern.matches(regex, ip);
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return ip;
    }

}
