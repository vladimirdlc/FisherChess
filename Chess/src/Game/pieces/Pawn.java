package Game.pieces;

import Game.Board;
import Game.Square;
import Game.util.Vector2;
import java.awt.Toolkit;
import java.util.ArrayList;
/**
 *
 * @author Vladimir de la Cruz
 */
public class Pawn extends Piece {
    public Pawn(int x, int y) {
        super(x,y);
        firsTurn = true;
    }

    private boolean firsTurn;

    @Override
    public ArrayList<Vector2> getPossibleMoves() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        if(!this.isBlack()) //Have to go upr
        {
            mov.add(new Vector2(this.getX(), this.getY()-1));
            if(firsTurn) mov.add(new Vector2(this.getX(), this.getY()-2));
        }
        else
        {
            mov.add(new Vector2(this.getX(), this.getY()+1));
            if(firsTurn) mov.add(new Vector2(this.getX(), this.getY()+2));
        }

        return mov;
    }

    public ArrayList<Vector2> getCaptureMode() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        if(!this.isBlack()) //Tiene que subir
        {
            mov.add(new Vector2(this.getX()-1, this.getY()-1));
            mov.add(new Vector2(this.getX()+1, this.getY()-1));
        }
        else
        {
            mov.add(new Vector2(this.getX()-1, this.getY()+1));
            mov.add(new Vector2(this.getX()+1, this.getY()+1));
        }

        return mov;
    }


    @Override
    public boolean checkRouteTo(int x, int y, Square[][] squares)
    {
        Vector2 posObjetivo = new Vector2(x,y);
        if(getPossibleMoves().contains(posObjetivo))
        {
            if(firsTurn)
            {
                if(y == this.getY()+2 && squares[y-1][x].isEmpty()) 
                { return true; }
                if(y == this.getY()-2 && squares[y+1][x].isEmpty())
                { return true; }
                if(Math.abs(y - this.getY()) == 1 && squares[y][x].isEmpty())
                { return true; }
            }
            else if(squares[y][x].isEmpty()) return true;
        }

        if(x < 0 || y < 0) return false;    
        if(x > squares.length-1 || y > squares.length-1) return false;    
        
        if(getCaptureMode().contains(posObjetivo) && !squares[y][x].isEmpty() && squares[y][x].getPiece().isBlack() != this.isBlack())
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean moveCapture(int x, int y, Square[][] squares) {
        if(!checkRouteTo(x, y, squares)) return false;
        firsTurn = false; 
        return (getCaptureMode().contains(new Vector2(x,y)) && squares[y][x].capture(this))
        || (squares[y][x].occupy(this));
    }

    public boolean canCaptureTo(int x, int y, Square[][] squares)
    {
        //The pawn have two ways of capture or move
        return this.getCaptureMode().contains(new Vector2(x, y));
    }

    public boolean canPlay(Board board)
    {
        boolean canPlay = false;
        int originalx = this.getX();
        int originaly = this.getY();

        for(Vector2 pos : this.getPossibleMoves())
        {
            if(pos.getX() < 0 || pos.getY() < 0) continue;
            
            if(checkRouteTo(pos.getX(),pos.getY(),board.getSquares()))
            {
                this.setX(pos.getX());
                this.setY(pos.getY());

                board.getSquares()[pos.getY()][pos.getX()].occupy(this);

                if(!board.isInCheckColor(this.isBlack()))//Si no esta en jaque mi color
                { board.getSquares()[pos.getY()][pos.getX()].desocupar(); canPlay = true; break; }

                board.getSquares()[pos.getY()][pos.getX()].desocupar();
            }
        }

        this.setX(originalx);
        this.setY(originaly);

        for(Vector2 pos : this.getCaptureMode())
        {
            if(pos.getX() < 0 || pos.getY() < 0) continue;
            
            if(checkRouteTo(pos.getX(),pos.getY(),board.getSquares()))
            if(board.getSquares()[pos.getY()][pos.getX()].getPiece().isBlack() != this.isBlack()
                    && !board.isInCheckColor(this.isBlack()))
            {
                canPlay = true;
                break;
            }

            this.setX(pos.getX());
            this.setY(pos.getY());
        }

        this.setX(originalx);
        this.setY(originaly);

        return canPlay;
    }

    public boolean isFiirstTurn() {
        return firsTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firsTurn = firstTurn;
    }

}
