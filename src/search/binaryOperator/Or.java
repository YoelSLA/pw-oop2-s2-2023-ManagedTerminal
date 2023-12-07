package search.binaryOperator;

import java.util.List;
import java.util.stream.Stream;

import search.Search;
import trip.Trip;

public class Or extends BinaryOperator {

	public Or(Search leftClause, Search rightClause) {
		super(leftClause, rightClause);
	}

	/**
	 * Filtra una lista de viajes basándose en las condiciones especificadas por la
	 * cláusula OR.
	 *
	 * Este método combina los resultados de filtrar los viajes según las cláusulas
	 * izquierda y derecha de la condición OR. La lista resultante contiene
	 * únicamente viajes distintos.
	 *
	 * @param trips Una lista de viajes que se va a filtrar.
	 * @return Una lista filtrada de viajes que cumplen con las condiciones de la
	 *         cláusula OR.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		// Se filtran los viajes basándose en la cláusula izquierda de la condición OR.
		List<Trip> leftClause = this.getLeftClause().filterTrips(trips);

		// Se filtran los viajes basándose en la cláusula derecha de la condición OR.
		List<Trip> rightClause = this.getRightClause().filterTrips(trips);

		// Se combinan los resultados de las cláusulas izquierda y derecha, asegurando
		// viajes distintos.
		return Stream.concat(leftClause.stream(), rightClause.stream()).distinct().toList();
	}

}
