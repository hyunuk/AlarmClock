/**
 * Created by hyunuk71@gmail.com on 23/08/2018
 * Github : http://github.com/hyunuk71
 */
public class AddPopup extends PopupPanel {
	Alarm alarm;
	MainFrame mainFrame;

	public AddPopup(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		leftBtn.setText("Add");
		rightBtn.setText("Cancel");
		addPopupInit();
	}

	public void addPopupInit() {
		labelText.setText("");
		hoursCombo.setSelectedIndex(0);
		minutesCombo.setSelectedIndex(0);
		soundsList.setSelectedIndex(0);
		repeatChecker.setSelected(false);
		on.setSelected(true);
	}

	public void addAlarm() {
		String label = labelText.getText();
		int hour = Integer.parseInt(hoursCombo.getSelectedItem().toString());
		int minute = Integer.parseInt(minutesCombo.getSelectedItem().toString());
		String sound = soundsList.getSelectedValue();
		boolean isRepeat = repeatChecker.isSelected();
		boolean isSet = on.isSelected() ? true : false;

		addPopupInit();

		alarm = new Alarm(label, hour, minute, sound, isRepeat, isSet);
		if (isSet == true) alarm.start();
		mainFrame.alarmArrayList.add(alarm);
	}
}
