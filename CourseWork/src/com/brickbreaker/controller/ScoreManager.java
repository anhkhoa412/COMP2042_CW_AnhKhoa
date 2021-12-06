package com.brickbreaker.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.brickbreaker.model.Score;

public class ScoreManager {
	public ArrayList<Score> scores;
	public ArrayList names;

	int score = 0;

	public File file;

	/**
	 * checks for the score file, if missing makes score file
	 */
	public ScoreManager() {

		scores = new ArrayList<Score>();
		ArrayList<String> names = new ArrayList<String>();

		File file = new File("Highscore.txt");
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		readscores();

	}
	/**
	 * This function writes new data into the file
	 * @param username is the user name of the player
	 * @param score is the score the player has have
	 * @param level is the level the player has reached
	 */
	
	public void writescores(String username, int score) {
		boolean found = false;

		for (int x = 0; x < scores.size(); x++) {
			if (scores.get(x).Username.equals(username)) {
				if (scores.get(x).Score < score) {
					scores.get(x).Score = score;
				}
				found = true;
			}
		}
		if (!found) {
			scores.add(new Score(username, score));
		}

		try {
			FileWriter myWriter = new FileWriter("Highscore.txt");
			for (int i = 0; i < scores.size(); i++) {

				if (!scores.get(i).Username.equals("")) {
					myWriter.write(
							scores.get(i).Username + "\n" + scores.get(i).Score + "\n" );
				}
			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	/**
	 * this function reads data and stores it in an array 
	 */
	public void readscores() {
		int second = 1;
		String user = "";
		int score = 0;
		try {
			File myObj = new File("Highscore.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (second == 3) {

					scores.add(new Score(user, score));
					second = 1;
				} else if (second == 1) {

					second = 2;
					user = data;
				} else if (second == 2) {
					second = 3;
					score = Integer.valueOf(data);
				}

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	/**
	 * method to get the scores
	 */
	
	public ArrayList getscore() {
		return (scores);
	}

	
	public int gethiscore() {
		int hi = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i).Score > hi) {
				hi = scores.get(i).Score;
			}
		}
		return (hi);
	}

}
