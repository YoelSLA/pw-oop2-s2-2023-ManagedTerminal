package terminal;

public abstract class Terminal {

	private String name;

	public Terminal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
