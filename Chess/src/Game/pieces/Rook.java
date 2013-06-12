package Game.pieces;

import Game.Square;
import Game.util.Vector2;
import java.awt.Toolkit;
import java.util.ArrayList;
/**
 *
 * @author Vladimir de la Cruz
 */
public class Rook extends Piece {
    public Rook(int x, int y) {
        super(x,y);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        for(int i = 1; i < 8; i++)
        {
            mov.add(new Vector2(this.getX(), this.getY()+i));
            mov.add(new Vector2(this.getX(), this.getY()-i));
            mov.add(new Vector2(this.getX()+i, this.getY()));
            mov.add(new Vector2(this.getX()-i, this.getY()));
        }
        return mov;
    }

    @Override
    public boolean checkRouteTo(int x, int y, Square[][] squares)
    {
        ArrayList<Vector2> mov = getPossibleMoves();
        Vector2 posObjetive = new Vector2(x,y);
        boolean validated = true;

        if(mov.contains(posObjetive))
        {
            if(x > this.getX())
            {
                for(int i = this.getX()+1; i < x; i++)
                {
                    if(!mov.contains(new Vector2(i,y)) || (i != x && !squares[y][i].isEmpty())) validated = false;
                }
            }
            else if(x < this.getX())
            {
                for(int i = x+1; i < this.getX(); i++)
                {
                    if(!mov.contains(new Vector2(i,y)) || (i != x && !squares[y][i].isEmpty())) validated = false;
                }
            }
            else if(y < this.getY())
            {
                for(int i = y+1; i < this.getY(); i++)
                {
                    if(!mov.contains(new Vector2(x,i)) || !squares[i][x].isEmpty()) validated = false;
                }
            }
            else if(y > this.getY())
            {
                for(int i = this.getY()+1; i < y; i++)
                {
                    if(!mov.contains(new Vector2(x,i)) || !squares[i][x].isEmpty()) validated = false;
                }
            }
       }
        else return false;

        return validated;
    }
}
