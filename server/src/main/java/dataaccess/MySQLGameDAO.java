package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MySQLGameDAO implements GameDAO {
    @Override
    public int createGame(String gameName) throws DataAccessException {
        GameData game = new GameData(0, null, null, gameName, new ChessGame());
        var statement = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, json) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            var preparedStatement = conn.prepareStatement(statement);
            String json = new Gson().toJson(game.game());
            preparedStatement.setNull(1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(2, java.sql.Types.VARCHAR);
            preparedStatement.setString(3, gameName);
            preparedStatement.setString(4, json);
            preparedStatement.executeUpdate();
            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM game";
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement);) {
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }

    }

    @Override
    public GameData getGame(Integer id) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteUsername,blackUsername,gameName,json FROM game WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    var json = rs.getString("json");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    ChessGame game = new Gson().fromJson(json, ChessGame.class);
                    return new GameData(id,whiteUsername,blackUsername,gameName,game);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
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

    public void configureDatabase() throws DataAccessException {
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
