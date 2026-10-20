package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{
    private final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void insertUser(UserData user) throws DataAccessException {
        users.put(user.username(),user);
    }

    @Override
    public UserData getUser(String user) throws DataAccessException {
        return users.get(user.username());
    }

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }
}
