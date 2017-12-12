package helper;

import java.awt.MouseInfo;

public class CursorPos {
	
	public CursorPos() {
		
	}

	public int xPos() {
		
		return (int) MouseInfo.getPointerInfo().getLocation().getX();
	
	}
	
	public int yPos() {
		
		return (int) MouseInfo.getPointerInfo().getLocation().getY();
		
	}
}
