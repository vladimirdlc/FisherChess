package Game;

import Game.modes.ClassicChessMode;
import Game.modes.FisherChessMode;
import Game.pieces.Piece;
import Game.pieces.King;
import Game.pieces.Pawn;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Panel que muestra el board de juego en pantalla
 */
public class JPanelGame extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean isMouse; //Controla si el mouse esta en la zona de dibujo
    private int marginLeft;
    private int marginUp;
    private int difenX; // Difference between the position of the block and the mouse position "x"
    private int difenY; // Difference between the position of the block and the mouse position "y"
    private boolean evtClick = false;
    private boolean pieceSelected = false;
    Player playerWhite, playerBlack;
    boolean isBlackTurn = false;
    Board board;

    public JPanelGame() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        marginLeft = 150;
        marginUp = 5;
        isMouse = false;
        new Repaint().start();

        String namePWhites = "";
        namePWhites += JOptionPane.showInputDialog(null,
                "Input the white's player name",
                "Before the beggining...",
                JOptionPane.PLAIN_MESSAGE);
        
        if(namePWhites.isEmpty()) namePWhites = "WhitePlayer";
        
        playerWhite = new Player(namePWhites, false);
        String namePBlack = "";
        namePBlack += JOptionPane.showInputDialog(null,
                "Input the black's player name",
                "Before the beggining...",
                JOptionPane.PLAIN_MESSAGE);
        
        if(namePBlack.isEmpty()) namePBlack = "BlackPlayer";

        playerBlack = new Player(namePBlack, true);
        playerWhite.setAvatar(Toolkit.getDefaultToolkit().getImage("img/blanca.png"));
        playerBlack.setAvatar(Toolkit.getDefaultToolkit().getImage("img/negra.png"));

        Object[] options = {"Classic Mode",
            "Fisher Mode"};
        int n = JOptionPane.showOptionDialog(null,
                "Game Mode",
                "Chose modality",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        board = (n == 0) ? new Board(new ClassicChessMode()) : new Board(new FisherChessMode());
        board.setImage(Toolkit.getDefaultToolkit().getImage("img/tablero.png"));
    }

    /*
     * Funcion que recibe un componente grafico y draw completo
     * @param g Componente grafico
     */
    public void paintComponent(Graphics g) {
        board.draw(g);
        board.drawPieces(g, 50, 50, 200, 0);

        if (evtClick == true) {
            drawTakenPiece(g);
        }

        g.setColor(new Color(255, 255, 255, 35));
        g.fillRect(15, 106, 120, 100);
        g.fillRect(665, 106, 120, 100);
        g.setColor(new Color(255, 255, 255, 255));

        playerWhite.drawName(g, 20, 121);
        playerBlack.drawName(g, 670, 121);

        if (!isBlackTurn) {
            g.drawString("Your turn ", 20, 135);
            playerBlack.drawAvatar(g, 48, 145);
        } else {
            g.drawString("Your turn ", 670, 135);
            playerWhite.drawAvatar(g, 700, 145);
        }

    }

    public void drawTakenPiece(Graphics g) {
        if (board.getTakenPiece() != null) {
            board.getTakenPiece().drawConDif(g, difenX - 25, difenY - 25);
        }
    }

    /*
     * Checks whether the mouse is over a piece
     * @param Piece piece against wich the revision is made
     */
    public boolean valInfoMouse(Piece piece) {
        boolean info = false;
        if (isMouse) {
            int x1 = marginLeft + piece.getX() * 50 + 50;
            int x2 = marginLeft + piece.getX() * 50 + 100;
            int y1 = marginUp + piece.getY() * 50;
            int y2 = marginUp + piece.getY() * 50 + 50;

            try {
                if (isMouse && getMousePosition().x <= x2 && getMousePosition().x >= x1 && getMousePosition().y <= y2 && getMousePosition().y >= y1) {
                    info = true;
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        return info;
    }

    public void mousePressed(MouseEvent e) {
        pieceSelected = false;
        moveClickedPiece();
        difenX = e.getX();
        difenY = e.getY();
    }
    
    private void moveClickedPiece()
    {
            for (Piece piece : board.getPieces()) { //Check wich piece was taken
            if (valInfoMouse(piece)) {
                if (((piece.isBlack() && isBlackTurn)) || ((!piece.isBlack() && !isBlackTurn))) {
                    board.setTakenPiece(piece);
                    try {
                        board.setOriginalPiece((Piece) piece.Clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(JPanelGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    pieceSelected = true;
                    board.getSquares()[piece.getY()][piece.getX()].desocupar();
                    evtClick = true;
                    break;
                }
            } else {
                evtClick = false;
            }
        }
    }

    //If the mouse enters a drawable area
    public void mouseEntered(MouseEvent e) {
        isMouse = true;
    }
    
    public void mouseDragged(MouseEvent e) {
        if (pieceSelected == true) {
            evtClick = true;
            difenX = e.getX();
            difenY = e.getY();
        }
    }

    public void restoreMovement() {
        board.restoreMovement();
        pieceSelected = false;
    }

    public void showJaqueMate() {
        JOptionPane.showMessageDialog(this, "Win " + ((isBlackTurn) ? playerBlack.getName() : playerWhite.getName())
                + " of the " + ((isBlackTurn) ? "Black's" : "White's"));
    }

    public void mouseReleased(MouseEvent e) {
        if (evtClick == true) {
            evtClick = false;
            playerMoveLogic();
        }
    }
    
    private void playerMoveLogic()
    {
        Square casilla = null;
        Piece pieceEnDestino = null;

        if ((casilla = getDestinySquare()) != null) {
            pieceEnDestino = casilla.getPiece();
            boolean hasMoved = board.getTakenPiece().moveCapture(casilla.getX(), casilla.getY(), board.getSquares());

            if (hasMoved) {
                if (board.isInCheckColor(isBlackTurn)) {
                    JOptionPane.showMessageDialog(this, "Invalid move. Puts your king in Check.");
                    restoreMovement();

                    if (pieceEnDestino != null) {
                        casilla.occupy(pieceEnDestino);
                    }
                } 
                else
                {
                    if (board.isInCheckMateColor(!isBlackTurn)) {
                        JOptionPane.showMessageDialog(this, "CheckMate");
                        showJaqueMate();
                        isBlackTurn = !isBlackTurn;
                    } else if (board.isDrownedColor(!isBlackTurn)) {
                        JOptionPane.showMessageDialog(this, "Draw for drowning.");
                        isBlackTurn = !isBlackTurn;
                    } else {
                        if (pieceEnDestino instanceof King) {
                            showJaqueMate();
                        }
                        pieceSelected = false;
                        isBlackTurn = !isBlackTurn;
                    }
                    
                    if(board.getTakenPiece() instanceof Pawn)
                    {
                        //Sólo si llegó al final
                        if(casilla.getY() == 0 || casilla.getY() == board.getSquares().length-1)
                        {
                            casilla.desocupar();
                            Piece promotedPiece = board.promotePiece(board.getTakenPiece(), "Queen");
                            casilla.occupy(promotedPiece);
                        }
                    }
                }
            } else {
                restoreMovement();
            }
        } 
        else {
            restoreMovement();
        }
    }

    /*
     * Method tha search the square above wich the mouse was pressed
     * @return Square The square pressed by the mouse
     */
    private Square getDestinySquare() {
        for (int y = 0; y < board.getSquares().length; y++) {
            for (int x = 0; x < board.getSquares()[y].length; x++) {
                if (isMouse) {
                    int x1 = marginLeft + x * 50 + 50;
                    int x2 = marginLeft + x * 50 + 100;
                    int y1 = marginUp + y * 50;
                    int y2 = marginUp + y * 50 + 50;

                    try {
                        if (isMouse && getMousePosition().x <= x2 && getMousePosition().x >= x1 && getMousePosition().y <= y2 && getMousePosition().y >= y1) {
                            return board.getSquares()[y][x];
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }

        return null;
    }

    private class Repaint extends Thread {

        public void run() {
            while (true) {
                repaint();
                try {
                    this.sleep(40);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
