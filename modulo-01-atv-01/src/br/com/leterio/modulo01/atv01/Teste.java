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
		System.out.println("Olá, bem-vindo ao teste da relação entre as classes Veículo e Motor.");
		System.out.println("Esta é a classe de testes.");

		scanner = new Scanner(System.in);
		menu();
		scanner.close();
	}

	// Menu

	private static void menu() {
		menuloop: while (true) {
			System.out.println("\nSelecione uma opção:");
			System.out.println("1 - Cadastrar novo veículo");
			System.out.println("2 - Listar Veículos");
			System.out.println("3 - Remover Veículo");
			System.out.println("4 - Sair");

			switch ((int) readRequiredUserInputAsNumeric("> Digite a sua opção:") /* Apenas a parte inteira */) {
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
					System.out.println("Obrigado e até logo ;D");
					break menuloop;
				default:
					System.err.println("Você selecionou uma opção inválida.");
			}
		}
	}

	// Cadastro do veículo

	public static void cadastrarNovoVeiculo() {
		if (VEICULOS.size() >= COUNT_VEICULOS) {
			System.err.println("O limite de veículos cadastrados já foi atingido.\nRemova alguma entrada antes de cadastrar uma nova.");
			return;
		}

		Veiculo veiculo = new Veiculo();
		populateVeiculoData(veiculo);
		VEICULOS.add(veiculo);
	}

	private static void populateVeiculoData(Veiculo veiculo) {
		System.out.println("\nDigite os dados do novo veículo:");

		veiculo.setPlaca(readRequiredUserInput("Digite a placa do veículo:"));
		veiculo.setMarca(readRequiredUserInput("Digite a marca do veículo:"));
		veiculo.setModelo(readRequiredUserInput("Digite o modelo do veículo:"));
		veiculo.setVelocMax((float) readRequiredUserInputAsNumeric("Digite a velocidade máxima do veículo:"));
		veiculo.setMotor(new Motor());
		veiculo.getMotor().setQtdPist((int) readRequiredUserInputAsNumeric("Digite a quantiadade de pistões do motor:"));
		veiculo.getMotor().setPotencia((int) readRequiredUserInputAsNumeric("Digite a potência do motor:"));
	}

	// Listagem de veículos

	private static void listVeiculos() {
		if (VEICULOS.isEmpty()) {
			System.err.println("Nenhum veículo está cadastrado.");
			return;
		}

		System.out.println("\nOs seguintes veículos estão cadastrados:");

		int position = 1;
		for (Veiculo veiculo : VEICULOS) {
			printVeiculo(veiculo, position++);
		}
	}

	// Remoção de veículo

	private static void removeVeiculo() {
		if (VEICULOS.isEmpty()) { // fail fast
			System.err.println("Nenhum veículo está cadastrado");
			return;
		}

		int indexToBeRemoved = (int) readRequiredUserInputAsNumeric("\nDigite o índice do veículo a ser removido:");

		if (indexToBeRemoved <= 0) {
			System.err.println("O índice deve ser maior do que zero.");
			return;
		}

		if (indexToBeRemoved > VEICULOS.size()) {
			System.err.println("O índice digitado não está presente na lista de veículos.");
			return;
		}

		VEICULOS.remove(indexToBeRemoved - 1);
	}

	private static void printVeiculo(Veiculo veiculo, int atPosition) {
		System.out.println("\nVeículo no índice " + atPosition);
		System.out.println("\tPlaca: " + veiculo.getPlaca());
		System.out.println("\tMarca: " + veiculo.getMarca());
		System.out.println("\tModelo: " + veiculo.getModelo());
		System.out.println("\tVelocidade Máxima: " + veiculo.getVelocMax());
		if (veiculo.getMotor() != null) {
			System.out.println("\tMotor: ");
			System.out.println("\t\tQuantidade de pistões: " + veiculo.getMotor().getQtdPist());
			System.out.println("\t\tPotência: " + veiculo.getMotor().getPotencia());
		}
	}

	// Util

	private static double readRequiredUserInputAsNumeric(String preMessage) {
		while (true) {
			try {
				return readUserInputAsNumeric(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lançada
			}
		}
	}

	private static double readUserInputAsNumeric(String preMessage) {
		try {
			return Double.parseDouble(readUserInput(preMessage));
		} catch (NumberFormatException e) {
			String message = "O valor inserido não era um número válido";
			System.err.println(message);
			throw new RuntimeException(message, e);
		}
	}

	private static String readRequiredUserInput(String preMessage) {
		while (true) {
			try {
				return readUserInput(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lançada
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
			String message = "Ocorreu um erro ao capturar entrada do usuário.\nEste erro é fatal;";
			System.err.println(message);
			System.exit(1);
			throw new Error(message, e);
		}
	}
}
