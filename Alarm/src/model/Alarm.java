package model;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by hyunuk71@gmail.com on 26/08/2018
 * Github : http://github.com/hyunuk71
 */
public class Alarm extends Thread {
	private final String[] SOUNDS = {"Chicken", "Beep", "Classic"};

	private String label;
	private int hour;
	private int minute;
	private String sound;
	private boolean isRepeat;
	private boolean isSet;

	public Alarm(String label, int hour, int minute, String sound, boolean isRepeat, boolean isSet) {
		this.label = label;
		this.hour = hour;
		this.minute = minute;
		this.sound = sound;
		this.isRepeat = isRepeat;
		this.isSet = isSet;
	}

	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public String getSound() {
		return sound;
	}
	public String getLabel() {
		return label;
	}
	public boolean isRepeat() {
		return isRepeat;
	}
	public boolean isSet() {
		return isSet;
	}

	@Override
	public void run() {
		try {
			int sleepTime = calcTimeDifference(hour, minute) * 1000;

			do {
				Thread.sleep(sleepTime);
				sleepTime = 86400 * 1000;
				URL soundURL = null;
				if (sound.equals(SOUNDS[0])) {
					soundURL = this.getClass().getResource("/sounds/birds.wav");
				} else if (sound.equals(SOUNDS[1])) {
					soundURL = this.getClass().getResource("/sounds/beep.wav");
				} else if (sound.equals(SOUNDS[2])) {
					soundURL = this.getClass().getResource("/sounds/classic.wav");
				}
				soundPlay(soundURL);
			} while (isRepeat);
			isSet = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// when an alarm is setting, it compares the difference of time and sleep for the return value.
	public int calcTimeDifference(int hour, int minute) {
		GregorianCalendar cal = new GregorianCalendar();
		int calHour = cal.get(Calendar.AM_PM) == 0 ? (cal.get(Calendar.HOUR)) : (cal.get(Calendar.HOUR) + 12);
		int hourDifference = hour - calHour;
		int minuteDifference = minute - cal.get(Calendar.MINUTE);
		int secondDifference = 0 - cal.get(Calendar.SECOND);
		int timeDifference = (hourDifference * 3600) + (minuteDifference * 60) + secondDifference;
		timeDifference = (timeDifference <= 0) ? (86400 + timeDifference) : timeDifference;

		return timeDifference;
	}

	private void soundPlay(URL url) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(10000);
			clip.stop();

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
