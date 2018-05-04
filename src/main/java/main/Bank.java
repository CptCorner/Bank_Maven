package main;

import verarbeitung.*;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private long bankleitzahl;
    private long anzahlKonten;
    private List<Konto> kontoList;

    /**
     * Konstruktor
     *
     * @param bankleitzahl long
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        anzahlKonten = 0;
        kontoList = new ArrayList<>();
    }

    /**
     * Standard-Getter, returns bankleitzahl
     *
     * @return bankleitzahl
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * erstellt neues Girokonto
     *
     * @param inhaber Inhaber des Kontos
     * @param dispo   Hoehe des Dispo-Kredites
     * @return Kontonummer des erstellten Kontos
     */
    public long girokontoErstellen(Kunde inhaber, double dispo) {
        anzahlKonten++;
        kontoList.add(new Girokonto(inhaber, anzahlKonten, dispo));
        return anzahlKonten;
    }

    /**
     * erstellt neues Sparbuch
     *
     * @param inhaber Inhaber des Kontos
     * @return Kontonummer des erstellten Kontos
     */
    public long sparbuchErstellen(Kunde inhaber) {
        anzahlKonten++;
        kontoList.add(new Sparbuch(inhaber, anzahlKonten));
        return anzahlKonten;
    }

    /**
     * @return gekuerzte String-Repraesentation der Konten
     */
    public String getAlleKonten() {
        StringBuilder s = new StringBuilder();

        for (Konto temp : kontoList) {
            if (temp != null) {
                s.append("Kontonummer: ").append(temp.getKontonummer()).append(", Kontostand: ").append(temp.getKontostand());
                s.append(System.getProperty("line.separator"));
            }
        }
        return s.toString();
    }

    /**
     * @return Liste mit allen gueltigen Kontonummern
     */
    public List<Long> getAlleKontonummern() {
        List<Long> resultList = new ArrayList<>();
        for (long i = 0; i < anzahlKonten; i++) {
            if (kontoList.get((int) i) != null)
                resultList.add(i + 1);
        }
        return resultList;
    }

    /**
     * Startet Abhebevorgang vom Konto
     *
     * @param von    Kontonummer des Kontos
     * @param betrag der abgehoben werden soll
     * @return ob der Vorgang erfolgreich war
     */
    public boolean geldAbheben(long von, double betrag) {
        if (von <= anzahlKonten && von > 0) {
            try {
                if (kontoList.get((int) von - 1) != null)
                    return kontoList.get((int) von - 1).abheben(betrag);
                else return false;
            } catch (GesperrtException e) {
                e.printStackTrace();
                return false;
            }

        } else return false;
    }

    /**
     * @param auf    Konto, auf das Geld eingezahlt werden soll
     * @param betrag der eingezahlt werden soll
     */
    public void geldEinzahlen(long auf, double betrag) {
        if (auf <= anzahlKonten && auf > 0) {
            if (kontoList.get((int) auf - 1) != null)
                kontoList.get((int) (auf - 1)).einzahlen(betrag);
        }
    }

    /**
     * loescht ein Konto
     *
     * @param nummer des zu loeschenden Kontos
     * @return ob der Vorgang erfolgreich war
     */
    public boolean kontoLoeschen(long nummer) {
        if (nummer <= anzahlKonten && nummer > 0) {
            if (kontoList.get((int) nummer - 1) != null) {
                kontoList.set((int) nummer - 1, null);
                return true;
            } else return false;
        } else return false;
    }

    /**
     * @param nummer des Kontos, das abgefragt wird
     * @return den Kontostand des Kontos
     * @throws KontoNummerNichtVorhandenException Wenn die Kontonummer nicht vorhanden war
     */
    public double getKontostand(long nummer) throws KontoNummerNichtVorhandenException {
        if (nummer <= anzahlKonten && nummer > 0) {
            if (kontoList.get((int) nummer - 1) != null) {
                return kontoList.get((int) nummer - 1).getKontostand();
            } else throw new KontoNummerNichtVorhandenException(nummer);
        } else throw new KontoNummerNichtVorhandenException(nummer);
    }

    /**
     * Ueberweist Geld von einem Girokonto auf ein anderes Girokonto
     *
     * @param vonKontonr       des Kontos VON dem ueberwiesen werden soll
     * @param nachKontonr      des Kontos ZU dem ueberwiesen werden soll
     * @param betrag           der ueberwiesen werden soll
     * @param verwendungszweck der ueberweisung
     * @return ob der Vorgang erfolgreich war
     * @throws KontoNummerNichtVorhandenException wenn eine der Kontonummern nicht vorhanden ist
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws KontoNummerNichtVorhandenException {
        if (vonKontonr <= anzahlKonten && vonKontonr > 0) {

            if (nachKontonr <= anzahlKonten && nachKontonr > 0) {

                if (kontoList.get((int) vonKontonr - 1) != null) {

                    if (kontoList.get((int) nachKontonr - 1) != null) {
                        //beide Konton sind Vorhanden
                        if (kontoList.get((int) vonKontonr - 1) instanceof Girokonto && kontoList.get((int) nachKontonr - 1) instanceof Girokonto) {
                            Girokonto von = (Girokonto) kontoList.get((int) vonKontonr - 1);
                            Girokonto nach = (Girokonto) kontoList.get((int) nachKontonr - 1);

                            try {
                                if (von.ueberweisungAbsenden(betrag, nach.getInhaber().getName(), nachKontonr, bankleitzahl, verwendungszweck)) {
                                    nach.ueberweisungEmpfangen(betrag, von.getInhaber().getName(), vonKontonr, bankleitzahl, verwendungszweck);
                                    return true;
                                }
                                return false;
                            } catch (GesperrtException e) {
                                e.printStackTrace();
                                return false;
                            }


                        } else return false;


                    } else throw new KontoNummerNichtVorhandenException(nachKontonr);

                } else throw new KontoNummerNichtVorhandenException(vonKontonr);

            } else throw new KontoNummerNichtVorhandenException(nachKontonr);

        } else throw new KontoNummerNichtVorhandenException(vonKontonr);
    }


}
