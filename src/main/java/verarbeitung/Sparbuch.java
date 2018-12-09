package verarbeitung;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ein Sparbuch
 *
 * @author Doro
 */
public class Sparbuch extends Konto implements Serializable {
    /**
     * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
     */
    private double zinssatz;

    /**
     * Monatlich erlaubter Gesamtbetrag fuer Abhebungen
     */
    public static final double ABHEBESUMME = 2000;

    /**
     * Betrag, der im aktuellen Monat bereits abgehoben wurde
     */
    private double bereitsAbgehoben = 0;
    /**
     * Monat und Jahr der letzten Abhebung
     */
    private LocalDate zeitpunkt = LocalDate.now();

    public Sparbuch() {
        zinssatz = 0.03;
    }

    public Sparbuch(Kunde inhaber, long kontonummer) {
        super(inhaber, kontonummer);
        zinssatz = 0.03;
    }

    @Override
    public String toString() {
        String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
                super.toString()
                + "Zinssatz: " + this.zinssatz * 100 + "%" + System.lineSeparator();
        return ausgabe;
    }



    @Override
    boolean checkIfPossible(double betrag) throws GesperrtException {

        double btrg = getAktuelleWaehrung().euroInWaehrungUmrechnen(betrag);
        if (btrg < 0) {
            throw new IllegalArgumentException();
        }
        if (this.isGesperrt()) {
            GesperrtException e = new GesperrtException(this.getKontonummer());
            throw e;
        }
        LocalDate heute = LocalDate.now();
        if (heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear()) {
            this.bereitsAbgehoben = 0;
        }
        return getKontostand() - btrg >= 0.50 &&
                bereitsAbgehoben + btrg <= Sparbuch.ABHEBESUMME;
    }

    @Override
    void betragAbheben(double betrag) {
        double btrg = getAktuelleWaehrung().euroInWaehrungUmrechnen(betrag);

        setKontostand(getKontostand() - btrg);
        bereitsAbgehoben += btrg;
        this.zeitpunkt = LocalDate.now();

    }
}
