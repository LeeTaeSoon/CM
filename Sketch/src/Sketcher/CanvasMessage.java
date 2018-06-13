package Sketcher;

import java.io.Serializable;

public class CanvasMessage implements Serializable {
	
	static final int CANVAS_DEFAULT = 0;
	static final int CANVAS_DRAW_POINT = 1;
	static final int CANVAS_LOAD_PICTURE = 2;
	static final int CANVAS_CLEAR_ALL = 3;
	
	Point pt;
	int type;
	String filePath;
	
	public CanvasMessage() {
		type = CANVAS_DEFAULT;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int MessageType) {
		if( MessageType > 0 && MessageType < 4 )
			this.type = MessageType;
		else
			this.type = CANVAS_DEFAULT;
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
