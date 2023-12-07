package search.selection;

import search.Search;
import terminal.Terminal;

public abstract class Selection implements Search {

	private Terminal terminal;

	public Selection(Terminal terminal) {
		this.terminal = terminal;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

}
