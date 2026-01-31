package atm;

/**
 * Abstract Transaction class
 */
public abstract class Transaction {
    protected final Account account;
    protected final BankDatabase bankDatabase;
    protected final CashDispenser cashDispenser;

    public Transaction(Account account, BankDatabase db, CashDispenser dispenser) {
        this.account = account;
        this.bankDatabase = db;
        this.cashDispenser = dispenser;
    }

    // execute will return a message describing result
    public abstract String execute();
}
