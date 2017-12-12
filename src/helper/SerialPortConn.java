package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import jssc.*;

public class SerialPortConn {

	private String[] portNames = SerialPortList.getPortNames();
	private SerialPort serialPort;

	public SerialPortConn() {
		

	}

	public ArrayList<String> getPorts() {

		ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(portNames));

		if (portNames.length == 0) {
			System.out.println(
					"There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
			System.out.println("Press Enter to exit...");
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringList;
	}

	public boolean checkPort(String com) {

		serialPort = new SerialPort(com);
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR;
			serialPort.setEventsMask(mask);
			serialPort.writeString("");
			return true;
		} catch (SerialPortException ex) {
			return false;
		}

	}

	public void sendData(String COM, String command) throws SerialPortException {

		serialPort = new SerialPort(COM);
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR;
			serialPort.setEventsMask(mask);
			serialPort.writeString(command);
		} catch (SerialPortException ex) {
			System.out.println("There are an error on writing string to port : " + ex);
		}
	}
}
