package routing;

import java.util.List;

import stretch.Stretch;

public class FewerIntermediateTerminals extends Routing {

	@Override
	protected Double calculateRouting(List<Stretch> stretchs) {
		return (double) stretchs.stream().count();
	}

}
