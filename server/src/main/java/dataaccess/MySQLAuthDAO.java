package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.*;
import java.util.UUID;

public class MySQLAuthDAO implements AuthDAO {
    @Override
    public void insertAuth(AuthData auth) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken,username) VALUES(?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, auth.authToken());
            preparedStatement.setString(2, auth.username());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, token);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new AuthData(
                                rs.getString("authToken"),
                                rs.getString("username")

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
    public void deleteAuth(String token) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        try (Connection conn = DatabaseManager.getConnection();) {
            var preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1,token);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM auth";
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement);) {
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException("unauthorized");
        }

    }
}
