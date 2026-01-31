package atm;

/**
 * CashDispenser manages $20 bills. Starts with 500 $20 bills.
 */
public class CashDispenser {
    private int count20;

    public CashDispenser() {
        this.count20 = 500; // initial supply
    }

    public boolean hasSufficientCash(int amountCents) {
        // amount must be multiple of 2000 cents ($20)
        if (amountCents % 2000 != 0) return false;
        int needed = amountCents / 2000;
        return needed <= count20;
    }

    public boolean dispenseCash(int amountCents) {
        if (!hasSufficientCash(amountCents)) return false;
        int needed = amountCents / 2000;
        count20 -= needed;
        return true;
    }

    public int getCount20() {
        return count20;
    }

    public int getTotalCashCents() {
        return count20 * 2000;
    }
}
