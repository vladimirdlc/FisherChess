package Game.modes;

import Game.pieces.Piece;

/**
 * Crea un ajedrez en modo de ajedrez clasico
 * @author Vladimir de la Cruz
 */
public class ClassicChessMode extends PiecesFactory {

    @Override
    public Piece[][] initialize() {
        Piece[][] pieces =
        generateMode(
                "rook!,knight!,bishop!,queen!,king!,bishop!,knight!,rook!;"
                + "pawn!,pawn!,pawn!,pawn!,pawn!,pawn!,pawn!,pawn!;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,r,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "pawn,pawn,pawn,pawn,pawn,pawn,pawn,pawn;"
                + "rook,knight,bishop,queen,king,bishop,knight,rook"
                );

        return pieces;
    }

}
