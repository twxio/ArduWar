package helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Save {

	private ArrayList<Skill> skillArray = new ArrayList<Skill>();
	private AddonPos addonPos;
	private File fileArray, fileAddonPos;
	private FileWriter write;
	private String profileName;

	public Save(String profileName, AddonPos addonPos, ArrayList<Skill> skillArray) throws IOException {

		this.addonPos = addonPos;
		this.profileName = profileName;
		this.skillArray = skillArray;

	}
	
	public void SaveProfile() throws IOException {
		
		
		
		fileArray = new File("Profiles" , profileName + ".ini");
		fileAddonPos = new File("Profiles", profileName + "Addon.ini");

		write = new FileWriter(fileArray);

		for (int i = 0; i < skillArray.size(); i++) {

			write.write(skillArray.get(i).getColor().getRed() + " " + skillArray.get(i).getColor().getGreen() + " "
					+ skillArray.get(i).getColor().getBlue() + " " + i + "\n");
		}

		write.flush();
		write.close();

		write = new FileWriter(fileAddonPos);
		write.write(addonPos.getX() + " " + addonPos.getY());
		write.flush();
		write.close();
		
	}

}
