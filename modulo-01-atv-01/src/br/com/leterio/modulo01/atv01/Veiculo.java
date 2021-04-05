package br.com.leterio.modulo01.atv01;

public class Veiculo {
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
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public float getVelocMax() {
		return velocMax;
	}

	public void setVelocMax(float velocMax) {
		this.velocMax = velocMax;
	}

	public Motor getMotor() {
		return motor;
	}

	public void setMotor(Motor motor) {
		this.motor = motor;
	}
}
