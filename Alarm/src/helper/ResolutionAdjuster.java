package helper;

import java.awt.*;

public class ResolutionAdjuster {
	public static int adjustWidth(int widthFor3840) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int currentWidth = gd.getDisplayMode().getWidth();
		return (widthFor3840*currentWidth) / 3840;
	}

	public static int adjustHeight(int heightFor2160) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int currentHeight = gd.getDisplayMode().getHeight();
		return (heightFor2160*currentHeight) / 3840;
	}
}
