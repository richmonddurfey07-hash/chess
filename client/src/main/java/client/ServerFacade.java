package client;

import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import exception.ResponseException;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResult register(String username, String password, String email) throws ResponseException {
        var req = new RegisterRequest(username,password,email);
        var request = buildRequest("POST", "/user", null, req);
        var response = sendRequest(request);
        return handleResponse(response, RegisterResult.class);
    }

    public LoginResult login(LoginRequest req) throws ResponseException {
        var request = buildRequest("POST", "/session", null, req);
        var response = sendRequest(request);
        return handleResponse(response, LoginResult.class);
    }

    public LogoutResult logout(String authToken) throws ResponseException{
        var request = buildRequest("DELETE", "/session", authToken, null);
        var response = sendRequest(request);
        return handleResponse(response, LogoutResult.class);
    }


    public ListResult listGames(String authToken) throws ResponseException {
        var request = buildRequest("GET", "/game", authToken,null);
        var response = sendRequest(request);
        return handleResponse(response, ListResult.class);
    }

    public CreateResult createGames(String gameName, String authToken) throws ResponseException {
        var req = new CreateRequest(gameName, authToken);
        var request = buildRequest("POST", "/game", authToken, req);
        var response = sendRequest(request);
        return handleResponse(response, CreateResult.class);
    }

    public JoinResult joinGames(int gameID, String playerColor, String authToken) throws ResponseException {
        var req = new JoinRequest(playerColor,gameID, authToken);
        var request = buildRequest("PUT", "/game", authToken, req);
        var response = sendRequest(request);
        return handleResponse(response, JoinResult.class);
    }

    private HttpRequest buildRequest(String method, String path, String head, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (head != null) {
            request.setHeader("Authorization", head);
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (ResponseException ex) {
            throw new ResponseException(401,ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new ResponseException(status, "other failure: ");
            }

            throw new ResponseException(status, "other failure: ");
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    public void clear() throws ResponseException{
        var request = buildRequest("DELETE", "/db", null, null);
        var response = sendRequest(request);
        handleResponse(response, null);
    }
}