package chess;

import java.util.Arrays;
import java.util.Objects;
import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] squares = new ChessPiece[8][8];

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {

        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for(int i =1; i <=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition location = new ChessPosition(i,j);
                addPiece(location,null);
            }
        }
        for(int i = 1; i<= 8; i++){
            ChessPosition blackPosition = new ChessPosition(7,i);
            ChessPiece black = new ChessPiece(BLACK,PAWN);
            addPiece(blackPosition,black);
            ChessPosition whitePosition = new ChessPosition(2,i);
            ChessPiece white = new ChessPiece(WHITE,PAWN);
            addPiece(whitePosition,white);
        }
        ChessPosition blackRook = new ChessPosition(8,1);
        ChessPosition blackKnight = new ChessPosition(8,2);
        ChessPosition blackBishop = new ChessPosition(8,3);
        ChessPosition blackQueen = new ChessPosition(8,4);
        ChessPosition blackKing = new ChessPosition(8,5);
        ChessPosition blackBishop2 = new ChessPosition(8,6);
        ChessPosition blackKnight2 = new ChessPosition(8,7);
        ChessPosition blackRook2 = new ChessPosition(8,8);
        ChessPosition whiteRook = new ChessPosition(1,1);
        ChessPosition whiteKnight = new ChessPosition(1,2);
        ChessPosition whiteBishop = new ChessPosition(1,3);
        ChessPosition whiteQueen = new ChessPosition(1,4);
        ChessPosition whiteKing = new ChessPosition(1,5);
        ChessPosition whiteBishop2 = new ChessPosition(1,6);
        ChessPosition whiteKnight2 = new ChessPosition(1,7);
        ChessPosition whiteRook2 = new ChessPosition(1,8);
        ChessPiece blackRookPiece = new ChessPiece(BLACK,ROOK);
        ChessPiece blackKnightPiece = new ChessPiece(BLACK,KNIGHT);
        ChessPiece blackBishopPiece = new ChessPiece(BLACK,BISHOP);
        ChessPiece blackKingPiece = new ChessPiece(BLACK,KING);
        ChessPiece blackQueenPiece = new ChessPiece(BLACK,QUEEN);
        ChessPiece whiteRookPiece = new ChessPiece(WHITE,ROOK);
        ChessPiece whiteKnightPiece = new ChessPiece(WHITE,KNIGHT);
        ChessPiece whiteBishopPiece = new ChessPiece(WHITE,BISHOP);
        ChessPiece whiteKingPiece = new ChessPiece(WHITE,KING);
        ChessPiece whiteQueenPiece = new ChessPiece(WHITE,QUEEN);
        addPiece(blackRook,blackRookPiece);
        addPiece(blackRook2, blackRookPiece);
        addPiece(blackKnight,blackKnightPiece);
        addPiece(blackKnight2, blackKnightPiece);
        addPiece(blackBishop,blackBishopPiece);
        addPiece(blackBishop2, blackBishopPiece);
        addPiece(blackQueen,blackQueenPiece);
        addPiece(blackKing, blackKingPiece);
        addPiece(whiteRook,whiteRookPiece);
        addPiece(whiteRook2, whiteRookPiece);
        addPiece(whiteKnight,whiteKnightPiece);
        addPiece(whiteKnight2, whiteKnightPiece);
        addPiece(whiteBishop,whiteBishopPiece);
        addPiece(whiteBishop2, whiteBishopPiece);
        addPiece(whiteKing,whiteKingPiece);
        addPiece(whiteQueen, whiteQueenPiece);
    }

    public ChessBoard makeCopy() {
        ChessBoard copy = new ChessBoard();
        for(int i = 1; i<=8; i++){
            for(int j = 1; j<=8; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = getPiece(position);
                copy.addPiece(position,piece);
            }
        }
        return copy;
    }
}
