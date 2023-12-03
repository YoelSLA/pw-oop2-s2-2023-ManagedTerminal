package search.binaryOperator;

import java.util.ArrayList;
import java.util.List;

import search.Search;
import trip.Trip;

public class And extends BinaryOperator {

	public And(Search leftClause, Search rightClause) {
		super(leftClause, rightClause);
	}

	/**
	 * Filtra una lista de viajes basándose en las condiciones especificadas por la
	 * cláusula AND.
	 *
	 * Este método compara las listas de viajes filtradas por las cláusulas
	 * izquierda y derecha de la condición AND. Retorna la intersección de estas
	 * listas, es decir, los viajes que cumplen con ambas condiciones.
	 *
	 * @param trips Una lista de viajes que se va a filtrar.
	 * @return Una lista filtrada de viajes que cumplen con las condiciones de la
	 *         cláusula AND.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		List<Trip> newTripsFiltered = new ArrayList<>();

		// Se filtra los viajes basándose en la cláusula izquierda de la condición AND.
		List<Trip> firstFiltered = this.getLeftClause().filterTrips(trips);
		newTripsFiltered.addAll(firstFiltered);

		// Se filtra los viajes basándose en la cláusula derecha de la condición AND.
		List<Trip> secondFiltered = this.getRightClause().filterTrips(trips);

		// Se obtiene la intersección de las listas filtradas.
		List<Trip> intersection = new ArrayList<>(newTripsFiltered);
		intersection.retainAll(secondFiltered);

		return intersection;
	}

}
