package wowrotabot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Random;

import helper.AddonPos;
import helper.Skill;
import jssc.SerialPort;
import jssc.SerialPortException;

public class ArduinoBot {

	private ArrayList<Skill> skillArray = new ArrayList<Skill>();
	private AddonPos addonPos;
	private Thread thread;
	private Color color, colorAtAddonPos;
	private Robot r;
	private String COM;
	private SerialPort serialPort;
	private Random ran = new Random();
	private int x = ran.nextInt(50) + 100;


	public ArduinoBot(AddonPos addonPos, ArrayList<Skill> skillArray, String COM) {

		this.COM = COM;
		this.addonPos = addonPos;
		this.skillArray = skillArray;
		serialPort = new SerialPort(COM);
		
	}

	public void start() throws AWTException, SerialPortException, InterruptedException {

		r = new Robot();

		colorAtAddonPos = new Color(255, 255, 255);

		thread = new Thread() {
			@Override
			public void run() {
				while (thread.isAlive()) {

					try {
						serialPort.openPort();
						serialPort.setParams(9600, 8, 1, 0);

						colorAtAddonPos = r.getPixelColor(addonPos.getX(), addonPos.getY());

						for (int i = 0; i < skillArray.size(); i++) {

							color = new Color(255, 255, 255);
							color = skillArray.get(i).getColor();

							if (colorAtAddonPos.getRed() == color.getRed()
									&& colorAtAddonPos.getGreen() == color.getGreen()
									&& colorAtAddonPos.getBlue() == color.getBlue()) {
								try {

									serialPort.writeInt(i+1);
									Thread.sleep(x);

								} catch (SerialPortException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}

						serialPort.closePort();
					} catch (SerialPortException ex) {
						System.out.println(ex);
					}
				}

			}

		};
		thread.start();

	}

	public void stop() throws SerialPortException {

		thread.stop();
		serialPort.closePort();
		
	}
}