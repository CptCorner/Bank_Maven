package verarbeitung;

/**
 * stellt ein allgemeines Konto dar
 */
public abstract class Konto implements Comparable<Konto> {

    /**
     * die aktuelle Waehrung
     */
    private Waehrung aktuelleWaehrung;

    /**
     * der Kontoinhaber
     */
    private Kunde inhaber;

    /**
     * die Kontonummer
     */
    private final long nummer;

    /**
     * der aktuelle Kontostand
     */
    private double kontostand;

    /**
     * setzt den aktuellen Kontostand
     *
     * @param kontostand neuer Kontostand
     */
    protected void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    /**
     * Wenn das Konto gesperrt ist (gesperrt = true), koennen keine Aktionen daran mehr vorgenommen werden,
     * die zum Schaden des Kontoinhabers waeren (abheben, Inhaberwechsel)
     */
    private boolean gesperrt;

    /**
     * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
     * der anfaengliche Kontostand wird auf 0 gesetzt.
     *
     * @param inhaber     Kunde
     * @param kontonummer long
     * @throws IllegalArgumentException wenn der Inhaber null
     */
    public Konto(Kunde inhaber, long kontonummer) {
        if (inhaber == null)
            throw new IllegalArgumentException("Inhaber darf nicht null sein!");
        this.inhaber = inhaber;
        this.nummer = kontonummer;
        this.kontostand = 0;
        this.gesperrt = false;
        this.aktuelleWaehrung = Waehrung.EUR;
    }

    /**
     * setzt alle Eigenschaften des Kontos auf Standardwerte
     */
    public Konto() {
        this(Kunde.MUSTERMANN, 1234567);
    }

    /**
     * liefert den Kontoinhaber zurueck
     *
     * @return Kunde
     */
    public final Kunde getInhaber() {
        return this.inhaber;
    }

    /**
     * setzt den Kontoinhaber
     *
     * @param kinh neuer Kontoinhaber
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn kinh null ist
     */
    public final void setInhaber(Kunde kinh) throws GesperrtException {
        if (kinh == null)
            throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
        if (this.gesperrt)
            throw new GesperrtException(this.nummer);
        this.inhaber = kinh;

    }

    /**
     * liefert den aktuellen Kontostand
     *
     * @return double
     */
    public final double getKontostand() {
        return kontostand;
    }

    /**
     * liefert die Kontonummer zurueck
     *
     * @return long
     */
    public final long getKontonummer() {
        return nummer;
    }

    /**
     * liefert zurueck, ob das Konto gesperrt ist oder nicht
     *
     * @return
     */
    public final boolean isGesperrt() {
        return gesperrt;
    }

    /**
     * Erhoeht den Kontostand um den eingezahlten Betrag.
     *
     * @param betrag double
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public void einzahlen(double betrag) {
        if (betrag < 0) {
            throw new IllegalArgumentException("Negativer Betrag");
        }
        setKontostand(getKontostand() + betrag);
    }

    /**
     * Erhoeht den Kontostand um den eingezahlten Betrag in der angegebenen Waehrung.
     *
     * @param betrag double
     * @param w      Waehrung
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public void einzahlen(double betrag, Waehrung w) {
        double betragInEUR = w.waehrungInEuroUmrechnen(betrag);
        double betragInZielWaehrung = w.euroInWaehrungUmrechnen(betragInEUR);
        einzahlen(betragInZielWaehrung);
    }

    /**
     * Gibt eine Zeichenkettendarstellung der Kontodaten zurueck.
     */
    @Override
    public String toString() {
        String ausgabe;
        ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
                + System.getProperty("line.separator");
        ausgabe += "Inhaber: " + this.inhaber;
        ausgabe += "Aktueller Kontostand: " + this.kontostand + " Euro ";
        ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
        return ausgabe;
    }

    /**
     * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
     *
     * @param betrag double
     * @return true, wenn die Abhebung geklappt hat,
     * false, wenn sie abgelehnt wurde
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public abstract boolean abheben(double betrag)
            throws GesperrtException;

    /**
     * Mit dieser Methode wird der geforderte Betrag in der angegebenen Waehrung vom Konto abgehoben, wenn es nicht gesperrt ist.
     *
     * @param betrag double
     * @param w      Waehrung
     * @return true, wenn die Abhebung geklappt hat,
     * false, wenn sie abgelehnt wurde
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
        double betragInEUR = w.waehrungInEuroUmrechnen(betrag);
        double betragInZielWaehrung = aktuelleWaehrung.euroInWaehrungUmrechnen(betragInEUR);
        return abheben(betragInZielWaehrung);
    }

    /**
     * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr moeglich.
     */
    public final void sperren() {
        this.gesperrt = true;
    }

    /**
     * entsperrt das Konto, alle Kontoaktionen sind wieder moeglich.
     */
    public final void entsperren() {
        this.gesperrt = false;
    }


    /**
     * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
     *
     * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
     */
    public final String getGesperrtText() {
        if (this.gesperrt) {
            return "GESPERRT";
        } else {
            return "";
        }
    }

    /**
     * liefert die ordentlich formatierte Kontonummer
     *
     * @return auf 10 Stellen formatierte Kontonummer
     */
    public String getKontonummerFormatiert() {
        return String.format("%10d", this.nummer);
    }

    /**
     * liefert den ordentlich formatierten Kontostand
     *
     * @return formatierter Kontostand mit 2 Nachkommastellen und Waehrungssymbol
     */
    public String getKontostandFormatiert() {
        return String.format("%10.2f Euro", this.getKontostand());
    }

    /**
     * Aendert die Waehrung des Kontos in die angegebene Waehrung. Der Kontostand wird dabei umgerechnet.
     *
     * @param neu Waehrung
     */
    public void waehrungswechsel(Waehrung neu) {
        double betragInEUR = aktuelleWaehrung.waehrungInEuroUmrechnen(getKontostand());
        double betragInZielWaehrung = neu.euroInWaehrungUmrechnen(betragInEUR);
        setKontostand(betragInZielWaehrung);
        aktuelleWaehrung = neu;
    }

    /**
     * Gibt die aktuelle Waehrung des Kontos aus.
     *
     * @return Waehrung
     */
    public Waehrung getAktuelleWaehrung() {
        return aktuelleWaehrung;
    }

    /**
     * Vergleich von this mit other; Zwei Konten gelten als gleich,
     * wen sie die gleiche Kontonummer haben
     *
     * @param other
     * @return true, wenn beide Konten die gleiche Nummer haben
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;
        if (this.nummer == ((Konto) other).nummer)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
    }

    @Override
    public int compareTo(Konto other) {
        if (other.getKontostand() > this.getKontostand())
            return -1;
        if (other.getKontostand() < this.getKontostand())
            return 1;
        return 0;
    }
}
