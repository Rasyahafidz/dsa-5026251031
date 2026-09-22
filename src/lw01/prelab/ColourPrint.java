package lw1.prelab;

public class ColourPrint extends PrintJob {

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int pages = getPages();
        int charge = 2000; // Biaya setup tetap

        if (pages <= 10) {
            charge += pages * 1500;
        } else {
            // 10 halaman pertama x 1500, sisa halaman x 1000
            charge += (10 * 1500) + ((pages - 10) * 1000);
        }

        return charge;
    }

    @Override
    public String label() {
        return "Colour";
    }
}
