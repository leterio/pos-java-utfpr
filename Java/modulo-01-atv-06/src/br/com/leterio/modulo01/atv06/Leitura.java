package br.com.leterio.modulo01.atv06;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Leitura {
	private final Scanner scanner = new Scanner(System.in);

	public double entDadosNumericosObrigatorios(String preMessage) {
		while (true) {
			try {
				return entDadosNumericos(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lançada
			}
		}
	}

	public double entDadosNumericos(String preMessage) {
		try {
			return Double.parseDouble(entDados(preMessage));
		} catch (NumberFormatException e) {
			String message = "O valor inserido não era um número válido";
			System.err.println(message);
			throw new RuntimeException(message, e);
		}
	}

	public String entDadosObrigatorios(String preMessage) {
		while (true) {
			try {
				return entDados(preMessage);
			} catch (RuntimeException e) {
				// Descarta a RE lançada
			}
		}
	}

	public String entDados(String preMessage) {
		if (preMessage == null) {
			System.out.println("Digite o valor:");
		} else {
			System.out.println(preMessage);
		}

		try {
			String read = scanner.nextLine();
			System.err.println("[TRACE] " + read);
			return read;
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
