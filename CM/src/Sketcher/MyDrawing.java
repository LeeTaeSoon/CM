package Sketcher;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
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
	

    
	
	public MyDrawing(){
//		setLayout(new FlowLayout(FlowLayout.CENTER));
		PaintTool=new PaintToolFrame();
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
		PaintTool.colorChooser.getSelectionModel().addChangeListener(new ColorHandler());
	}
	public void initSlideNote() {
		slideNote = new JTextArea(10,140);
	}
	public void saveTextArea(String txt_url) {
		String text = slideNote.getText();
		try (PrintWriter out = new PrintWriter(txt_url)) {
		    out.println(text);
		}
		catch(IOException e){
			e.printStackTrace();
		}
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
                        BufferedImage img = ImageIO.read(new File(pathName));
                        img.getScaledInstance(can2.getWidth(), can2.getHeight(), Image.SCALE_SMOOTH);
                        can2.setImage(img);
                        can2.repaint();
                    }
                } catch (Exception exception) {
                    // TODO Auto-generated catch block
                    exception.printStackTrace();
                }
				
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
			((MyCanvas)can).addPoint();
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
			((MyCanvas)can).addPoint();
			can.repaint(); 
		}

		public void mouseMoved(MouseEvent e){
		}

	}
	
}
