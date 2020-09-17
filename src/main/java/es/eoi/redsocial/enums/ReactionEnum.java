package es.eoi.redsocial.enums;

public enum ReactionEnum {

	LIKE(1, "LIKE"), HATE(2, "HATE"), LOVE(3, "LOVE");

	private final int codigo;
	private final String tipo;

	private ReactionEnum(int codigo, String tipo) {
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
