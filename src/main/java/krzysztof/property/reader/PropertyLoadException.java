package krzysztof.property.reader;

class PropertyLoadException extends RuntimeException {

	private static final long serialVersionUID = -1963384420501969276L;

	public PropertyLoadException() {
	}

	PropertyLoadException(String string) {
		super(string);
	}
}
