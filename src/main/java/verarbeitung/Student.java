package verarbeitung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Spezieller Kunde einer Bank
 *
 * @author Jakob Wendt, s0559720
 */

public class Student extends Kunde {
    /**
     * Name der Universitaet, an der der Student studiert
     */
    private String universitaet;
    /**
     * Name des Studienfaches, welches studiert wird
     */
    private String studienfach;
    /**
     * Liste der Semester, fuer die eine Studienbescheinigung vorhanden ist
     */
    private List<Semester> bescheinigungen;


    /**
     * Konstruktor, leitet Parameter von Super zum Super-Konstruktor weiter
     * @param vorname String
     * @param nachname String
     * @param adresse String
     * @param gebdat LocalDate
     * @param universitaet String
     * @param studienfach String
     */
    public Student(String vorname, String nachname, String adresse, LocalDate gebdat, String universitaet, String studienfach) {
        super(vorname, nachname, adresse, gebdat);
        this.studienfach = studienfach;
        this.universitaet = universitaet;
        this.bescheinigungen = new ArrayList<>();
    }

    /**
     * Konstruktor, leitet Parameter von Super zum Super-Konstruktor weiter
     * @param vorname String
     * @param nachname String
     * @param adresse String
     * @param gebdat String
     * @param universitaet String
     * @param studienfach String
     */
    public Student(String vorname, String nachname, String adresse, String gebdat, String universitaet, String studienfach) {
        super(vorname, nachname, adresse, gebdat);
        this.studienfach = studienfach;
        this.universitaet = universitaet;
        this.bescheinigungen = new ArrayList<>();
    }

    /**
     * Getter fuer 'universitaet'
     * @return String, Universitaet
     */
    public String getUniversitaet() {
        return universitaet;
    }

    /**
     * Setter fuer 'universitaet'
     * @param universitaet
     */
    public void setUniversitaet(String universitaet) {
        this.universitaet = universitaet;
    }

    /**
     * Getter fuer 'studienfach'
     * @return String, studienfach
     */
    public String getStudienfach() {
        return studienfach;
    }

    /**
     * Setter fuer 'studienfach'
     * @param studienfach
     */
    public void setStudienfach(String studienfach) {
        this.studienfach = studienfach;
    }

    /**
     * Studienbescheinigung fuer das gegebene Semester einreichen
     * @param jahr String
     * @param zeitraum enum (Semester.Zeitraum), entweder SOMMERSEMESTER oder WINTERSEMESTER
     * @return boolean, ob die Bescheinigung nicht schon bereits vorhanden war
     */
    public boolean studienbescheinigungEinreichen(String jahr, Semester.Zeitraum zeitraum) {
        Semester semester = new Semester(zeitraum, jahr);
        return studienbescheinigungEinreichen(semester);
    }


    /**
     * Studienbescheinigung fuer das gegebene Semester einreichen
     * @param semester, Semester
     * @return boolean, ob die Bescheinigung nicht schon bereits vorhanden war
     */
    public boolean studienbescheinigungEinreichen(Semester semester) {
        if (!bescheinigungen.contains(semester)) {
            bescheinigungen.add(semester);
            return true;
        } else return false;
    }

    /**
     * loescht (wenn vorhanden) die Studienbescheinigung fuer das angegebene Semester
     * @param jahr String
     * @param zeitraum enum (Semester.Zeitraum), entweder SOMMERSEMESTER oder WINTERSEMESTER
     * @return boolean, ob eine Studienbescheinigung fuer das angegebene Semester vorhanden war
     */
    public boolean studienbescheinigungLoeschen(String jahr, Semester.Zeitraum zeitraum) {
        Semester semester = new Semester(zeitraum, jahr);
        return studienbescheinigungLoeschen(semester);
    }

    /**
     * loescht (wenn vorhanden) die Studienbescheinigung fuer das angegebene Semester
     * @param semester Semester
     * @return boolean, ob eine Studienbescheinigung fuer das angegebene Semester vorhanden war
     */
    public boolean studienbescheinigungLoeschen(Semester semester) {
        if (bescheinigungen.contains(semester)) {
            bescheinigungen.remove(semester);
            return true;
        } else return false;
    }

    /**
     * prueft, ob eine Studienbescheinigung fuer den angegebenen Zeitraum vorhanden ist
     * @param jahr, String
     * @param zeitraum enum (Semester.Zeitraum), entweder SOMMERSEMESTER oder WINTERSEMESTER
     * @return boolean, ob eine Studienbescheinigung fuer das angegebene Semester vorhanden ist
     */
    public boolean hatStudienbescheinigung(String jahr, Semester.Zeitraum zeitraum) {
        Semester semester = new Semester(zeitraum, jahr);
        return hatStudienbescheinigung(semester);
    }

    /**
     * prueft, ob eine Studienbescheinigung fuer den angegebenen Zeitraum vorhanden ist
     *
     * @param semester Semester
     * @return boolean, ob eine Studienbescheinigung fuer das angegebene Semester vorhanden ist
     */
    public boolean hatStudienbescheinigung(Semester semester) {
        return bescheinigungen.contains(semester);
    }

    @Override
    public String toString() {
        StringBuilder ausgabe = new StringBuilder(super.toString());
        ausgabe.append(this.studienfach).append(System.getProperty("line.separator"));
        ausgabe.append(this.universitaet).append(System.getProperty("line.separator"));
        ausgabe.append("Liste der Semester, fuer die eine Studienbescheinigung vorliegt: ").append(System.getProperty("line.separator"));
        for (Semester tempSemester : bescheinigungen) {
            switch (tempSemester.getZeitraum()) {

                case SOMMERSEMESTER: {
                    ausgabe.append("Sommersemester ").append(tempSemester.getJahr()).append(System.getProperty("line.separator"));
                }
                break;
                case WINTERSEMESTER: {
                    ausgabe.append("Wintersemester ").append(tempSemester.getJahr()).append(System.getProperty("line.separator"));
                }
                break;
            }

        }
        ausgabe.append("Ende der Liste der Studienbescheinigungen").append(System.getProperty("line.separator"));
        return ausgabe.toString();
    }

    /**
     * Klasse zum Speichern eines Semesters (Semester besteht aus Zeitraum und Jahr)
     */
    public static class Semester {


        /**
         * Zum Speichern des Zeitraums (Sommer- oder Wintersemester)
         */
        public enum Zeitraum {
            SOMMERSEMESTER,
            WINTERSEMESTER
        }

        /**
         * Speichert den Zeitraum
         */
        private Zeitraum zeitraum;
        /**
         * Speichert das Jahr
         */
        private String jahr;

        /**
         * Konstruktor
         * @param zeitraum entweder SOMMERSEMESTER oder WINTERSEMESTER
         * @param jahr String
         */
        public Semester(Zeitraum zeitraum, String jahr) {
            this.zeitraum = zeitraum;
            this.jahr = jahr;
        }

        /**
         * Getter fuer 'zeitraum'
         * @return zeitraum
         */
        public Zeitraum getZeitraum() {
            return zeitraum;
        }

        /**
         * Getter fuer 'jahr'
         * @return jahr
         */
        public String getJahr() {
            return jahr;
        }
    }


}
