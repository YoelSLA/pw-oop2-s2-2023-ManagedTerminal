package routing;

import java.util.List;

import stretch.Stretch;

public class LowerPrice extends Routing {

	@Override
	protected Double calculateRouting(List<Stretch> stretchs) {
		return stretchs.stream().mapToDouble(Stretch::getPrice).sum();
	}

}