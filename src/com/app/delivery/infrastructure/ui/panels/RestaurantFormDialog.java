package com.app.delivery.infrastructure.ui.panels;

import com.app.delivery.core.domain.models.Restaurant;
import com.app.delivery.infrastructure.persistence.repository.RestaurantRepository;
import com.app.delivery.infrastructure.ui.components.*;

import javax.swing.*;
import java.awt.*;

public class RestaurantFormDialog extends JDialog {

    private CustomTextField txtName, txtAddress, txtPhone;
    private JCheckBox chkActive;
    private RestaurantRepository repo;
    private RestaurantPanel parent;
    private Restaurant current;

    public RestaurantFormDialog(Restaurant restaurant, RestaurantRepository repo,
                                RestaurantPanel parent) {
        this.repo = repo;
        this.parent = parent;
        this.current = restaurant;

        setTitle(restaurant == null ? "Nuevo Restaurante" : "Editar Restaurante");
        setModal(true);
        setSize(460, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // === Encabezado con ícono ===
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.setBackground(Color.WHITE);
        JLabel lblIcon = new JLabel(new ImageIcon(getClass().getResource("/icons/rest_ico.png")));
        CustomLabel lblTitle = new CustomLabel(getTitle());
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        lblTitle.setForeground(new Color(33, 150, 243));
        headerPanel.add(lblIcon);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // === Formulario ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 6, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtName = new CustomTextField("Ingrese el nombre");
        txtAddress = new CustomTextField("Ingrese la dirección");
        txtPhone = new CustomTextField("Ingrese el teléfono");
        chkActive = new JCheckBox("Activo");
        chkActive.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new CustomLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new CustomLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtAddress, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new CustomLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPhone, gbc);

        row++;
        gbc.gridx = 1; gbc.gridy = row;
        formPanel.add(chkActive, gbc);

        add(formPanel, BorderLayout.CENTER);

        // === Botones ===
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        buttonsPanel.setBackground(Color.WHITE);

        CustomButton btnSave = new CustomButton("Guardar");
        btnSave.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));

        CustomButton btnCancel = new CustomButton("Cancelar");
        btnCancel.setSecondaryStyle();
        btnCancel.setIcon(new ImageIcon(getClass().getResource("/icons/cancelar.png")));

        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);
        add(buttonsPanel, BorderLayout.SOUTH);

        // === Pre-cargar datos si es edición ===
        if (restaurant != null) {
            txtName.setText(restaurant.getName());
            txtAddress.setText(restaurant.getAddress());
            txtPhone.setText(restaurant.getPhone());
            chkActive.setSelected(restaurant.isActive());
        }

        // === Acciones ===
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveRestaurant());
    }

    private void saveRestaurant() {
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre y dirección son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (current == null) {
                Restaurant nuevo = new Restaurant(name, address, phone);
                nuevo.setActive(chkActive.isSelected());
                repo.insert(nuevo);
            } else {
                current.setName(name);
                current.setAddress(address);
                current.setPhone(phone);
                current.setActive(chkActive.isSelected());
                repo.update(current);
            }

            JOptionPane.showMessageDialog(this,
                    "Datos guardados correctamente.",
                    "Guardado correctamente",
                    JOptionPane.INFORMATION_MESSAGE);

            parent.loadRestaurants();
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el restaurante:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

