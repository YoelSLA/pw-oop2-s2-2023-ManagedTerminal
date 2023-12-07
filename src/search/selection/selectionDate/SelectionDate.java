package search.selection.selectionDate;

import java.time.LocalDate;
import java.util.List;

import search.criteria.Criteria;
import search.selection.Selection;
import terminal.Terminal;
import trip.Trip;

public abstract class SelectionDate extends Selection {

	private Criteria criteria;
	private LocalDate searchDate;

	public SelectionDate(Criteria criteria, LocalDate searchDate, Terminal terminal) {
		super(terminal);
		this.criteria = criteria;
		this.searchDate = searchDate;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public LocalDate getSearchDate() {
		return searchDate;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public void setSearchDate(LocalDate searchDate) {
		this.searchDate = searchDate;
	}

	/**
	 * Calcula y devuelve la fecha de llegada estimada de un viaje a una terminal
	 * espec�fica.
	 *
	 * @param trip El viaje para el cual se calcular� la fecha de llegada estimada.
	 * @return La fecha de llegada estimada del viaje a la terminal.
	 * @throws Exception
	 */
	protected LocalDate calculateArrivalDate(Trip trip) {
		return trip.calculateEstimatedArrivalDateToTerminal(getTerminal()).toLocalDate();
	}

	/**
	 * Realiza una comparaci�n de fechas basada en un criterio espec�fico.
	 * 
	 * @param date Fecha a comparar.
	 * @return true si la comparaci�n seg�n el criterio es verdadera, false de lo
	 *         contrario.
	 */
	protected Boolean searchByCriteriaTo(LocalDate searchDate) {
		switch (criteria) {
		case GREATHER_THAN:
			return this.searchDate.isBefore(searchDate);
		case EQUALS:
			return this.searchDate.equals(searchDate);
		default:
			return this.searchDate.isAfter(searchDate);
		}
	}

}
