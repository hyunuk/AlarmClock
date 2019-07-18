package view;

import model.Alarm;

/**
 * Created by hyunuk71@gmail.com on 23/08/2018
 * Github : http://github.com/hyunuk71
 */
public class EditPopup extends PopupPanel {
	public EditPopup() {
		leftBtn.setText("Edit");
		rightBtn.setText("Cancel");
	}

	public void setEditPopup(Alarm editingAlarm) {
		String label = editingAlarm.getLabel();
		int hour = editingAlarm.getHour();
		int minute = editingAlarm.getMinute();
		String sound = editingAlarm.getSound();
		boolean isRepeat = editingAlarm.isRepeat();
		boolean isSet = editingAlarm.isSet();

		labelText.setText(label);
		hoursCombo.setSelectedIndex(hour);
		minutesCombo.setSelectedIndex(minute);
		if (sound.equals(SOUNDS[0])) {
			soundsList.setSelectedIndex(0);
		} else if (sound.equals(SOUNDS[1])) {
			soundsList.setSelectedIndex(1);
		} else if (sound.equals(SOUNDS[2])) {
			soundsList.setSelectedIndex(2);
		}

		if (isRepeat) {
			repeatChecker.setSelected(true);
		} else {
			repeatChecker.setSelected(false);
		}

		if (isSet) {
			on.setSelected(true);
		} else {
			off.setSelected(true);
		}

	}

	public Alarm editAlarm() {
		String label = labelText.getText();
		int hour = Integer.parseInt(hoursCombo.getSelectedItem().toString());
		int minute = Integer.parseInt(minutesCombo.getSelectedItem().toString());
		String sound = soundsList.getSelectedValue();
		boolean isRepeat = repeatChecker.isSelected();
		boolean isSet = on.isSelected() ? true : false;

		Alarm editedAlarm = new Alarm(label, hour, minute, sound, isRepeat, isSet);
		return editedAlarm;
	}
}
