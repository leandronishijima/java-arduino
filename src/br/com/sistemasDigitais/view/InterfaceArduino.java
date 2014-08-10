package br.com.sistemasDigitais.view;

import static java.awt.GridBagConstraints.HORIZONTAL;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import br.com.sistemasDigitais.controller.ArduinoConexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class InterfaceArduino extends JFrame {
	
	private JTextField textFieldPortaArduino;
	private JTextField textFieldEntradaDeTexto;
	private JButton btnEnviarDados;
	
	private ArduinoConexao arduinoConexao;
	
	public static void inicializaTela() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InterfaceArduino teste = new InterfaceArduino();
				teste.pack();
				teste.setVisible(true);
			}
		});
	}
	
	public InterfaceArduino() {
		configuraGridBagLayout();
		adicionaTextFieldPortaArduino();
		adicionaTextFieldParaEntradaDeTexto();
		adicionaButtonEnviarDados();
		initializeBindings();
	}
	
	private void initializeBindings() {
		btnEnviarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciaConexaoArduinoEEnviaDados();
			}
		});
	}
	
	private void iniciaConexaoArduinoEEnviaDados() {
		arduinoConexao = new ArduinoConexao(textFieldPortaArduino.getText());
		
		String textoAEnviar = textFieldEntradaDeTexto.getText();
		if(textoAEnviar.length() <= 16)
			arduinoConexao.enviarString(textoAEnviar);
	}
	
	private void configuraGridBagLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private void adicionaTextFieldPortaArduino() {
		JLabel lblPortaArduino = new JLabel("Porta Arduino:");
		GridBagConstraints gbc_lblPortaArduino = new GridBagConstraints();
		gbc_lblPortaArduino.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortaArduino.gridx = 0;
		gbc_lblPortaArduino.gridy = 0;
		getContentPane().add(lblPortaArduino, gbc_lblPortaArduino);
		
		textFieldPortaArduino = new JTextField();
		textFieldPortaArduino.setText("COM5");
		textFieldPortaArduino.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textFieldPortaArduino = new GridBagConstraints();
		gbc_textFieldPortaArduino.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPortaArduino.anchor = GridBagConstraints.WEST;
		gbc_textFieldPortaArduino.gridx = 1;
		gbc_textFieldPortaArduino.gridy = 0;
		getContentPane().add(textFieldPortaArduino, gbc_textFieldPortaArduino);
		textFieldPortaArduino.setColumns(10);
	}

	private void adicionaTextFieldParaEntradaDeTexto() {
		JLabel lblTextoParaEnviar = new JLabel("Texto para enviar:");
		GridBagConstraints gbc_lblTextoParaEnviar = new GridBagConstraints();
		gbc_lblTextoParaEnviar.insets = new Insets(0, 0, 5, 5);
		gbc_lblTextoParaEnviar.gridx = 0;
		gbc_lblTextoParaEnviar.gridy = 1;
		getContentPane().add(lblTextoParaEnviar, gbc_lblTextoParaEnviar);
	}

	private void adicionaButtonEnviarDados() {
		textFieldEntradaDeTexto = new JTextField();
		GridBagConstraints gbc_textFieldEntradaDeTexto = new GridBagConstraints();
		gbc_textFieldEntradaDeTexto.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEntradaDeTexto.fill = HORIZONTAL;
		gbc_textFieldEntradaDeTexto.gridwidth = 4;
		gbc_textFieldEntradaDeTexto.gridx = 1;
		gbc_textFieldEntradaDeTexto.gridy = 1;
		getContentPane().add(textFieldEntradaDeTexto, gbc_textFieldEntradaDeTexto);
		
		btnEnviarDados = new JButton("Enviar dados");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 2;
		getContentPane().add(btnEnviarDados, gbc_btnNewButton);
	}

}
