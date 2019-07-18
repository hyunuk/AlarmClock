package view;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * Created by hyunuk71@gmail.com on 22/08/2018
 * Github : http://github.com/hyunuk71
 */
public class PopupPanel extends JPanel {
	protected final String[] HOURS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
	protected final String[] MINUTES = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
			"51", "52", "53", "54", "55", "56", "57", "58", "59"};
	protected final String[] SOUNDS = {"Chicken", "Beep", "Classic"};

	protected JLabel labelLabel = new JLabel("Label");
	protected JTextField labelText = new JTextField();

	protected JLabel hoursLabel = new JLabel("Hours");
	protected JComboBox<String> hoursCombo = new JComboBox<>(HOURS);
	protected JLabel minutesLabel = new JLabel("Minutes");
	protected JComboBox<String> minutesCombo = new JComboBox<>(MINUTES);

	protected JLabel soundLabel = new JLabel("Sound");
	protected JList<String> soundsList = new JList<>(SOUNDS);
	protected JScrollPane sp = new JScrollPane(soundsList);

	protected JCheckBox repeatChecker = new JCheckBox("Repeat Everyday");

	protected JButton playBtn = new JButton("Play Sound");
	protected JButton leftBtn = new JButton();
	protected JButton rightBtn = new JButton();
	protected JRadioButton on = new JRadioButton("Alarm On");
	protected JRadioButton off = new JRadioButton("Alarm Off");
	protected ButtonGroup onOff = new ButtonGroup();

	public PopupPanel() {
		setLayout(null);
		attach(labelLabel, 10, 10, 40, 20);
		attach(labelText, 60, 10, 220, 20);
		attach(hoursLabel, 10, 50, 50, 20);
		attach(hoursCombo, 60, 50, 80, 25);
		attach(minutesLabel, 150, 50, 50, 20);
		attach(minutesCombo, 210, 50, 70, 25);
		attach(soundLabel, 10, 100, 50, 20);
		attach(sp, 60, 100, 80, 60);
		attach(repeatChecker, 150, 100, 150, 20);
		attach(playBtn, 150, 130, 120, 25);
		onOff.add(on);
		onOff.add(off);
		attach(on, 50, 165, 100, 30);
		attach(off, 170, 165, 100, 30);
		attach(leftBtn, 50, 220, 80, 30);
		attach(rightBtn, 170, 220, 80, 30);

		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				URL sound = null;
				if (soundsList.getSelectedValue().equals("Chicken")) {
					sound = this.getClass().getResource("/sounds/birds.wav");
				} else if (soundsList.getSelectedValue().equals("Beep")) {
					sound = this.getClass().getResource("/sounds/beep.wav");
				} else if (soundsList.getSelectedValue().equals("Classic")) {
					sound = this.getClass().getResource("/sounds/classic.wav");
				}
				soundPlay(sound);
			}
		});
	}

	Clip clip = null;
	private void soundPlay(URL sound) {
		boolean wasRun = false;
		try {
			if (clip == null) {
				clip = AudioSystem.getClip();
			}
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

	private void attach(JComponent abc, int x, int y, int width, int height) {
		this.add(abc);
		abc.setBounds(x, y, width, height);
	}
}
