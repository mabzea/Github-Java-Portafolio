package atm;

public class BalanceInquiry extends Transaction {

    public BalanceInquiry(Account account, BankDatabase db, CashDispenser dispenser) {
        super(account, db, dispenser);
    }

    @Override
    public String execute() {
        return "Your account balance is: " + account.getFormattedBalance();
    }
}
