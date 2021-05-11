package br.com.leterio.modulo01.atv04;

public final class Carga extends Veiculo implements Calc {
	private int tara;
	private int cargaMax;

	public Carga() {
		super();
		this.tara = 0;
		this.cargaMax = 0;
	}

	@Override
	public float calcVel(float velocMax) {
		return velocMax * 100000;
	}

	@Override
	protected String getVelocMaxUnit() {
		return "Cm/h";
	}

	@Override
	public int calcular() {
		return (int) (tara + cargaMax + getVelocMax() + getMotor().getPotencia() + getMotor().getQtdPist());
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

		toStringBuilder
				.append("calcular: ")
				.append(calcular())
				.append(System.lineSeparator());

		toStringBuilder.append(super.toString());

		return toStringBuilder.toString();
	}
}
