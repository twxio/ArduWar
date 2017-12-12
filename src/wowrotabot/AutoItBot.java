package wowrotabot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;

import helper.AddonPos;
import helper.Caster;
import helper.Skill;

public class AutoItBot {

	private ArrayList<Skill> skillArray = new ArrayList<Skill>();
	private AddonPos addonPos;
	private Thread thread;
	private Color color, colorAtAddonPos;
	private Robot r;
	private Caster caster;

	public AutoItBot(AddonPos addonPos, ArrayList<Skill> skillArray) {

		this.addonPos = addonPos;
		this.skillArray = skillArray;
	}

	public void start() throws AWTException {

		colorAtAddonPos = new Color(255, 255, 255);
		r = new Robot();
		thread = new Thread() {
			@Override
			public void run() {
				while (thread.isAlive()) {

					colorAtAddonPos = r.getPixelColor(addonPos.getX(), addonPos.getY());

					for (int i = 0; i < skillArray.size(); i++) {
						
						color = new Color(255, 255, 255);
						color = skillArray.get(i).getColor();

						if (colorAtAddonPos.getRed() == color.getRed() && colorAtAddonPos.getGreen() == color.getGreen()
								&& colorAtAddonPos.getBlue() == color.getBlue()) {
							System.out.println("Skill found");
							caster = new Caster(skillArray.get(i).getKey());
							caster.CastSpell();

						}
						else System.out.println("Skill not found");
					}
				}
			}
		};
		thread.start();
	}

	public void stop() {

		thread.stop();
	}

}
