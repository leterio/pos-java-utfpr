package br.com.leterio.modulo01.atv06;

public class BDVeiculoCapacityException extends Exception {
	private static final long serialVersionUID = 1L;

	public BDVeiculoCapacityException() {
		super("Capacidade de veículos atingida");
	}
}
