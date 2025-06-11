/**
 * Vector2 with int instead of double
 * Cross-compatibility between this and Vector2 is not guarenteed
 * 
 * @author Portal
 * @version 1.1.0
 */
public class Vector2i  
{
    private int x;
    private int y;
    
    public Vector2i(int _x, int _y) {
        x = _x;
        y = _y;
    }
    public Vector2i(Vector2i other) { // dont ask.
        x = other.x();
        y = other.y();
    }
    public Vector2i(Vector2 other) { // dont ask.
        x = (int) other.x();
        y = (int) other.y();
    }
    public Vector2i() {
        x = 0;
        y = 0;
    }
    
    public int x() { return x; }
    public int y() { return y; }
    
    public Vector2i clone() {
        return new Vector2i(this);
    }

    public void setX(int _x) {x = _x; }
    public void setY(int _y) {y = _y; }
    public void set(Vector2i other) { set(other.x(), other.y()); }
    public void set(int _x, int _y) {
        x = _x;
        y = _y;
    }
    
    public void addX(int _x) { x += _x; }
    public void addY(int _y) { y += _y; }
    public void add(Vector2i other) { add(other.x(), other.y()); }
    public void add(int a) { add(a, a); }
    public void add(int _x, int _y) {
        x += _x;
        y += _y;
    }
    
    public static Vector2i addX(Vector2i v, int a) {
        Vector2i c = v.clone();
        c.addX(a);
        return c;
    }
    public static Vector2i addXY(Vector2i v, int a) {
        Vector2i c = v.clone();
        c.addY(a);
        return c;
    }
    public static Vector2i add(Vector2i v, int a) {
        Vector2i c = v.clone();
        c.add(a, a);
        return c;
    }
    public static Vector2i add(Vector2i v, Vector2i a) {
        Vector2i c = v.clone();
        c.add(a);
        return c;
    }
    
    public void subX(int _x) { x -= _x; }
    public void subY(int _y) { y -= _y; }
    public void subtract(Vector2i other) { subtract(other.x(), other.y()); }
    public void subtract(int s) { subtract(s, s); }
    public void subtract(int _x, int _y) {
        x -= _x;
        y -= _y;
    }
    
    public static Vector2i subX(Vector2i v, int s) {
        Vector2i c = v.clone();
        c.subX(s);
        return c;
    }
    public static Vector2i subY(Vector2i v, int s) {
        Vector2i c = v.clone();
        c.subY(s);
        return c;
    }
    public static Vector2i subtract(Vector2i v, int s) {
        Vector2i c = v.clone();
        c.subtract(s, s);
        return c;
    }
    public static Vector2i subtract(Vector2i v, Vector2i s) {
        Vector2i c = v.clone();
        c.subtract(s.x(), s.y());
        return c;
    }
    
    public void multX(int _x) { x *= _x; }
    public void multY(int _y) { y *= _y; }
    public void multiply(Vector2i other) { multiply(other.x(), other.y()); }
    public void multiply(int m) { multiply(m, m); }
    public void multiply(int _x, int _y) {
        x *= _x;
        y *= _y;
    }
    
    public static Vector2i multX(Vector2i v, int m) {
        Vector2i c = v.clone();
        c.multX(m);
        return c;
    }
    public static Vector2i multY(Vector2i v, int m) {
        Vector2i c = v.clone();
        c.multY(m);
        return c;
    }
    public static Vector2i multiply(Vector2i v, int m) {
        Vector2i c = v.clone();
        c.multiply(m, m);
        return c;
    }
    
    public static Vector2i multiply(Vector2i v, Vector2i m) {
        Vector2i c = v.clone();
        c.multiply(m);
        return c;
    }
    
    public void divX(int _x) {x /= _x; }
    public void divY(int _y) {y /= _y; }
    public void divide(Vector2i other) { divide(other.x(), other.y()); }
    public void divide(int d) { divide(d, d); }
    public void divide(int _x, int _y) {
        x /= _x;
        y /= _y;
    }
    
    public static Vector2i divX(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.divX(d);
        return c;
    }
    public static Vector2i divY(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.divY(d);
        return c;
    }
    public static Vector2i divide(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.divide(d, d);
        return c;
    }
    public static Vector2i divide(Vector2i v, Vector2i d) {
        Vector2i c = v.clone();
        c.divide(d);
        return c;
    }
    
    public void modX(int _x) {x %= _x; }
    public void modY(int _y) {y %= _y; }
    public void modulo(Vector2i other) { modulo(other.x(), other.y()); }
    public void modulo(int d) { modulo(d, d); }
    public void modulo(int _x, int _y) {
        x %= _x;
        y %= _y;
    }
    
    public static Vector2i modX(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.modX(d);
        return c;
    }
    public static Vector2i modY(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.modY(d);
        return c;
    }
    public static Vector2i modulo(Vector2i v, int d) {
        Vector2i c = v.clone();
        c.modulo(d, d);
        return c;
    }
    public static Vector2i modulo(Vector2i v, Vector2i d) {
        Vector2i c = v.clone();
        c.modulo(d);
        return c;
    }

    
    public double getLen() {
        return Math.sqrt( (x * x) + (y * y) );
    }
    
    public boolean equals(Vector2i other) { return ( (x == other.x()) && (y == other.y()) ); }
    
    public boolean isEqualApprox(Vector2i other) { return ( MathUtil.isEqualApprox(x, other.x()) && MathUtil.isEqualApprox(y, other.y()) ); }

    public static Vector2 normalize(Vector2i v) {
        double len = v.getLen();
        return new Vector2(v.x() / len, v.y() / len);
    }
    
    public static Vector2 angleToVector(double angle) {
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }
    
    public void clamp(int maxX, int maxY) { clamp(0, maxX, 0, maxY); }
    public void clamp(Vector2i v) { clamp(v.x(), v.y()); }
    public void clamp(Vector2i v, Vector2i o) { clamp(v.x(), o.x(), v.y(), o.y()); }
    public void clamp(int minX, int maxX, int minY, int maxY) {
        x = MathUtil.clamp(x, minX, maxX);
        y = MathUtil.clamp(y, minY, maxY);
    }
    
    public static Vector2i clamp(Vector2i v, int minX, int maxX, int minY, int maxY) {
        Vector2i c = v.clone();
        c.clamp(minX, maxX, minY, maxY);
        return c;
    }
    
    public static Vector2i clamp(Vector2i v, int maxX, int maxY) {
        Vector2i c = v.clone();
        c.clamp(maxX, maxY);
        return c;
    }
    
    public Vector2 directionTo(Vector2i to) {
        return Vector2i.normalize(Vector2i.subtract(to, this));
    }
    
    public int distanceTo(Vector2i to) {
        return (int) Math.sqrt(Math.pow(to.x() - x, 2) + Math.pow(to.y() - y, 2));
    }
    
    public String toString() { return "(" + x + ", " + y + ")"; }
}
