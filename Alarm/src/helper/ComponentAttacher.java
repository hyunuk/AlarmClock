package helper;

import javax.swing.*;
import java.awt.*;

public class ComponentAttacher {
	public static void attach(Container caller, JComponent abc, int x, int y, int width, int height) {
		caller.add(abc);
		abc.setBounds(x, y, width, height);
	}

	public static void attach(Container caller, JComponent abc, Rectangle r) {
		caller.add(abc);
		abc.setBounds(r);
	}

	public static void attach(Container caller, JComponent abc, Point p, Dimension d) {
		caller.add(abc);
		abc.setPreferredSize(d);
		abc.setLocation(p);
	}
}
