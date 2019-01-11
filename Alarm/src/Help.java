import java.awt.*;

/**
 * Created by hyunuk71@gmail.com on 02/09/2018
 * Github : http://github.com/hyunuk71
 */
public class Help {
	public static int adjustWidth(int widthFor3840) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int currentWidth = gd.getDisplayMode().getWidth();
		int modWidth = (widthFor3840*currentWidth)/3840;
		return modWidth;
	}

	public static int adjustHeight(int heightFor2160) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int currentHeight = gd.getDisplayMode().getHeight();
		int modHeight = (heightFor2160*currentHeight)/3840;
		return modHeight;
	}
}
