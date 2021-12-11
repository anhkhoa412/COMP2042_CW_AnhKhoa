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
import com.brickbreaker.model.Player;
import com.brickbreaker.model.RubberBall;

/** 
 * Class of wall
 * */
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
	private int length = 150;

	private String highScore = "Nobody:0";

	/** Constructor of class wall.
	 * @param position of ball,
	 * @param  number of ball,
	 * @param  speed of ball
	 * @param create player */
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
		randomPaddle();
		player = new Player((Point) ballPos.clone(), setlength(), 10, drawArea, null, 0);
		this.length = length;
		area = drawArea;


	}
	/* creates a ball */
	private void makeBall(Point2D ballPos) {
		ball = new RubberBall(ballPos);
	}

	public void checkLevels() {
		if (level == 1) {
		}

	}

	/** Call method move in player and ball */
	public void move() {
		player.move();
		ball.move();
	}

	/** check the collision for ball and player, ball and any bircks, all and wall */
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
	/** Bricks will crack when ball hit in any directions */
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
	/** When ball hit the border */
	private boolean impactBorder() {
		Point2D p = ball.getPosition();
		return ((p.getX() < area.getX()) || (p.getX() > (area.getX() + area.getWidth())));
	}
	/** Count the brick left */
	public int getBrickCount() {
		return brickCount;
	}
	/** Count the ball left */
	public int getBallCount() {
		return ballCount;
	}
	/** Check lose ball */
	public boolean isBallLost() {
		return ballLost;
	}
	/** Reset the ball when lose ball */
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

	/** Reset the number of bricks and balls*/
	public void wallReset() {
		for (Brick b : bricks)
			b.repair();
		brickCount = bricks.length;
		ballCount = 3;
	}
	/** No balls left */
	public boolean ballEnd() {
		return ballCount == 0;
	}
	/** No bricks left */
	public boolean isDone() {
		return brickCount == 0;
	}

	public void setBallXSpeed(int s) {
		ball.setXSpeed(s);
	}

	public void setBallYSpeed(int s) {
		ball.setYSpeed(s);
	}
	/** Reset number of balls*/
	public void resetBallCount() {
		ballCount = 3;
	}

	/** count player's score */
	public int countScore() {
		return player.getScore();
	}

	/** Read the high score file */
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
	/** Check score and write in file */
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
	/** Check the previous high score */
	public int checkpreviosscore() {
		return player.getScore();
	}
	/** Get player's highScore */
	public String getHighScore() {
		return highScore;
	}

	public void setHighScore(String highScore) {
		this.highScore = highScore;
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
	public int levelup() {
		this.length = length/2;
		return length;
	}
	public int getlength() {
		return length;
	}
	public int setlength() {
		this.length = getlength() ;
		return length;
	}
	public int setSlength(){
		this.length = getlength() /2;
		return length;
	}
	public int setSslength(){
		this.length = getlength() /4;
		return length;
	}
	public int randomPaddle(){
		Integer number = Integer.valueOf(rnd.nextInt(3));
		if (number.intValue() == 0){
			setlength();
			System.out.println("Value:"+number.intValue());
		}else if (number.intValue() == 2){
			setSslength();
			System.out.println("Value:"+number.intValue());
		}
		return length;
	}

}
