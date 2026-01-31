package atm;

/**
 * Account stores account number, PIN and balance in cents (int).
 * Use cents to avoid floating point rounding issues.
 */
public class Account {
    private final int accountNumber; // 5-digit
    private final int pin; // 5-digit
    private int balanceCents; // integer cents

    public Account(int accountNumber, int pin, int balanceCents) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balanceCents = balanceCents;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePIN(int inputPin) {
        return this.pin == inputPin;
    }

    public int getBalanceCents() {
        return balanceCents;
    }

    public String getFormattedBalance() {
        return String.format("$%,d.%02d", balanceCents / 100, Math.abs(balanceCents % 100));
    }

    public void credit(int amountCents) {
        // add amount (deposit)
        this.balanceCents += amountCents;
    }

    public boolean debit(int amountCents) {
        // withdraw amount if possible
        if (amountCents <= balanceCents) {
            balanceCents -= amountCents;
            return true;
        } else {
            return false;
        }
    }
}
