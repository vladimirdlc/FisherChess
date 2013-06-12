package Game;

import Game.pieces.Piece;

/**
 *
 * @author Vladimir
 */
public class Square {
    private boolean isEmpty;
    private Piece pieceEnCasilla;
    private int x;
    private int y;

    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.isEmpty = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }

    public void desocupar() {
        this.isEmpty = true;
        pieceEnCasilla = null;
    }

    public boolean occupy(Piece p) {
        if(isEmpty() )
        {
            p.setX(this.x);
            p.setY(this.y);
            pieceEnCasilla = p;
            isEmpty = false;
            return true;
        }
        else return false;
    }

    public boolean capture(Piece atacante) {
        //If is not empty and the piece is not of the same color
        boolean comio = !isEmpty() && (atacante.isBlack() != pieceEnCasilla.isBlack()) ;
        if(comio)
        {
            desocupar();
            occupy(atacante);
        }
        return comio;
    }

    public Piece getPiece()
    {
        return pieceEnCasilla;
    }

}
