package verarbeitung;

import java.io.Serializable;

/**
 * Liste aller angebotenen Kontoarten
 * @author Doro
 *
 */
public enum Kontoart implements Serializable {
    /**
     * ein Girokonto
     */
    GIROKONTO("ganz hoher Dispo"),
    /**
     * ein Sparbuch
     */
    SPARBUCH("ganz viele Zinsen"),
    /**
     * ein Festgeldkonto
     */
    FESTGELDKONTO("nur Dummy");

    private Kontoart(String text)
    {
        this.werbebotschaft = text;
    }

    private String werbebotschaft;

    /**
     * die Werbeung dieser Kontoart
     * @return
     */
    public String getWerbebotschaft() {
        return werbebotschaft;
    }


}
