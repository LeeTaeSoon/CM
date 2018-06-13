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
		PointPanel.add(btPlus=new JButton("ũ��"));
		PointPanel.add(btMinus=new JButton("�۰�"));
		PointPanel.add(btClear=new JButton("�����"));
		PointPanel.add(btAllClear=new JButton("��������"));
		PointPanel.add(btSave=new JButton("����"));
		PointPanel.add(btLoad = new JButton("�ҷ�����"));
		
		add(PointPanel);
		add(colorChooser);
	}
}
