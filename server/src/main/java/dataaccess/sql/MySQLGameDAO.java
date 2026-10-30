package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLGameDAO implements GameDAO {
    @Override
    public int createGame(String gameName) throws DataAccessException {
        GameData game = new GameData(0, null, null, gameName, new ChessGame());
        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, json) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
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
        Collection<GameData> games = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername,blackUsername,gameName, json FROM game";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        games.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var json = rs.getString("json");
        ChessGame game = new Gson().fromJson(json, ChessGame.class);
        return new GameData(gameID,whiteUsername,blackUsername,gameName,game);
    }

    @Override
    public void updateGame(GameData newGame) throws DataAccessException {
        String statement = "UPDATE game SET whiteUsername=?, blackUsername=?, gameName=?, json=? WHERE gameID=?";
        try (Connection conn = DatabaseManager.getConnection()) {
            var preparedStatement = conn.prepareStatement(statement);
            String json = new Gson().toJson(newGame.game());
            preparedStatement.setString(1, newGame.whiteUsername());
            preparedStatement.setString(2, newGame.blackUsername());
            preparedStatement.setString(3, newGame.gameName());
            preparedStatement.setString(4, json);
            preparedStatement.setInt(5,newGame.gameID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM game";
        try (Connection conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement);) {
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException("unauthorized");
        }

    }

    @Override
    public GameData getGame(Integer id) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername,blackUsername,gameName,json FROM game WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        var json = rs.getString("json");
                        var whiteUsername = rs.getString("whiteUsername");
                        var blackUsername = rs.getString("blackUsername");
                        var gameName = rs.getString("gameName");
                        ChessGame game = new Gson().fromJson(json, ChessGame.class);
                        return new GameData(id, whiteUsername, blackUsername, gameName, game);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

}
