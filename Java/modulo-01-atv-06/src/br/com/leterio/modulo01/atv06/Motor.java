package br.com.leterio.modulo01.atv06;

public class Motor {
	private int qtdPist;
	private int potencia;

	public Motor() {
		this.qtdPist = 0;
		this.potencia = 0;
	}

	public int getQtdPist() {
		return qtdPist;
	}

	public void setQtdPist(int qtdPist) {
		this.qtdPist = qtdPist;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();

		toStringBuilder
				.append("Quantidade de pistões: ")
				.append(getQtdPist())
				.append(System.lineSeparator());

		toStringBuilder
				.append("Potência: ")
				.append(getPotencia())
				.append(System.lineSeparator());

		return toStringBuilder.toString();
	}
}
