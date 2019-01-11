import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hyunuk71@gmail.com on 20/08/2018
 * Github : http://github.com/hyunuk71
 */
public class MainFrame extends JFrame implements Runnable, ActionListener {
	private Thread thread;
	private JPanel clockPanel;
	private JPanel buttonPanel;
	private JPanel listPanel;
	private JDialog addPopup;
	private JDialog editPopup;
	private AddPopup addPopupPanel = new AddPopup(this);
	private EditPopup editPopupPanel = new EditPopup();
	private ListPanel list = new ListPanel(this);
	private ClockPanel clock = new ClockPanel();
	private ButtonPanel btns = new ButtonPanel();
	private boolean isAlarmOpen = false;
	private static int editIndex;
	private Help help = new Help();
	public static ArrayList<Alarm> alarmArrayList = new ArrayList<>();

	public void start() {
		init();
		buttonClickEvent();
		setVisible(true);
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void init() {
		this.pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Alarm Clock");

		setSize(400, 130);
		setLocation(help.adjustWidth(100), help.adjustHeight(100));
		setAlwaysOnTop(true);
		setLayout(null);

		clockPanel = clock;
		add(clockPanel);
		clockPanel.setLocation(0, 0);
		listPanel = list;
		add(listPanel);
		listPanel.setLocation(0, 105);
		buttonPanel = btns;
		add(buttonPanel);
		buttonPanel.setLocation(0, 380);

		addPopup = new JDialog(this, "Add Alarm", true);
		addPopup.setLocation(this.getX()+50, this.getY()+50);
		addPopup.add(addPopupPanel);
		editPopup = new JDialog(this, "Edit Alarm", true);
		editPopup.setLocation(this.getX()+50, this.getY()+50);
		editPopup.add(editPopupPanel);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (clock.alarmBtn == e.getSource()) {
			if (this.getHeight() == 130) {
				isAlarmOpen = true;
				setSize(400, 500);
			} else {
				isAlarmOpen = false;
				setSize(400, 130);
			}
		}
		if (btns.addBtn == e.getSource()) openPopup(addPopup);
		if (btns.editBtn == e.getSource()) {
			editIndex = list.alarmList.getSelectedIndex();
			if (editIndex != -1) {
				Alarm editingAlarm = alarmArrayList.get(editIndex );
				editPopupPanel.setEditPopup(editingAlarm);
				openPopup(editPopup);
			}
		}
		if (btns.deleteBtn == e.getSource()) deleteAlarm();
		if (btns.loadBtn == e.getSource()) loadFunc();
		if (btns.saveBtn == e.getSource()) saveFunc();

		if (addPopupPanel.leftBtn == e.getSource()) { // Add - confirm adding
			addPopupPanel.addAlarm();
			list.updateAlarmListModel(alarmArrayList);
			addPopup.dispose();
		}
		if (addPopupPanel.rightBtn == e.getSource()) { // Add - cancel
			addPopupPanel.addPopupInit();
			addPopup.dispose();
		}
		if (editPopupPanel.leftBtn == e.getSource()) { // Edit - confirm editing
			alarmArrayList.get(editIndex).interrupt();
			alarmArrayList.set(editIndex, editPopupPanel.editAlarm());
			editPopupPanel.editAlarm().start();
			list.updateAlarmListModel(alarmArrayList);
			editPopup.dispose();
		}
		if (editPopupPanel.rightBtn == e.getSource()) { // Edit - cancel
			editPopup.dispose();
		}
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

	public void buttonClickEvent() {
		clock.alarmBtn.addActionListener(this);
		btns.addBtn.addActionListener(this);
		btns.editBtn.addActionListener(this);
		btns.deleteBtn.addActionListener(this);
		btns.loadBtn.addActionListener(this);
		btns.saveBtn.addActionListener(this);
		addPopupPanel.leftBtn.addActionListener(this);
		addPopupPanel.rightBtn.addActionListener(this);
		editPopupPanel.leftBtn.addActionListener(this);
		editPopupPanel.rightBtn.addActionListener(this);
	}

	public void openPopup(JDialog popup){
		popup.setSize(300, 300);
		popup.setVisible(true);
	}

	private void deleteAlarm() {
		JList alarmList = list.alarmList;
		int index = alarmList.getSelectedIndex();
		if (index != -1) {
			alarmArrayList.get(index).interrupt();
			alarmArrayList.remove(index);
			list.updateAlarmListModel(alarmArrayList);
		} else {
			System.out.println("No item is selected!");
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