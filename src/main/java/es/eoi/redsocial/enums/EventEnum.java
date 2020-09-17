package es.eoi.redsocial.enums;

public enum EventEnum {


	SCHEDULED(1, "SCHEDULED"), INPROGRESS(2, "IN PROGRESS"), PASSED(3, "PASSED");

	private final int codigo;
	private final String tipo;

	private EventEnum(int codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}
	
}
