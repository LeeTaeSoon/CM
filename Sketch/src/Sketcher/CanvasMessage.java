package Sketcher;

import java.io.Serializable;
import java.util.ArrayList;

public class CanvasMessage implements Serializable {
	
	static final int CANVAS_DEFAULT = 0;
	static final int CANVAS_DRAW_POINT = 1;
	static final int CANVAS_LOAD_PICTURE = 2;
	static final int CANVAS_CLEAR_ALL = 3;
	static final int CANVAS_DRAW_ALL = 4;
	
	Point pt;
	ArrayList<Point> pt_list;
	int type;
	String filePath;
	int dataType;
	String text;
	
	public CanvasMessage() {
		pt_list = new ArrayList<>();
		type = CANVAS_DEFAULT;
	}
	
	
	public ArrayList<Point> getPtList() {
		return pt_list;
	}
	public void setPtList(ArrayList<Point> pt_list) {
		this.pt_list = pt_list;
	}

	public int getType() {
		return type;
	}
	public void setType(int MessageType) {
		if( MessageType > 0 && MessageType < 5 )
			this.type = MessageType;
		else
			this.type = CANVAS_DEFAULT;
	}
		
	public int getDataType() {
		return dataType;
	}
	
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Point getPt() {
		return pt;
	}

	public void setPt(Point pt) {
		this.pt = pt;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String toString() {
		return "filePath : " + filePath + ", type : " + type + ", Point : " + pt.h + " " + pt.w + " " + pt.x + " " + pt.y + " " + pt.c.toString(); 
	}
}
