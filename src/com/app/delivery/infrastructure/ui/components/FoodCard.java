package com.app.delivery.infrastructure.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodCard extends JPanel {
    private String name;
    private double price;
    private JLabel lblName, lblPrice;
    private JLabel lblImage;
    private boolean selected = false;

    public FoodCard(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // === Imagen ===
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaled = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        lblImage = new JLabel(new ImageIcon(scaled));
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblImage, BorderLayout.CENTER);

        // === Texto ===
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setBackground(Color.WHITE);

        lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblPrice = new JLabel(String.format("₡ %.2f", price), SwingConstants.CENTER);
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPrice.setForeground(new Color(33, 150, 243));

        info.add(lblName);
        info.add(lblPrice);
        add(info, BorderLayout.SOUTH);

        // === Efecto de selección ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(250, 250, 250));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }

    public boolean isSelected() {
        return selected;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    private void toggleSelection() {
        selected = !selected;
        updateCardStyle();
    }

    private void updateCardStyle() {
        if (selected) {
            setBackground(new Color(232, 245, 233)); // verde claro
            setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 3, true));
        } else {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        }
        repaint();
    }

    public void setSelected(boolean _selected){
        this.selected = _selected;
        setBackground(selected ? new Color(200, 230, 255) : Color.WHITE);
    }

    public void clearSelected(){
        this.selected = false;
        updateCardStyle();
    }
}
