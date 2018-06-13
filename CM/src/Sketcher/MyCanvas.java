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
	//처음에 까만색 점 안찍히게 하기 위해서 x,y -값 지정
	int x=-50; int y=-50; int w=10; int h=10;
	Color cr=Color.black;
	Image background;
	ArrayList<Point> pointList;
	
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
	public void setImage(Image img) {
		this.clearAll();
		this.background = img;
		this.getGraphics().drawImage(background,0,0,null);
		
	}
	public void addPoint() {
		pointList.add(new Point(x,y,w,h,cr));
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
