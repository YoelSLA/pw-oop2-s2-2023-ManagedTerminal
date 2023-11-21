package filteredSearch.criteria;

import java.util.List;

import filteredSearch.Filter;
import maritimeCircuit.MaritimeCircuit;

public interface Criteria extends Filter{
	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits);
}
