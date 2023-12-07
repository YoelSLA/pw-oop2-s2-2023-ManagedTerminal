package routing;

import java.util.List;

import stretch.Stretch;

public class ShorterTime extends Routing {

	@Override
	protected Double calculateRouting(List<Stretch> stretchs) {
		return stretchs.stream().mapToDouble(s -> s.getTime().toSeconds()).sum();
	}

}
