package view;

import model.Alarm;
import viewModel.AppManager;

import java.util.Objects;

/**
 * Created by hyunuk71@gmail.com on 23/08/2018
 * Github : http://github.com/hyunuk71
 */
class EditPopup extends Popup {
	private AppManager appManager;

	EditPopup(AppManager appManager) {
		this.appManager = appManager;

		leftBtn.setText("Edit");
		leftBtn.addActionListener(e -> {
			int editIndex = appManager.getEditIndex();
			Alarm editingAlarm = appManager.getEditingAlarm(editIndex);
			appManager.alarmInterrupt(editingAlarm);
			appManager.setAlarm(editIndex, editAlarm());
			appManager.updateAlarmListModel();
			this.dispose();
		});
		cancelBtn.addActionListener(e -> {
			this.dispose();
		});

	}

	void initEditPopup(Alarm editingAlarm) {
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

	Alarm editAlarm() {
		String label = labelText.getText();
		int hour = Integer.parseInt(Objects.requireNonNull(hoursCombo.getSelectedItem()).toString());
		int minute = Integer.parseInt(Objects.requireNonNull(minutesCombo.getSelectedItem()).toString());
		String sound = soundsList.getSelectedValue();
		boolean isRepeat = repeatChecker.isSelected();
		boolean isSet = on.isSelected();

		return new Alarm(label, hour, minute, sound, isRepeat, isSet);
	}
}
