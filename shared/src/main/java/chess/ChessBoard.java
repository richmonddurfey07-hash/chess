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
            ChessPosition BlackPosition = new ChessPosition(7,i);
            ChessPiece black = new ChessPiece(BLACK,PAWN);
            addPiece(BlackPosition,black);
            ChessPosition WhitePosition = new ChessPosition(2,i);
            ChessPiece white = new ChessPiece(WHITE,PAWN);
            addPiece(WhitePosition,white);
        }
        ChessPosition BlackRook = new ChessPosition(8,1);
        ChessPosition BlackKnight = new ChessPosition(8,2);
        ChessPosition BlackBishop = new ChessPosition(8,3);
        ChessPosition BlackQueen = new ChessPosition(8,4);
        ChessPosition BlackKing = new ChessPosition(8,5);
        ChessPosition BlackBishop2 = new ChessPosition(8,6);
        ChessPosition BlackKnight2 = new ChessPosition(8,7);
        ChessPosition BlackRook2 = new ChessPosition(8,8);
        ChessPosition WhiteRook = new ChessPosition(1,1);
        ChessPosition WhiteKnight = new ChessPosition(1,2);
        ChessPosition WhiteBishop = new ChessPosition(1,3);
        ChessPosition WhiteQueen = new ChessPosition(1,4);
        ChessPosition WhiteKing = new ChessPosition(1,5);
        ChessPosition WhiteBishop2 = new ChessPosition(1,6);
        ChessPosition WhiteKnight2 = new ChessPosition(1,7);
        ChessPosition WhiteRook2 = new ChessPosition(1,8);
        ChessPiece blackRook = new ChessPiece(BLACK,ROOK);
        ChessPiece blackKnight = new ChessPiece(BLACK,KNIGHT);
        ChessPiece blackBishop = new ChessPiece(BLACK,BISHOP);
        ChessPiece blackKing = new ChessPiece(BLACK,KING);
        ChessPiece blackQueen = new ChessPiece(BLACK,QUEEN);
        ChessPiece whiteRook = new ChessPiece(WHITE,ROOK);
        ChessPiece whiteKnight = new ChessPiece(WHITE,KNIGHT);
        ChessPiece whiteBishop = new ChessPiece(WHITE,BISHOP);
        ChessPiece whiteKing = new ChessPiece(WHITE,KING);
        ChessPiece whiteQueen = new ChessPiece(WHITE,QUEEN);
        addPiece(BlackRook,blackRook);
        addPiece(BlackRook2, blackRook);
        addPiece(BlackKnight,blackKnight);
        addPiece(BlackKnight2, blackKnight);
        addPiece(BlackBishop,blackBishop);
        addPiece(BlackBishop2, blackBishop);
        addPiece(BlackQueen,blackQueen);
        addPiece(BlackKing, blackKing);
        addPiece(WhiteRook,whiteRook);
        addPiece(WhiteRook2, whiteRook);
        addPiece(WhiteKnight,whiteKnight);
        addPiece(WhiteKnight2, whiteKnight);
        addPiece(WhiteBishop,whiteBishop);
        addPiece(WhiteBishop2, whiteBishop);
        addPiece(WhiteKing,whiteKing);
        addPiece(WhiteQueen, whiteQueen);
    }
}
