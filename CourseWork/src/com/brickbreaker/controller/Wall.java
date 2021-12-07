/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.brickbreaker.controller;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import com.brickbreaker.model.Ball;
import com.brickbreaker.model.CementBrick;
import com.brickbreaker.model.ClayBrick;
import com.brickbreaker.model.Player;
import com.brickbreaker.model.RubberBall;
import com.brickbreaker.model.Score;
import com.brickbreaker.model.SteelBrick;

public class Wall {


	private Random rnd;
	private Rectangle area;

	public Brick[] bricks;
	public Ball ball;
	public Player player;

	private Brick[][] levels;
	private int level;

	private Point startPoint;
	private int brickCount;
	private int ballCount;
	private boolean ballLost;
	public Score score;

	private String highScore = "Nobody:0";

	public Wall(Rectangle drawArea,  Point ballPos) {

		this.startPoint = new Point(ballPos);

		ballCount = 3;
		ballLost = false;

		rnd = new Random();

		makeBall(ballPos);
		int speedX, speedY;
		do {
			speedX = rnd.nextInt(5) - 2;
		} while (speedX == 0);
		do {
			speedY = -rnd.nextInt(3);
		} while (speedY == 0);

		ball.setSpeed(speedX, speedY);

		player = new Player((Point) ballPos.clone(), 150, 10, drawArea, null, 0);

		area = drawArea;

	}

	private void makeBall(Point2D ballPos) {
		ball = new RubberBall(ballPos);
	}

	public void checkLevels() {
		if (level == 1) {

		}

	}

	public void move() {
		player.move();
		ball.move();
	}

	public void findImpacts() {
		if (player.impact(ball)) {
			ball.reverseY();
		} else if (impactWall()) {
			/*
			 * for efficiency reverse is done into method impactWall because for every brick
			 * program checks for horizontal and vertical impacts
			 */
			brickCount--;
			player.addscore();
		} else if (impactBorder()) {
			ball.reverseX();

		} else if (ball.getPosition().getY() < area.getY()) {
			ball.reverseY();

		} else if (ball.getPosition().getY() > area.getY() + area.getHeight()) {
			ballCount--;
			ballLost = true;
		}
	}

	private boolean impactWall() {
		for (Brick b : bricks) {
			switch (b.findImpact(ball)) {
			// Vertical Impact
			case Brick.UP_IMPACT:
				ball.reverseY();
				return b.setImpact(ball.down, Brick.Crack.UP);
			case Brick.DOWN_IMPACT:
				ball.reverseY();
				return b.setImpact(ball.up, Brick.Crack.DOWN);

			// Horizontal Impact
			case Brick.LEFT_IMPACT:
				ball.reverseX();
				return b.setImpact(ball.right, Brick.Crack.RIGHT);
			case Brick.RIGHT_IMPACT:
				ball.reverseX();
				return b.setImpact(ball.left, Brick.Crack.LEFT);
			}
		}
		return false;
	}

	private boolean impactBorder() {
		Point2D p = ball.getPosition();
		return ((p.getX() < area.getX()) || (p.getX() > (area.getX() + area.getWidth())));
	}

	public int getBrickCount() {
		return brickCount;
	}

	public int getBallCount() {
		return ballCount;
	}

	public boolean isBallLost() {
		return ballLost;
	}

	public void ballReset() {
		player.moveTo(startPoint);
		ball.moveTo(startPoint);
		int speedX, speedY;
		do {
			speedX = rnd.nextInt(5) - 2;
		} while (speedX == 0);
		do {
			speedY = -rnd.nextInt(3);
		} while (speedY == 0);

		ball.setSpeed(speedX, speedY);
		ballLost = false;
	}

	public void wallReset() {
		for (Brick b : bricks)
			b.repair();
		brickCount = bricks.length;
		ballCount = 3;
	}

	public boolean ballEnd() {
		return ballCount == 0;
	}

	public boolean isDone() {
		return brickCount == 0;
	}

	public void nextLevel() {
		bricks = levels[level++];
		this.brickCount = bricks.length;
	}

	public boolean hasLevel() {
		return level < levels.length;
	}

	public void setBallXSpeed(int s) {
		ball.setXSpeed(s);
	}

	public void setBallYSpeed(int s) {
		ball.setYSpeed(s);
	}

	public void resetBallCount() {
		ballCount = 3;
	}

	// count score
	public int countScore() {
		return player.getScore();
	}

	// get highscore
	public String GetHighScore() {
		// format: Khoa: 100
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			return highScore = reader.readLine();
		} catch (Exception e) {
			return highScore = "Nobody:0";
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void CheckScore() {
		if (player.getScore() > Integer.parseInt((highScore.split(":")[1]))) {

			String name = JOptionPane.showInputDialog("You set a new highScore. What your name?");
			highScore = name + ":" + player.getScore();
			name = player.getName();

			File scoreFile = new File("highscore.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile, true);
				writer = new BufferedWriter(writeFile);
				writer.newLine();
				writer.write(this.highScore);
			} catch (Exception e) {

			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (Exception e) {
				}

			}
		}
	}

	public int checkpreviosscore() {
		return player.getScore();
	}

	public String getHighScore() {
		return highScore;
	}

	public void setHighScore(String highScore) {
		this.highScore = highScore;
	}

	public int getlevel() {
		// TODO Auto-generated method stub
		return level;
	}

	public Brick[] getBricks() {
		return bricks;
	}

	public void setBricks(Brick[] bricks) {
		this.bricks = bricks;
	}

	public void setBrickCount(int brickCount) {
		this.brickCount = brickCount;
	}
	

}
