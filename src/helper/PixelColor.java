package helper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

public class PixelColor {
	
	private CursorPos cursorPos;
	private Robot r;
	private Color color;
	
	public PixelColor(CursorPos cursorPos) throws AWTException {
		
		this.cursorPos = cursorPos;
		color = new Color(255,255,255);
		r = new Robot();
		
		color = r.getPixelColor(cursorPos.xPos(), cursorPos.yPos());
	}
	
	public int getRed() {
		return color.getRed();
	}
	
	public int getGreen() {
		return color.getGreen();
	
	}
	public int getBlue() {
		return color.getBlue();
	
	}

}
