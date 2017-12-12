package helper;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Load {
	
	private ArrayList<Skill> skillArray = new ArrayList<Skill>();
	private AddonPos addonPos;
	private Skill skill;
	private File filenameArray, filenameAddon;
	private Scanner scanner; 
	private Color color;
	private String FileName;
	
	public Load(String FileName, ArrayList<Skill> skillArray, AddonPos addonPos) throws FileNotFoundException {
		
		this.FileName = FileName;
		this.skillArray = skillArray;
		this.addonPos = addonPos;
	}
	
	public ArrayList<Skill> setSkillArray() throws FileNotFoundException {
		
		filenameArray = new File("Profiles", FileName + ".ini");
		scanner = new Scanner(filenameArray);
		
		while (scanner.hasNextInt()) {
						
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int z = scanner.nextInt();
			int Taste = scanner.nextInt() + 1;
						
			color = new Color(x, y, z);
			skill = new Skill(color, Integer.toString(Taste));
			skillArray.add(skill);
			
		}
		return skillArray;
				
	}
	
	public AddonPos setAddonPos() throws FileNotFoundException {
					
		filenameAddon = new File("Profiles", FileName + "Addon.ini");
		scanner = new Scanner(filenameAddon);
		
		while (scanner.hasNextInt()) {
						
			int x = scanner.nextInt();
			int y = scanner.nextInt();
		
			
		addonPos = new AddonPos(x, y);	
				
	}
		return addonPos;
	
	}
	
	
}
