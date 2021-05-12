package br.com.leterio.modulo01.atv06;

public class VelocException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public VelocException() {
		super("A velocidade máxima está fora dos limites brasileiros");
	}
}
