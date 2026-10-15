package dataaccess;

import model.UserData;

public interface UserDAO {
    void insertUser(UserData user) throws DataAccessException;

    UserData getUser(UserData user) throws DataAccessException;

    void clear() throws DataAccessException;
}
