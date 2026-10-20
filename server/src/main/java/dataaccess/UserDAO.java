package dataaccess;

import model.UserData;

public interface UserDAO {
    void insertUser(UserData user) throws DataAccessException;

    UserData getUser(String user) throws DataAccessException;

    void clear() throws DataAccessException;
}
