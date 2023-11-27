package search.criteria;

import java.util.List;

import trip.Trip;

public interface Criteria {

	List<Trip> filterTrips(List<Trip> trips);

}
