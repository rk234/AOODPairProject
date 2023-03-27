package utils;

public class Vector2 {
    private float x;
    private float y;
   
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float a) {
        this.x = a;
        this.y = a;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(other.getX() + x, other.getY() + y);
    }


    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.getX(), y - other.getY());
    }

    public static float distance(Vector2 a, Vector2 b) {
        return b.subtract(a).magnitude();
    }


    public Vector2 scale(float scalar) {
        return new Vector2(x*scalar, y*scalar);
    }


    public Vector2 multiply(Vector2 other) {
        return new Vector2(other.getX() * x, other.getY() * y);
    }

    public float dot(Vector2 other) {
        return other.getX()*x + other.getY()*y;
    }

    public float magnitude() {
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vector2 normalize() {
        //returns unit vector
        return new Vector2(x/magnitude(), y/magnitude());
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return "Vec2(" + x + ", " + y + ")";
    }
}
