package br.com.leterio.modulo01.atv06;

public class VeicExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public VeicExistException() {
		super("J� existe um ve�culo com esta placa");
	}
}
