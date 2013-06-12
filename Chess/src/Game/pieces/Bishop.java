package Game.pieces;

import Game.Square;
import Game.util.Vector2;
import java.util.ArrayList;
/**
 * Representa una piece Bishop
 * @author Vladimir de la Cruz
 */
public class Bishop extends Piece {
    public Bishop(int x, int y) {
        super(x,y);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        for(int i = 1; i < 8; i++)
        {
            mov.add(new Vector2(this.getX()+i, this.getY()+i));
            mov.add(new Vector2(this.getX()-i, this.getY()-i));
            mov.add(new Vector2(this.getX()-i, this.getY()+i));
            mov.add(new Vector2(this.getX()+i, this.getY()-i));
        }
        return mov;
    }

    @Override
    public boolean checkRouteTo(int x, int y, Square[][] casillas)
    {
        ArrayList<Vector2> mov = getPossibleMoves();
        Vector2 posObjetivo = new Vector2(x,y);
        boolean validated = true;

        if(mov.contains(posObjetivo))
        {
            if(x > this.getX() && y > this.getY())
            {
                for(int i = x-this.getX()-1; i > 0; i--)
                {
                    if(!mov.contains(new Vector2(this.getX()+i,this.getY()+i))
                            || !casillas[this.getY()+i][this.getX()+i].isEmpty()) validated = false;
                }
            }
            else if(x < this.getX() && y < this.getY())
            {
                for(int i = this.getX()-x-1; i > 0; i--)
                {
                    if(!mov.contains(new Vector2(x+i,y+i))
                            || !casillas[y+i][x+i].isEmpty()) validated = false;
                }
            }
            else if(x < this.getX() && y > this.getY())
            {
                for(int i = this.getX()-x-1; i > 0; i--)
                {
                    if(!mov.contains(new Vector2(this.getX()-i,this.getY()+i))
                            || !casillas[this.getY()+i][this.getX()-i].isEmpty()) validated = false;
                }
            }
            else if(x > this.getX() && y < this.getY())
            {
                for(int i = x-this.getX()-1; i > 0; i--)
                {
                    if(!mov.contains(new Vector2(this.getX()+i,this.getY()-i))
                            || !casillas[this.getY()-i][this.getX()+i].isEmpty()) validated = false;
                }
            }
        }
        else return false;

        return validated;
    }

}
