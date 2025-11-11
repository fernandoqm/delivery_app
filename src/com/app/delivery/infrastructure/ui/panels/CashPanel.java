package com.app.delivery.infrastructure.ui.panels;

import javax.swing.*;
import java.awt.*;

public class CashPanel extends JPanel {
    public CashPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("💰 Gestión de Caja", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
