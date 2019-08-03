package view;

import helper.ComponentAttacher;
import helper.Observer;
import helper.TimeAndDateDisplayer;
import viewModel.AppManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by hyunuk71@gmail.com on 20/08/2018
 * Github : http://github.com/hyunuk71
 */
public class MainFrame extends JFrame implements Observer {
	private final Dimension MAIN_FRAME_DIM = new Dimension(400, 130);
	private final Rectangle CLOCK_PANEL_RECT = new Rectangle(0, 0, 400, 93);
	private final Dimension CLOCK_PANEL_DIM = new Dimension(400, 93);
	private final Rectangle LIST_PANEL_RECT = new Rectangle(0, 105, 400, 270);
	private final Dimension LIST_PANEL_DIM = new Dimension(400, 270);
	private final Rectangle BUTTON_PANEL_RECT = new Rectangle(0, 380, 400, 100);
	private final Dimension BUTTON_PANEL_DIM = new Dimension(400, 100);
	private final Point CLOCK_PANEL_POINT = new Point(0, 0);
	private final Point LIST_PANEL_POINT = new Point(0, 105);
	private final Point BUTTON_PANEL_POINT = new Point(0, 380);

	private AppManager appManager;

	private AddPopup addPopup;
	private EditPopup editPopup;
	private int clickedIndex;
	private JLabel timeLabel, dateLabel;
	private JCheckBox hourChk;

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.start();
	}

	private void start() {
		appManager = new AppManager();
		appManager.addObserver(this);
		initView();
		addPopup = new AddPopup(appManager);
		editPopup = new EditPopup(appManager);
		appManager.start();
	}

	private void initView() {
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Alarm Clock");
		setLocation(100, 100);
		setAlwaysOnTop(true);
		setPreferredSize(MAIN_FRAME_DIM);
		this.pack();
		ComponentAttacher.attach(this, clockPanel(), CLOCK_PANEL_POINT, CLOCK_PANEL_DIM);
		ComponentAttacher.attach(this, listPanel(), LIST_PANEL_POINT, LIST_PANEL_DIM);
		ComponentAttacher.attach(this, buttonPanel(), BUTTON_PANEL_POINT, BUTTON_PANEL_DIM);
		setVisible(true);

//		ComponentAttacher.attach(this, buttonPanel(), BUTTON_PANEL_RECT);
//		ComponentAttacher.attach(this, clockPanel(), CLOCK_PANEL_RECT);
//		ComponentAttacher.attach(this, listPanel(), LIST_PANEL_RECT);

	}

	private JPanel clockPanel() {
		JPanel retPanel = new JPanel(null);

		timeLabel = new JLabel();
		dateLabel = new JLabel();
		hourChk = new JCheckBox("24hr");
		JButton alarmBtn = new JButton("Alarm");
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
		DefaultListModel<String> alarmListModel = appManager.getAlarmListModel();
		JScrollPane scr;

		alarmListModel.addElement("No Alarms");
		JList<String> alarmList = appManager.getAlarmList();
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
			clickedIndex = appManager.getSelectedIndex();
			if (clickedIndex != -1) {
				appManager.setEditIndex(clickedIndex);
				editPopup.initEditPopup(appManager.getEditingAlarm(clickedIndex));
				openPopup(editPopup);
			}
		});
		deleteBtn.addActionListener(e -> {
			clickedIndex = appManager.getSelectedIndex();
			if (clickedIndex != -1) {
				appManager.deleteAlarm(clickedIndex);
			}

		});
		loadBtn.addActionListener(e -> appManager.loadFunc());
		saveBtn.addActionListener(e -> appManager.saveFunc(this));

		return retPanel;
	}

	private void openPopup(JDialog popup){
		popup.setSize(300, 300);
		popup.setBounds(this.getX()+50, this.getY()+50, 300, 300);
		popup.setVisible(true);
	}

	private boolean isAlarmOpen() {
		return this.getHeight() == 130;
	}

	@Override
	public void update() {
		timeLabel.setText(TimeAndDateDisplayer.clockDisplay(hourChk.isSelected()));
		dateLabel.setText(TimeAndDateDisplayer.dateDisplay());
	}
}
