package id.co.meda.survey.model;

/**
 * Created by Faris on 28/04/2016.
 */
public class Voucher {
    private String name;
    private int voucher;

    public Voucher(String name, int voucher) {
        this.name = name;
        this.voucher = voucher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoucher() {
        return voucher;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }
}
