package com.app.delivery.infrastructure.persistence.repository;

import com.app.delivery.core.domain.models.Restaurant;
import com.app.delivery.infrastructure.persistence.DatabaseConecction;
import com.app.delivery.infrastructure.utils.ErrorHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    public void insert(Restaurant r) {
        String sql = "INSERT INTO restaurant (name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.showError(null, "Error al agregar un restaurante",e);
        }
    }

    public List<Restaurant> findAll() {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant ORDER BY id DESC";
        try (Connection conn = DatabaseConecction.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs == null){
                ErrorHandler.showInfo(null,"No se tienen datos que mostrar");
                return list;
            }

            while (rs.next()) {
                list.add(new Restaurant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("active") == 1
                ));
            }
        } catch (SQLException e) {
            ErrorHandler.showError(null,"Problemas al cargar la lista de restaurantes",e);
        }
        return list;
    }

    public void update(Restaurant r) {
        String sql = "UPDATE restaurant SET name=?, address=?, phone=?, active=? WHERE id=?";
        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getPhone());
            ps.setInt(4, r.isActive() ? 1 : 0);
            ps.setInt(5, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.showError(null,"No se pudo editar el restaurante", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM restaurant WHERE id=?";
        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.showError(null, "No se pudo eliminar el restaurante",e);
        }
    }



}
