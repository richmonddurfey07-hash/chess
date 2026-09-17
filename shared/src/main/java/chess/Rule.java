package chess;

import java.util.*;

import static chess.ChessPiece.PieceType.PAWN;
import static chess.ChessPiece.PieceType.QUEEN;


public class Rule {
    private final boolean sliding;
    private final int[][] directions;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rule rule = (Rule) o;
        return sliding == rule.sliding && Objects.deepEquals(directions, rule.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sliding, Arrays.deepHashCode(directions));
    }

    public Rule(boolean sliding, int[][] directions) {
        this.sliding = sliding;
        this.directions = directions;
    }

    public boolean isSliding() {
        return sliding;
    }

    public int[][] getDirections() {
        return directions;
    }

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        if (isSliding()) {
            int[][] directions = getDirections();
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                int multiplier = 1;
                ChessPosition newPosition = new ChessPosition(row + direction[0], col + direction[1]);
                boolean go = true;
                while (go) {
                    ChessPosition testPosition = new ChessPosition(row + (direction[0] * multiplier), col + (direction[1] * multiplier));
                    ChessPiece piece = board.getPiece(myPosition);
                    if (testPosition.onBoard(testPosition)) {
                        ChessPiece space = board.getPiece(testPosition);
                        if (space == null) {
                            ChessMove newMove = new ChessMove(myPosition, testPosition, null);
                            moves.add(newMove);
                            multiplier++;
                        } else if (space.getTeamColor() != piece.getTeamColor()) {
                            ChessMove newMove = new ChessMove(myPosition, testPosition, null);
                            moves.add(newMove);
                            go = false;
                        } else if (space.getTeamColor() == piece.getTeamColor()) {
                            go = false;
                        }
                    } else {
                        break;
                    }
                }
            }
            return moves;
        } else {
            ChessPiece piece = board.getPiece(myPosition);
            ChessPiece.PieceType type = piece.getPieceType();
            if (type == PAWN) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    ChessPosition newPosition = new ChessPosition(row - 1, col);
                    if(newPosition.getRow() == 1){
                        ChessMove move = new ChessMove(myPosition, newPosition, QUEEN);
                        moves.add(move);
                    }
                    else{
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    if (row == 7) {
                        ChessPosition nextPosition = new ChessPosition(row - 2, col);
                        ChessMove move2 = new ChessMove(myPosition, nextPosition, null);
                        moves.add(move2);
                    }
                } else {
                    ChessPosition newPosition = new ChessPosition(row + 1, col);
                    if(newPosition.getRow() == 8){
                        ChessMove move = new ChessMove(myPosition, newPosition, QUEEN);
                        moves.add(move);
                    }
                    else{
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    if (row == 2) {
                        ChessPosition nextPosition = new ChessPosition(row + 2, col);
                        ChessMove move2 = new ChessMove(myPosition, nextPosition, null);
                        moves.add(move2);
                    }
                }
                return moves;
            } else {
                int[][] directions = getDirections();
                for (int[] direction : directions) {
                    int row = myPosition.getRow();
                    int col = myPosition.getColumn();
                    ChessPosition newPosition = new ChessPosition(row + direction[0], col + direction[1]);
                    if (newPosition.onBoard(newPosition)) {
                        ChessPiece space = board.getPiece(newPosition);
                        if (space == null || space.getTeamColor() != piece.getTeamColor()) {
                            ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                            moves.add(newMove);
                        }
                    }
                }
                return moves;
            }
        }
    }
}
