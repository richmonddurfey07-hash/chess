package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void insertAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String token) throws DataAccessException;
    void deleteAuth(String token) throws DataAccessException;
    void clear();
}
