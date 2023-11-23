package maritimeCircuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(sections);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaritimeCircuit other = (MaritimeCircuit) obj;
		return Objects.equals(sections, other.sections);
	}
}
