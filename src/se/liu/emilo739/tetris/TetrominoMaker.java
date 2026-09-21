package se.liu.emilo739.tetris;

public class TetrominoMaker {

    public Poly getPoly(int n){
	SquareType[][] poly;
	switch (n){
	    case 0:
		//  - - - -
		//  x x x x
		//  - - - -
		//  - - - -
		poly = createEmptyPoly(4, 4);
		poly[1][0] = poly[1][1] = poly[1][2] = poly[1][3] = SquareType.I;
		break;
	    case 1:
		poly = createEmptyPoly(4, 4);
		poly[2][2] = SquareType.L; // [ - - - - ]
		poly[1][0] = SquareType.L; // [ x - - - ]
		poly[2][0] = SquareType.L; // [ x x x - ]
		poly[2][1] = SquareType.L; // [ - - - - ]
		break;
	    case 2:
		poly = createEmptyPoly(4, 4);
		poly[2][2] = SquareType.J; // [ - - - - ]
		poly[1][2] = SquareType.J; // [ - - x - ]
		poly[2][0] = SquareType.J; // [ x x x - ]
		poly[2][1] = SquareType.J; // [ - - - - ]
		break;
	    case 3:
		poly = createEmptyPoly(4, 4);
		poly[1][1] = SquareType.S; // [ - - - - ]
		poly[1][2] = SquareType.S; // [ - x x - ]
		poly[2][1] = SquareType.S; // [ - x x - ]
		poly[2][2] = SquareType.S; // [ - - - - ]
		break;
	    case 4:
		poly = createEmptyPoly(4, 4);
		poly[1][1] = SquareType.Z; // [ - x x - ]
		poly[1][2] = SquareType.Z; // [ x x - - ]
		poly[2][0] = SquareType.Z; // [ - - - - ]
		poly[2][1] = SquareType.Z; // [ - - - - ]
		break;
	    case 5:
		poly = createEmptyPoly(4, 4);
		poly[2][2] = SquareType.T; // [ - - - - ]
		poly[1][1] = SquareType.T; // [ - x - - ]
		poly[2][0] = SquareType.T; // [ x x x - ]
		poly[2][1] = SquareType.T; // [ - - - - ]
		break;
	    case 6:
		poly = createEmptyPoly(4, 4);
		poly[2][2] = SquareType.O;//  [ - - - - ]
		poly[1][0] = SquareType.O; // [ x x - - ]
		poly[1][1] = SquareType.O; // [ - x x - ]
		poly[2][1] = SquareType.O; // [ - - - - ]
		break;
	    default:
		throw new IllegalArgumentException("Invalid index " + n);
	}
	Poly polyObject = new Poly(poly);
	return polyObject;
    }
    private static SquareType[][] createEmptyPoly(int width, int height){
	SquareType[][] poly = new SquareType[width][height];
	for (int x = 0; x < width; x++){
	    for (int y = 0; y < height; y++)
		poly[y][x] = SquareType.EMPTY;
	}
	return poly;
    }
}
