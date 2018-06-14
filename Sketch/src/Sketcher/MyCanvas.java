package Sketcher;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MyCanvas extends Canvas {
	
	
	int x=-50; int y=-50; int w=10; int h=10;
	Color cr=Color.black;
	public String imagePath;
	Image background;
	public ArrayList<Point> pointList;
	
	//Graphics former_g;
	
	public MyCanvas() {
		super();
		pointList = new ArrayList<>();
	}
		
	@Override
	public void paint(Graphics g){
		//former_g = g.create();
		//super.paint(g);
		
		for(Point p : pointList) {
			g.setColor(p.c);
			g.fillOval(p.x, p.y, p.w, p.h);
			//Graphics2D g2 = (Graphics2D) g;
			//g2.setStroke(new BasicStroke(p.w));
		}
	}
	
	public static CanvasMessage makeCanvasMessage(Point p) {
		CanvasMessage canMsg = new CanvasMessage();
		canMsg.setPt(p);
		canMsg.setType(CanvasMessage.CANVAS_DRAW_POINT);
		
		return canMsg;
	}
	
	public static CanvasMessage makeCanvasMessage(String FilePath) {
		CanvasMessage canMsg = new CanvasMessage();
		canMsg.setFilePath(FilePath);
		canMsg.setType(CanvasMessage.CANVAS_LOAD_PICTURE);
		
		return canMsg;
	}
	public static CanvasMessage makeCanvasReloadMessage(String FilePath, ArrayList<Point> pt_list) {
		CanvasMessage canMsg = new CanvasMessage();
		canMsg.setFilePath(FilePath);
		canMsg.setPtList(pt_list);
		canMsg.setType(CanvasMessage.CANVAS_DRAW_ALL);
		
		return canMsg;
	}
	public static CanvasMessage makeCanvasClearMessage() {
		CanvasMessage canMsg = new CanvasMessage();
		canMsg.setType(CanvasMessage.CANVAS_CLEAR_ALL);
		
		return canMsg;
	}
		
	public void setImage(Image img,String pathName) {
		//this.clearAll();
		this.imagePath = pathName;
		this.background = img;
		this.getGraphics().drawImage(background,0,0,null);
	}
	public void setImage(String pathName) {
		try {
			this.imagePath = pathName;
	        BufferedImage img = ImageIO.read(new File(pathName));
	        img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
	        setImage(img,pathName);
		}catch(Exception exception) {
			exception.printStackTrace();
		}
        //repaint();
	}
	public void addPoint() {
		pointList.add(new Point(x,y,w,h,cr));
	}
	public void addPoint(Point p) {
		pointList.add(new Point(p));
	}
	public void handleMessage(CanvasMessage msg) {
		int msgType = msg.getType();
		
		switch(msgType) {
		case CanvasMessage.CANVAS_DEFAULT:
			System.out.println("Message Type not configured");
			break;
		case CanvasMessage.CANVAS_DRAW_POINT:
			this.addPoint(msg.getPt());
			break;
		case CanvasMessage.CANVAS_CLEAR_ALL:
			this.clearAll();
			break;
		case CanvasMessage.CANVAS_LOAD_PICTURE:
			this.clearAll();
			this.setImage(msg.getFilePath());
			break;
		case CanvasMessage.CANVAS_DRAW_ALL:
			if(msg.getPtList().size() > 0) {
				this.pointList.clear();
				ArrayList<Point> recvPtList = msg.getPtList();
				for(int i = 0 ; i < recvPtList.size(); i++)
					pointList.add(new Point(recvPtList.get(i).x,recvPtList.get(i).y,recvPtList.get(i).w,recvPtList.get(i).h,recvPtList.get(i).c));
					//					this.addPoint(recvPtList.get(i));
			}
			if(msg.getFilePath() != null)
				this.setImage(msg.getFilePath());
			//repaint();
			break;
		}
		repaint();
	}
	
	@Override
	public void update(Graphics g){
		paint(g);
	}
	public void setColor(Color c) {
		this.cr = c;
	}
	public void clearAll() {
		this.background = null;
		this.pointList.clear();
		this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
		this.repaint();
	}
	public void saveToImage(String url) {
		
		if(background != null)
			this.getGraphics().drawImage(background,0,0,null);
		for(Point p : pointList) {
			this.getGraphics().setColor(p.c);
			this.getGraphics().fillOval(p.x, p.y, p.w, p.h);
		}

        BufferedImage image = new  BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        if(background != null)
			graphics.drawImage(background,0,0,null);
		for(Point p : pointList) {
			graphics.setColor(p.c);
			graphics.fillOval(p.x, p.y, p.w, p.h);
		}
        //this.paintAll(graphics);
        //graphics.dispose();
        try {
            if (ImageIO.write(image, "png", new File(url)))
                System.out.println("-- saved");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        this.repaint();
	}
}
