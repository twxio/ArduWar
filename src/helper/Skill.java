package helper;

import java.awt.Color;

public class Skill {

	private String key;
	private Color color;
	
	public Skill(Color color, String key) {

		this.key = key;
		this.color = color;		
	}

	public Color getColor() {
		return color;
	}

	public String getKey() {
		return key;
	}

}
