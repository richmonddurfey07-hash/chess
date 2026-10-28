package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {
    @Override
    public String createAuth(String username) throws DataAccessException {
        return "";
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
