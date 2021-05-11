package br.com.leterio.modulo01.atv03;

public final class Passeio extends Veiculo {
	private int qtdePassageiros;

	public Passeio() {
		super();
		this.qtdePassageiros = 0;
	}

	@Override
	public float calcVel(float velocMax) {
		return velocMax * 1000;
	}

	public int getQtdePassageiros() {
		return qtdePassageiros;
	}

	public void setQtdePassageiros(int qtdePassageiros) {
		this.qtdePassageiros = qtdePassageiros;
	}
	
	@Override
	protected String getVelocMaxUnit() {
		return "m/h";
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();

		toStringBuilder
				.append("Quantidade de passageiros: ")
				.append(getQtdePassageiros())
				.append(System.lineSeparator());

		toStringBuilder.append(super.toString());

		return toStringBuilder.toString();
	}
}
