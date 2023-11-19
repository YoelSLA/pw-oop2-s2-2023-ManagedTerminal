package maritimeCircuit;

import java.util.ArrayList;
import java.util.List;

import section.Section;
import terminal.Terminal;

public class MaritimeCircuit {

	private List<Section> sections;

	public MaritimeCircuit() {
		this.sections = new ArrayList<Section>();
	}

	public void addSection(Section section) {
		sections.add(section);
	}

	public Double getPrice() {
		return sections.stream().mapToDouble(Section::getPrice).sum();
	}

	public List<Section> getSections() {
		return sections;
	}

	public boolean itHasASectionWhereItIs(Terminal terminal) {
		return sections.stream().anyMatch(section -> section.isItHasATerminal(terminal));
	}

}
