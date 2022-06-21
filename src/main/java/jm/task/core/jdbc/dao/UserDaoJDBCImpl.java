package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private Connection connection = getConnection();

    private static String createTableSql = "CREATE TABLE IF NOT EXISTS `mydb`.`users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `age` INT NOT NULL,\n" +
            "                    PRIMARY KEY (`id`))";

    private static final String dropTableSql = "DROP TABLE `mydb`.`users`";
    private static final String insertSql = "INSERT INTO `mydb`.`users` (name, lastName, age) values (?, ?, ?)";
    private static final String deleteByIdSql = "DELETE FROM `mydb`.`users` WHERE id = ?";
    private static final String selectSql = "SELECT * FROM `mydb`.`users`";
    private static final String deleteSql = "DELETE FROM `mydb`.`users`";

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                User user = new User();
                user.setAge(resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));

                usersList.add(user);
            }
            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(deleteSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
