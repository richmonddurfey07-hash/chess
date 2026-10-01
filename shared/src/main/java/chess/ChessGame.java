package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static chess.ChessPiece.PieceType.KING;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor color;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && color == chessGame.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, color);
    }

    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.color = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return color;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        color = team;

    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(board.getPiece(startPosition)==null){
            return null;
        }
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> myMoves = piece.pieceMoves(board,startPosition);
        Collection<ChessMove> moves = new ArrayList<>();
        for(ChessMove move : myMoves){
            ChessBoard newBoard = board.makeCopy();
            newBoard.addPiece(move.getEndPosition(),piece);
            newBoard.addPiece(move.getStartPosition(),null);
            if(!isInCheck(piece.getTeamColor(),newBoard)){
                moves.add(move);
            }
        }
        return moves;
    }

    private boolean isInCheck(TeamColor teamColor, ChessBoard newBoard) {
        ChessPosition king = findKing(teamColor, newBoard);
        Collection<ChessMove> enemyMoves = findEnemies(teamColor,newBoard);
        for(ChessMove move : enemyMoves){
            ChessPosition threat = move.getEndPosition();
            if(threat.equals(king)){
                return true;
            }
        }
        return false;
    }

    private Collection<ChessMove> findEnemies(TeamColor teamColor, ChessBoard newBoard) {
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        for(int i = 1; i <=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = newBoard.getPiece(position);
                if(piece!=null && piece.getTeamColor()!=teamColor) {
                    enemyMoves.addAll(piece.pieceMoves(newBoard, position));
                }
            }
        }
        return enemyMoves;
    }

    private Collection<ChessMove> findTeam(TeamColor teamColor, ChessBoard newBoard) {
        Collection<ChessMove> TeamMoves = new ArrayList<>();
        for(int i = 1; i <=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = newBoard.getPiece(position);
                if(piece!=null && piece.getTeamColor()==teamColor) {
                    TeamMoves.addAll(validMoves(position));
                }
            }
        }
        return TeamMoves;
    }

    private ChessPosition findKing(TeamColor teamColor, ChessBoard newBoard) {
        for(int i = 1; i <=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = newBoard.getPiece(position);
                if(piece!=null && piece.getTeamColor()==teamColor && piece.getPieceType()==KING){
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if(board.getPiece(move.getStartPosition())==null){
            throw new InvalidMoveException("Invalid Move");
        }
        ChessPiece piece = board.getPiece(move.getStartPosition());
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if(!validMoves.contains(move)){
            throw new InvalidMoveException("Invalid Move");
        }
        if(piece.getTeamColor()!=color){
            throw new InvalidMoveException("Invalid Move");
        }
        if(move.getPromotionPiece()!=null){
             ChessPiece newPiece = new ChessPiece(piece.getTeamColor(),move.getPromotionPiece());
            board.addPiece(move.getEndPosition(),newPiece);
        }
        else {
            board.addPiece(move.getEndPosition(), piece);
        }
        board.addPiece(move.getStartPosition(),null);
        if(piece.getTeamColor()== TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }
        else{setTeamTurn(TeamColor.WHITE);}
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor,this.board);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && findTeam(teamColor, board).isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && findTeam(teamColor, board).isEmpty();
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
