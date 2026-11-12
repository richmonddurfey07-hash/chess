package client;

import chess.*;
import dataaccess.DataAccessException;
import model.LoginRequest;

import java.util.Arrays;
import java.util.Scanner;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlAttr.InputType.RESET;

public class ClientMain {
    private static State state;
    private static ServerFacade server;

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private static void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }



    public static String eval(String input) {
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

    public static String register(String... params) throws DataAccessException {
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

    public static String login(String... params) throws DataAccessException {
        if (params.length >= 2) {
            try{
                var result = server.login(new LoginRequest(params[0], params[1]));
                state = State.SIGNEDIN;
                return String.format("You signed in as %s.", params[0]);
            } catch (DataAccessException e) {
                return e.getMessage();
            }
        }
        throw new DataAccessException("Unvalid username or password");
    }

    private static String logout() {
        return null;
    }

    private static String observeGame(String[] params) {
        return null;
    }

    private static String joinGame(String[] params) {
        return null;
    }

    private static String listGames() {
        return null;
    }

    private static String createGame() {
        return null;
    }

    public static String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <username><password><email>
                    - login <username><password>
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
