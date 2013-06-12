package Game.pieces;

import Game.Square;
import Game.util.Vector2;
import java.util.ArrayList;
/**
 *
 * @author Vladimir de la Cruz
 */
public class Knight extends Piece {
    public Knight(int x, int y) {
        super(x,y);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves() {
        ArrayList<Vector2> mov = new ArrayList<Vector2>();
        mov.add(new Vector2(this.getX()+1, this.getY()+2));
        mov.add(new Vector2(this.getX()+1, this.getY()-2));
        mov.add(new Vector2(this.getX()-1, this.getY()+2));
        mov.add(new Vector2(this.getX()-1, this.getY()-2));

        mov.add(new Vector2(this.getX()+2, this.getY()+1));
        mov.add(new Vector2(this.getX()+2, this.getY()-1));
        mov.add(new Vector2(this.getX()-2, this.getY()+1));
        mov.add(new Vector2(this.getX()-2, this.getY()-1));
        return mov;
    }

    @Override
    public boolean checkRouteTo(int x, int y, Square[][] casillas)
    {
        return getPossibleMoves().contains(new Vector2(x,y));
    }

}
