package com.app.delivery.infrastructure.ui;

import com.app.delivery.infrastructure.ui.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Delivery App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === HEADER ===
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        headerPanel.setLayout(new BorderLayout());

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/icons/delivery-scrooter.png"));
        Image scaledImage = rawIcon.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        ImageIcon appIcon = new ImageIcon(scaledImage);

        JLabel titleLabel = new JLabel("Delivery App", appIcon, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        titleLabel.setIconTextGap(10);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        setIconImage(rawIcon.getImage());

        // === SIDEBAR ===
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setLayout(new GridLayout(6, 1, 0, 8));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnRestaurants = createStyledButton("Restaurantes", "/icons/rest_ico.png");
        JButton btnMenu = createStyledButton("Menú", "/icons/food.png");
        JButton btnOrders = createStyledButton("Pedidos", "/icons/order.png");
        JButton btnCash = createStyledButton("Caja", "/icons/cash-register.png");
        JButton btnExit = createStyledButton("Salir", "/icons/salir.png");

        sidePanel.add(btnRestaurants);
        sidePanel.add(btnMenu);
        sidePanel.add(btnOrders);
        sidePanel.add(btnCash);
        sidePanel.add(new JLabel());
        sidePanel.add(btnExit);

        add(sidePanel, BorderLayout.WEST);

        // === MAIN PANEL ===
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Agregar los paneles (pantallas)
        RestaurantPanel restaurantPanel = new RestaurantPanel();
        MenuPanel menuPanel = new MenuPanel();
        OrderPanel orderPanel = new OrderPanel();
        CashPanel cashPanel = new CashPanel();

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(restaurantPanel, "RESTAURANTS");
        mainPanel.add(orderPanel, "ORDERS");
        mainPanel.add(cashPanel, "CASH");

        //Navegación
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        ActionListener navListener = e -> handleNavigation(e.getActionCommand());

        btnRestaurants.setActionCommand("RESTAURANTS");
        btnMenu.setActionCommand("MENU");
        btnOrders.setActionCommand("ORDERS");
        btnCash.setActionCommand("CASH");
        btnExit.setActionCommand("EXIT");

        btnRestaurants.addActionListener(navListener);
        btnMenu.addActionListener(navListener);
        btnOrders.addActionListener(navListener);
        btnCash.addActionListener(navListener);
        btnExit.addActionListener(navListener);

    }

    private void navigateTo(String view) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, view);
    }

    private void handleNavigation(String action) {
        switch (action) {
            case "RESTAURANTS" -> navigateTo("RESTAURANTS");
            case "MENU" -> navigateTo("MENU");
            case "ORDERS" -> navigateTo("ORDERS");
            case "CASH" -> navigateTo("CASH");
            case "EXIT" -> System.exit(0);
            default -> System.out.println("Acción desconocida: " + action);
        }
    }

    // === Crear botones ===
    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text, new ImageIcon(getClass().getResource(iconPath)));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(224, 247, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }
}
