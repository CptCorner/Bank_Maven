package main;

import verarbeitung.Konto;

public class ConsoleObserver extends Observer {
    public ConsoleObserver(Konto konto) {
        this.subject = konto;
    }

    @Override
    public void update() {
        System.out.println("Konto " + subject.getKontonummerFormatiert() + " wurde verändert!");
        System.out.println("Neuer Status:");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(subject.toString());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");
    }
}
