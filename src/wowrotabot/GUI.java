package wowrotabot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import helper.AddonPos;
import helper.ComboItem;
import helper.CursorPos;
import helper.Load;
import helper.PixelColor;
import helper.Save;
import helper.Skill;
import jssc.SerialPortException;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import helper.SerialPortConn;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnStartArduino, btnAutoIt, btnStop, buttonStopArdu, resetbtn, btnSave, btnLoad;
	private JPanel panel, panel_2, panel_1, panel_3, panel_4, panel_5, panel_6, panel_7, panel_8, panel_9, panel_10,
			panel_11, panel_12, panel_13, panel_14, panel_16;
	private JLabel label_1, label_2, label_3, lblNewLabel, lblNewLabel_1, lblAddon, Skill1, label;
	private JLabel label_4, label_5, label_6, label_7, label_8, label_9, label_10, label_11;
	private JTextArea textArea;
	private CursorPos cursorPos;
	private Thread thread;
	private PixelColor pixelColor;
	private boolean skill1set = false, skill2set = false, skill3set = false, skill4set = false, skill5set = false;
	private boolean skill6set = false, skill7set = false, skill8set = false, skill9set = false, skill10set = false;
	private AddonPos addonPos;
	private ArrayList<Skill> skillArray = new ArrayList<Skill>();
	private Color color;
	private Robot r;
	private AutoItBot autoItBot;
	private int count = 0;
	private Save save;
	private Load load;
	private JComboBox<ComboItem> comboBox, comboBox_2;
	private SerialPortConn sc;
	private ArduinoBot arduBot;
	private Font font;
	private JScrollPane scrollPane;
	private ComboItem p1, p2, p3, p4, p5, p6, p7, p8;

	public GUI() {		
			
		initGUI();
		CursorPosAndColorOfPixel();
		initKeys();
	}
		
	public void initKeys() {
		
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F3) {
					cursorPos = new CursorPos();
					addonPos = new AddonPos(cursorPos.xPos(), cursorPos.yPos());
					setAddonPos(
							"Addon @ " + Integer.toString(addonPos.getX()) + " | " + Integer.toString(addonPos.getY()));
				}

				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F2) {

					count++;
					color = new Color(255, 255, 255);
					try {
						r = new Robot();
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
					color = r.getPixelColor(addonPos.getX(), addonPos.getY());
					if (skillArray.size() < 10) {
						skillArray.add(new Skill(color, Integer.toString(count)));
					} else
						writeToLog("List full" + "\n");
					setSkilltoLabel(color);
				}
				
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F4) {
					
						writeToLog("Started..." + "\n");
						if (skillArray.size() < 10) {
							textArea.setText("Please add 10 Skills" + "\n");
						} else {
							String COM = String.valueOf(comboBox.getSelectedItem());
							arduBot = new ArduinoBot(addonPos, skillArray, COM);
							try {
								arduBot.start();
							} catch (AWTException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SerialPortException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F5) {
					
					try {
						arduBot.stop();
						writeToLog("Stopped!" + "\n");
					} catch (SerialPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F6) {

					try {
						r = new Robot();
						r.mouseMove(addonPos.getX(), addonPos.getY());
					} catch (AWTException e) {
						e.printStackTrace();
					}
				}	
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
			}
		});	
	}

	public void initGUI() {
		
		font = new Font("Roboto", Font.BOLD, 10);	
		setIconImage(new ImageIcon("lib\\image.png").getImage());
		p1 = new ComboItem("Profile 1");
		p2 = new ComboItem("Profile 2");
		p3 = new ComboItem("Profile 3");
		p4 = new ComboItem("Profile 4");
		p5 = new ComboItem("Profile 5");
		p6 = new ComboItem("Profile 6");
		p7 = new ComboItem("Profile 7");
		p8 = new ComboItem("Profile 8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setFocusable(true);

		setTitle("ArduWar");
		Font labelFont = new Font("Roboto", Font.BOLD, 11);
		UIManager.put("Label.font", labelFont);
		UIManager.put("Button.font", labelFont);
		contentPane.setLayout(null);
		contentPane.setLayout(null);

		btnStartArduino = new JButton("Start Arduino");
		btnStartArduino.setContentAreaFilled(false);
		btnStartArduino.addActionListener(this);
		btnStartArduino.setFont(font);
		btnStartArduino.setBounds(4, 411, 99, 23);
		btnStartArduino.setFocusable(false);
		contentPane.add(btnStartArduino);

		btnAutoIt = new JButton("Start AutoIt");
		btnAutoIt.addActionListener(this);
		btnAutoIt.setContentAreaFilled(false);
		btnAutoIt.setFont(font);
		btnAutoIt.setBounds(4, 380, 99, 23);
		btnAutoIt.setFocusable(false);
		contentPane.add(btnAutoIt);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(4, 109, 158, 260);
		panel.setFocusable(false);
		contentPane.add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 147, 237);
		panel.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFocusable(false);
		textArea.setLineWrap(true);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "PixelColor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(4, 11, 79, 87);
		panel_2.setFocusable(false);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		label_1 = new JLabel("Red");
		label_1.setBounds(6, 66, 83, 14);
		label_1.setFocusable(false);
		panel_2.add(label_1);

		label_2 = new JLabel("Green");
		label_2.setBounds(6, 41, 83, 14);
		label_2.setFocusable(false);
		panel_2.add(label_2);

		label_3 = new JLabel("Blue");
		label_3.setBounds(6, 16, 83, 14);
		label_3.setFocusable(false);
		panel_2.add(label_3);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cursor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(93, 11, 71, 87);
		panel_1.setFocusable(false);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblNewLabel = new JLabel("X");
		lblNewLabel.setFocusable(false);
		lblNewLabel.setBounds(10, 25, 46, 14);
		panel_1.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Y");
		lblNewLabel_1.setFocusable(false);
		lblNewLabel_1.setBounds(10, 50, 46, 14);
		panel_1.add(lblNewLabel_1);

		panel_3 = new JPanel();
		panel_3.setFocusable(false);
		panel_3.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(174, 11, 204, 45);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		lblAddon = new JLabel("Addon @: ");
		lblAddon.setFocusable(false);
		lblAddon.setBounds(6, 16, 132, 14);
		panel_3.add(lblAddon);

		panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(null, "Skill Colors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_14.setBounds(172, 53, 206, 349);
		contentPane.add(panel_14);
		panel_14.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setBounds(6, 16, 92, 56);
		panel_14.add(panel_4);
		panel_4.setBorder(new TitledBorder(null, "Skill 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setVisible(false);
		panel_4.setLayout(null);

		Skill1 = new JLabel("");
		Skill1.setBounds(6, 16, 80, 33);
		panel_4.add(Skill1);
		Skill1.setFocusable(false);
		Skill1.setOpaque(true);

		panel_5 = new JPanel();
		panel_5.setBounds(108, 16, 92, 56);
		panel_14.add(panel_5);
		panel_5.setBorder(new TitledBorder(null, "Skill 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setVisible(false);
		panel_5.setLayout(null);

		label = new JLabel("");
		label.setBounds(6, 16, 80, 33);
		panel_5.add(label);
		label.setOpaque(true);
		label.setFocusable(false);

		panel_6 = new JPanel();
		panel_6.setBounds(6, 83, 92, 56);
		panel_14.add(panel_6);
		panel_6.setBorder(new TitledBorder(null, "Skill 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setVisible(false);
		panel_6.setLayout(null);

		label_4 = new JLabel("");
		label_4.setBounds(6, 16, 80, 33);
		panel_6.add(label_4);
		label_4.setOpaque(true);
		label_4.setFocusable(false);

		panel_7 = new JPanel();
		panel_7.setBounds(108, 83, 92, 56);
		panel_14.add(panel_7);
		panel_7.setBorder(new TitledBorder(null, "Skill 4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setVisible(false);
		panel_7.setLayout(null);

		label_5 = new JLabel("");
		label_5.setBounds(6, 16, 80, 33);
		panel_7.add(label_5);
		label_5.setOpaque(true);
		label_5.setFocusable(false);

		panel_8 = new JPanel();
		panel_8.setBounds(6, 150, 92, 56);
		panel_14.add(panel_8);
		panel_8.setBorder(new TitledBorder(null, "Skill 5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setVisible(false);
		panel_8.setLayout(null);

		label_6 = new JLabel("");
		label_6.setBounds(6, 16, 80, 33);
		panel_8.add(label_6);
		label_6.setOpaque(true);
		label_6.setFocusable(false);

		panel_9 = new JPanel();
		panel_9.setBounds(108, 150, 92, 56);
		panel_14.add(panel_9);
		panel_9.setBorder(new TitledBorder(null, "Skill 6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setVisible(false);
		panel_9.setLayout(null);

		label_7 = new JLabel("");
		label_7.setBounds(6, 16, 80, 33);
		panel_9.add(label_7);
		label_7.setOpaque(true);
		label_7.setFocusable(false);

		panel_10 = new JPanel();
		panel_10.setBounds(6, 217, 92, 56);
		panel_14.add(panel_10);
		panel_10.setBorder(new TitledBorder(null, "Skill 7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.setVisible(false);
		panel_10.setLayout(null);

		label_8 = new JLabel("");
		label_8.setBounds(6, 16, 80, 33);
		panel_10.add(label_8);
		label_8.setOpaque(true);
		label_8.setFocusable(false);

		panel_11 = new JPanel();
		panel_11.setBounds(108, 217, 92, 56);
		panel_14.add(panel_11);
		panel_11.setBorder(new TitledBorder(null, "Skill 8", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_11.setVisible(false);
		panel_11.setLayout(null);

		label_9 = new JLabel("");
		label_9.setBounds(6, 16, 80, 33);
		panel_11.add(label_9);
		label_9.setOpaque(true);
		label_9.setFocusable(false);

		panel_12 = new JPanel();
		panel_12.setBounds(6, 286, 92, 56);
		panel_14.add(panel_12);
		panel_12.setBorder(new TitledBorder(null, "Skill 9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_12.setVisible(false);
		panel_12.setLayout(null);

		label_10 = new JLabel("");
		label_10.setBounds(6, 16, 80, 33);
		panel_12.add(label_10);
		label_10.setOpaque(true);
		label_10.setFocusable(false);

		panel_13 = new JPanel();
		panel_13.setBounds(108, 286, 92, 56);
		panel_14.add(panel_13);
		panel_13.setBorder(new TitledBorder(null, "Skill 10", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_13.setVisible(false);
		panel_13.setLayout(null);

		label_11 = new JLabel("");
		label_11.setBounds(6, 16, 80, 33);
		panel_13.add(label_11);
		label_11.setOpaque(true);
		label_11.setFocusable(false);

		btnStop = new JButton("Stop");
		btnStop.setContentAreaFilled(false);
		btnStop.setBounds(107, 380, 60, 23);
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		btnStop.setFocusable(false);
		contentPane.add(btnStop);

		comboBox = new JComboBox<ComboItem>();
		comboBox.setBounds(4, 445, 92, 23);
		sc = new SerialPortConn();
		ArrayList<String> coms = new ArrayList<String>();
		coms = sc.getPorts();
		comboBox.setModel(new DefaultComboBoxModel(coms.toArray()));
		comboBox.setFocusable(false);
		contentPane.add(comboBox);

		buttonStopArdu = new JButton("Stop");
		buttonStopArdu.setFocusable(false);
		buttonStopArdu.setContentAreaFilled(false);
		buttonStopArdu.setFont(font);
		buttonStopArdu.addActionListener(this);
		buttonStopArdu.setFocusable(false);
		buttonStopArdu.setBounds(107, 411, 60, 23);
		contentPane.add(buttonStopArdu);
		
		resetbtn = new JButton("Reset");
		resetbtn.setFocusable(false);
		resetbtn.setContentAreaFilled(false);
		resetbtn.setFont(font);
		resetbtn.addActionListener(this);
		resetbtn.setBounds(100, 445, 71, 23);
		contentPane.add(resetbtn);
		
		panel_16 = new JPanel();
		panel_16.setBorder(new TitledBorder(null, "Save / Load", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_16.setBounds(182, 415, 208, 80);
		contentPane.add(panel_16);
		panel_16.setLayout(null);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(113, 16, 89, 23);
		btnSave.addActionListener(this);
		btnSave.setContentAreaFilled(false);
		btnSave.setFont(font);
		btnSave.setFocusable(false);
		panel_16.add(btnSave);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(113, 50, 89, 23);
		btnLoad.addActionListener(this);
		btnLoad.setContentAreaFilled(false);
		btnLoad.setFont(font);
		btnLoad.setFocusable(false);
		panel_16.add(btnLoad);
		
		comboBox_2 = new JComboBox<ComboItem>();
		comboBox_2.setBounds(6, 17, 92, 20);
		comboBox_2.addItem(p1);
		comboBox_2.addItem(p2);
		comboBox_2.addItem(p3);
		comboBox_2.addItem(p4);
		comboBox_2.addItem(p5);
		comboBox_2.addItem(p6);
		comboBox_2.addItem(p7);
		comboBox_2.addItem(p8);
		comboBox_2.setFocusable(false);
		panel_16.add(comboBox_2);
		setVisible(true);
	}

	public void CursorPosAndColorOfPixel() {

		thread = new Thread() {
			@Override
			public void run() {
				while (thread.isAlive()) {
					cursorPos = new CursorPos();

					try {
						pixelColor = new PixelColor(cursorPos);
					} catch (AWTException e) {
						e.printStackTrace();
					}

					lblNewLabel.setText("x: " + Integer.toString(cursorPos.xPos()));
					lblNewLabel_1.setText("y: " + Integer.toString(cursorPos.yPos()));
					label_3.setText("Red: " + pixelColor.getRed());
					label_2.setText("Green: " + pixelColor.getGreen());
					label_1.setText("Blue: " + pixelColor.getBlue());
				}
			}
		};
		thread.start();
	}

	public void setAddonPos(String text) {

		lblAddon.setText(text);
	}

	public void writeToLog(String text) {

		textArea.append(text);
	}

	public void setSkilltoLabel(Color color) {

		if (!skill1set) {
			panel_4.setVisible(true);
			Skill1.setBackground(color);
			skill1set = true;
			writeToLog("Skill 1 added" + "\n");

		} else if (!skill2set) {

			panel_5.setVisible(true);
			label.setBackground(color);
			skill2set = true;
			writeToLog("Skill 2 added" + "\n");

		} else if (!skill3set) {

			panel_6.setVisible(true);
			label_4.setBackground(color);
			skill3set = true;
			writeToLog("Skill 3 added" + "\n");

		} else if (!skill4set) {

			panel_7.setVisible(true);
			label_5.setBackground(color);
			skill4set = true;
			writeToLog("Skill 4 added" + "\n");

		} else if (!skill5set) {

			panel_8.setVisible(true);
			label_6.setBackground(color);
			skill5set = true;
			writeToLog("Skill 5 added" + "\n");

		} else if (!skill6set) {

			panel_9.setVisible(true);
			label_7.setBackground(color);
			skill6set = true;
			writeToLog("Skill 6 added" + "\n");

		} else if (!skill7set) {

			panel_10.setVisible(true);
			label_8.setBackground(color);
			skill7set = true;
			writeToLog("Skill 7 added" + "\n");
		} else if (!skill8set) {

			panel_11.setVisible(true);
			label_9.setBackground(color);
			skill8set = true;
			writeToLog("Skill 8 added" + "\n");
		} else if (!skill9set) {

			panel_12.setVisible(true);
			label_10.setBackground(color);
			skill9set = true;
			writeToLog("Skill 9 added" + "\n");

		} else if (!skill10set) {

			panel_13.setVisible(true);
			label_11.setBackground(color);
			skill10set = true;
			writeToLog("Skill 10 added" + "\n");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == this.btnAutoIt) {
			int reply = JOptionPane.showConfirmDialog(null,
					"Be Aware! AutoIt is unsave und will likely result in a Ban. Continue?", "Caution!",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				textArea.setText("Started..." + "\n");

				if (skillArray.size() < 10) {

					textArea.setText("Please add 10 Skills" + "\n");
				} else {

					autoItBot = new AutoItBot(addonPos, skillArray);
					try {
						autoItBot.start();
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		if (ae.getSource() == this.btnStartArduino) {

			String COM = String.valueOf(comboBox.getSelectedItem());
			arduBot = new ArduinoBot(addonPos, skillArray, COM);
			try {
				arduBot.start();
			} catch (AWTException | SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (ae.getSource() == this.btnStop) {

			autoItBot.stop();
			textArea.setText("Stopped" + "\n");
		}

		if (ae.getSource() == this.buttonStopArdu) {

			try {
				arduBot.stop();
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textArea.setText("Stopped" + "\n");
		}
		
		if (ae.getSource() == this.btnSave) {

			try {
												
				save = new Save(comboBox_2.getSelectedItem().toString(), addonPos, skillArray);
				save.SaveProfile();
				textArea.setText("Saved" + "\n");
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		
		if (ae.getSource() == this.btnLoad) {

			addonPos = new AddonPos(0, 0);
			skillArray = new ArrayList<Skill>();
			

			try {
				load = new Load(comboBox_2.getSelectedItem().toString(), skillArray, addonPos);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				skillArray = load.setSkillArray();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				addonPos = load.setAddonPos();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			panel_4.setVisible(true);
			Skill1.setBackground(skillArray.get(0).getColor());
			skill1set = true;
			writeToLog("Skill 1 added" + "\n");

			panel_5.setVisible(true);
			label.setBackground(skillArray.get(1).getColor());
			skill2set = true;
			writeToLog("Skill 2 added" + "\n");

			panel_6.setVisible(true);
			label_4.setBackground(skillArray.get(2).getColor());
			skill3set = true;
			writeToLog("Skill 3 added" + "\n");

			panel_7.setVisible(true);
			label_5.setBackground(skillArray.get(3).getColor());
			skill4set = true;
			writeToLog("Skill 4 added" + "\n");

			panel_8.setVisible(true);
			label_6.setBackground(skillArray.get(4).getColor());
			skill5set = true;
			writeToLog("Skill 5 added" + "\n");

			panel_9.setVisible(true);
			label_7.setBackground(skillArray.get(5).getColor());
			skill6set = true;
			writeToLog("Skill 6 added" + "\n");

			panel_10.setVisible(true);
			label_8.setBackground(skillArray.get(6).getColor());
			skill7set = true;
			writeToLog("Skill 7 added" + "\n");

			panel_11.setVisible(true);
			label_9.setBackground(skillArray.get(7).getColor());
			skill8set = true;
			writeToLog("Skill 8 added" + "\n");

			panel_12.setVisible(true);
			label_10.setBackground(skillArray.get(8).getColor());
			skill9set = true;
			writeToLog("Skill 9 added" + "\n");

			panel_13.setVisible(true);
			label_11.setBackground(skillArray.get(9).getColor());
			skill10set = true;
			writeToLog("Skill 10 added" + "\n");

			lblAddon.setText("Addon @ " + addonPos.getX() + " | " + addonPos.getY());
			
			writeToLog("Load successful" + "\n");

		}
		
		if (ae.getSource() == this.resetbtn) {
			
			addonPos.setX(0);
			addonPos.setY(0);
			panel_4.setVisible(false);
			panel_5.setVisible(false);
			panel_6.setVisible(false);
			panel_7.setVisible(false);
			panel_8.setVisible(false);
			panel_9.setVisible(false);
			panel_10.setVisible(false);
			panel_11.setVisible(false);
			panel_12.setVisible(false);
			panel_13.setVisible(false);
			skill1set = false;
			skill2set = false;
			skill3set = false;
			skill4set = false;
			skill5set = false;
			skill6set = false;
			skill7set = false;
			skill8set = false;
			skill9set = false;
			skill10set = false;
			lblAddon.setText("Addon @");
			writeToLog("Reset successful" + "\n");	
			skillArray.clear();
		}
	}
}
