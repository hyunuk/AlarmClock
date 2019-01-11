import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

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


}
