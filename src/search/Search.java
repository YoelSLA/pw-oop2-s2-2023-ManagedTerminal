package search;

import java.util.List;

import trip.Trip;

/**
 * Interfaz que representa un filtro. Requiere implementar el algoritmo de
 * filtro de viajes a partir de una lista de los mismos.
 * 
 * @author Gabriela Fascetta
 */
public interface Search {

	public List<Trip> filterTrips(List<Trip> trips);
}
