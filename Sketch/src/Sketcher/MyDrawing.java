package Sketcher;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import MainProgram.CMWinClient;

public class MyDrawing extends JPanel {
	
	static final Dimension COLOR_BUTTON_SIZE = new Dimension(10,10);
	static final int CANVAS_WIDTH = 600;
	static final int CANVAS_HEIGHT = 600;
	static final String IMG_PATH = "./resources/images/";
    static final String TXT_PATH = "./resources/texts/";
    static final String IMG_EXT = ".png";
    static final String TXT_EXT = ".txt";
	
	JPanel CanvasPanel;
	Canvas can;
	PaintToolFrame PaintTool;
	JList userList;
	JTextArea slideNote;
	
	JPanel DrawingPanel;
	
	CMWinClient m_client;
	
	public MyDrawing(CMWinClient m_client){
//		setLayout(new FlowLayout(FlowLayout.CENTER));
		PaintTool=new PaintToolFrame();
		this.m_client = m_client;
		init();
	}
	public void init() {
		initCanvas();
		initButton();
		initUserList();
		initSlideNote();
		
		JScrollPane ListScroll = new JScrollPane(userList);
		ListScroll.setSize(new Dimension(200,700));
		
		DrawingPanel = new JPanel();
		DrawingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		DrawingPanel.add(ListScroll);
		DrawingPanel.add(CanvasPanel);
		DrawingPanel.add(PaintTool);
		
		add(DrawingPanel,BorderLayout.CENTER);
		add(new JScrollPane(slideNote),BorderLayout.SOUTH);
	}
	public void initUserList() {
		userList = new JList();
		userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		userList.setVisibleRowCount(40);
		userList.setBorder(new TitledBorder(new LineBorder(Color.black, 2),"Current User"));
		userList.setSize(new Dimension(100,600));
	}
	public void updateUserList() {
		Object[] a = m_client.getGroupUsers().toArray();
		userList.setListData(a);
		revalidate();
	}
	public void initCanvas() {
		CanvasPanel=new JPanel(){
			public Insets getInsets(){
				return new Insets(40,10,10,10);
			}
		}; 
		CanvasPanel.setBackground(Color.lightGray);
		
		can=new MyCanvas();

		can.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		can.setBackground(Color.white); 
		can.addMouseMotionListener(new CanvasHandler());
		
		JScrollPane scrollCanvas = new JScrollPane(can,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		CanvasPanel.add(scrollCanvas);
	}
	public void initButton() {
		ButtonHandler btnHandler = new ButtonHandler();

		PaintTool.btPlus.addActionListener(btnHandler);
		PaintTool.btMinus.addActionListener(btnHandler);
		PaintTool.btClear.addActionListener(btnHandler);
		PaintTool.btAllClear.addActionListener(btnHandler);
		PaintTool.btSave.addActionListener(btnHandler);
		PaintTool.btLoad.addActionListener(btnHandler);
		PaintTool.btnBack.addActionListener(btnHandler);
		PaintTool.colorChooser.getSelectionModel().addChangeListener(new ColorHandler());
	}
	public void initSlideNote() {
		slideNote = new JTextArea(10,140);
	}
	public void saveTextArea(String txt_url) {
		String text = slideNote.getText();
		
		try {
			File file = new File(txt_url);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false)); // true for append
			slideNote.write(writer);
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void receiveMessage(CanvasMessage msg) {
		((MyCanvas)this.can).handleMessage(msg);
	}
	class ColorHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			Color selCr = PaintTool.colorChooser.getColor();
			MyCanvas can2 = (MyCanvas)can;
			can2.setColor(selCr);
		}
		
	}
	class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object o=e.getSource();
			MyCanvas can2 = (MyCanvas)can;

			if(o==PaintTool.btPlus) {
				can2.w +=10;
				can2.h +=10;
			}
			else if(o==PaintTool.btMinus){
				if(can2.w > 10) {
					can2.w -= 10; can2.h -= 10; 
				}
			}
			else if(o==PaintTool.btClear) {
				can2.cr= can.getBackground();
			}
			else if(o==PaintTool.btAllClear){
				can2.clearAll();
			}
			else if(o == PaintTool.btSave) {
                String room_name = "canvas";
                String img_url = IMG_PATH+room_name+IMG_EXT;
                String txt_url = TXT_PATH+room_name+TXT_EXT;
                can2.saveToImage(img_url);
                saveTextArea(txt_url);
			}
			else if(o == PaintTool.btLoad) {
				
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png");
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(null);
                try {
                    if (response == JFileChooser.APPROVE_OPTION) {
                        String pathName = fileChooser.getSelectedFile().getPath();
                        m_client.sendCanvasMessage(MyCanvas.makeCanvasMessage(pathName));
                        //BufferedImage img = ImageIO.read(new File(pathName));
                        //img.getScaledInstance(can2.getWidth(), can2.getHeight(), Image.SCALE_SMOOTH);
                        //can2.setImage(img);
                        //can2.repaint();
                    }
                } catch (Exception exception) {
                    // TODO Auto-generated catch block
                    exception.printStackTrace();
                }
			}
			else if(o == PaintTool.btnBack) {
				m_client.goLobby();
				m_client.goBack();
			}
		}
	}
	class CanvasHandler implements MouseMotionListener, MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int xx=e.getX(); 
			int yy=e.getY();
			((MyCanvas)can).x=xx; ((MyCanvas)can).y=yy;
			//((MyCanvas)can).addPoint();
			MyCanvas myCanvas = (MyCanvas)can;
			Point pt = new Point(myCanvas.x,myCanvas.y,myCanvas.w,myCanvas.h,myCanvas.cr);
			m_client.sendCanvasMessage(MyCanvas.makeCanvasMessage(pt));
			can.repaint(); 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseDragged(MouseEvent e){
			int xx=e.getX(); 
			int yy=e.getY();
			((MyCanvas)can).x=xx; ((MyCanvas)can).y=yy;
			MyCanvas myCanvas = (MyCanvas)can;
			Point pt = new Point(myCanvas.x,myCanvas.y,myCanvas.w,myCanvas.h,myCanvas.cr);
			m_client.sendCanvasMessage(MyCanvas.makeCanvasMessage(pt));
			can.repaint(); 
		}

		public void mouseMoved(MouseEvent e){
		}

	}
	
}
