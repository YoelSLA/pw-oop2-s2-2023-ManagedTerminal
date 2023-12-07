package terminal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Client;
import load.Dry;
import load.Load;
import order.ExportOrder;
import search.binaryOperator.And;
import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;
import search.selection.selectionDate.DepartureDate;
import service.Washed;
import trip.Trip;

class ServiceForClientsTest extends ManagedTerminalTest {

	private Dry dry;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------
	private ArrivalDate arrivalDate;
	// ------------------------------------------------------------
	private DestinationTerminal destinationTerminal;
	// ------------------------------------------------------------
	private DepartureDate departureDate;
	// ------------------------------------------------------------
	private And and;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		dry = mock(Dry.class);
		// ------------------------------------------------------------------------------------------
		exportOrder = spy(new ExportOrder(ivan, tripOne, dry, lima, alberto, volvo));
		// ------------------------------------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), lima); // 10-11-23
		// ------------------------------------------------------------------------------------------
		departureDate = new DepartureDate(Criteria.EQUALS, LocalDate.of(2023, Month.NOVEMBER, 12), buenosAires); // 12-11-23
		// ------------------------------------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(guayaquil);
		// ------------------------------------------------------------------------------------------
		and = new And(destinationTerminal, departureDate);
		// ------------------------------------------------------------------------------------------
		buenosAires.registerTruckTransportCompany(transportVesprini);
	}

	@Test
	void searchTrips_FromBuenosAiresWithSpecificCriteria_ReturnsExpectedTrips() throws Exception {
		
		// Set Up
		configureSimulatedTrips();
		
		// Criterio de busqueda compuesto (AND)
		And andTwo = new And(destinationTerminal, departureDate);
		and.setLeftClause(arrivalDate);
		and.setRightClause(andTwo);
		
		// Assert
		assertEquals(List.of(tripOne), buenosAires.searchTrips(andTwo));
	}

	@Test
	void bestCircuitFor_FromBuenosAiresToLima_ReturnsOptimalMaritimeCircuit() throws Exception {
		
		// Set Up
		configureSimulatedTrips();
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(true);
		
		// Assert
		assertEquals(maritimeCircuitOne, buenosAires.bestCircuitFor(lima));
	}

	@Test
	void testHireWashedService_AddsServiceToExportOrder() throws Exception {
		
		// Set Up
		buenosAires.hireExportService(exportOrder);
		
		// Excercise
		buenosAires.hireWashedServiceFor(dry, ivan);
		
		// Assert
		assertTrue("La orden de exportaci�n contiene el servicio de lavado.",
				exportOrder.getServices().stream().anyMatch(Washed.class::isInstance));

	}

	@Test
	void testHireWashedService_ThrowsExceptionWhenLoadNotFound() throws Exception {
		// Set Up
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireWashedServiceFor(mock(Load.class), ivan);
		}, "Order corresponding to the load and client not found.");

	}

	@Test
	void testHireWashedService_ThrowsExceptionWhenClientNotFound() throws Exception {
		// Set Up
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireWashedServiceFor(dry, mock(Client.class));
		}, "Order corresponding to the load and client not found.");

	}

	@Test
	void testNextDepartureDateTo_ReturnsNextDate_WhenTripsAvailable() {
		// Set Up
		when(apmMaersk.hasTerminal(buenosAires)).thenReturn(true);
		buenosAires.registerShippingLine(apmMaersk);

		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.getOriginTerminal()).thenReturn(buenosAires);

		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.getOriginTerminal()).thenReturn(montevideo);

		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne, mock(Trip.class), tripTwo, mock(Trip.class)));
		
		// Assert
		assertEquals(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00), buenosAires.nextDepartureDateTo(lima));

	}
	
	@Test
	void shouldThrowExceptionWhenDestinationTerminalNotFound() {
		// Set Up
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(lima)).thenReturn(false);
		when(tripOne.getOriginTerminal()).thenReturn(buenosAires);
		
		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(lima)).thenReturn(false);
		when(tripTwo.getOriginTerminal()).thenReturn(montevideo);
		
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne));
		when(apmMaersk.hasTerminal(buenosAires)).thenReturn(true);
		buenosAires.registerShippingLine(apmMaersk);

		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.nextDepartureDateTo(lima);
		}, "There is no estimated date for this destiny.");

	}

	@Test
	void testTimeItTakesToGetTo_ReturnsTime_WhenShippingLineAvailable() {
		// Set Up
		when(apmMaersk.hasTerminal(buenosAires)).thenReturn(true);
		when(apmMaersk.timeItTakesToGetTo(buenosAires, lima)).thenReturn(20);
		buenosAires.registerShippingLine(apmMaersk);
		
		
		// Assert
		assertEquals(20, buenosAires.timeItTakesToGetTo(apmMaersk, lima));

	}

	@Test
	void testTimeItTakesToGetTo_ThrowsException_WhenDestinationIsOrigin() {
		assertThrows(RuntimeException.class, () -> {
			buenosAires.timeItTakesToGetTo(apmMaersk, buenosAires);
		}, "Shipping Line not found.");
	}
	
	private void configureSimulatedTrips() throws Exception {
		
		when(apmMaersk.hasTerminal(buenosAires)).thenReturn(true);
		buenosAires.registerShippingLine(apmMaersk);
		
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.hasTerminal(guayaquil)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 12:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 10, 00)); // 13-11-23 | 10:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.

		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(false);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.
	}

}
