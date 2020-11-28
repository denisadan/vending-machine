package vendingmachine.model;

public enum Coin {
    PENNY_1("PENNY"),
    NICKEL_5("NICKEL"),
    DIME_10("DIME"),
    QUARTER_25("QUARTER");

   private String value;

    Coin(String val) {
        this.value = val;
    }

    @Override
    public String toString() {
        return value;
    }
}
