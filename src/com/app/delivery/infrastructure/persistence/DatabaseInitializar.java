package com.app.delivery.infrastructure.persistence;

import java.sql.*;

public class DatabaseInitializar {

    public static void initialize() {
        try (Connection conn = DatabaseConecction.getConnection();
             Statement stmt = conn.createStatement()) {

            // === CREACIÓN DE TABLAS ===

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS restaurant (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    address TEXT NOT NULL,
                    phone TEXT,
                    active INTEGER DEFAULT 1
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    precio REAL NOT NULL,
                    imagen TEXT  -- ruta o nombre del archivo dentro del proyecto
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    created_at TEXT NOT NULL,
                    total REAL NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS order_items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    order_id INTEGER NOT NULL,
                    item_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    subtotal REAL NOT NULL,
                    FOREIGN KEY (order_id) REFERENCES orders(id),
                    FOREIGN KEY (item_id) REFERENCES items(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cash_register (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    opened_at TEXT NOT NULL,
                    closed_at TEXT,
                    initial_amount REAL NOT NULL DEFAULT 0,
                    current_amount REAL NOT NULL DEFAULT 0,
                    status TEXT NOT NULL DEFAULT 'OPEN'
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cash_movements (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    register_id INTEGER NOT NULL,
                    movement_date TEXT NOT NULL,
                    type TEXT NOT NULL,
                    amount REAL NOT NULL,
                    description TEXT,
                    FOREIGN KEY (register_id) REFERENCES cash_register(id)
                )
            """);

            System.out.println("Tablas creadas o verificadas correctamente.");

            // === datos ejemplos ===
            insertSampleRestaurants(conn);
            insertSampleItems(conn);

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertSampleRestaurants(Connection conn) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM restaurant";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Agregando restaurantes de ejemplo...");

                String insertSql = """
                    INSERT INTO restaurant (name, address, phone, active)
                    VALUES (?, ?, ?, ?)
                """;
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, "La Casa del Sabor");
                    ps.setString(2, "Av. Central 123");
                    ps.setString(3, "8888-1111");
                    ps.setInt(4, 1);
                    ps.executeUpdate();

                    ps.setString(1, "Pizza Loca");
                    ps.setString(2, "Calle 8, San José");
                    ps.setString(3, "8888-2222");
                    ps.setInt(4, 1);
                    ps.executeUpdate();

                    ps.setString(1, "Café del Parque");
                    ps.setString(2, "Av. Segunda, frente al parque");
                    ps.setString(3, "8888-3333");
                    ps.setInt(4, 1);
                    ps.executeUpdate();
                }
            }
        }
    }

    private static void insertSampleItems(Connection conn) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM items";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Agregando comidas de ejemplo...");

                String insertSql = """
                    INSERT INTO items (nombre, precio, imagen)
                    VALUES (?, ?, ?)
                """;
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, "Hamburguesa");
                    ps.setDouble(2, 3500);
                    ps.setString(3, "/foods/burger.png");
                    ps.executeUpdate();

                    ps.setString(1, "Pizza");
                    ps.setDouble(2, 4200);
                    ps.setString(3, "/foods/pizza.png");
                    ps.executeUpdate();

                    ps.setString(1, "Ensalada Verde");
                    ps.setDouble(2, 2800);
                    ps.setString(3, "/foods/ensalada.png");
                    ps.executeUpdate();

                    ps.setString(1, "Tacos");
                    ps.setDouble(2, 2200);
                    ps.setString(3, "/foods/tacos.png");
                    ps.executeUpdate();

                    ps.setString(1, "Café");
                    ps.setDouble(2, 1500);
                    ps.setString(3, "/foods/cafe.png");
                    ps.executeUpdate();

                    ps.setString(1, "Postre");
                    ps.setDouble(2, 2000);
                    ps.setString(3, "/foods/postres.png");
                    ps.executeUpdate();
                }
            }
        }
    }
}
