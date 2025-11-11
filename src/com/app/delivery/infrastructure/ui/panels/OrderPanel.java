package com.app.delivery.infrastructure.ui.panels;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    public OrderPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("🛵 Gestión de Pedidos", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}

