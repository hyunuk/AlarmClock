package viewModel;

import helper.Observable;
import helper.Observer;
import model.Alarm;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class AppManager implements Runnable, Observable {
	private List<Alarm> alarmArrayList;
	private List<Observer> observers;
	private JList<String> alarmList;
	private	DefaultListModel<String> alarmListModel;

	private int editIndex;

	private Thread thread;
	private boolean is24 = false;

	public AppManager() {
		observers = new ArrayList<>();
		alarmArrayList = new ArrayList<>();
		alarmListModel = new DefaultListModel<>();
		alarmList = new JList<>(alarmListModel);
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void setIs24(boolean is24) {
		this.is24 = is24;
	}

	public void updateAlarmListModel() {
		String alarmStr;

		for (Alarm alarm : alarmArrayList) {
			String label = alarm.getLabel();
			int hour = alarm.getHour();
			int minute = alarm.getMinute();
			String sound = alarm.getSound();
			String repeat = alarm.isRepeat() ? ", Repeat" : ", Not Repeat";
			String set = alarm.isSet() ? ", Set" : ", Not Set";

			alarmStr = label + " (" + String.format("%02d", hour) + ":" + String.format("%02d", minute) + "), Sound: "
				+ sound + repeat + set;
			alarmListModel.addElement(alarmStr);
		}

		alarmList.setModel(alarmListModel);
	}

	public void loadFunc() {
		for (Alarm value : alarmArrayList) {
			value.interrupt();
		}
		alarmArrayList.clear();

		JFileChooser jf = new JFileChooser();
		int response = jf.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			File f = jf.getSelectedFile();
			Scanner s;
			try {
				s = new Scanner(new FileReader(f));

				String[] data;
				while (s.hasNextLine()) {
					data = s.nextLine().split(",");
					Alarm alarm = new Alarm(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]));
					alarm.start();
					alarmArrayList.add(alarm);

					updateAlarmListModel();
				}
			} catch (FileNotFoundException e) {
				System.out.println("error:" + e);
			}
		}
	}

	public void saveFunc(Component parent) {
		FileWriter writer = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a file or make a new text file to save");
		int userSelection = fileChooser.showSaveDialog(parent);
		BufferedWriter bWriter = null;

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				writer = new FileWriter(fileChooser.getSelectedFile() + ".txt");
				bWriter = new BufferedWriter(writer);
				String str;

				for (Alarm alarm : alarmArrayList) {
					String label = alarm.getLabel();
					int hour = alarm.getHour();
					int minute = alarm.getMinute();
					String sound = alarm.getSound();
					boolean repeat = alarm.isRepeat();
					boolean set = alarm.isSet();
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

	public void deleteAlarm(int clickedIndex) {
		alarmArrayList.get(clickedIndex).interrupt();
		alarmArrayList.remove(clickedIndex);
		updateAlarmListModel();
	}

	@Override
	public void run() {
		while (true) {
			try{
				notifyObserver();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Alarm getEditingAlarm(int editIndex) {
		return alarmArrayList.get(editIndex);
	}

	public void setEditIndex(int editIndex) {
		this.editIndex = editIndex;
	}

	public int getEditIndex() {
		return editIndex;
	}

	public void alarmInterrupt(Alarm alarm) {
		alarm.interrupt();
	}

	public void setAlarm(int index, Alarm alarm) {
		if (alarm.isSet()) alarm.start();
		if (index == -1) {
			alarmArrayList.add(alarm);
		} else {
			alarmArrayList.set(index, alarm);
		}
	}

	public JList<String> getAlarmList() {
		return alarmList;
	}

	public int getSelectedIndex() {
		return alarmList.getSelectedIndex();
	}

	public DefaultListModel<String> getAlarmListModel() {
		return alarmListModel;
	}

	@Override
	public void addObserver(Observer o) {
		if (!observers.contains(o)) observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}

	@Override
	public void notifyObserver() {
		for (Observer o : observers) {
			o.update();
		}
	}
}
