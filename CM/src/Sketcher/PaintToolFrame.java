package Sketcher;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PaintToolFrame extends JPanel {
	public JButton btPlus, btMinus, btClear, btAllClear, btSave, btLoad;
	public JPanel PointPanel;
	JColorChooser colorChooser;
	
	public PaintToolFrame(){
		super();
		
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		PointPanel = new JPanel();
		colorChooser = new JColorChooser();
		
		PointPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		PointPanel.add(btPlus=new JButton("크게"));
		PointPanel.add(btMinus=new JButton("작게"));
		PointPanel.add(btClear=new JButton("지우기"));
		PointPanel.add(btAllClear=new JButton("모두지우기"));
		PointPanel.add(btSave=new JButton("저장"));
		PointPanel.add(btLoad = new JButton("불러오기"));
		
		add(PointPanel);
		add(colorChooser);
	}
}
