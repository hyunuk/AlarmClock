package view;

import helper.ComponentAttacher;
import helper.SoundPlayer;

import javax.swing.*;

/**
 * Created by hyunuk71@gmail.com on 22/08/2018
 * Github : http://github.com/hyunuk71
 */
abstract class Popup extends JDialog {
	private final String[] HOURS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
	private final String[] MINUTES = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
			"51", "52", "53", "54", "55", "56", "57", "58", "59"};
	final String[] SOUNDS = {"Chicken", "Beep", "Classic"};

	private SoundPlayer soundPlayer = new SoundPlayer();

	private JLabel labelLabel = new JLabel("Label");
	private JLabel hoursLabel = new JLabel("Hours");
	private JLabel minutesLabel = new JLabel("Minutes");
	private JLabel soundLabel = new JLabel("Sound");

	JTextField labelText = new JTextField();
	JComboBox<String> hoursCombo = new JComboBox<>(HOURS);
	JComboBox<String> minutesCombo = new JComboBox<>(MINUTES);
	JList<String> soundsList = new JList<>(SOUNDS);
	private JScrollPane sp = new JScrollPane(soundsList);
	JCheckBox repeatChecker = new JCheckBox("Repeat Everyday");

	private JButton playBtn = new JButton("Play Sound");
	JButton leftBtn = new JButton();
	JButton cancelBtn = new JButton();

	JRadioButton on = new JRadioButton("Alarm On");
	JRadioButton off = new JRadioButton("Alarm Off");
	private ButtonGroup onOff = new ButtonGroup();

	Popup() {
		setLayout(null);
		initView();
	}

	private void initView() {
		ComponentAttacher.attach(this, labelLabel, 10, 10, 40, 20);
		ComponentAttacher.attach(this, hoursLabel, 10, 50, 50, 20);
		ComponentAttacher.attach(this, minutesLabel, 150, 50, 50, 20);

		ComponentAttacher.attach(this, labelText, 60, 10, 220, 20);
		ComponentAttacher.attach(this, hoursCombo, 60, 50, 80, 25);
		ComponentAttacher.attach(this, minutesCombo, 210, 50, 70, 25);
		ComponentAttacher.attach(this, soundLabel, 10, 100, 50, 20);
		ComponentAttacher.attach(this, sp, 60, 100, 80, 60);
		ComponentAttacher.attach(this, repeatChecker, 150, 100, 150, 20);
		ComponentAttacher.attach(this, playBtn, 150, 130, 120, 25);
		onOff.add(on);
		onOff.add(off);
		ComponentAttacher.attach(this, on, 50, 165, 100, 30);
		ComponentAttacher.attach(this, off, 170, 165, 100, 30);
		ComponentAttacher.attach(this, leftBtn, 50, 220, 80, 30);
		ComponentAttacher.attach(this, cancelBtn, 170, 220, 80, 30);

		playBtn.addActionListener(e -> {
			soundPlayer.soundPlay(soundsList.getSelectedValue());
		});
	}
}
