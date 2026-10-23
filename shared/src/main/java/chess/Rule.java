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
        Collection<ChessMove> moves = new ArrayList<>();
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
                moves = pawnMoves(myPosition,piece,board);
            }
            else {
                moves = nonSliding(moves,myPosition,board);
            }
        }
        return moves;
    }

    private Collection<ChessMove> nonSliding(Collection<ChessMove> moves, ChessPosition myPosition,ChessBoard board) {
        int[][] directions = getDirections();
        ChessPiece piece = board.getPiece(myPosition);
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

    private Collection<ChessMove> pawnMoves(ChessPosition myPosition, ChessPiece piece, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
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
        ChessPosition attack1 = new ChessPosition(row+direction,col-1);
        ChessPosition attack2 = new ChessPosition(row+direction,col+1);
        if (attack1.onBoard(attack1)) {
            getAttackPiece(myPosition,attack1,piece,board,promotion,moves);
        }
        if(attack2.onBoard(attack2)){
            getAttackPiece(myPosition,attack2,piece,board,promotion,moves);
        }
        ChessPosition newPosition = new ChessPosition(row + direction, col);
        if(newPosition.getRow() == promotion){
            promotionMoves(myPosition,newPosition,moves);
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
    }

    private void getAttackPiece(ChessPosition myPosition, ChessPosition attack1, ChessPiece piece, ChessBoard board,int promotion,Collection<ChessMove>moves) {
        if(board.getPiece(attack1)!=null && board.getPiece(attack1).getTeamColor()!=piece.getTeamColor()){
            if(attack1.getRow() == promotion){
                promotionMoves(myPosition,attack1,moves);
            }
            else{
                ChessMove move = new ChessMove(myPosition, attack1, null);
                moves.add(move);
            }
        }
    }

    private void promotionMoves(ChessPosition myPosition, ChessPosition promotionPosition, Collection<ChessMove> moves) {
        moves.add(new ChessMove(myPosition, promotionPosition, QUEEN));
        moves.add(new ChessMove(myPosition, promotionPosition, ROOK));
        moves.add(new ChessMove(myPosition, promotionPosition, BISHOP));
        moves.add(new ChessMove(myPosition, promotionPosition, KNIGHT));
    }
}
