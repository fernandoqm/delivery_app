package com.app.delivery.infrastructure.utils;

import javax.swing.*;
import java.awt.*;


public class ErrorHandler {

    // Cambia a false en producción
    private static final boolean DEBUG_MODE = true;

    /**
     * Muestra un mensaje de error genérico al usuario.
     */
    public static void showError(Component parent, String message, Exception ex) {
        SwingUtilities.invokeLater(() -> {
            String detail = message;

            if (DEBUG_MODE && ex != null) {
                detail += "\n\nDetalles técnicos:\n" + ex.getMessage();
            }

            JOptionPane.showMessageDialog(
                    parent,
                    detail,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        });

        if (DEBUG_MODE && ex != null) {
            ex.printStackTrace(); // Solo imprime en consola en modo debug
        }
    }

    /**
     * Muestra un mensaje informativo (por ejemplo, operación exitosa).
     */
    public static void showInfo(Component parent, String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(parent, message, "Información", JOptionPane.INFORMATION_MESSAGE)
        );
    }

    /**
     * Muestra una advertencia (sin interrumpir el flujo).
     */
    public static void showWarning(Component parent, String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(parent, message, "Advertencia", JOptionPane.WARNING_MESSAGE)
        );
    }
}

