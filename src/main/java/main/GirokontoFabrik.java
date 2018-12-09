package main;

import verarbeitung.Girokonto;
import verarbeitung.Konto;
import verarbeitung.Kunde;

public class GirokontoFabrik implements Kontofabrik {

    private Kunde kunde;
    private long nr;
    private double dispo;

    public GirokontoFabrik(Kunde kunde, double dispo) {
        this.kunde = kunde;
        this.dispo = dispo;
    }

    @Override
    public Konto create() {
        return new Girokonto(kunde, nr, dispo);
    }

    @Override
    public void setNr(long nr) {
        this.nr = nr;
    }
}
