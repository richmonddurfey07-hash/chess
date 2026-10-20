package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    private final HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authTokens.put(authToken, authData);
        return authToken;
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
