package atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ATMGui implements a Swing-based GUI that simulates the ATM screen and keypad.
 * Run this class (main).
 */
public class ATMGui {
    private final ATM atm;
    private final JFrame frame;
    private final JTextArea screenArea;
    private final JTextField inputField; // used with keypad
    private Account authenticatedAccount;

    // state tracking for input mode
    private enum Mode { AUTH_ACCOUNT, AUTH_PIN, MAIN_MENU, WITHDRAW_MENU, DEPOSIT_ENTRY }
    private Mode mode = Mode.AUTH_ACCOUNT;

    private Timer depositTimer; // for deposit timeout dialog

    public ATMGui() {
        // components
        Screen screen = new Screen();
        Keypad keypad = new Keypad();
        CashDispenser dispenser = new CashDispenser();
        DepositSlot depositSlot = new DepositSlot();
        BankDatabase db = new BankDatabase();

        atm = new ATM(screen, keypad, dispenser, depositSlot, db);

        frame = new JFrame("ATM Simulation");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        screenArea = new JTextArea();
        screenArea.setEditable(false);
        screenArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        screenArea.setLineWrap(true);
        screenArea.setWrapStyleWord(true);
        JScrollPane screenScroll = new JScrollPane(screenArea);
        screenScroll.setPreferredSize(new Dimension(400, 300));
        frame.add(screenScroll, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setFont(new Font("Monospaced", Font.BOLD, 18));
        rightPanel.add(inputField, BorderLayout.NORTH);

        JPanel keypadPanel = buildKeypadPanel();
        rightPanel.add(keypadPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton enterBtn = new JButton("ENTER");
        enterBtn.addActionListener(e -> handleEnter());
        JButton clearBtn = new JButton("CLEAR");
        clearBtn.addActionListener(e -> inputField.setText(""));
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(e -> {
            String t = inputField.getText();
            if (!t.isEmpty()) inputField.setText(t.substring(0, t.length() - 1));
        });
        bottomPanel.add(enterBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(backBtn);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(rightPanel, BorderLayout.EAST);

        // top status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel info = new JLabel("ATM Simulator — Sample accounts: 12345/11111 ($50), 54321/22222 ($1000), 11111/33333 ($250.75)");
        topPanel.add(info);
        frame.add(topPanel, BorderLayout.NORTH);

        // start
        showWelcome();

        frame.setVisible(true);
    }

    private JPanel buildKeypadPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            String s = String.valueOf(i);
            JButton b = new JButton(s);
            b.addActionListener(e -> inputField.setText(inputField.getText() + s));
            panel.add(b);
        }
        JButton b0 = new JButton("0");
        b0.addActionListener(e -> inputField.setText(inputField.getText() + "0"));
        panel.add(new JLabel("")); // placeholder
        panel.add(b0);
        panel.add(new JLabel(""));
        return panel;
    }

    private void showWelcome() {
        mode = Mode.AUTH_ACCOUNT;
        inputField.setText("");
        screenArea.setText("");
        appendScreen("Welcome!\nPlease enter your 5-digit account number:");
    }

    private void appendScreen(String s) {
        screenArea.append(s + "\n");
        screenArea.setCaretPosition(screenArea.getDocument().getLength());
    }

    private void handleEnter() {
        String input = inputField.getText().trim();
        switch (mode) {
            case AUTH_ACCOUNT:
                handleAccountEntry(input);
                break;
            case AUTH_PIN:
                handlePinEntry(input);
                break;
            case MAIN_MENU:
                handleMainMenuSelection(input);
                break;
            case WITHDRAW_MENU:
                handleWithdrawSelection(input);
                break;
            case DEPOSIT_ENTRY:
                handleDepositEntry(input);
                break;
            default:
                showWelcome();
        }
    }

    private void handleAccountEntry(String input) {
        if (input.length() != 5 || !input.matches("\\d{5}")) {
            appendScreen("Invalid account number format. Please enter 5 digits.");
            inputField.setText("");
            return;
        }
        appendScreen("Account entered: " + input);
        inputField.setText("");
        appendScreen("Please enter your 5-digit PIN:");
        mode = Mode.AUTH_PIN;
        // store temporarily the account number in the inputField's client property? simpler: parse later
        // Instead, set a temporary text by storing in inputField's name:
        inputField.putClientProperty("accountNumber", Integer.parseInt(input));
    }

    private void handlePinEntry(String input) {
        if (input.length() != 5 || !input.matches("\\d{5}")) {
            appendScreen("Invalid PIN format. Please enter 5 digits.");
            inputField.setText("");
            return;
        }
        Object acctObj = inputField.getClientProperty("accountNumber");
        if (acctObj == null) {
            appendScreen("Internal error. Restarting.");
            showWelcome();
            return;
        }
        int accountNumber = (int) acctObj;
        int pin = Integer.parseInt(input);
        boolean ok = atm.authenticate(accountNumber, pin);
        inputField.putClientProperty("accountNumber", null);
        inputField.setText("");
        if (ok) {
            authenticatedAccount = atm.getCurrentAccount();
            appendScreen("Authentication successful. Welcome, account " + accountNumber + "!");
            showMainMenu();
        } else {
            appendScreen("Invalid account number or PIN. Returning to welcome.");
            showWelcome();
        }
    }

    private void showMainMenu() {
        mode = Mode.MAIN_MENU;
        appendScreen("\n--- MAIN MENU ---");
        appendScreen("1. Balance inquiry");
        appendScreen("2. Withdrawal");
        appendScreen("3. Deposit");
        appendScreen("4. Exit");
        appendScreen("Enter choice (1-4):");
        inputField.setText("");
    }

    private void handleMainMenuSelection(String input) {
        if (!input.matches("[1-4]")) {
            appendScreen("Please enter 1, 2, 3, or 4.");
            inputField.setText("");
            return;
        }
        int choice = Integer.parseInt(input);
        inputField.setText("");
        switch (choice) {
            case 1:
                doBalanceInquiry();
                break;
            case 2:
                showWithdrawalMenu();
                break;
            case 3:
                promptDepositAmount();
                break;
            case 4:
                appendScreen("Thank you! Signing out.");
                atm.signOut();
                authenticatedAccount = null;
                showWelcome();
                break;
        }
    }

    private void doBalanceInquiry() {
        BalanceInquiry bi = new BalanceInquiry(authenticatedAccount, atm.getBankDatabase(), atm.getCashDispenser());
        appendScreen(bi.execute());
        showMainMenu();
    }

    private void showWithdrawalMenu() {
        mode = Mode.WITHDRAW_MENU;
        appendScreen("\n--- WITHDRAWAL MENU ---");
        appendScreen("1. $20");
        appendScreen("2. $40");
        appendScreen("3. $60");
        appendScreen("4. $100");
        appendScreen("5. $200");
        appendScreen("6. Cancel");
        appendScreen("Enter choice (1-6):");
        inputField.setText("");
    }

    private void handleWithdrawSelection(String input) {
        if (!input.matches("[1-6]")) {
            appendScreen("Enter a number between 1 and 6.");
            inputField.setText("");
            return;
        }
        int sel = Integer.parseInt(input);
        inputField.setText("");
        if (sel == 6) {
            appendScreen("Transaction cancelled. Returning to main menu.");
            showMainMenu();
            return;
        }
        int[] amounts = {2000, 4000, 6000, 10000, 20000}; // cents
        int requested = amounts[sel - 1];
        Withdrawal w = new Withdrawal(authenticatedAccount, atm.getBankDatabase(), atm.getCashDispenser(), requested);
        String result = w.execute();
        appendScreen(result);
        if (result.startsWith("Please take your cash")) {
            appendScreen("Reminder: take your cash now.");
        }
        showMainMenu();
    }

    private void promptDepositAmount() {
        mode = Mode.DEPOSIT_ENTRY;
        appendScreen("Enter deposit amount in cents (e.g., 125 for $1.25) or 0 to cancel:");
        inputField.setText("");
    }

    private void handleDepositEntry(String input) {
        if (!input.matches("\\d+")) {
            appendScreen("Please enter only digits representing cents.");
            inputField.setText("");
            return;
        }
        int cents = Integer.parseInt(input);
        inputField.setText("");
        if (cents == 0) {
            appendScreen("Deposit canceled. Returning to main menu.");
            showMainMenu();
            return;
        }
        // prompt to insert envelope within 2 minutes
        appendScreen(String.format("Please insert a deposit envelope for $%,d.%02d within 2 minutes.", cents/100, Math.abs(cents%100)));
        showDepositDialog(cents);
    }

    private void showDepositDialog(int cents) {
        JDialog dialog = new JDialog(frame, "Insert Deposit Envelope", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());
        JLabel lbl = new JLabel(String.format("<html>Please insert your deposit envelope containing $%,d.%02d.<br>Click 'Insert Envelope' to simulate insertion.<br>Dialog will timeout in 2 minutes.</html>",
                cents/100, Math.abs(cents%100)));
        lbl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        dialog.add(lbl, BorderLayout.CENTER);

        JPanel p = new JPanel();
        JButton insertBtn = new JButton("Insert Envelope");
        JButton cancelBtn = new JButton("Cancel");
        p.add(insertBtn);
        p.add(cancelBtn);
        dialog.add(p, BorderLayout.SOUTH);

        final boolean[] envelopeInserted = {false};

        // timer for 2 minutes
        depositTimer = new Timer(120_000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositTimer.stop();
                if (!envelopeInserted[0]) {
                    dialog.dispose();
                    appendScreen("Transaction canceled due to inactivity (no envelope).");
                    showMainMenu();
                }
            }
        });
        depositTimer.setRepeats(false);
        depositTimer.start();

        insertBtn.addActionListener(e -> {
            envelopeInserted[0] = true;
            if (depositTimer != null) depositTimer.stop();
            dialog.dispose();
            // create deposit transaction and execute
            Deposit d = new Deposit(authenticatedAccount, atm.getBankDatabase(), atm.getCashDispenser(), cents);
            String res = d.execute();
            appendScreen(res);
            showMainMenu();
        });

        cancelBtn.addActionListener(e -> {
            if (depositTimer != null) depositTimer.stop();
            dialog.dispose();
            appendScreen("Deposit canceled by user. Returning to main menu.");
            showMainMenu();
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMGui());
    }
}
