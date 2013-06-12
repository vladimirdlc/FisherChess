package Game.modes;


import Game.pieces.Bishop;
import Game.pieces.Pawn;
import Game.pieces.Rook;
import Game.pieces.Piece;
import Game.pieces.Queen;
import Game.pieces.Knight;
import Game.pieces.King;
import java.awt.Toolkit;

/**
 *
 * @author Vladimir
 */
public abstract class PiecesFactory {

    public abstract Piece[][] initialize();

    protected Piece[][] generateMode(String modo) {
        Piece[][] processedPieces = new Piece[8][8];
        String[] filas = modo.split(";");
        int y = 0;
        for (String fil : filas) {
            int x = 0;
            for (String celda : fil.split(",")) {
                Piece pieceAgregar = null;

                if (celda.startsWith("rook")) {
                    pieceAgregar = new Rook(x, y);
                } else if (celda.startsWith("bishop")) {
                    pieceAgregar = new Bishop(x, y);
                } else if (celda.startsWith("queen")) {
                    pieceAgregar = new Queen(x, y);
                } else if (celda.startsWith("king")) {
                    pieceAgregar = new King(x, y);
                } else if (celda.startsWith("knight")) {
                    pieceAgregar = new Knight(x, y);
                } else if (celda.startsWith("pawn")) {
                    pieceAgregar = new Pawn(x, y);
                }

                if(pieceAgregar != null)
                {
                    if(celda.endsWith("!"))
                    {
                        pieceAgregar.setBlack(true);
                        pieceAgregar.setImagen(Toolkit.getDefaultToolkit().getImage("img/"+celda.replace("!","")+".png"));
                    }
                    else
                    {
                        pieceAgregar.setImagen(Toolkit.getDefaultToolkit().getImage("img/"+celda+"b.png"));
                    }
                }


                processedPieces[y][x] = pieceAgregar;
                x++;
            }
            y++;
        }


        return processedPieces;
    }
    
    
   public static void setImageToPiece(Piece piece)
   {
       if(piece instanceof Queen) 
       {
           String dir = "img/queen" + ((piece.isBlack()) ? "" : "b")+".png";    
           piece.setImagen(Toolkit.getDefaultToolkit().getImage(dir));
       }
   }
    
}
