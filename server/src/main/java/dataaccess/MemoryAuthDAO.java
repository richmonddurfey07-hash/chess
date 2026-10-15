package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    private final HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public void createAuth(AuthData token) throws DataAccessException {
        authTokens.put(token.authToken(),token);
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return authTokens.get(token);
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {
        authTokens.remove(token);
    }

    @Override
    public void clear() {
        authTokens.clear();
    }
}
