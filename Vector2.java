/**
 * Vector2 class with double precision. (x, y)
 * 
 * @author Portal
 * @version 1.1.0
 */
public class Vector2  
{
    private double x;
    private double y;
    
    public Vector2(double _x, double _y) {
        x = _x;
        y = _y;
    }
    public Vector2(int _x, int _y) {
        x = (double) _x;
        y = (double) _y;
    }
    public Vector2(Vector2 other) { // dont ask.
        x = other.x();
        y = other.y();
    }
    public Vector2(Vector2i other) { // dont ask.
        x = (double) other.x();
        y = (double) other.y();
    }
    public Vector2() {
        x = 0.0;
        y = 0.0;
    }
    
    public double x() { return x; }
    public double y() { return y; }
    
    public Vector2 clone() {
        return new Vector2(this);
    }

    public void setX(double _x) {x = _x; }
    public void setY(double _y) {y = _y; }
    public void set(Vector2 other) { set(other.x(), other.y()); }
    public void set(double _x, double _y) {
        x = _x;
        y = _y;
    }
    
    public void addX(double _x) { x += _x; }
    public void addY(double _y) { y += _y; }
    public void add(Vector2 other) { add(other.x(), other.y()); }
    public void add(double a) { add(a, a); }
    public void add(double _x, double _y) {
        x += _x;
        y += _y;
    }
    
    public static Vector2 addX(Vector2 v, double a) {
        Vector2 c = v.clone();
        c.addX(a);
        return c;
    }
    public static Vector2 addXY(Vector2 v, double a) {
        Vector2 c = v.clone();
        c.addY(a);
        return c;
    }
    public static Vector2 add(Vector2 v, double a) {
        Vector2 c = v.clone();
        c.add(a, a);
        return c;
    }
    public static Vector2 add(Vector2 v, Vector2 a) {
        Vector2 c = v.clone();
        c.add(a);
        return c;
    }
    
    public void subX(double _x) { x -= _x; }
    public void subY(double _y) { y -= _y; }
    public void subtract(Vector2 other) { subtract(other.x(), other.y()); }
    public void subtract(double s) { subtract(s, s); }
    public void subtract(double _x, double _y) {
        x -= _x;
        y -= _y;
    }
    
    public static Vector2 subX(Vector2 v, double s) {
        Vector2 c = v.clone();
        c.subX(s);
        return c;
    }
    public static Vector2 subY(Vector2 v, double s) {
        Vector2 c = v.clone();
        c.subY(s);
        return c;
    }
    public static Vector2 subtract(Vector2 v, double s) {
        Vector2 c = v.clone();
        c.subtract(s, s);
        return c;
    }
    public static Vector2 subtract(Vector2 v, Vector2 s) {
        Vector2 c = v.clone();
        c.subtract(s.x(), s.y());
        return c;
    }
    
    public void multX(double _x) { x *= _x; }
    public void multY(double _y) { y *= _y; }
    public void multiply(Vector2 other) { multiply(other.x(), other.y()); }
    public void multiply(double m) { multiply(m, m); }
    public void multiply(double _x, double _y) {
        x *= _x;
        y *= _y;
    }
    
    public static Vector2 multX(Vector2 v, double m) {
        Vector2 c = v.clone();
        c.multX(m);
        return c;
    }
    public static Vector2 multY(Vector2 v, double m) {
        Vector2 c = v.clone();
        c.multY(m);
        return c;
    }
    public static Vector2 multiply(Vector2 v, double m) {
        Vector2 c = v.clone();
        c.multiply(m, m);
        return c;
    }
    
    public static Vector2 multiply(Vector2 v, Vector2 m) {
        Vector2 c = v.clone();
        c.multiply(m);
        return c;
    }
    
    public void divX(double _x) {x /= _x; }
    public void divY(double _y) {y /= _y; }
    public void divide(Vector2 other) { divide(other.x(), other.y()); }
    public void divide(double d) { divide(d, d); }
    public void divide(double _x, double _y) {
        x /= _x;
        y /= _y;
    }
    
    public static Vector2 divX(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.divX(d);
        return c;
    }
    public static Vector2 divY(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.divY(d);
        return c;
    }
    public static Vector2 divide(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.divide(d, d);
        return c;
    }
    public static Vector2 divide(Vector2 v, Vector2 d) {
        Vector2 c = v.clone();
        c.divide(d);
        return c;
    }
    
    public void modX(double _x) {x %= _x; }
    public void modY(double _y) {y %= _y; }
    public void modulo(Vector2 other) { modulo(other.x(), other.y()); }
    public void modulo(double d) { modulo(d, d); }
    public void modulo(double _x, double _y) {
        x %= _x;
        y %= _y;
    }
    
    public static Vector2 modX(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.modX(d);
        return c;
    }
    public static Vector2 modY(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.modY(d);
        return c;
    }
    public static Vector2 modulo(Vector2 v, double d) {
        Vector2 c = v.clone();
        c.modulo(d, d);
        return c;
    }
    public static Vector2 modulo(Vector2 v, Vector2 d) {
        Vector2 c = v.clone();
        c.modulo(d);
        return c;
    }
    
    public double getLen() {
        return Math.sqrt( (x * x) + (y * y) );
    }
    
    public boolean equals(Vector2 other) { return ( (x == other.x()) && (y == other.y()) ); }
    
    public boolean isEqualApprox(Vector2 other) { return ( MathUtil.isEqualApprox(x, other.x()) && MathUtil.isEqualApprox(y, other.y()) ); }

    public static Vector2 normalize(Vector2 v) {
        double len = v.getLen();
        return new Vector2(v.x() / len, v.y() / len);
    }
    
    public void normalize() {
        double len = Math.sqrt( (x * x) + (y * y) );
        x /= len;
        y /= len;
    }
    
    public static Vector2 angleToVector(double angle) {
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }
    
    public void clamp(double maxX, double maxY) { clamp(0.0, maxX, 0.0, maxY); }
    public void clamp(Vector2 v) { clamp(v.x(), v.y()); }
    public void clamp(Vector2 v, Vector2 o) { clamp(v.x(), o.x(), v.y(), o.y()); }
    public void clamp(double minX, double maxX, double minY, double maxY) {
        x = MathUtil.clamp(x, minX, maxX);
        y = MathUtil.clamp(y, minY, maxY);
    }
    
    public static Vector2 clamp(Vector2 v, double minX, double maxX, double minY, double maxY) {
        Vector2 c = v.clone();
        c.clamp(minX, maxX, minY, maxY);
        return c;
    }
    
    public static Vector2 clamp(Vector2 v, double maxX, double maxY) {
        Vector2 c = v.clone();
        c.clamp(maxX, maxY);
        return c;
    }
    
    public double cross(Vector2 other) {
        Vector2 ns = Vector2.normalize(other);
        Vector2 no = Vector2.normalize(other);
        return (ns.x() * no.y()) - (ns.y() * no.x());
    }
    
    public double dot(Vector2 other) {
        Vector2 ns = Vector2.normalize(other);
        Vector2 no = Vector2.normalize(other);
        return (ns.x() * no.x()) + (ns.y() * no.y());
    }
    
    public Vector2 directionTo(Vector2 to) {
        return Vector2.normalize(Vector2.subtract(to, this));
    }
    
    public double distanceTo(Vector2 to) {
        return Math.sqrt(Math.pow(to.x() - x, 2) + Math.pow(to.y() - y, 2));
    }
    
    public String toString() { return "(" + x + ", " + y + ")"; }
}
