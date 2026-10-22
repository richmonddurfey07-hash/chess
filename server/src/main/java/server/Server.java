package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.*;
import service.*;

import java.lang.reflect.Type;
import java.util.Map;


public class Server {

    private final Javalin javalin;
    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final ClearService clearService;
    private final RegisterService registerService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final GameService gameService;

    public Server() {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        clearService = new ClearService(authDAO,userDAO, gameDAO);


        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        registerService = new RegisterService(userDAO, authDAO);
        loginService = new LoginService(userDAO,authDAO);
        logoutService = new LogoutService(authDAO);
        gameService = new GameService(authDAO,userDAO,gameDAO);

        javalin.delete("/db", ctx-> clear(ctx));

        javalin.post("/user", ctx-> register(ctx));

        javalin.post("/session",ctx->login(ctx));

        javalin.delete("/session", ctx->logout(ctx));

        javalin.post("/game", ctx->create(ctx));

        javalin.put("/game", ctx->join(ctx));

        javalin.get("/game", ctx->list(ctx));
        // Register your endpoints and exception handlers here.

    }

    private void handleError(Context ctx, DataAccessException e) {
        String message = e.getMessage();

        switch (message) {
            case "unauthorized" -> ctx.status(401);
            case "bad request" -> ctx.status(400);
            case "already taken" -> ctx.status(403);
            default -> ctx.status(500);
        }

        ctx.result(new Gson().toJson(
                Map.of("message", "Error: " + message)
        ));
    }

    private void clear(Context context) throws DataAccessException {
        clearService.clear();
        context.status(200);
        context.result("{}");
    }

    private void register(Context ctx) {
        try{
            RegisterRequest req = new Gson().fromJson(ctx.body(), RegisterRequest.class);
            RegisterResult res = registerService.register(req);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch (DataAccessException e) {
            handleError(ctx,e);
        }
    }

    private void login(Context ctx){
        try{
            LoginRequest req = new Gson().fromJson(ctx.body(), LoginRequest.class);
            LoginResult res = loginService.login(req);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch (DataAccessException e){
            handleError(ctx,e);
        }
    }

    private void logout(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            LogoutResult res = logoutService.logout(authToken);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch (DataAccessException e){
            handleError(ctx,e);
        }
    }

    private void create(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            GameData body = new Gson().fromJson(ctx.body(), (Type) GameData.class);
            CreateRequest req = new CreateRequest(body.gameName(),authToken);
            CreateResult res = gameService.create(req);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch(DataAccessException e){
            handleError(ctx,e);
        }
    }

    private void join(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            JoinRequest body = new Gson().fromJson(ctx.body(), (Type) JoinRequest.class);
            JoinRequest req = new JoinRequest(body.playerColor(),body.gameID(),authToken);
            JoinResult res = gameService.join(req);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch(DataAccessException e){
            handleError(ctx,e);
        }
    }

    private void list(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            ListResult res = gameService.listGames(authToken);
            ctx.status(200);
            ctx.result(new Gson().toJson(res));
        } catch (DataAccessException e){
            handleError(ctx,e);
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
