package viewModel;

import model.Alarm;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class AppManager implements Runnable {
	private final String[] DAY_OF_WEEK = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
	private final String[] MONTH = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

	private Thread thread;
	private boolean is24 = false;


	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private String clockDisplay(boolean is24) {
		GregorianCalendar cal = new GregorianCalendar();
		String hour24 = cal.get(Calendar.AM_PM) == 0 ? df.format(cal.get(Calendar.HOUR)) : df.format(cal.get(Calendar.HOUR) + 12);
		String hour12 = df.format(cal.get(Calendar.HOUR)).equals("0") ? "12" : df.format(cal.get(Calendar.HOUR));
		String minute = df.format(cal.get(Calendar.MINUTE));
		String second = df.format(cal.get(Calendar.SECOND));
		String amPm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String currentTime24 = hour24 + " : " + minute + " : " + second + " ";
		String currentTime12 = hour12 + " : " + minute + " : " + second + " " + amPm;
		String currentTime;
		currentTime = is24 ? currentTime24 : currentTime12;

		return currentTime;
	}

	private String dateDisplay() {
		GregorianCalendar cal = new GregorianCalendar();

		int year = cal.get(Calendar.YEAR);
		String dayOfWeek = DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)];
		String month = MONTH[cal.get(Calendar.MONTH)];

		String date = df.format(cal.get(Calendar.DATE));
		String currentDate = dayOfWeek + " - " + month + " " + date + ". " + year;
		dateLabel.setText(currentDate);
		return currentDate;
	}

	public void setIs24(boolean is24) {
		this.is24 = is24;
	}

	protected void updateAlarmListModel(ArrayList<Alarm> alarmArrayList) {
		alarmListModel.clear();
		String alarmStr = "";

		for (int i = 0; i < alarmArrayList.size(); i++) {
			String label = alarmArrayList.get(i).getLabel();
			int hour = alarmArrayList.get(i).getHour();
			int minute = alarmArrayList.get(i).getMinute();
			String sound = alarmArrayList.get(i).getSound();
			String repeat = alarmArrayList.get(i).isRepeat() ? ", Repeat" : ", Not Repeat";
			String set = alarmArrayList.get(i).isSet() ? ", Set" : ", Not Set";

			alarmStr = label + " (" + String.format("%02d", hour) + ":" + String.format("%02d", minute) + "), Sound: "
				+ sound + repeat + set;
			alarmListModel.addElement(alarmStr);
		}

		alarmList.setModel(alarmListModel);
	}

	public void clockThread() {
		timeLabel.setText(clockDisplay(is24));
		dateDisplay();
	}

	private void loadFunc() {
		for (int i = 0; i < alarmArrayList.size(); i++) {
			alarmArrayList.get(i).interrupt();
		}
		alarmArrayList.clear();

		JFileChooser jf = new JFileChooser();
		int response = jf.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			File f = jf.getSelectedFile();
			Scanner s;
			try {
				s = new Scanner(new FileReader(f));

				String data[];
				while (s.hasNextLine()) {
					data = s.nextLine().split(",");
					Alarm alarm = new Alarm(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]));
					alarm.start();
					alarmArrayList.add(alarm);

					list.updateAlarmListModel(alarmArrayList);
				}
			} catch (FileNotFoundException e) {
				System.out.println("error:" + e);
			}
		}
	}

	private void saveFunc() {
		FileWriter writer = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a file or make a new text file to save");
		int userSelection = fileChooser.showSaveDialog(this);
		BufferedWriter bWriter = null;

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				writer = new FileWriter(fileChooser.getSelectedFile() + ".txt");
				bWriter = new BufferedWriter(writer);
				String str = "";

				for (int i = 0; i < alarmArrayList.size(); i++) {
					String label = alarmArrayList.get(i).getLabel();
					int hour = alarmArrayList.get(i).getHour();
					int minute = alarmArrayList.get(i).getMinute();
					String sound = alarmArrayList.get(i).getSound();
					boolean repeat = alarmArrayList.get(i).isRepeat();
					boolean set = alarmArrayList.get(i).isSet();
					str = label + "," + hour + "," + minute + "," + sound + "," + repeat + "," + set + "\r\n";

					bWriter.append(str);
				}
				bWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bWriter != null) bWriter.close();
					if (writer != null) writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void run() {
		while (true) {
			try{
				clock.clockThread();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
