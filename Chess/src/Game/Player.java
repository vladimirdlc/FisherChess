package Game;


import java.awt.Graphics;
import java.awt.Image;

/**
 * Representa a un jugador con su name y que bando juega: blancas o negras.
 * @author Vladimir
 */
public class Player {
    private String name;
    private boolean isBlack;
    private Image avatar;

    public Player(String name, boolean black)
    {
        this.name = name;
        this.isBlack = black;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setIsBlack(boolean esNegro) {
        this.isBlack = esNegro;
    }

    public String getName() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public void setAvatar(Image image) {
        this.avatar = image;
    }

    public void drawName(Graphics g, int x, int y) {
        g.drawString(name, x, y);
    }

    public void drawAvatar(Graphics g, int x, int y) {
        g.drawImage(avatar, x, y, null);
    }

}
