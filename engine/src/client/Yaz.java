package client;

import java.io.Serializable;

public class Yaz implements Serializable {
    private int yaz;

/*
    public Yaz() {
        yaz = 1;
    }
*/

    public int getYaz() {
        return yaz;
    }

    public void setYaz(int yaz) {
        this.yaz = yaz;
    }

}
