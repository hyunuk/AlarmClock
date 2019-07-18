package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by hyunuk71@gmail.com on 26/08/2018
 * Github : http://github.com/hyunuk71
 */
public class ListPanel extends JPanel {
	protected DefaultListModel<String> alarmListModel = new DefaultListModel<>();
	protected JList<String> alarmList;
	protected JScrollPane scr;
	protected MainFrame mainFrame;

	public ListPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLayout(null);
		setSize(389, 270);
		setBorder(new TitledBorder(new EtchedBorder(), "Alarm"));

		initList();
	}

	protected void initList() {
		alarmListModel.addElement("No Alarms");
		alarmList = new JList<>(alarmListModel);
		alarmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		alarmList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		scr = new JScrollPane(alarmList);
		scr.setBounds(5, 17, 380, 247);
		add(scr);

		alarmList.setModel(alarmListModel);
	}



}
