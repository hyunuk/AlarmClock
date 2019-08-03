package helper;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
	private final URL BIRDS = this.getClass().getResource("/sounds/birds.wav");
	private final URL BEEP = this.getClass().getResource("/sounds/beep.wav");
	private final URL CLASSIC = this.getClass().getResource("/sounds/classic.wav");

	public void soundPlay(String selectedValue) {
		URL sound = null;
		Clip clip;

		switch (selectedValue) {
			case "Chicken":
				sound = BIRDS;
				break;
			case "Beep":
				sound = BEEP;
				break;
			case "Classic":
				sound = CLASSIC;
				break;
		}
		boolean wasRun = false;

		try {
			clip = AudioSystem.getClip();
			if (clip.isRunning()) {
				clip.stop();
				clip.close();
				wasRun = true;
			}
			if (!wasRun) {
				clip.close();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
