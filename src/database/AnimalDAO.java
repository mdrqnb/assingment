package database;

import model.Animal;
import model.Cat;
import model.Dog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Insert failed");
            e.printStackTrace();
            return false;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public void printAllAnimals() {
        String sql = "SELECT animal_id, name, type, age, healthy FROM animal ORDER BY animal_id";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return;

        try (PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            System.out.println("\nAll animals from DB");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("animal_id") + ") " +
                                rs.getString("name") + " | " +
                                rs.getString("type") + " | age=" +
                                rs.getInt("age") + " | healthy=" +
                                rs.getBoolean("healthy")
                );
            }

        } catch (SQLException e) {
            System.out.println("Select failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public Animal getAnimalById(int animalId) {
        String sql = "SELECT animal_id, name, type, age, healthy FROM animal WHERE animal_id = ?";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return null;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, animalId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return extractAnimal(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            System.out.println("getAnimalById failed");
            e.printStackTrace();
            return null;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public boolean updateAnimal(int animalId, String name, String type, int age, boolean healthy) {
        String sql = "UPDATE animal SET name = ?, type = ?, age = ?, healthy = ? WHERE animal_id = ?";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return false;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, name);
            st.setString(2, type);
            st.setInt(3, age);
            st.setBoolean(4, healthy);
            st.setInt(5, animalId);

            int rows = st.executeUpdate();
            System.out.println("Updated rows: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Update failed!");
            e.printStackTrace();
            return false;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public boolean deleteAnimal(int animalId) {
        String sql = "DELETE FROM animal WHERE animal_id = ?";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return false;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, animalId);

            int rows = st.executeUpdate();
            System.out.println("Deleted rows: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Delete failed");
            e.printStackTrace();
            return false;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public List<Animal> searchByName(String partOfName) {
        List<Animal> list = new ArrayList<>();
        String sql = "SELECT animal_id, name, type, age, healthy " +
                "FROM animal WHERE name ILIKE ? ORDER BY name";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return list;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, "%" + partOfName + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Animal a = extractAnimal(rs);
                    if (a != null)
                        list.add(a);
                }
            }
            System.out.println("Found: " + list.size());
            return list;

        } catch (SQLException e) {
            System.out.println("Search failed");
            e.printStackTrace();
            return list;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public List<Animal> searchByAgeRange(int minAge, int maxAge) {
        List<Animal> list = new ArrayList<>();
        String sql = "SELECT animal_id, name, type, age, healthy " +
                "FROM animal WHERE age BETWEEN ? AND ? " +
                "ORDER BY age DESC";
        Connection c = DatabaseConnection.getConnection();
        if (c == null)
            return list;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, minAge);
            st.setInt(2, maxAge);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Animal a = extractAnimal(rs);
                    if (a != null)
                        list.add(a);
                }
            }
            System.out.println("Found: " + list.size());
            return list;

        } catch (SQLException e) {
            System.out.println("Search failed");
            e.printStackTrace();
            return list;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    public List<Animal> searchByMinAge(int minAge) {
        List<Animal> list = new ArrayList<>();
        String sql = "SELECT animal_id, name, type, age, healthy " +
                "FROM animal WHERE age >= ? " +
                "ORDER BY age DESC";
        Connection c = DatabaseConnection.getConnection();
        if (c == null) return list;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, minAge);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Animal a = extractAnimal(rs);
                    if (a != null)
                        list.add(a);
                }
            }
            System.out.println("Found: " + list.size());
            return list;

        } catch (SQLException e) {
            System.out.println("Search failed");
            e.printStackTrace();
            return list;

        } finally {
            DatabaseConnection.closeConnection(c);
        }
    }

    private Animal extractAnimal(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String type = rs.getString("type");
        int age = rs.getInt("age");
        boolean healthy = rs.getBoolean("healthy");

        if (type != null && type.equalsIgnoreCase("dog")) {
            return new Dog(name, age, healthy, "unknown");
        }
        if (type != null && type.equalsIgnoreCase("cat")) {
            return new Cat(name, age, healthy, "unknown");
        }
        return null;
    }
}