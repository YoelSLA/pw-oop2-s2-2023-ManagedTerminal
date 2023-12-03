package search;

import java.util.List;

import trip.Trip;

public interface Search {

	public List<Trip> filterTrips(List<Trip> trips);
}
