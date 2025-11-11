import com.app.delivery.infrastructure.persistence.DatabaseInitializar;
import com.app.delivery.infrastructure.ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializar.initialize();

        SwingUtilities.invokeLater(
                ()-> {
                    new MainFrame().setVisible(true);
                }
        );
    }
}