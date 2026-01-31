package atm;

/**
 * ATM provides higher-level operations and composes hardware components and DB.
 */
public class ATM {
    private final Screen screen;
    private final Keypad keypad;
    private final CashDispenser cashDispenser;
    private final DepositSlot depositSlot;
    private final BankDatabase bankDatabase;

    private Account currentAccount;

    public ATM(Screen screen, Keypad keypad, CashDispenser dispenser, DepositSlot slot, BankDatabase db) {
        this.screen = screen;
        this.keypad = keypad;
        this.cashDispenser = dispenser;
        this.depositSlot = slot;
        this.bankDatabase = db;
    }

    public boolean authenticate(int accountNumber, int pin) {
        Account acc = bankDatabase.getAccount(accountNumber);
        if (acc != null && acc.validatePIN(pin)) {
            currentAccount = acc;
            return true;
        }
        currentAccount = null;
        return false;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void signOut() {
        currentAccount = null;
    }

    public BankDatabase getBankDatabase() {
        return bankDatabase;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public DepositSlot getDepositSlot() {
        return depositSlot;
    }
}
