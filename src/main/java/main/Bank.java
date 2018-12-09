package main;

import verarbeitung.*;

import java.io.*;
import java.util.*;

public class Bank implements Cloneable, Serializable {
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


    public long kontoErstellen(Kontofabrik fabrik) {
        anzahlKonten++;
        fabrik.setNr(anzahlKonten);
        kontoList.add(fabrik.create());
        return anzahlKonten;
    }

    /**
     * erstellt neues Girokonto
     *
     * @param inhaber Inhaber des Kontos
     * @param dispo   Hoehe des Dispo-Kredites
     * @return Kontonummer des erstellten Kontos
     */
    public long girokontoErstellen(Kunde inhaber, double dispo) {
        return kontoErstellen(new GirokontoFabrik(inhaber, dispo));
    }

    /**
     * erstellt neues Sparbuch
     *
     * @param inhaber Inhaber des Kontos
     * @return Kontonummer des erstellten Kontos
     */
    public long sparbuchErstellen(Kunde inhaber) {
        return kontoErstellen(new SparbuchFabrik(inhaber));
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


    /**
     * gibt eine Liste aller Kunden zurueck, die mindestens ein Konto haben, das mindestens den angegebenen Betrag als Kontostand hat
     *
     * @param minimum minimaler Betrag
     * @return Liste der Kunden
     */
    public List<Kunde> getKundenMitVollemKonto(double minimum) {
        List<Kunde> kundenList = new ArrayList<>();

        for (Konto tempKonto : kontoList) {
            if (!kundenList.contains(tempKonto.getInhaber())) {
                if (tempKonto.getKontostand() >= minimum)
                    kundenList.add(tempKonto.getInhaber());
            }
        }
        return kundenList;
    }

    /**
     * gibt Liste mit den Geburtstagen der Kunden zurueck
     *
     * @return geordnete Liste der Geburtstage aller Kunden
     */
    public String getKundengeburtstage() {
        HashMap<String, Long> namenGeburtstagsMap = new HashMap<>();

        for (Konto tempKonto : kontoList) {
            if (!namenGeburtstagsMap.containsKey(tempKonto.getInhaber().getName())) {
                Calendar cal = Calendar.getInstance();
                cal.set(tempKonto.getInhaber().getGeburtstag().getYear(), tempKonto.getInhaber().getGeburtstag().getMonth().getValue(), tempKonto.getInhaber().getGeburtstag().getDayOfMonth());
                namenGeburtstagsMap.put(tempKonto.getInhaber().getName(), cal.getTimeInMillis());
            }
        }

        Map<String, Long> result = new LinkedHashMap<>();
        namenGeburtstagsMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));


        Object[] names = result.keySet().toArray();
        Object[] dates = result.values().toArray();
        StringBuilder resultString = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < result.size(); i++) {
            cal.setTimeInMillis((Long) dates[i]);
            resultString.append(names[i]).append(": ").append(cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR));
            resultString.append(System.getProperty("line.separator"));
        }
        return resultString.toString();
    }

    /**
     * @return Liste der Luecken in den Kontonummern
     */
    public List<Long> getKontonummernLuecken() {

        long minimum = Collections.min(getAlleKontonummern());
        long maximum = Collections.max(getAlleKontonummern());

        List<Long> luecken = new ArrayList<>();
        for (long i = minimum; i <= maximum; i++) {
            luecken.add(i);
        }

        for (Long tempKontonummer : getAlleKontonummern()) {
            luecken.remove(tempKontonummer);
        }
        return luecken;
    }

    protected Object clone() throws CloneNotSupportedException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInput in = new ObjectInputStream(bis)) {
                return in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
