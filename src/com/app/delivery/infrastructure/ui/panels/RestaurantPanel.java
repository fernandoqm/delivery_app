package com.app.delivery.infrastructure.ui.panels;

import com.app.delivery.core.domain.models.Restaurant;
import com.app.delivery.infrastructure.persistence.repository.RestaurantRepository;
import com.app.delivery.infrastructure.ui.components.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RestaurantPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private RestaurantRepository repo;

    public RestaurantPanel() {
        repo = new RestaurantRepository();
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // === Encabezado ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel lblIcon = new JLabel(new ImageIcon(getClass().getResource("/icons/rest_ico.png")));
        lblIcon.setPreferredSize(new Dimension(40, 40));

        CustomLabel lblTitle = new CustomLabel("Restaurantes");
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 150, 243));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(lblIcon);
        titlePanel.add(lblTitle);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // === Tabla ===
        model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Dirección", "Teléfono", "Activo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // === Botonera ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        CustomButton btnAdd = new CustomButton("Nuevo");
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/resources/icons/add.png")));
        btnAdd.setIconTextGap(8);

        CustomButton btnEdit = new CustomButton("Editar");
        btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
        btnEdit.setIconTextGap(8);

        CustomButton btnDelete = new CustomButton("Eliminar");
        btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        btnDelete.setIconTextGap(8);
        btnDelete.setDangerStyle();

        CustomButton btnRefresh = new CustomButton("Actualizar");
        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/icons/refresh.png")));
        btnRefresh.setIconTextGap(8);
        btnRefresh.setSecondaryStyle();

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // === Acciones ===
        btnRefresh.addActionListener(e -> loadRestaurants());
        btnAdd.addActionListener(e -> openForm(null));
        btnEdit.addActionListener(e -> editSelected());
        btnDelete.addActionListener(e -> deleteSelected());

        // === Cargar datos iniciales ===
        loadRestaurants();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.setSelectionBackground(new Color(232, 245, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
    }

    public void loadRestaurants() {
        model.setRowCount(0);
        List<Restaurant> list = repo.findAll();

        for (Restaurant r : list) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getAddress(),
                    r.getPhone(),
                    r.isActive() ? "Sí" : "No"
            });
        }
    }

    private void openForm(Restaurant restaurant) {
        RestaurantFormDialog dialog = new RestaurantFormDialog(restaurant, repo, this);
        dialog.setVisible(true);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un restaurante para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Restaurant r = new Restaurant(
                (int) model.getValueAt(row, 0),
                (String) model.getValueAt(row, 1),
                (String) model.getValueAt(row, 2),
                (String) model.getValueAt(row, 3),
                "Sí".equals(model.getValueAt(row, 4))
        );
        openForm(r);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un restaurante para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este restaurante?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(row, 0);
            repo.delete(id);
            loadRestaurants();
        }
    }
}
