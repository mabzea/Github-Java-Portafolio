package atm;

/**
 * Deposit transaction: deposit amount (in cents).
 * In this simplified simulation, deposit credits the account immediately, but is marked as pending verification.
 */
public class Deposit extends Transaction {
    private final int amountCents;

    public Deposit(Account account, BankDatabase db, CashDispenser dispenser, int amountCents) {
        super(account, db, dispenser);
        this.amountCents = amountCents;
    }

    @Override
    public String execute() {
        if (amountCents <= 0) {
            return "Deposit cancelled or invalid amount.";
        }
        account.credit(amountCents);
        return String.format("Deposit received (pending verification). Amount credited: $%,d.%02d",
                amountCents / 100, Math.abs(amountCents % 100));
    }
}
