package atm;

import java.util.HashMap;
import java.util.Map;

/**
 * BankDatabase stores accounts. Single-ATM simplified DB.
 */
public class BankDatabase {
    private final Map<Integer, Account> accounts = new HashMap<>();

    public BankDatabase() {
        // sample accounts (accountNumber -> Account)
        // 1) account 12345 PIN 11111 $50.00
        accounts.put(12345, new Account(12345, 11111, 5000));
        // 2) account 54321 PIN 22222 $1000.00
        accounts.put(54321, new Account(54321, 22222, 100000));
        // 3) account 11111 PIN 33333 $250.75
        accounts.put(11111, new Account(11111, 33333, 25075));
    }

    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }
}
