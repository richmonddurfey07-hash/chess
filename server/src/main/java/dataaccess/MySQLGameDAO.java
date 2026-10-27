package dataaccess;

import model.GameData;

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
}
