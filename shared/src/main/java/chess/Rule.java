package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Rule {
    private final boolean sliding;
    private final int[][] directions;

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
                ChessPosition newPosition = new ChessPosition(row + direction[0], col + direction[1]);
                while (newPosition.onBoard(newPosition)) {
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                    moves.add(newMove);
                }
            }
        } else {
            int[][] directions = getDirections();
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                ChessPosition newPosition = new ChessPosition(row + direction[0], col + direction[1]);
                if (newPosition.onBoard(newPosition)) {
                    ChessPiece piece = board.getPiece(myPosition);
                    ChessPiece space = board.getPiece(newPosition);
                    if(space==null || space.getTeamColor() != piece.getTeamColor()) {
                        ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                        moves.add(newMove);
                    }
                }
            }
            return moves;
        }
        return List.of();

    }
}
