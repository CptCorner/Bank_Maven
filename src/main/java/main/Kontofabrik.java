package main;

import verarbeitung.Konto;

public interface Kontofabrik {


    public Konto create();

    public void setNr(long nr);
}
