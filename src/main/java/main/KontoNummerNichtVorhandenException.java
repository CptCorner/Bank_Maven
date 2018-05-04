package main;

public class KontoNummerNichtVorhandenException extends Exception {

    public KontoNummerNichtVorhandenException(long kontonummer)
    {
        super("Das Angefragte Konto mit der folgenden Kontonummer ist nicht vorhanden: " + kontonummer);
    }
}
