package main;

import verarbeitung.*;

import java.time.LocalDate;

/**
 * Testprogramm fuer Konten
 * @author Doro
 *
 */
public class Kontentest {

    /**
     * Testprogramm fuer Konten
     * @param args wird nicht benutzt
     */
    public static void main(String[] args) {
		/*Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause",
				LocalDate.parse("1976-07-13")); */

        Student ich = new Student(
                "Jakob",
                "Wendt",
                "Daheim",
                LocalDate.parse("1998-09-02"),
                "HTW Berlin",
                "Angwandte Informatik");

        Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
        meinGiro.einzahlen(50);
        System.out.println(meinGiro);

        Sparbuch meinSpar = new Sparbuch(ich, 9876);
        meinSpar.einzahlen(50);
        try
        {
            boolean hatGeklappt = meinSpar.abheben(70);
            System.out.println("Abhebung hat geklappt: " + hatGeklappt);
            System.out.println(meinSpar);
        }
        catch (GesperrtException e)
        {
            System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
        }

        Konto ko = new Girokonto(); //Zuweisungskompatibilitaet
        System.out.println(ko.toString());  //Polymorphie

        int a = 100;
        int b;
        b = a;  //Kopie
        b = b + 50;
        System.out.println(a);  //100

        Konto bo = ko;  //Referenzen
        bo.einzahlen(50);
        System.out.println(ko.getKontostand());  //50

        try {
            ko.setInhaber(ich);

            ich.setAdresse("GanzWoandersHin");
            System.out.println(ko.getInhaber().getAdresse());

            ko.getInhaber().setAdresse("Und wieder zurueck");


        } catch (GesperrtException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Kontoart art;
        art = Kontoart.GIROKONTO;
        // verboten: art = new Kontoart();
        System.out.println(art.ordinal() + " " + art.name());
        art = Kontoart.valueOf("SPARBUCH");
        System.out.println(art);

        Kontoart[] alle = Kontoart.values();
        for(int i =0; i < alle.length; i++)
            System.out.println(alle[i] + ":" + alle[i].getWerbebotschaft());





    }

}
