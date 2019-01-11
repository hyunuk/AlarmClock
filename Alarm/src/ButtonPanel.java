import javax.swing.*;

/**
 * Created by hyunuk71@gmail.com on 22/08/2018
 * Github : http://github.com/hyunuk71
 */
public class ButtonPanel extends JPanel {
	protected JButton addBtn = new JButton("ADD");
	protected JButton editBtn = new JButton("EDIT");
	protected JButton deleteBtn = new JButton("DELETE");
	protected JButton loadBtn = new JButton("LOAD");
	protected JButton saveBtn = new JButton("SAVE");

	public ButtonPanel() {
		setLayout(null);
		setSize(400, 100);

		attach(addBtn, 20, 10, 100, 30);
		attach(editBtn, 140, 10, 100, 30);
		attach(deleteBtn, 260, 10, 100, 30);
		attach(loadBtn, 80, 50, 100, 30);
		attach(saveBtn, 200, 50, 100, 30);
	}

	private void attach(JComponent abc, int x, int y, int width, int height) {
		this.add(abc);
		abc.setBounds(x, y, width, height);
	}
}
