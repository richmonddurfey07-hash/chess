package chess;

import java.util.*;

import static chess.ChessPiece.PieceType.*;


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
                int direction = 0;
                int promotion = 0;
                int start = 0;
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    direction = -1;
                    promotion = 1;
                    start = 7;
                }
                else{
                    direction = 1;
                    promotion = 8;
                    start = 2;
                }
                    ChessPosition Attack1 = new ChessPosition(row+direction,col-1);
                    ChessPosition Attack2 = new ChessPosition(row+direction,col+1);
                if (Attack1.onBoard(Attack1)) {
                    if(board.getPiece(Attack1)!=null && board.getPiece(Attack1).getTeamColor()!=piece.getTeamColor()){
                        if(Attack1.getRow() == promotion){
                            ChessMove move1 = new ChessMove(myPosition, Attack1, QUEEN);
                            ChessMove move2 = new ChessMove(myPosition, Attack1, ROOK);
                            ChessMove move3 = new ChessMove(myPosition, Attack1, BISHOP);
                            ChessMove move4 = new ChessMove(myPosition, Attack1, KNIGHT);
                            moves.add(move1);
                            moves.add(move2);
                            moves.add(move3);
                            moves.add(move4);

                        }
                        else{
                            ChessMove move = new ChessMove(myPosition, Attack1, null);
                            moves.add(move);
                        }
                    }
                }
                if(Attack2.onBoard(Attack2)){
                    if(board.getPiece(Attack2)!=null && board.getPiece(Attack2).getTeamColor()!=piece.getTeamColor()) {
                        if (Attack1.getRow() == 1) {
                            ChessMove move = new ChessMove(myPosition, Attack2, QUEEN);
                            ChessMove move2 = new ChessMove(myPosition, Attack2, ROOK);
                            ChessMove move3 = new ChessMove(myPosition, Attack2, BISHOP);
                            ChessMove move4 = new ChessMove(myPosition, Attack2, KNIGHT);
                            moves.add(move);
                            moves.add(move2);
                            moves.add(move3);
                            moves.add(move4);

                        } else {
                            ChessMove move = new ChessMove(myPosition, Attack2, null);
                            moves.add(move);
                        }
                    }
                }
                    ChessPosition newPosition = new ChessPosition(row + direction, col);
                    if(newPosition.getRow() == promotion){
                        ChessMove move = new ChessMove(myPosition, newPosition, QUEEN);
                        ChessMove move2 = new ChessMove(myPosition, newPosition, ROOK);
                        ChessMove move3 = new ChessMove(myPosition, newPosition, BISHOP);
                        ChessMove move4 = new ChessMove(myPosition, newPosition, KNIGHT);
                        moves.add(move);
                        moves.add(move2);
                        moves.add(move3);
                        moves.add(move4);

                    }
                    if(row==start){
                        ChessPosition nextPosition = new ChessPosition(row+direction+direction,col);
                        if (board.getPiece(newPosition)==null && board.getPiece(nextPosition)==null) {
                            ChessMove move = new ChessMove(myPosition, nextPosition, null);
                            moves.add(move);
                        }
                    }
                    if(board.getPiece(newPosition)==null && newPosition.getRow()!=promotion){
                        ChessMove move = new ChessMove(myPosition,newPosition,null);
                        moves.add(move);
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
