package dataaccess;

import model.UserData;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class MySQLUserDAO implements UserDAO{
    @Override
    public void insertUser(UserData user) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
                String hash = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, hash);
                preparedStatement.setString(3, user.email());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String user) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, user);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new UserData(
                                rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                        );
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM user";
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement);) {
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException("unauthorized");
        }

    }
}
