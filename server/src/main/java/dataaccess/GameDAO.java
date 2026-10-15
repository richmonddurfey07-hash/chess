package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.List;

public interface GameDAO {
    void createGame(String gameName) throws DataAccessException;
    Collection<GameData> listGames(GameData game) throws DataAccessException;
    void updateGame(GameData newGame) throws DataAccessException;
    void clear();
    GameData getGame(Integer id) throws DataAccessException;
}
