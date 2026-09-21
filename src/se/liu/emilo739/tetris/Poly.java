package se.liu.emilo739.tetris;

public class Poly
{
    private final int height;
    private final int width;
    private SquareType[][] poly;
    public  Poly(final SquareType[][] poly){
        this.poly = poly;
        this.height = poly.length;
        this.width = poly[0].length;
    }

    public SquareType[][] getPoly() {
        return poly;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    public Poly rotateRight() {

        Poly newPoly = new Poly(new SquareType[width][height]);

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++){
                newPoly.poly[c][height-1-r] = poly[r][c];
            }
        }

        return newPoly;
    }

    public Poly rotateLeft() {
        return rotateRight().rotateRight().rotateRight();
    }
}
