package verarbeitung;

import static org.junit.Assert.*;

/**
 * @author Jakob Wendt, s0559720
 */
public class WaehrungTest {

    @org.junit.Test
    public void euroInWaehrungUmrechnen() {
        Waehrung w1 = Waehrung.EUR;
        assertEquals(5, w1.euroInWaehrungUmrechnen(5), 0);
        Waehrung w2 = Waehrung.BGN;
        assertEquals(50.85158, w2.euroInWaehrungUmrechnen(26), 0);
        Waehrung w3 = Waehrung.LTL;
        assertEquals(0, w3.euroInWaehrungUmrechnen(0), 0);
        Waehrung w4 = Waehrung.KM;
        assertEquals(-3.91166, w4.euroInWaehrungUmrechnen(-2), 0);
    }

    @org.junit.Test
    public void waehrungInEuroUmrechnen() {
        Waehrung w1 = Waehrung.EUR;
        assertEquals(27, w1.waehrungInEuroUmrechnen(27), 0);

        Waehrung w2 = Waehrung.BGN;
        assertEquals(69.0244039614895, w2.waehrungInEuroUmrechnen(135), 0);

        Waehrung w3 = Waehrung.LTL;
        assertEquals(0, w3.waehrungInEuroUmrechnen(0), 0);

        Waehrung w4 = Waehrung.KM;
        assertEquals(-31.18880475296933, w4.waehrungInEuroUmrechnen(-61), 0);
    }
}