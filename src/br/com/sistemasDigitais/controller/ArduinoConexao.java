package br.com.sistemasDigitais.controller;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class ArduinoConexao implements SerialPortEventListener {

	private String appName;
	private String portaUSB;
	private SerialPort serialPort = null;
	private BufferedReader in;
	private OutputStream out;

	private static final int TIME_OUT = 1000; // timeout para a porta
	private static final int DATA_RATE = 9600; // Arduino porta serial

	public boolean conexaoEstabelecida() {
		try {
			CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			System.out.println("Tentando conexão:");
			while (portId == null && portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
						.nextElement();
				System.out.println("   port" + currPortId.getName());
				if (currPortId.getName().equals(portaUSB)
						|| currPortId.getName().startsWith(portaUSB)) {

					serialPort = (SerialPort) currPortId
							.open(appName, TIME_OUT);
					portId = currPortId;
					System.out.println("Conectado com sucesso na porta: "
							+ currPortId.getName());
					break;
				}
			}

			if (portId == null || serialPort == null) {
				System.err
						.println("Não foi possível se conectar com o Arduino!");
				return false;
			}

			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

			timeOutParaSincrinizacaoArduino();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Interrupção forçada para evitar problemas de sincronização com o arduino
	 */
	private static void timeOutParaSincrinizacaoArduino() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}
	}

	public void enviarString(String data) {
		if (conexaoEstabelecida()) {
			try {
				timeOutParaSincrinizacaoArduino();
				System.out.println("Enviando string: '" + data + "'");
				out = serialPort.getOutputStream();
				out.write(data.getBytes());
			} catch (Exception e) {
				System.err.println(e.toString());
				System.exit(0);
			} finally {
				timeOutParaSincrinizacaoArduino();
				close();
			}
		}
	}

	/**
	 * Método com thread safe para fechar conexao serial
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle para evento da porta serial
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		try {
			switch (oEvent.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:
				if (in == null) {
					in = new BufferedReader(new InputStreamReader(
							serialPort.getInputStream()));
				}
				String inputLine = in.readLine();
				System.out.println(inputLine);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public ArduinoConexao(String nomePortaUSB) {
		appName = getClass().getName();
		portaUSB = nomePortaUSB;
	}

}
