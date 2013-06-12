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
public class King extends Piece {
    public King(int x, int y) {
        super(x,y);
    }

    /*
     * Check whether this king is in check
     * @param tablero Un modelo de Board
     * @return Si se encuentra en jaque este King
     */
    public boolean isInCheck(Board board)
    {
        for(Piece p : board.getPiecesColor(!this.isBlack()))
        {
            //The pawn has two ways to move or capture
            if(!p.canCaptureTo(this.getX(), this.getY(), board.getSquares())) continue;
            else return true;
        }

        return false;
    }

    public boolean isInCheckMate(Board tablero, Vector2 origenAtaque) {

        for(Piece p : tablero.getPiecesColor(this.isBlack()))
        {
            if(p == this) continue;
            if(p.canPlay(tablero)) return false;
            if(p.canCaptureTo(origenAtaque.getX(), origenAtaque.getY(), tablero.getSquares())) return false;
        }

        return (this.isInCheck(tablero)) && !this.canPlay(tablero);
    }


    public boolean isDrowned(Board tablero, Vector2 origenAtaque) {

        for(Piece p : tablero.getPiecesColor(this.isBlack()))
        {
             if(p == this) continue;
             if(p.canPlay(tablero)) return false;
             if(p.canCaptureTo(origenAtaque.getX(), origenAtaque.getY(), tablero.getSquares())) return false;
        }

        //Si no esta en jaque y no puede jugar esta ahogado
        return (!this.isInCheck(tablero)) && !this.canPlay(tablero);
    }

    /*
     * Determine wether someone can move without ending in draw
     * @param boad model of Board
     * @return If have availible moves
     */
    public boolean canPlay(Board board) {
        boolean canPlay = false;
        int originalx = this.getX();
        int originaly = this.getY();
        int limitxy = board.getSquares().length-1;

        for(Vector2 pos : this.getPossibleMoves())
        {
            if(pos.getX() < 0 || pos.getY() < 0) continue;
            if(pos.getX() > limitxy || pos.getY() > limitxy) continue;
            //5,0
            if(board.getSquares()[pos.getY()][pos.getX()].isEmpty() ||
                (board.getSquares()[this.getY()][this.getX()].getPiece() != null 
                 && board.getSquares()[this.getY()][this.getX()].getPiece().isBlack() != this.isBlack()))
            {
                this.setX(pos.getX());
                this.setY(pos.getY());

                if(!this.isInCheck(board))
                { canPlay = true; break; }
            }
        }

        this.setX(originalx);
        this.setY(originaly);

        return canPlay;
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        mov.add(new Vector2(this.getX(), this.getY()+1));
        mov.add(new Vector2(this.getX(), this.getY()-1));
        mov.add(new Vector2(this.getX()+1, this.getY()));
        mov.add(new Vector2(this.getX()-1, this.getY()));
        mov.add(new Vector2(this.getX()+1, this.getY()+1));
        mov.add(new Vector2(this.getX()-1, this.getY()-1));
        mov.add(new Vector2(this.getX()-1, this.getY()+1));
        mov.add(new Vector2(this.getX()+1, this.getY()-1));
        return mov;
    }

    @Override
    public boolean checkRouteTo(int x, int y, Square[][] casillas)
    {
        Vector2 posObjetivo = new Vector2(x,y);
        if(getPossibleMoves().contains(posObjetivo))
        {
            return true;
        }
        else return false;
    }


}
