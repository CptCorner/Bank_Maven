package main;

import verarbeitung.Konto;
import verarbeitung.Kunde;
import verarbeitung.Sparbuch;

public class SparbuchFabrik implements Kontofabrik {

    private Kunde kunde;
    private long nr;

    public SparbuchFabrik(Kunde kunde) {
        this.kunde = kunde;
    }


    @Override
    public Konto create() {
        return new Sparbuch(kunde, nr);
    }

    @Override
    public void setNr(long nr) {
        this.nr = nr;
    }
}
