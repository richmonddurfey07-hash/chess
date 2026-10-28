package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLUserDAO implements UserDAO{
    @Override
    public void insertUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String user) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              PRIMARY KEY (username)
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
