package Game.modes;

import Game.pieces.Piece;

/**
 * Modo Test
 * @author Vladimir
 */
public class TestChessMode extends PiecesFactory {

    @Override
    public Piece[][] initialize() {
        Piece[][] pieces =
        generateMode(
                //Si se juega correctamente se puede probar tablas o jaque mate
                //cargar por archivos de texto si se quiere probar más facilmente (NIY)
                "x,x,x,x,x,x,x,x;"
                + "x,rook,x,x,x,x,pawn!,x;"
                + "x,x,x,x,x,king!,pawn,x;"
                + "rook,queen,x,x,x,x,x,pawn;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "pawn!,x,pawn!,x,x,x,x,x;"
                + "rook!,bishop!,pawn!,x,x,x,x,king"
                );

        return pieces;
    }

}
