package view;

import helper.ComponentAttacher;
import helper.ResolutionAdjuster;
import model.Alarm;
import viewModel.AppManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hyunuk71@gmail.com on 20/08/2018
 * Github : http://github.com/hyunuk71
 */
public class MainFrame extends JFrame {
	private final Dimension MAIN_FRAME_DIM = new Dimension(400, 130);
	private final Rectangle CLOCK_PANEL_RECT = new Rectangle(389, 93, 0, 0);
	private final Rectangle LIST_PANEL_RECT = new Rectangle(0, 105, 389, 270);
	private final Rectangle BUTTON_PANEL_RECT = new Rectangle(0, 380, 400, 100);

	private AppManager appManager;

	private JDialog addPopup;
	private JDialog editPopup;
	private AddPopup addPopupPanel = new AddPopup(this);
	private EditPopup editPopupPanel = new EditPopup();
	private int editIndex;
	private ArrayList<Alarm> alarmArrayList = new ArrayList<>();
	private JList<String> alarmList;

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.start();
	}

	private void start() {
		appManager = new AppManager();
		initView();
		appManager.start();
	}

	private void initView() {
		this.pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Alarm Clock");
		setLocation(ResolutionAdjuster.adjustWidth(100), ResolutionAdjuster.adjustHeight(100));
		setPreferredSize(MAIN_FRAME_DIM);
		setAlwaysOnTop(true);
		setVisible(true);
		setLayout(null);

		ComponentAttacher.attach(this, clockPanel(), CLOCK_PANEL_RECT);
		ComponentAttacher.attach(this, listPanel(), LIST_PANEL_RECT);
		ComponentAttacher.attach(this, buttonPanel(), BUTTON_PANEL_RECT);


		addPopup = new JDialog(this, "Add Alarm", true);
		addPopup.setLocation(this.getX()+50, this.getY()+50);
		addPopup.add(addPopupPanel);
		editPopup = new JDialog(this, "Edit Alarm", true);
		editPopup.setLocation(this.getX()+50, this.getY()+50);
		editPopup.add(editPopupPanel);
	}

	private JPanel clockPanel() {
		JPanel retPanel = new JPanel(null);

		JLabel timeLabel = new JLabel();
		JLabel dateLabel = new JLabel();
		JCheckBox hourChk = new JCheckBox("24hr");
		JButton alarmBtn = new JButton("Alarm");
		DecimalFormat df = new DecimalFormat("00");
		retPanel.setBorder(new TitledBorder(new EtchedBorder(), "Clock"));
		timeLabel.setFont(new Font("Tahoma",Font.BOLD, 30));
		dateLabel.setFont(new Font("Tahoma",Font.ITALIC, 20));

		ComponentAttacher.attach(retPanel, timeLabel, 20, 20, 250, 30);
		ComponentAttacher.attach(retPanel, dateLabel, 20, 60, 200, 20);
		ComponentAttacher.attach(retPanel, hourChk, 280, 23, 80, 20);
		ComponentAttacher.attach(retPanel, alarmBtn, 280, 50, 80, 30);

		hourChk.addActionListener(e -> appManager.setIs24(hourChk.isSelected()));
		alarmBtn.addActionListener(e -> {
			if (isAlarmOpen()) {
				setSize(400, 500);
			} else {
				setSize(400, 130);
			}
		});

		return retPanel;
	}

	private JPanel listPanel() {
		JPanel retPanel = new JPanel(null);
		DefaultListModel<String> alarmListModel = new DefaultListModel<>();
		JScrollPane scr;

		alarmListModel.addElement("No Alarms");
		alarmList = new JList<>(alarmListModel);
		alarmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		alarmList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		scr = new JScrollPane(alarmList);
		ComponentAttacher.attach(retPanel, scr, 5, 17, 380, 247);
		alarmList.setModel(alarmListModel);

		return retPanel;
	}

	private JPanel buttonPanel() {
		JPanel retPanel = new JPanel(null);

		JButton addBtn = new JButton("ADD");
		JButton editBtn = new JButton("EDIT");
		JButton deleteBtn = new JButton("DELETE");
		JButton loadBtn = new JButton("LOAD");
		JButton saveBtn = new JButton("SAVE");

		ComponentAttacher.attach(this, addBtn, 20, 10, 100, 30);
		ComponentAttacher.attach(this, editBtn, 140, 10, 100, 30);
		ComponentAttacher.attach(this, deleteBtn, 260, 10, 100, 30);
		ComponentAttacher.attach(this, loadBtn, 80, 50, 100, 30);
		ComponentAttacher.attach(this, saveBtn, 200, 50, 100, 30);

		addBtn.addActionListener(e -> openPopup(addPopup));
		editBtn.addActionListener(e -> {
			editIndex = alarmList.getSelectedIndex();
			if (editIndex != -1) {
				Alarm editingAlarm = alarmArrayList.get(editIndex );
				editPopupPanel.setEditPopup(editingAlarm);
				openPopup(editPopup);
			}
		});
		deleteBtn.addActionListener(e -> deleteAlarm());
		loadBtn.addActionListener(e -> loadFunc());
		saveBtn.addActionListener(e -> saveFunc());

		return retPanel;
	}


	public void actionPerformed(ActionEvent e) {
		if (addPopupPanel.leftBtn == e.getSource()) { // Add - confirm adding
			addPopupPanel.addAlarm();
			updateAlarmListModel(alarmArrayList);
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
			updateAlarmListModel(alarmArrayList);
			editPopup.dispose();
		}
		if (editPopupPanel.rightBtn == e.getSource()) { // Edit - cancel
			editPopup.dispose();
		}
	}


	public void buttonClickEvent() {

		addPopupPanel.leftBtn.addActionListener(this);
		addPopupPanel.rightBtn.addActionListener(this);
		editPopupPanel.leftBtn.addActionListener(this);
		editPopupPanel.rightBtn.addActionListener(this);
	}

	private void openPopup(JDialog popup){
		popup.setSize(300, 300);
		popup.setVisible(true);
	}

	private void deleteAlarm() {
		int index = alarmList.getSelectedIndex();
		if (index != -1) {
			alarmArrayList.get(index).interrupt();
			alarmArrayList.remove(index);
			updateAlarmListModel(alarmArrayList);
		} else {
			System.out.println("No item is selected!");
		}
	}


	private boolean isAlarmOpen() {
		return this.getHeight() == 130;
	}
}
