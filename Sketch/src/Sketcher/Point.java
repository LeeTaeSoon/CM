package Sketcher;

import java.awt.Color;
import java.io.Serializable;

public class Point implements Serializable {
	public int x;
	public int y;
	public int w;
	public int h;
	public Color c;
	
	public Point(int x, int y,int w, int h, Color c) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
	}
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.w = p.w;
		this.h = p.h;
		this.c = p.c;
	}
}
