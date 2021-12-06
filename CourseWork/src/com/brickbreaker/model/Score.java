package com.brickbreaker.model;

public class Score {
	
	public String Username;
	public int Score;
	public Score(String Username, int score) {
		this.Username = Username;
		this.Score = score;

	}

	public void addScore() {
		Score = Score + 5;
	}
	public int getScore() {
		return (Score);
	}


	public String getUsername() {
		return (Username);
	}

	public void setScore(int Score) {
		this.Score = Score;
	}

	public void setUsername(String Username) {
		this.Username = Username;
	}

}


