package atm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DepositSlot simulates envelope insertion with a 2-minute timeout.
 * The GUI will call requestDeposit() which returns a DepositResult via callback.
 */
public class DepositSlot {

    public interface DepositListener {
        void onEnvelopeInserted();
        void onTimeout();
    }

    /**
     * Start waiting for envelope insertion for up to timeoutMillis milliseconds.
     * UI code will show a dialog with a button to simulate envelope insertion.
     */
    public void waitForEnvelope(int timeoutMillis, DepositListener listener) {
        // For GUI integration, the GUI will create its own Timer and button
        // so we only provide the listener interface. Implementation handled in GUI.
        // This placeholder remains for completeness if non-GUI logic used.
        Timer t = new Timer(timeoutMillis, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer)e.getSource()).stop();
                listener.onTimeout();
            }
        });
        t.setRepeats(false);
        t.start();
        // Note: GUI will call listener.onEnvelopeInserted() when user clicks "Insert Envelope".
    }
}
