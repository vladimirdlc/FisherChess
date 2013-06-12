package Game;


import Game.modes.PiecesFactory;
import Game.pieces.King;
import Game.pieces.Piece;
import Game.pieces.Queen;
import Game.util.Vector2;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * In charge of putting each of the pieces in place.
 * @author Vladimir de la Cruz
 */
public class Board implements Cloneable {

    private PiecesFactory gameType;

    private Square[][] squares;
    private Piece takenPiece;
    private Piece pieceOriginal;
    private Image imagen;


    public Board(PiecesFactory typeIni) {
        gameType = typeIni;
        initialize();
    }

    private void initialize() {
        Piece[][] pieceIni = gameType.initialize();
        squares = new Square[pieceIni.length][pieceIni[0].length];

        for (int row = 0; row < pieceIni.length; row++) {
            for (int col = 0; col < pieceIni[row].length; col++) {
                if (pieceIni[col][row] == null) {
                    squares[col][row] = new Square(row, col);
                }
                else {
                    squares[col][row] = new Square(row, col);
                    squares[col][row].occupy(pieceIni[col][row]);

                }
            }
        }

    }

    void drawPieces(Graphics g, int px, int py) {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (squares[col][row].getPiece() != null) {
                    squares[col][row].getPiece().drawConDif(g, px * row, py * col);
                }
            }
        }
    }

    void drawPieces(Graphics g, int px, int py, int offx, int offy) {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (squares[col][row].getPiece() != null) {
                    squares[col][row].getPiece().drawConDif(g, (px * row) + offx, (py * col) + offy);
                }
            }
        }
    }

    void draw(Graphics g) {
        g.drawImage(this.imagen, 0, 0, null);
    }


    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (squares[col][row].getPiece() != null) {
                    pieces.add(squares[col][row].getPiece());
                }
            }
        }

        return pieces;
    }

    public ArrayList<Piece> getPiecesColor(boolean isBkack) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (squares[col][row].getPiece() != null) {
                    if (isBkack && squares[col][row].getPiece().isBlack()) {
                        pieces.add(squares[col][row].getPiece());
                    } else if (!isBkack && !squares[col][row].getPiece().isBlack()) {
                        pieces.add(squares[col][row].getPiece());
                    }
                }
            }
        }

        return pieces;
    }

    public boolean isInCheckColor(boolean isBlack) {
        for (Piece p : getPiecesColor(isBlack)) {
            if (p.isBlack() == isBlack && (p instanceof King)) {
                return ((King) p).isInCheck(this);
            }
        }

        return false;
    }


    public boolean isInCheckMateColor(boolean isBlack) {
        for (Piece p : getPiecesColor(isBlack)) {
            if (p.isBlack() == isBlack && (p instanceof King)) {
                return ((King) p).isInCheckMate(this, new Vector2(takenPiece.getX(), takenPiece.getY()));
            }
        }

        return false;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public boolean isDrownedColor(boolean isBlack) {
        boolean isDrownedKing = true;

        for (Piece p : getPiecesColor(isBlack)) {
            if (p.isBlack() == isBlack) {
                if(p instanceof King) isDrownedKing = ((King) p).isDrowned(this, new Vector2(takenPiece.getX(), takenPiece.getY()));
            }
        }
        
        return isDrownedKing;
    }

    public Image getImage() {
        return imagen;
    }

    public void setImage(Image imagen) {
        this.imagen = imagen;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public void setTakenPiece(Piece pieceTomada) {
        this.takenPiece = pieceTomada;
    }

    public void setOriginalPiece(Piece pieceOriginal) {
        this.pieceOriginal = pieceOriginal;
    }

    public void restoreMovement() {
        this.getSquares()[takenPiece.getY()][takenPiece.getX()].desocupar();
        this.getSquares()[pieceOriginal.getY()][pieceOriginal.getX()].occupy(pieceOriginal);
    }

    public Piece promotePiece(Piece takenPiece, String promoteTo) {
        int x = takenPiece.getX();
        int y = takenPiece.getY();
      
        Piece newpiece = null;
         
        if(promoteTo.equals("Queen"))
        {
            newpiece = new Queen(x, y);
            newpiece.setBlack(takenPiece.isBlack());
            PiecesFactory.setImageToPiece(newpiece);
        }
        
        return newpiece;
    }

}
