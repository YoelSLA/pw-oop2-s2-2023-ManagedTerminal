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
	 * Filtra una lista de viajes bas�ndose en las condiciones especificadas por la
	 * cl�usula OR.
	 *
	 * Este m�todo combina los resultados de filtrar los viajes seg�n las cl�usulas
	 * izquierda y derecha de la condici�n OR. La lista resultante contiene
	 * �nicamente viajes distintos.
	 *
	 * @param trips Una lista de viajes que se va a filtrar.
	 * @return Una lista filtrada de viajes que cumplen con las condiciones de la
	 *         cl�usula OR.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		// Se filtran los viajes bas�ndose en la cl�usula izquierda de la condici�n OR.
		List<Trip> leftClause = this.getLeftClause().filterTrips(trips);

		// Se filtran los viajes bas�ndose en la cl�usula derecha de la condici�n OR.
		List<Trip> rightClause = this.getRightClause().filterTrips(trips);

		// Se combinan los resultados de las cl�usulas izquierda y derecha, asegurando
		// viajes distintos.
		return Stream.concat(leftClause.stream(), rightClause.stream()).distinct().toList();
	}

}
