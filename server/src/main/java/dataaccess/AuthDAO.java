package dataaccess;

import model.AuthData;

public interface AuthDAO {
    String createAuth(String username) throws DataAccessException;
    AuthData getAuth(String token) throws DataAccessException;
    void deleteAuth(String token) throws DataAccessException;
    void clear();
}
