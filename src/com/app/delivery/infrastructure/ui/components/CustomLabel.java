package com.app.delivery.infrastructure.ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(new Color(60, 60, 60));
    }
}
