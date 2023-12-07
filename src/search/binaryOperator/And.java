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
	 * Filtra una lista de viajes bas�ndose en las condiciones especificadas por la
	 * cl�usula AND.
	 *
	 * Este m�todo compara las listas de viajes filtradas por las cl�usulas
	 * izquierda y derecha de la condici�n AND. Retorna la intersecci�n de estas
	 * listas, es decir, los viajes que cumplen con ambas condiciones.
	 *
	 * @param trips Una lista de viajes que se va a filtrar.
	 * @return Una lista filtrada de viajes que cumplen con las condiciones de la
	 *         cl�usula AND.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		List<Trip> newTripsFiltered = new ArrayList<>();

		// Se filtra los viajes bas�ndose en la cl�usula izquierda de la condici�n AND.
		List<Trip> firstFiltered = this.getLeftClause().filterTrips(trips);
		newTripsFiltered.addAll(firstFiltered);

		// Se filtra los viajes bas�ndose en la cl�usula derecha de la condici�n AND.
		List<Trip> secondFiltered = this.getRightClause().filterTrips(trips);

		// Se obtiene la intersecci�n de las listas filtradas.
		List<Trip> intersection = new ArrayList<>(newTripsFiltered);
		intersection.retainAll(secondFiltered);

		return intersection;
	}

}
