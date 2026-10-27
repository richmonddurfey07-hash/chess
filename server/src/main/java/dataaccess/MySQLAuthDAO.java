package dataaccess;

import model.AuthData;

public class MySQLAuthDAO implements AuthDAO {
    @Override
    public String createAuth(String username) throws DataAccessException {
        return "";
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {

    }

    @Override
    public void clear() {

    }
}
