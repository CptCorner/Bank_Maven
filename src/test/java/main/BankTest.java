package main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import verarbeitung.Kunde;

import java.time.LocalDate;
import java.util.List;

public class BankTest {

    private Bank testBank;

    @Before
    public void Preparation() {
        testBank = new Bank(559720);
    }

    @Test
    public void getBankleitzahl() {
        Assert.assertEquals((long) 559720, testBank.getBankleitzahl());
    }

    @Test
    public void girokontoErstellen() {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 0);
        Assert.assertNotSame(kontonr1, kontonr2);
        Assert.assertEquals((long) 1, kontonr1);
        Assert.assertEquals((long) 2, kontonr2);
    }

    @Test
    public void sparbuchErstellen() {
        long kontonr1 = testBank.sparbuchErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()));
        long kontonr2 = testBank.sparbuchErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()));
        Assert.assertNotSame(kontonr1, kontonr2);
        Assert.assertEquals((long) 1, kontonr1);
        Assert.assertEquals((long) 2, kontonr2);
    }

    @Test
    public void geldAbheben() {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        Assert.assertFalse(testBank.geldAbheben(kontonr1, 700));
        Assert.assertTrue(testBank.geldAbheben(kontonr1, 0));
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 500);
        Assert.assertTrue(testBank.geldAbheben(kontonr2, 300));
        Assert.assertFalse(testBank.geldAbheben(kontonr2, 699));
        long kontonr3 = testBank.sparbuchErstellen(new Kunde("Kunde", "drei", "Adresse", LocalDate.now()));
        Assert.assertFalse(testBank.geldAbheben(kontonr3, 700));
    }

    @Test
    public void geldEinzahlen() throws KontoNummerNichtVorhandenException {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        testBank.geldEinzahlen(kontonr1, 500);
        Assert.assertEquals((double) 500, testBank.getKontostand(kontonr1));
    }

    @Test
    public void kontoLoeschen() {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);

        Assert.assertEquals(1, testBank.getAlleKontonummern().size());
        testBank.kontoLoeschen(kontonr1);
        Assert.assertEquals(0, testBank.getAlleKontonummern().size());
    }

    @Test
    public void getKontostand() throws KontoNummerNichtVorhandenException {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        testBank.geldEinzahlen(kontonr1, 500);
        Assert.assertEquals((double) 500, testBank.getKontostand(kontonr1));
        testBank.geldAbheben(kontonr1, 200);
        Assert.assertEquals((double) 300, testBank.getKontostand(kontonr1));
    }

    @Test
    public void geldUeberweisen() throws KontoNummerNichtVorhandenException {

        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 0);

        Assert.assertFalse(testBank.geldUeberweisen(kontonr1, kontonr2, 120000, "Zweck"));

        testBank.geldEinzahlen(kontonr1, 500);
        Assert.assertEquals((double) 500, testBank.getKontostand(kontonr1));

        testBank.geldUeberweisen(kontonr1, kontonr2, 200, "Zweck 2");

        Assert.assertEquals((double) 300, testBank.getKontostand(kontonr1));
        Assert.assertEquals((double) 200, testBank.getKontostand(kontonr2));

    }

    @Test
    public void getAlleKonten() {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 0);
        long kontonr3 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);
        System.out.println(testBank.getAlleKonten());

        testBank.kontoLoeschen(kontonr2);
        System.out.println(testBank.getAlleKonten());

    }

    @Test
    public void getAlleKontonummern() {
        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 0);
        long kontonr3 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);


        Assert.assertEquals(3, testBank.getAlleKontonummern().size());
        Assert.assertTrue(testBank.getAlleKontonummern().contains(kontonr1));
        Assert.assertTrue(testBank.getAlleKontonummern().contains(kontonr2));
        Assert.assertTrue(testBank.getAlleKontonummern().contains(kontonr3));

        testBank.kontoLoeschen(kontonr2);
        Assert.assertEquals(2, testBank.getAlleKontonummern().size());

        Assert.assertTrue(testBank.getAlleKontonummern().contains(kontonr1));
        Assert.assertFalse(testBank.getAlleKontonummern().contains(kontonr2));
        Assert.assertTrue(testBank.getAlleKontonummern().contains(kontonr3));

    }

    @Test
    public void pleitegeierSperren() {
    }

    @Test
    public void getKundenMitVollemKonto() {
    }

    @Test
    public void getKundengeburtstage() {

        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now().minusDays(2)), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now().minusDays(6)), 0);
        long kontonr3 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);

        System.out.println(testBank.getKundengeburtstage());

    }

    @Test
    public void getKontonummernLuecken() {

        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        long kontonr2 = testBank.girokontoErstellen(new Kunde("Kunde", "Zwei", "Adresse", LocalDate.now()), 0);
        long kontonr3 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);
        long kontonr4 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);
        long kontonr5 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);
        long kontonr6 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);
        long kontonr7 = testBank.girokontoErstellen(new Kunde("Kunde", "Drei", "Adresse", LocalDate.now()), 0);

        testBank.kontoLoeschen(kontonr2);
        testBank.kontoLoeschen(kontonr3);
        testBank.kontoLoeschen(kontonr5);
        testBank.kontoLoeschen(kontonr7);

        List<Long> luecken = testBank.getKontonummernLuecken();

        for (Long tempLuecke : luecken) {
            System.out.println(tempLuecke);
        }
    }


    @Test
    public void cloneTest() {
        //used other names than demanded for better clarity

        long kontonr1 = testBank.girokontoErstellen(new Kunde("Kunde", "Eins", "Adresse", LocalDate.now()), 0);
        try {
            Bank clonedBank = (Bank) testBank.clone();


            testBank.geldEinzahlen(kontonr1, 12);

            System.out.println(testBank.getKontostand(kontonr1));
            System.out.println(clonedBank.getKontostand(kontonr1));
            Assert.assertNotSame(testBank.getKontostand(kontonr1), clonedBank.getKontostand(kontonr1));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (KontoNummerNichtVorhandenException e) {
            e.printStackTrace();
        }
    }
}