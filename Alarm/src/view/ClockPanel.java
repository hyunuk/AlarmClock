//package view;
//
//import javax.swing.*;
//import javax.swing.border.EtchedBorder;
//import javax.swing.border.TitledBorder;
//import java.awt.*;
//import java.text.DecimalFormat;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
///**
// * Created by hyunuk71@gmail.com on 20/08/2018
// * Github : http://github.com/hyunuk71
// */
//public class ClockPanel extends JPanel {
//	private final String[] dayOfWeekArr = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
//	private final String[] monthArr = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
//
//	private JLabel timeLabel = new JLabel();
//	private JLabel dateLabel = new JLabel();
//	private JCheckBox hourChk = new JCheckBox("24hr");
//	public JButton alarmBtn = new JButton("Alarm");
//	static DecimalFormat df = new DecimalFormat("00");
//	public boolean is24 = false;
//
//	public ClockPanel() {
//		setLayout(null);
//		setSize(389, 93);
//		setBorder(new TitledBorder(new EtchedBorder(), "Clock"));
//		timeLabel.setFont(new Font("Tahoma",Font.BOLD, 30));
//		dateLabel.setFont(new Font("Tahoma",Font.ITALIC, 20));
//
//		attach(timeLabel, 20, 20, 250, 30);
//		attach(dateLabel, 20, 60, 200, 20);
//		attach(hourChk, 280, 23, 80, 20);
//		attach(alarmBtn, 280, 50, 80, 30);
//
//		hourChk.addActionListener(e -> is24 = hourChk.isSelected());
//	}
//
//	private String clockDisplay(boolean is24) {
//		GregorianCalendar cal = new GregorianCalendar();
//		String hour24 = cal.get(Calendar.AM_PM) == 0 ? df.format(cal.get(Calendar.HOUR)) : df.format(cal.get(Calendar.HOUR) + 12);
//		String hour12 = df.format(cal.get(Calendar.HOUR)).equals("0") ? "12" : df.format(cal.get(Calendar.HOUR));
//		String minute = df.format(cal.get(Calendar.MINUTE));
//		String second = df.format(cal.get(Calendar.SECOND));
//		String amPm = cal.get(Calendar.AM_PM) == 0 ? "AM" :"PM";
//		String currentTime24 = hour24 + " : " + minute + " : "  + second + " ";
//		String currentTime12 = hour12 + " : " + minute + " : "  + second + " " + amPm;
//		String currentTime;
//		currentTime = is24 ? currentTime24 : currentTime12;
//
//		return currentTime;
//	}
//
//	private String dateDisplay() {
//		GregorianCalendar cal = new GregorianCalendar();
//
//		int year = cal.get(Calendar.YEAR);
//		String dayOfWeek = dayOfWeekArr[cal.get(Calendar.DAY_OF_WEEK)];
//		String month = monthArr[cal.get(Calendar.MONTH)];
//
//		String date = df.format(cal.get(Calendar.DATE));
//		String currentDate = dayOfWeek + " - " + month + " " + date + ". " + year;
//		dateLabel.setText(currentDate);
//		return currentDate;
//	}
//
//	public void clockThread(){
//		timeLabel.setText(clockDisplay(is24));
//		dateDisplay();
//	}
//
//	private void attach(JComponent abc, int x, int y, int width, int height) {
//		this.add(abc);
//		abc.setBounds(x, y, width, height);
//	}
//}
