package database;

import model.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalDAO {

    public boolean insertAnimal(Animal animal) {
        String sql = "INSERT INTO animal (name, type, age, healthy) VALUES (?, ?, ?, ?)";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return false;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, animal.getName());
            st.setString(2, animal.getType());
            st.setInt(3, animal.getAge());
            st.setBoolean(4, animal.isHealthy());

            int rows = st.executeUpdate();
            System.out.println("Inserted rows: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Insert failed!");
            e.printStackTrace();
            return false;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public void printAllAnimals() {
        String sql = "SELECT animal_id, name, type, age, healthy FROM animal ORDER BY animal_id";
        Connection c = DatabaseConnection.getConnection();
        if (c == null) return;

        try (PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            System.out.println("\n--- ALL ANIMALS FROM DB ---");
            while (rs.next()) {
                int id = rs.getInt("animal_id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int age = rs.getInt("age");
                boolean healthy = rs.getBoolean("healthy");

                System.out.println(id + ") " + name + " | " + type + " | age=" + age + " | healthy=" + healthy);
            }

        } catch (SQLException e) {
            System.out.println("Select failed!");
            e.printStackTrace();

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }
}
