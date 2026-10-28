package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MySQLAuthDAO implements AuthDAO {
    @Override
    public void insertAuth(AuthData auth) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("INSERT INTO user (username, authToken) VALUES(?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, auth.username());
            preparedStatement.setString(2, auth.authToken());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {

    }

    @Override
    public void clear() {

    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              authToken varchar(256) NOT NULL,
              PRIMARY KEY (authToken)
              FOREIGN KEY(username) REFERENCES user(username)
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unauthorized");
        }
    }
}
