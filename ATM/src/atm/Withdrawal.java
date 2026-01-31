package atm;

public class Withdrawal extends Transaction {
    private final int amountCents;

    public Withdrawal(Account account, BankDatabase db, CashDispenser dispenser, int amountCents) {
        super(account, db, dispenser);
        this.amountCents = amountCents;
    }

    @Override
    public String execute() {
        if (amountCents <= 0) return "Invalid withdrawal amount.";
        if (amountCents % 2000 != 0) return "Withdrawals must be in $20 increments.";
        if (amountCents > account.getBalanceCents()) {
            return "Insufficient funds. Please select a smaller amount.";
        }
        if (!cashDispenser.hasSufficientCash(amountCents)) {
            return "ATM does not have enough cash. Please choose a smaller amount.";
        }
        boolean debited = account.debit(amountCents);
        if (!debited) {
            return "Error debiting account. Transaction canceled.";
        }
        boolean dispensed = cashDispenser.dispenseCash(amountCents);
        if (!dispensed) {
            // rollback
            account.credit(amountCents);
            return "Error dispensing cash. Transaction canceled.";
        }
        return String.format("Please take your cash: $%,d.%02d", amountCents / 100, Math.abs(amountCents % 100));
    }
}
