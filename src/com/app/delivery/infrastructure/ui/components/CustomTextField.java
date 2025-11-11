package com.app.delivery.infrastructure.ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomTextField extends JTextField {

    private String placeholder;

    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        setBackground(new Color(250, 250, 250));
        setText(placeholder);
        setForeground(Color.GRAY);

        // Efecto hover
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        });

        // Placeholder dinámico
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    public String getRealText() {
        return getText().equals(placeholder) ? "" : getText();
    }
}

