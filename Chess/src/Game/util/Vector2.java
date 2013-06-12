package Game.util;



public class Vector2 {

    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2(Vector2 original) {
        this.x = original.x;
        this.y = original.y;
    }

    public Vector2 normalizar() {
        Vector2 aux = new Vector2(this);
        aux.normalizarAsigna();
        return aux;
    }

    public void normalizarAsigna() {
        double lng = longitud();
        x /= lng;
        y /= lng;
    }

    public void sumaAsigna(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Vector2 suma(Vector2 operando) {
        Vector2 aux = new Vector2(this);
        aux.sumaAsigna(operando);
        return aux;
    }

    public void sumaAsigna(Vector2 operando) {
        this.sumaAsigna(operando.x, operando.y);
    }

    public void restaAsigna(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public Vector2 resta(Vector2 operando) {
        Vector2 aux = new Vector2(this);
        aux.restaAsigna(operando);
        return aux;
    }

    public void restaAsigna(Vector2 operando) {
        this.restaAsigna(operando.x, operando.y);
    }


    public void escalarAsigna(double escala) {
        this.x *= escala;
        this.y *= escala;
    }

    public Vector2 escalar(double escala) {
        Vector2 aux = new Vector2(this);
        aux.escalarAsigna(escala);
        return aux;
    }

    public boolean equals(int x, int y)
    {
        return (getX() == x && getY() == y);
    }


    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Vector2 comp = (Vector2) obj;
        return (getX() == comp.getX() && getY() == comp.getY());
    }

    /** Obtiene la longitud del vector actual. */
    public double longitud() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}