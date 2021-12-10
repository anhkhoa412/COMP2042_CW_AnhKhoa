package com.brickbreaker.model;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Class of object sound 
 * */
public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[100];
	
	/**Get the sound file*/
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/gametheme1.wav");
		soundURL[1] = getClass().getResource("/sound/hitbricks.wav");
	}
	/** Add the file to clip */
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch (Exception e) {			
		}
	}
	/**Play the sound */
	public void play() {
		clip.start();
	}
	/** Loop the sound nonstop*/
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	/** Stop the sound */
	public void stop() {
		clip.stop();
	}
}
