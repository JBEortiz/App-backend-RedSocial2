package es.eoi.redsocial.enums;

public enum RelationEnum {

	FRIEND(1, "FRIEND"), PENDING(2, "PENDING");

	private final int codigo;
	private final String tipo;

	private RelationEnum(int codigo, String tipo) {
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
