package client;

import chess.*;
import dataaccess.DataAccessException;
import model.LoginRequest;

import java.util.Arrays;
import java.util.Scanner;
import static ui.EscapeSequences.*;


public class ClientMain {
    private State state = State.SIGNEDOUT;
    private ServerFacade server;
    private String authToken;
    public ClientMain(String url) {
        server = new ServerFacade(url);
    }

    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        new ClientMain(serverUrl).run();
    }

    public void run() {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!"quit".equals(result)) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_BLINKING + ">>> " + SET_TEXT_COLOR_GREEN);
    }



    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "create" -> createGame();
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws DataAccessException {
        if (params.length >= 3) {
            try{
                var result = server.register(params[0],params[1], params[2]);
                state = State.SIGNEDIN;
                return login(params[0],params[1]);
            } catch (DataAccessException e) {
                return e.getMessage();
            }
        }
        throw new DataAccessException("");
    }

    public String login(String... params) throws DataAccessException {
        if (params.length >= 2) {
            try{
                var result = server.login(new LoginRequest(params[0], params[1]));
                state = State.SIGNEDIN;
                authToken = result.authToken();
                return String.format("You signed in as %s.", params[0]);
            } catch (DataAccessException e) {
                return e.getMessage();
            }
        }
        throw new DataAccessException("Unvalid username or password");
    }

    private String logout() throws DataAccessException{
        try{
            var result = server.logout(authToken);
            state = State.SIGNEDOUT;
            authToken = null;
            return "You signed out";
        } catch (DataAccessException e){
            return e.getMessage();
        }
    }

    private String observeGame(String[] params) {
        return null;
    }

    private String joinGame(String[] params) {
        try{
            var result = server.joinGames(Integer.parseInt(params[0]),params[1],authToken);
            return "You signed out";
        } catch (DataAccessException e){
            return e.getMessage();
        }
    }

    private String listGames() {
        try{
            var result = server.listGames(authToken);
            return "You signed out";
        } catch (DataAccessException e){
            return e.getMessage();
        }
    }

    private String createGame() {
        return null;
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <username><password><email>
                    - login <username><password>
                    - help
                    - quit
                    """;
        }
        return """
                - create <Name>
                - list
                - join <ID> [White|Black]
                - observe <ID>
                - logout
                - quit
                """;
    }

}
