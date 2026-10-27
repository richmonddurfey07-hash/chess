package dataaccess;

import model.UserData;

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
}
