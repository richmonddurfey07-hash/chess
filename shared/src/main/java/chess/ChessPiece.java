package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

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
            for (int i = myPosition.getRow(); 2 <= i && i <= 7; i++){
                int nextRow = i + 1;
                for (int j = myPosition.getColumn(); 2 <= j && j <= 7; j++){
                    int nextCol = j + 1;
                    ChessPosition newPosition = new ChessPosition(nextRow, nextCol);
                    if(board.getPiece(newPosition)==null){
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    break;
                }
                for (int j = myPosition.getColumn(); 2 <= j && j <= 7; j--){
                    int nextCol = j-1;
                    ChessPosition newPosition = new ChessPosition(nextRow, nextCol);
                    if(board.getPiece(newPosition)==null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    break;
                }
            }
            for (int i = myPosition.getRow(); 2 <= i && i <= 7; i--){
                int nextRow = i - 1;
                for (int j = myPosition.getColumn(); 2 <= j && j <= 7; j++){
                    int nextCol = j + 1;
                    ChessPosition newPosition = new ChessPosition(nextRow, nextCol);
                    if(board.getPiece(newPosition)==null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    break;
                }
                for (int j = myPosition.getColumn(); 2 <= j && j <= 7; j--){
                    int nextCol = j-1;
                    ChessPosition newPosition = new ChessPosition(nextRow, nextCol);
                    if(board.getPiece(newPosition)==null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, null);
                        moves.add(move);
                    }
                    break;
                }
            }
            return moves;
        }
        return List.of();
    }
}
