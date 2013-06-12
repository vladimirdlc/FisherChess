package Game.pieces;

import Game.Board;
import Game.Square;
import Game.util.Vector2;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/**
 * Abstract class that contains methods and properties that apply to all pieces
 * @author Vladimir
 */
public abstract class Piece implements Cloneable {
    private int x;
    private int y;

    private Image imagen;
    private boolean negra;

    public boolean isBlack() {
        return negra;
    }

    public void setBlack(boolean negra) {
        this.negra = negra;
    }


    /*
     * Creates an instance of piece setting position
     * @param x Origin at X
     * @param y Origin at Y
     */
    public Piece(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Object Clone() throws CloneNotSupportedException
    {
        return super.clone();
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

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public void draw(Graphics g)
    {
       g.drawImage(imagen, x, y, null);
    }
    public void drawConDif(Graphics g, int px, int py)
    {
       g.drawImage(imagen, x+px, y+py, null);
    }

    public boolean puedeMoverseA(int x, int y)
    {
        return this.getPossibleMoves().contains(new Vector2(x, y));
    }

    public boolean canCaptureTo(int x, int y, Square[][] casillas)
    {
        return checkRouteTo(x, y, casillas);
    }

    public abstract ArrayList<Vector2> getPossibleMoves();

    /*
     * Check if a path to a square is truly valid
     * @param x Pos X de la casilla
     * @param y Pos Y de la casilla
     * @param casillasA two-dimensional array of squares
     * @return Si es una ruta valida
     */
    public abstract boolean checkRouteTo(int x, int y, Square[][] casillas);

    /*
    * Try to occupy a position capturing or moving
    * @param x Pos X de la casilla
    * @param y Pos Y de la casilla
    * @param casillas Un arreglo bidimensional de las casillas
    * @return Si pudo mover/comer dicha posicion
    */
    public boolean moveCapture(int x, int y, Square[][] casillas)
    {
        if(!checkRouteTo(x, y, casillas)) return false; 
        return (casillas[y][x].capture(this) || casillas[y][x].occupy(this));
    }

    /*
    * Determines wheter a movement can played without putting his king in check
    * @param table A model of Board
    * @return If is able to play
    */
    public boolean canPlay(Board board)
    {
        boolean canPlay = false;
        int originalx = this.getX();
        int originaly = this.getY();
        int boardmax = board.getSquares().length-1;

        for(Vector2 pos : this.getPossibleMoves())
        {
            if(pos.getX() < 0 || pos.getY() < 0) continue;
            if(pos.getX() > boardmax || pos.getY() > boardmax) continue;
            
            if(checkRouteTo(pos.getX(),pos.getY(),board.getSquares()))
            {
                if(board.getSquares()[pos.getY()][pos.getX()].isEmpty()
                        || board.getSquares()[pos.getY()][pos.getX()].getPiece().isBlack() != this.isBlack())
                {
                    this.setX(pos.getX());
                    this.setY(pos.getY());

                    if(!board.isInCheckColor(this.negra))//If is not in check my color
                    { canPlay = true; break; }
                }
            }
        }

        this.setX(originalx);
        this.setY(originaly);

        return canPlay;
    }

}
