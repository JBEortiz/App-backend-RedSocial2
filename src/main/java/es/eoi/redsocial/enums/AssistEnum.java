package es.eoi.redsocial.enums;

public enum AssistEnum {

	ASSIST(1, "ASSIST"), NASSIST(2, "NOTASSIST");

	private final int codigo;
	private final String tipo;

	private AssistEnum(int codigo, String tipo) {
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
