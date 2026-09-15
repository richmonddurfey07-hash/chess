package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        List<ChessMove> moves = new ArrayList<>();
        if (piece.getPieceType() == PieceType.BISHOP){
            int blockedRU = 0;
            int blockedRD = 0;
            int blockedLU = 0;
            int blockedLD = 0;
            for (int i = 1; i <= 7; i++){
                int nextRow = i + myPosition.getRow();
                int nextCol = i + myPosition.getColumn();
                if(nextRow <= 8 && nextCol <= 8) {
                    ChessPosition newPosition = new ChessPosition(nextRow, nextCol);
                    ChessPiece space = board.getPiece(newPosition);
                    if (space!=null) {
                        blockedRU = 1;
                        if(space.getTeamColor()!=piece.getTeamColor()){
                            ChessMove move = new ChessMove(myPosition, newPosition, null);
                            moves.add(move);
                        }
                    }
                    if (blockedRU == 0) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                }
                int negCol = myPosition.getColumn() - i;
                if(nextRow <= 8 && negCol >= 1) {
                    ChessPosition newPosition = new ChessPosition(nextRow, negCol);
                    ChessPiece space = board.getPiece(newPosition);
                    if (space != null){
                        blockedLU = 1;
                        if(space.getTeamColor()!=piece.getTeamColor()){
                            ChessMove move = new ChessMove(myPosition, newPosition, null);
                            moves.add(move);
                        }
                    }
                    if (blockedLU == 0) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                }
            }
            for (int i = 1; i <= 7; i++){
                int nextRow = myPosition.getRow()-i;
                int negCol = myPosition.getColumn()-i;
                if(nextRow >= 1 && negCol >= 1) {
                    ChessPosition newPosition = new ChessPosition(nextRow, negCol);
                    ChessPiece space = board.getPiece(newPosition);
                    if (space != null){
                        blockedLD = 1;
                        if(space.getTeamColor()!=piece.getTeamColor()){
                            ChessMove move = new ChessMove(myPosition, newPosition, null);
                            moves.add(move);
                        }
                    }
                    if (blockedLD == 0) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                }
                int posCol = i + myPosition.getColumn();
                if(nextRow >=1 && posCol <=8) {
                    ChessPosition newPosition = new ChessPosition(nextRow, posCol);
                    ChessPiece space = board.getPiece(newPosition);
                    if (space != null){
                        blockedRD = 1;
                        if(space.getTeamColor()!=piece.getTeamColor()){
                            ChessMove move = new ChessMove(myPosition, newPosition, null);
                            moves.add(move);
                        }
                    }
                    if (blockedRD == 0) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                }
            }
            return moves;
        }
        return List.of();
    }
}
