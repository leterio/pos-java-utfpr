package br.com.leterio.modulo01.atv04;

public abstract class Veiculo {
	private String placa;
	private String marca;
	private String modelo;
	private float velocMax;
	private Motor motor;

	public Veiculo() {
		this.placa = " ";
		this.marca = " ";
		this.modelo = " ";
		this.velocMax = 0f;
		this.motor = new Motor();
	}

	public abstract float calcVel(float velocMax);

	public String getPlaca() {
		return placa;
	}

	public final void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public final void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public final void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public float getVelocMax() {
		return velocMax;
	}

	public final void setVelocMax(float velocMax) {
		this.velocMax = velocMax;
	}

	public Motor getMotor() {
		return motor;
	}

	public final void setMotor(Motor motor) {
		this.motor = motor;
	}

	protected abstract String getVelocMaxUnit();

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();

		toStringBuilder
				.append("Placa: ")
				.append(getPlaca())
				.append(System.lineSeparator());

		toStringBuilder
				.append("Marca: ")
				.append(getMarca())
				.append(System.lineSeparator());

		toStringBuilder
				.append("Modelo: ")
				.append(getModelo())
				.append(System.lineSeparator());

		toStringBuilder
				.append("Velocidade Máxima (calcVel): ")
				.append(String.format("%.2f %s", calcVel(getVelocMax()), getVelocMaxUnit()))
				.append(System.lineSeparator());

		if (motor != null) {
			toStringBuilder
					.append("Motor: ")
					.append(System.lineSeparator())
					.append("\t")
					.append(
							motor.toString()
									.replaceAll(
											System.lineSeparator(),
											System.lineSeparator() + "\t"));
		}

		return toStringBuilder.toString();
	}
}
