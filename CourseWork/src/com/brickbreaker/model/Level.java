
package com.brickbreaker.model;

import java.awt.*;

import com.brickbreaker.controller.Brick;
import com.brickbreaker.controller.Wall;

/**
 * Class of the level
 */

public class Level {

	/** Counting level */
	private static final int LEVELS_COUNT = 10;

	private static final int CLAY = 1;
	private static final int STEEL = 2;
	private static final int CEMENT = 3;

	private Brick[][] levels;
	private int level;

	private static Wall wall;

	/** Level constructor */
	public Level(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Wall wall) {
		levels = makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
		level = 0;
		this.wall = wall;

	}

	/** Makes a level using 1 brick's type */

	private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio,
			int type) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			double x = (i % brickOnLine) * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);
			tmp[i] = makeBrick(p, brickSize, type);
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			tmp[i] = new ClayBrick(p, brickSize);
		}
		return tmp;

	}

	/** this method makes a level using 2 brick's types */
	private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int F_type,
			int S_type) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		int centerLeft = brickOnLine / 2 - 1;
		int centerRight = brickOnLine / 2 + 1;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			int posX = i % brickOnLine;
			double x = posX * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);

			boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
			tmp[i] = b ? makeBrick(p, brickSize, F_type) : makeBrick(p, brickSize, S_type);
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			tmp[i] = makeBrick(p, brickSize, F_type);
		}
		return tmp;
	}

	/**
	 * this method creates level for the game. (Creating an array which represents a
	 * wall of bricks that should be loaded and drawn) the area the levels must
	 * encompass the number of bricks in each level the number of rows of bricks in
	 * each level the sizes of bricks
	 */
	private Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
		Brick[][] tmp = new Brick[LEVELS_COUNT][];
		tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
		tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
		tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
		tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);
		tmp[4] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CEMENT, CEMENT);
		tmp[5] = makemoreLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL, CLAY, CEMENT);
		return tmp;
	}
	/** makeBrick creates a brick of the types */
	private Brick makeBrick(Point point, Dimension size, int type) {
		Brick out;
		switch (type) {
		case CLAY:
			out = new ClayBrick(point, size);
			break;
		case STEEL:
			out = new SteelBrick(point, size);
			break;
		case CEMENT:
			out = new CementBrick(point, size);
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
		}
		return out;
	}
	/** this method makes a level using 3 brick's types */
	private Brick[] makemoreLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int F_type,
			int S_type, int T_type) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			int posX = i % brickOnLine;
			double x = posX * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);

			if (line % 3 == 0) {
				tmp[i] = makeBrick(p, brickSize, F_type);
			} else if (line % 3 == 1) {
				tmp[i] = makeBrick(p, brickSize, S_type);
			} else {
				tmp[i] = makeBrick(p, brickSize, T_type);
			}
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			if (y % 3 == 0) {
				tmp[i] = makeBrick(p, brickSize, F_type);
			} else if (y % 3 == 1) {
				tmp[i] = makeBrick(p, brickSize, S_type);
			} else {
				tmp[i] = makeBrick(p, brickSize, T_type);
			}
		}
		return tmp;
	}
	/** load the next level by adding new bricks and plus level */
	public void nextLevel() {
		if (hasLevel()) {
			wall.setBricks(levels[level++]);
			wall.setBrickCount(wall.getBricks().length);
		}
	}
	/** if there is a next level */
	public boolean hasLevel() {
		return level < levels.length;
	}
	/** get current level */
	public int getLevel() {
		return level;
	}

}