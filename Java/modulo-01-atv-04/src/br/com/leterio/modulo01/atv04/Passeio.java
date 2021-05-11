package br.com.leterio.modulo01.atv04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Passeio extends Veiculo implements Calc {
	private int qtdePassageiros;

	public Passeio() {
		super();
		this.qtdePassageiros = 0;
	}

	@Override
	public float calcVel(float velocMax) {
		return velocMax * 1000;
	}

	@Override
	public int calcular() {
		Matcher regexMatcher = Pattern
				.compile("[a-zA-Z]+")
				.matcher(
						this.getPlaca() +
								this.getMarca() +
								this.getModelo());

		int totalLetras = 0;
		while (regexMatcher.find()) {
			totalLetras += regexMatcher.group().length();
		}

		return totalLetras;
	}

	@Override
	protected String getVelocMaxUnit() {
		return "m/h";
	}

	public int getQtdePassageiros() {
		return qtdePassageiros;
	}

	public void setQtdePassageiros(int qtdePassageiros) {
		this.qtdePassageiros = qtdePassageiros;
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();

		toStringBuilder
				.append("Quantidade de passageiros: ")
				.append(getQtdePassageiros())
				.append(System.lineSeparator());

		toStringBuilder
				.append("calcular: ")
				.append(calcular())
				.append(System.lineSeparator());

		toStringBuilder.append(super.toString());

		return toStringBuilder.toString();
	}
}
