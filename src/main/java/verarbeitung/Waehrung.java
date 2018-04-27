package verarbeitung;


import org.jetbrains.annotations.Contract;

/**
 * enthaelt Euro und die durch Euro definierten Waehrungen
 *
 * @author Jakob Wendt, s0559720
 */
public enum Waehrung {
    /**
     * Euro
     */
    EUR,

    /**
     * Bulgarische Leva
     */
    BGN,
    /**
     * Litauische Lita
     */
    LTL,

    /**
     * Konvertible Mark
     */
    KM;

    /**
     * Wechselkurse (zu EUR)
     */
    @SuppressWarnings("FieldCanBeLocal")
    private double worthOfEUR = 1;
    private double worthOfBGN = 1.95583;
    private double worthOfKM = 1.95583;
    private double worthOfLTL = 3.4528;

    /**
     * Rechnet den in Euro angegebenen Betrag in die aktuelle Waehrung um
     *
     * @param betrag double
     * @return den Betrag in der aktuellen Waehrung (double)
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public double euroInWaehrungUmrechnen(double betrag) {
        double output = 0;
        switch (this) {
            case EUR:
                output = worthOfEUR * (betrag * worthOfEUR);
                break;
            case BGN:
                output = worthOfBGN * (betrag * worthOfEUR);
                break;
            case LTL:
                output = worthOfLTL * (betrag * worthOfEUR);
                break;
            case KM:
                output = worthOfKM * (betrag * worthOfEUR);
                break;
                /*(betrag * worthOfEUR) ist in diesem Fall nicht notwendig, da der Euro durch sich selbst (und die Zahl 1) definiert wird
                Sollte sich diese Definition jedoch aendern (oder eine eine andere Waehrung als Transfer-Waehrung genutzt werden,
                wird diese zusaetzliche Multiplikation wichtig.
                 */
        }
        return output;
    }

    /**
     * Rechnet den in der aktuellen Waehrung angegebenen Betrag in Euro um
     *
     * @param betrag double
     * @return den Betrag in Euro (double)
     */

    @Contract(pure = true)
    public double waehrungInEuroUmrechnen(double betrag) {

        double output = 0;

        switch (this) {
            case EUR:
                output = betrag / worthOfEUR;
                break;
            case BGN:
                output = betrag / worthOfBGN;
                break;
            case LTL:
                output = betrag / worthOfLTL;
                break;
            case KM:
                output = betrag / worthOfKM;
                break;
        }
        return output;
    }
}