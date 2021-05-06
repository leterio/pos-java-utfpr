package br.com.leterio.modulo01.atv03;

public final class Carga extends Veiculo {
	private int tara;
	private int cargaMax;

	public Carga() {
		super();
		this.tara = 0;
		this.cargaMax = 0;
	}

	@Override
	public float calcVel(float velocMax) {
		return velocMax;
	}

	public int getTara() {
		return tara;
	}

	public void setTara(int tara) {
		this.tara = tara;
	}

	public int getCargaMax() {
		return cargaMax;
	}

	public void setCargaMax(int cargaMax) {
		this.cargaMax = cargaMax;
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();

		toStringBuilder
				.append("Tara: ")
				.append(getTara())
				.append(System.lineSeparator());

		toStringBuilder
				.append("Carga Máxima: ")
				.append(getCargaMax())
				.append(System.lineSeparator());

		toStringBuilder.append(super.toString());

		return toStringBuilder.toString();
	}
}
