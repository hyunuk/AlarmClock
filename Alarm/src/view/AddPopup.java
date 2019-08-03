package view;

import model.Alarm;
import viewModel.AppManager;

import java.util.Objects;

/**
 * Created by hyunuk71@gmail.com on 23/08/2018
 * Github : http://github.com/hyunuk71
 */
class AddPopup extends Popup {
	private AppManager appManager;

	AddPopup(AppManager appManager) {
		this.appManager = appManager;

		leftBtn.setText("Add");
		addPopupInit();
		leftBtn.addActionListener(e -> {
			addAlarm();
			addPopupInit();
			this.dispose();
		});
		cancelBtn.addActionListener(e -> {
			addPopupInit();
			this.dispose();
		});
	}

	private void addPopupInit() {
		labelText.setText("");
		hoursCombo.setSelectedIndex(0);
		minutesCombo.setSelectedIndex(0);
		soundsList.setSelectedIndex(0);
		repeatChecker.setSelected(false);
		on.setSelected(true);
	}

	private void addAlarm() {
		String label = labelText.getText();
		int hour = Integer.parseInt(Objects.requireNonNull(hoursCombo.getSelectedItem()).toString());
		int minute = Integer.parseInt(Objects.requireNonNull(minutesCombo.getSelectedItem()).toString());
		String sound = soundsList.getSelectedValue();
		boolean isRepeat = repeatChecker.isSelected();
		boolean isSet = on.isSelected();

		appManager.setAlarm(-1, new Alarm(label, hour, minute, sound, isRepeat, isSet));
	}
}
