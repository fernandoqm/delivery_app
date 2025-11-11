package com.app.delivery.infrastructure.ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    private Color mainColor = new Color(33, 150, 243); // Azul principal
    private Color hoverColor = new Color(25, 118, 210);

    public CustomButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setBackground(mainColor);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(mainColor);
            }
        });
    }

    public void setSecondaryStyle() {
        setBackground(new Color(189, 189, 189));
        setForeground(Color.BLACK);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(158, 158, 158));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(189, 189, 189));
            }
        });
    }

    public void setDangerStyle() {
        Color danger = new Color(244, 67, 54);
        Color dangerHover = new Color(211, 47, 47);

        setBackground(danger);
        setForeground(Color.WHITE);


        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(dangerHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(danger);
            }
        });
    }

}
