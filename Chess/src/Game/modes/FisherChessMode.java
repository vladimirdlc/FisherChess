package Game.modes;


import Game.util.Functions;
import Game.pieces.Piece;
import java.util.Random;

/**
 * Crea un modelo de inicializacion de pieces bajo la variante aleatoria de Fisher
 * @author Vladimir de la Cruz
 */
public class FisherChessMode extends PiecesFactory {

    @Override
    public Piece[][] initialize() {
        Random randomGenerator = new Random();
        String formacion[] = new String[8];
        int posAlfil = randomGenerator.nextInt(4)*2;
        formacion[posAlfil] = "bishop";
        int posAlfil2 = randomGenerator.nextInt(4)*2+1;
        formacion[posAlfil2] = "bishop";
        int posReina = getNEmpty(randomGenerator.nextInt(6)+1, formacion);
        formacion[posReina] = "queen";
        int posCaballo = getNEmpty(randomGenerator.nextInt(5)+1, formacion);
        formacion[posCaballo] = "knight";
        int posCaballo2 = getNEmpty(randomGenerator.nextInt(4)+1, formacion);
        formacion[posCaballo2] = "knight";
        int posTorre = getNEmpty(1, formacion);
        formacion[posTorre] = "rook";
        int posRey = getNEmpty(1, formacion);
        formacion[posRey] = "king";
        int posTorre2 = getNEmpty(1, formacion);
        formacion[posTorre2] = "rook";


        Piece[][] pieces =
        generateMode(
                Functions.join(formacion, "!,") + "!;"
                + "pawn!,pawn!,pawn!,pawn!,pawn!,pawn!,pawn!,pawn!;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "pawn,pawn,pawn,pawn,pawn,pawn,pawn,pawn;"
                + Functions.join(formacion, ",")
/*                "x,x,x,x,x,x,x,x;" tablas
                + "x,rook,x,x,x,pawn!,x,x;"
                + "x,x,x,x,king!,pawn,x,x;"
                + "rook,x,x,x,x,x,pawn,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,x,x,x,x,x;"
                + "x,x,x,queen,x,x,king,x"*/
                );

        return pieces;
    }


    public int getNEmpty(int n, String formacion[])
    {
        int posEmpty = 0;
        for(int i = 0, j = 0; i < formacion.length && j != n; i++)
        {
            if(formacion[i] == null || formacion[i].isEmpty())
            {
                posEmpty = i;
                j++;
            }
        }
        return posEmpty;
    }

}
