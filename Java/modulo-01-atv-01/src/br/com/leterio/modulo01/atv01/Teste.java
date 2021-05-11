package br.com.leterio.modulo01.atv01;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Teste {
	private static final int COUNT_VEICULOS = 5;
	private static final List<Veiculo> VEICULOS = new ArrayList<>();

	private static Scanner scanner;

	public static void main(String[] args) {
		System.out.println("Ol�, bem-vindo ao teste da rela��o entre as classes Ve�culo e Motor.");
		System.out.println("Esta � a classe de testes.");

		scanner = new Scanner(System.in);
		menu();
		scanner.close();
	}

	// Menu

	private static void menu() {
		menuloop: while (true) {
			System.out.println("\nSelecione uma op��o:");
			System.out.println("1 - Cadastrar novo ve�culo");
			System.out.println("2 - Listar Ve�culos");
			System.out.println("3 - Remover Ve�culo");
			System.out.println("4 - Sair");

			switch ((int) readRequiredUserInputAsNumeric("> Digite a sua op��o:") /* Apenas a parte inteira */) {
				case 1:
					cadastrarNovoVeiculo();
					break;
				case 2:
					listVeiculos();
					break;
				case 3:
					removeVeiculo();
					break;
				case 4:
					System.out.println("Obrigado e at� logo ;D");
					break menuloop;
				default:
					System.err.println("Voc� selecionou uma op��o inv�lida.");
			}
		}
	}

	// Cadastro do ve�culo

	public static void cadastrarNovoVeiculo() {
		if (VEICULOS.size() >= COUNT_VEICULOS) {
			System.err.println("O limite de ve�culos cadastrados j� foi atingido.\nRemova alguma entrada antes de cadastrar uma nova.");
			return;
		}

		Veiculo veiculo = new Veiculo();
		populateVeiculoData(veiculo);
		VEICULOS.add(veiculo);
	}

	private static void populateVeiculoData(Veiculo veiculo) {
		System.out.println("\nDigite os dados do novo ve�culo:");

		veiculo.setPlaca(readRequiredUserInput("Digite a placa do ve�culo:"));
		veiculo.setMarca(readRequiredUserInput("Digite a marca do ve�culo:"));
		veiculo.setModelo(readRequiredUserInput("Digite o modelo do ve�culo:"));
		veiculo.setVelocMax((float) readRequiredUserInputAsNumeric("Digite a velocidade m�xima do ve�culo:"));
		veiculo.setMotor(new Motor());
		veiculo.getMotor().setQtdPist((int) readRequiredUserInputAsNumeric("Digite a quantiadade de pist�es do motor:"));
		veiculo.getMotor().setPotencia((int) readRequiredUserInputAsNumeric("Digite a pot�ncia do motor:"));
	}

	// Listagem de ve�culos

	private static void listVeiculos() {
		if (VEICULOS.isEmpty()) {
			System.err.println("Nenhum ve�culo est� cadastrado.");
			return;
		}

		System.out.println("\nOs seguintes ve�culos est�o cadastrados:");

		int position = 1;
		for (Veiculo veiculo : VEICULOS) {
			printVeiculo(veiculo, position++);
		}
	}

	// Remo��o de ve�culo

	private static void removeVeiculo() {
		if (VEICULOS.isEmpty()) { // fail fast
			System.err.println("Nenhum ve�culo est� cadastrado");
			return;
		}

		int indexToBeRemoved = (int) readRequiredUserInputAsNumeric("\nDigite o �ndice do ve�culo a ser removido:");

		if (indexToBeRemoved <= 0) {
			System.err.println("O �ndice deve ser maior do que zero.");
			return;
		}

		if (indexToBeRemoved > VEICULOS.size()) {
			System.err.println("O �ndice digitado n�o est� presente na lista de ve�culos.");
			return;
		}

		VEICULOS.remove(indexToBeRemoved - 1);
	}

	private static void printVeiculo(Veiculo veiculo, int atPosition) {
		System.out.println("\nVe�culo no �ndice " + atPosition);
		System.out.println("\tPlaca: " + veiculo.getPlaca());
		System.out.println("\tMarca: " + veiculo.getMarca());
		System.out.println("\tModelo: " + veiculo.getModelo());
		System.out.println("\tVelocidade M�xima: " + veiculo.getVelocMax());
		if (veiculo.getMotor() != null) {
			System.out.println("\tMotor: ");
			System.out.println("\t\tQuantidade de pist�es: " + veiculo.getMotor().getQtdPist());
			System.out.println("\t\tPot�ncia: " + veiculo.getMotor().getPotencia());
		}
	}

	// Util

	private static double readRequiredUserInputAsNumeric(String preMessage) {
		while (true) {
			try {
				return readUserInputAsNumeric(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lan�ada
			}
		}
	}

	private static double readUserInputAsNumeric(String preMessage) {
		try {
			return Double.parseDouble(readUserInput(preMessage));
		} catch (NumberFormatException e) {
			String message = "O valor inserido n�o era um n�mero v�lido";
			System.err.println(message);
			throw new RuntimeException(message, e);
		}
	}

	private static String readRequiredUserInput(String preMessage) {
		while (true) {
			try {
				return readUserInput(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lan�ada
			}
		}
	}

	private static String readUserInput(String preMessage) {
		if (preMessage == null) {
			System.out.println("Digite o valor:");
		} else {
			System.out.println(preMessage);
		}

		try {
			return scanner.nextLine();
		} catch (NoSuchElementException e) {
			String message = "Digite um valor.";
			System.err.println(message);
			throw new RuntimeException(message, e);
		} catch (IllegalStateException e) {
			String message = "Ocorreu um erro ao capturar entrada do usu�rio.\nEste erro � fatal;";
			System.err.println(message);
			System.exit(1);
			throw new Error(message, e);
		}
	}
}
