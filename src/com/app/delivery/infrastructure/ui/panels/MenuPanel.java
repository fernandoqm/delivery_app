package com.app.delivery.infrastructure.ui.panels;

import com.app.delivery.infrastructure.ui.components.*;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuPanel extends JPanel {

    private List<FoodCard> foodCards = new ArrayList<>();
    private JLabel lblTotal;

    public MenuPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //Encabezado
        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setOpaque(false);

        // === Título ===
        CustomLabel lblTitle = new CustomLabel("Menú del Día");
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 150, 243));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlHeader.add(lblTitle);

        CustomLabel lblSubTitle = new CustomLabel("Click sobre la imagen para seleccionar un producto." );
        lblSubTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        lblSubTitle.setForeground(new Color(33, 150, 243));
        lblSubTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlHeader.add(Box.createVerticalStrut(5));
        pnlHeader.add(lblSubTitle);

        add(pnlHeader, BorderLayout.NORTH);

        // === Comidas ===
        JPanel grid = new JPanel(new GridLayout(0, 3, 15, 15));
        grid.setBackground(Color.WHITE);

        foodCards.add(new FoodCard("Hamburguesa", 3500, "/foods/burger.png"));
        foodCards.add(new FoodCard("Pizza", 4200, "/foods/pizza.png"));
        foodCards.add(new FoodCard("Ensalada", 2800, "/foods/ensalada.png"));
        foodCards.add(new FoodCard("Taco", 2200, "/foods/tacos.png"));
        foodCards.add(new FoodCard("Café", 1500, "/foods/cafe.png"));
        foodCards.add(new FoodCard("Postre", 2000, "/foods/postres.png"));

        for (FoodCard card : foodCards) {
            grid.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // === Barra inferior total===
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottom.setBackground(Color.WHITE);

        lblTotal = new JLabel("Total: ₡ 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(new Color(33, 150, 243));

        CustomButton btnCalcularTotal = new CustomButton("Calcular Total");
        CustomButton btnLimpiar = new CustomButton("Vaciar");
        btnLimpiar.setDangerStyle();

        bottom.add(lblTotal);
        bottom.add(btnCalcularTotal);
        bottom.add(btnLimpiar);
        add(bottom, BorderLayout.SOUTH);

        // === Acciones ===

        btnCalcularTotal.addActionListener(e -> updateTotal());
        btnLimpiar.addActionListener(e -> clearSelection());
    }

    private void updateTotal() {
        double total = 0;
        for (FoodCard card : foodCards) {
            if (card.isSelected()) total += card.getPrice();
        }
        lblTotal.setText(String.format("Total: %s", formatCurrency(total)));
    }

    private void clearSelection() {
        double total = 0;
        for (FoodCard card : foodCards) {
            if (card.isSelected()) card.clearSelected();
        }
        lblTotal.setText(String.format("Total: %s", formatCurrency(0)));
    }

    private String formatCurrency(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "CR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return "₡" + nf.format(amount);
    }
}

