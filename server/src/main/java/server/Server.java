package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.RegisterRequest;
import model.RegisterResult;
import org.jetbrains.annotations.NotNull;
import service.ClearService;
import service.RegisterService;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final ClearService clearService;
    private final RegisterService registerService;

    public Server() {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        clearService = new ClearService(authDAO,userDAO, gameDAO);


        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        this.registerService = new RegisterService(userDAO, authDAO);

        javalin.delete("/db", ctx-> clear(ctx));

        javalin.post("/user", ctx-> register(ctx));
        // Register your endpoints and exception handlers here.

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
            ctx.status(403);
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + e.getMessage())));
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
