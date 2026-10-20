package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO implements GameDAO{
    private int id = 1;
    HashMap<Integer,GameData> games = new HashMap<>();

    @Override
    public int createGame(String gameName) throws DataAccessException {
        GameData game = new GameData(id,null,null,gameName,new ChessGame());
        games.put(id,game);
        return id;
    }

    @Override
    public Collection<GameData> listGames(GameData game) throws DataAccessException {
        return games.values();
    }

    @Override
    public void updateGame(GameData newGame) throws DataAccessException {
        games.put(newGame.gameID(),newGame);
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException{
        return games.get(gameID);
    }

    @Override
    public void clear(){
        games.clear();
    }
}
