package terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import load.Dry;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import search.binaryOperator.And;
import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;
import search.selection.selectionDate.DepartureDate;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import turn.Turn;

class ServiceForClientsTest extends ManagedTerminal2Test {

	private Terminal guayaquil;
	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	private Stretch montevideoBuenosAires;
	private Stretch limaMontevideo;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;
	// ------------------------------------------------------------
	private Trip tripOne;
	private Trip tripTwo;
	// ------------------------------------------------------------
	private ShippingLine apmMaersk;
	private ShippingLine seaLand;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Dry dry;
	// ------------------------------------------------------------
	private Turn turnImportOrder;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------
	private ArrivalDate arrivalDate;
	private DestinationTerminal destinationTerminal;
	private DepartureDate departureDate;
	private And and;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// TERMINAL
		guayaquil = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		// STRETCH
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getPrice()).thenReturn(1.040);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getPrice()).thenReturn(2.024);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getPrice()).thenReturn(1.821);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getPrice()).thenReturn(2.192);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));

		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(montevideoBuenosAires.getPrice()).thenReturn(2.905);
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(4));

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);
		when(limaMontevideo.getPrice()).thenReturn(1.497);
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(26));
		// ------------------------------------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		// ------------------------------------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		// ------------------------------------------------------------------------------------------
		// SHIPPING LINE
		apmMaersk = mock(ShippingLine.class);
		when(apmMaersk.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitOne));
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne));

		seaLand = mock(ShippingLine.class);
		when(seaLand.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitTwo));
		when(seaLand.getTrips()).thenReturn(List.of(tripTwo));
		// ------------------------------------------------------------------------------------------
		ivan = mock(Shipper.class);
		// ------------------------------------------------------------------------------------------
		dry = mock(Dry.class);
		// ------------------------------------------------------------------------------------------
		// TURN
		turnImportOrder = mock(Turn.class);
		when(turnImportOrder.getDriver()).thenReturn(null);
		// ------------------------------------------------------------------------------------------
		// EXPORT ORDER
		exportOrder = mock(ExportOrder.class);
		when(exportOrder.getClient()).thenReturn(ivan);
		when(exportOrder.getLoad()).thenReturn(dry);
		// ------------------------------------------------------------------------------------------
		// SELECTION
		arrivalDate = new ArrivalDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), lima); // 10-11-23
		departureDate = new DepartureDate(Criteria.EQUALS, LocalDate.of(2023, Month.NOVEMBER, 12), buenosAires); // 12-11-23
		destinationTerminal = new DestinationTerminal(guayaquil);
		// AND
		and = new And(destinationTerminal, departureDate);
		// ------------------------------------------------------------------------------------------
		buenosAires.registerShippingCompany(apmMaersk);
		buenosAires.registerShippingCompany(seaLand);
	}

	/**
	 * Prueba la funcionalidad de búsqueda de viajes desde Buenos Aires utilizando
	 * un criterio específico.
	 *
	 * En este caso, se registran compañías navieras, se configuran viajes simulados
	 * y se realiza una búsqueda utilizando un criterio compuesto. El objetivo es
	 * verificar que la búsqueda devuelva los viajes esperados según el criterio.
	 *
	 * @throws Exception Si hay problemas durante la ejecución de la prueba.
	 */
	@Test
	void searchTrips_FromBuenosAiresWithSpecificCriteria_ReturnsExpectedTrips() throws Exception {
		// Set Up
		configureSimulatedTrips();
		// Criterio de búsqueda compuesto (AND)
		And andTwo = new And(destinationTerminal, departureDate);
		and.setLeftClause(arrivalDate);
		and.setRightClause(andTwo);
		// Assert
		assertEquals(List.of(tripTwo), buenosAires.searchTrips(andTwo));
	}

	/**
	 * Prueba que el método bestCircuitFor devuelve el circuito marítimo óptimo
	 * desde Buenos Aires hasta Lima.
	 *
	 * En esta prueba, se registran compañías navieras, se configuran viajes
	 * simulados y se verifica que el método bestCircuitFor devuelva el circuito
	 * marítimo esperado entre Buenos Aires y Lima.
	 *
	 * @throws Exception Si hay problemas durante la ejecución de la prueba.
	 */
	@Test
	void bestCircuitFor_FromBuenosAiresToLima_ReturnsOptimalMaritimeCircuit() throws Exception {
		// Set Up
		configureSimulatedTrips();
		// Assert
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(true);

		assertEquals(maritimeCircuitOne, buenosAires.bestCircuitFor(lima));
	}

	private void configureSimulatedTrips() throws Exception {
		// Se configura el primer viaje.
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.

		// Se configura el segundo viaje.
		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 10:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 10, 00)); // 13-11-23 | 10:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.

	}

//	@Test
//	void x() throws Exception {
//		buenosAires.hireExportService(exportOrder);
//
//		buenosAires.hireWashedServiceFor(dry, ivan);
//	}

}
