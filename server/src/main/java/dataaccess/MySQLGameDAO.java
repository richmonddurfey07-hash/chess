package dataaccess;

import model.GameData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MySQLGameDAO implements GameDAO{
    @Override
    public int createGame(String gameName) throws DataAccessException {
        return 0;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(GameData newGame) throws DataAccessException {

    }

    @Override
    public void clear() {

    }

    @Override
    public GameData getGame(Integer id) throws DataAccessException {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              gameID int NOT NULL AUTO_INCREMENT,
              whiteUsername varchar(256),
              blackUsername varchar(256),
              gameName varchar(256) NOT NULL,
              json TEXT NOT NULL,
              PRIMARY KEY (gameID)
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
