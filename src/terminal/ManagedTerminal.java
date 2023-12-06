package terminal;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import bill.Bill;
import client.Client;
import client.Consignee;
import client.Shipper;
import driver.Driver;
import load.Load;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import order.ImportOrder;
import order.Order;
import orderValidation.ExportValidation;
import orderValidation.ImportValidation;
import position.Position;
import routing.Routing;
import search.Search;
import service.Electricity;
import service.ExcessStorage;
import service.Washed;
import service.Weigh;
import ship.Ship;
import shippingLine.ShippingLine;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;
import turn.Turn;

public class ManagedTerminal implements Terminal {

	private Double costPerBigLoad;
	private Double costPerKw;
	private Double costPerSmallLoad;
	private Double excessStorageCost;
	private Double weighingCost;
	private Routing routing;
	private List<Consignee> consignees;
	private List<ExportOrder> exportOrders;
	private List<ImportOrder> importOrders;
	private List<Shipper> shippers;
	private List<ShippingLine> shippingLines;
	private List<TruckTransportCompany> truckTransportCompanies;
	private List<Turn> turns;

	public ManagedTerminal(Routing routing) {
		setCostPerBigLoad(0.0);
		setCostPerKw(0.0);
		setCostPerSmallLoad(0.0);
		setExcessStorageCost(0.0);
		setWeighingCost(0.0);
		this.routing = routing;
		this.consignees = new ArrayList<Consignee>();
		this.exportOrders = new ArrayList<ExportOrder>();
		this.importOrders = new ArrayList<ImportOrder>();
		this.shippers = new ArrayList<Shipper>();
		this.shippingLines = new ArrayList<ShippingLine>();
		this.truckTransportCompanies = new ArrayList<TruckTransportCompany>();
		this.turns = new ArrayList<Turn>();
	}

	// ----------------------------------------------------------------------------------------------------
	// GETTERS
	// ----------------------------------------------------------------------------------------------------

	public Double getCostPerBigLoad() {
		return costPerBigLoad;
	}

	public Double getCostPerKw() {
		return costPerKw;
	}

	public Double getCostPerSmallLoad() {
		return costPerSmallLoad;
	}

	public Double getExcessStorageCost() {
		return excessStorageCost;
	}

	public Double getWeighingCost() {
		return weighingCost;
	}

	public Routing getRouting() {
		return routing;
	}

	public List<Consignee> getConsignees() {
		return consignees;
	}

	public List<ExportOrder> getExportOrders() {
		return exportOrders;
	}

	public List<ImportOrder> getImportOrders() {
		return importOrders;
	}

	public List<Shipper> getShippers() {
		return shippers;
	}

	public List<ShippingLine> getShippingLines() {
		return shippingLines;
	}

	public List<TruckTransportCompany> getTruckTransportCompanies() {
		return truckTransportCompanies;
	}

	public List<Turn> getTurns() {
		return turns;
	}

	@Override
	public String getName() {
		return "Puerto de Buenos Aires";
	}

	@Override
	public Position getPosition() {
		return new Position(-34.5795823299825, -58.373877081937);
	}

	// ----------------------------------------------------------------------------------------------------
	// SETTERS
	// ----------------------------------------------------------------------------------------------------

	public void setCostPerBigLoad(Double costPerBigLoad) {
		this.costPerBigLoad = costPerBigLoad;
	}

	public void setCostPerKw(Double costPerKw) {
		this.costPerKw = costPerKw;
	}

	public void setCostPerSmallLoad(Double costPerSmallLoad) {
		this.costPerSmallLoad = costPerSmallLoad;
	}

	public void setExcessStorageCost(Double excessStorageCost) {
		this.excessStorageCost = excessStorageCost;
	}

	public void setWeighingCost(Double weighingCost) {
		this.weighingCost = weighingCost;
	}

	// ----------------------------------------------------------------------------------------------------
	// SERVICE POR CLIENTS
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Encuentra y devuelve el mejor circuito marítimo desde el terminal actual
	 * hasta el terminal dado.
	 *
	 * @param terminal Terminal de destino.
	 * @return El mejor circuito marítimo desde el terminal actual hasta el terminal
	 *         dado.
	 * @throws Exception Si hay problemas durante el proceso de búsqueda del mejor
	 *                   circuito.
	 */
	public MaritimeCircuit bestCircuitFor(Terminal terminal) throws Exception {
		// Se obtiene todos los circuitos marítimos disponibles de las líneas
		// navieras.
		List<MaritimeCircuit> allCircuits = shippingLines.stream().flatMap(line -> line.getMaritimeCircuits().stream())
				.toList();
		// Se utiliza el servicio de enrutamiento para encontrar el mejor circuito entre
		// el terminal actual y el destino.
		return routing.bestCircuitBetween(this, terminal, allCircuits);
	}

	/**
	 * Calcula y devuelve la fecha de próxima salida hacia una terminal específica.
	 *
	 * @param terminal Terminal de destino.
	 * @return Fecha de próxima salida hacia la terminal especificada.
	 */
	public LocalDateTime nextDepartureDateTo(Terminal destiny) {
		return shippingLines.stream().flatMap(s -> s.getTrips().stream())
				.filter(t -> t.hasTerminal(this) && t.hasTerminal(destiny))
				.map(t -> t.calculateEstimatedArrivalDateToTerminal(this)).min(LocalDateTime::compareTo)
				.orElseThrow(() -> new RuntimeException("There is no estimated date for this destiny."));
	}

	/**
	 * Busca y filtra los viajes disponibles según los criterios proporcionados en
	 * la búsqueda.
	 *
	 * @param search Objeto de búsqueda que contiene los criterios de búsqueda.
	 * @return Lista de viajes filtrados según los criterios de búsqueda.
	 */
	public List<Trip> searchTrips(Search search) {
		// Se obtiene todos los viajes de cada una de las líneas navieras.
		List<Trip> allTrips = shippingLines.stream().flatMap(s -> s.getTrips().stream()).toList();

		// Se filtra los viajes según los criterios de búsqueda.
		return search.filterTrips(allTrips);
	}

	/**
	 * Calcula y devuelve el tiempo estimado que tomará llegar a una terminal de
	 * destino específica desde el inicio de una línea naviera.
	 *
	 * @param shippingLine Línea naviera desde la cual se inicia el viaje.
	 * @param destiny      Terminal de destino.
	 * @return Tiempo estimado en horas para llegar a la terminal de destino desde
	 *         el inicio de la línea naviera.
	 */
	public Integer timeItTakesToGetTo(ShippingLine shippingLine, Terminal destiny) {
		if (!shippingLines.contains(shippingLine)) {
			throw new RuntimeException("Shipping Line not found.");
		}
		return shippingLine.timeItTakesToGetTo(this, destiny);
	}

	/**
	 * Contrata el servicio de lavado para una carga específica y un cliente en la
	 * terminal gestionada.
	 *
	 * @param load   Carga para la cual se contratará el servicio de lavado.
	 * @param client Cliente para el cual se contratará el servicio de lavado.
	 */
	public void hireWashedServiceFor(Load load, Client client) {
		// Se crea una lista con todas las órdenes de la terminal gestionada.
		List<Order> orders = new ArrayList<>();
		orders.addAll(exportOrders);
		orders.addAll(importOrders);
		// Se busca la orden correspondiente para la carga y el cliente.
		Order order = orders.stream().filter(o -> o.getClient().equals(client) && o.getLoad().equals(load)).findFirst()
				.orElseThrow(
						() -> new IllegalArgumentException("Order corresponding to the load and client not found."));
		// Se registra el servicio de lavado en la orden.
		order.registerService(new Washed(costPerBigLoad, costPerSmallLoad));
	}

	// ----------------------------------------------------------------------------------------------------
	// PROCESS OF EXPORT ORDER
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Contrata el servicio de exportación para una orden de exportación específica.
	 *
	 * @param exportOrder Orden de exportación para la cual se contrata el servicio.
	 */
	public void hireExportService(ExportOrder exportOrder) {
		// Se verifica si el shipper está registrado en la terminal
		// gestionada; caso contrario, registrarlo.
		registerShipperIfNew((Shipper) exportOrder.getClient());
		// Se valida que el chofer y el camión estén registrados en la terminal
		// gestionada.
		ExportValidation.validateOrderInTerminal(this, exportOrder);
		// Se asigna una fecha al turno del shipper.
		setTurnDate(exportOrder, -6);
		// Se registra la orden de exportación en la terminal gestionada.
		exportOrders.add(exportOrder);
	}

	/**
	 * Registra la llegada de un camión con carga para una orden de exportación
	 * específica.
	 *
	 * @param exportOrder   Orden de exportación para la cual se registra la llegada
	 *                      del camión.
	 * @param driver        Chofer que realiza la llegada del camión.
	 * @param truck         Camión que llega con la carga.
	 * @param dateToArrival Fecha en la que se realiza la llegada del camión.
	 */
	public void truckArrivedWithLoad(ExportOrder exportOrder, Driver driver, Truck truck, LocalDateTime dateToArrival) {
		// Se valida que la orden sea valida para la terminal gestionada.
		ExportValidation.runFullOrderValidations(this, exportOrder, driver, truck, dateToArrival);
		// Se agrega el servicio de pesado a la orden.
		registerWeighService(exportOrder);
		// Se agrega el servicio eléctrico a la orden solamente si contiene una carga
		// Reefer.
		registerStartElectricityService(List.of(exportOrder), dateToArrival);
	}

	// ------------------------------------------------------------
	// PROCESS OF IMPORT ORDER
	// ------------------------------------------------------------

	/**
	 * Contrata el servicio de importación para una orden de importación específica.
	 *
	 * @param importOrder Orden de importación para la cual se contrata el servicio.
	 * @throws Exception
	 */
	public void hireImportService(ImportOrder importOrder) throws Exception {
		// Se verifica si el consignee está registrado en la terminal
		// gestionada; caso contrario, registrarlo.
		registerConsigneeIfNew((Consignee) importOrder.getClient());
		// Se valida que el chofer y el camión estén registrados en la terminal
		// gestionada.
		ExportValidation.validateOrderInTerminal(this, importOrder);
		// Se asigna una fecha al turno del consignee.
		setTurnDate(importOrder, 6);
		// Se registra la orden de importación en la terminal gestionada.
		importOrders.add(importOrder);
	}

	/**
	 * Registra la salida del camión con una carga para una orden de importación
	 * específica.
	 *
	 * @param importOrder   Orden de importación para la cual se registra la salida
	 *                      del camión.
	 * @param driver        Chofer que realiza la salida del camión.
	 * @param truck         Camión que sale con la carga.
	 * @param dateToArrival Fecha en la que se realiza la salida del camión.
	 */
	public void truckLeaveWithLoad(ImportOrder importOrder, Driver driver, Truck truck, LocalDateTime dateToArrival) {
		// Se valida que la orden de exportación sea valida para la terminal
		// gestionada.
		ImportValidation.runFullOrderValidations(this, importOrder, driver, truck);
		// Se registra el servicio de exceso de almacenamiento si corresponde.
		registerExcessStorageService(importOrder, dateToArrival);
		// Se registra el fin del servicio de electricidad, solo para órdenes con
		// carga Reefer.
		registerEndOfElectricityService(List.of(importOrder), dateToArrival);
		// Se envia la facturación con el desglose de los servicios y la
		// facturación del viaje al consignee.
		sendBillingToClient(importOrder, dateToArrival);
	}

	// ------------------------------------------------------------
	// SHIP COMMMUNICATION
	// ------------------------------------------------------------

	/**
	 * Notifica la llegada inminente de un buque a la terminal gestionada. Realiza
	 * acciones asociadas a la llegada inminente del buque (por implementar).
	 *
	 * @param ship Buque que está por llegar a la terminal gestionada.
	 */
	@Override
	public void notifyShipInminentArrival(Ship ship) {
		sendShipProximityNotifications(ship);
	}

	/**
	 * Notifica la llegada de un buque a la terminal gestionada. Realiza acciones
	 * asociadas a la llegada del buque, como registrar servicios eléctricos,
	 * establecer fechas de turno y finalizar servicios eléctricos para órdenes de
	 * importación y exportación.
	 *
	 * @param ship Buque que ha llegado a la terminal gestionada.
	 */
	@Override
	public void notifyShipArrival(Ship ship) {
		// Se le da la orden al buque de empezar el trabajo.
		ship.startWorking();
		// Se envia la notifacación a todos los clientes que su buque ha llegado.
		sendArrivalNotificationsToClients(ship, importOrders);
		// Se registra la fecha de finalización del servicio eléctrico para órdenes
		// de exportación que contengan cargas Reefer.
		registerEndOfElectricityService(exportOrders, calculateEstimatedArrivalDateToManagedTerminal(ship.getTrip()));
		// Se registra la fecha de inicio servicio eléctrico para órdenes de
		// importación que contengan cargas Reefer.
		registerStartElectricityService(importOrders, calculateEstimatedArrivalDateToManagedTerminal(ship.getTrip()));
		// Se le da la orden de partida al buque.
		ship.depart();
	}

	/**
	 * Notifica la partida de un buque de la terminal gestionada. Realiza acciones
	 * asociadas a la partida del buque, como enviar facturas a clientes de órdenes
	 * de exportación y notificar a los consignees sobre la partida del barco.
	 *
	 * @param ship Buque que ha partido de la terminal gestionada.
	 */
	@Override
	public void notifyShipDeparture(Ship ship) {
		// Se envia la factura con los gastos a todos los shippers con ese viaje.
		sendInvoicesForExportOrders(ship);
	}

	// ------------------------------------------------------------
	// PUBLIC METHODS
	// ------------------------------------------------------------

	public void registerConsignee(Consignee consignee) {
		consignees.add(consignee);
	}

	public void registerShipper(Shipper shipper) {
		shippers.add(shipper);
	}

	public void registerShippingLine(ShippingLine shippingLine) {
		if(shippingLine.hasTerminal(this)) {
			shippingLines.add(shippingLine);
		}
	}

	public void registerTruckTransportCompany(TruckTransportCompany truckTransportCompany) {
		truckTransportCompanies.add(truckTransportCompany);
	}

	/**
	 * Verifica si un conductor está registrado en alguna de las compañías de
	 * transporte.
	 *
	 * @param driver Conductor a verificar.
	 * @return `true` si el conductor está registrado, `false` de lo contrario.
	 */
	public boolean isDriverRegistered(Driver driver) {
		return getAllDistinctDriversFromTransportCompanies().contains(driver);
	}

	/**
	 * Verifica si un camión está registrado en alguna de las compañías de
	 * transporte.
	 *
	 * @param truck Camión a verificar.
	 * @return `true` si el camión está registrado, `false` de lo contrario.
	 */
	public boolean isTruckRegistered(Truck truck) {
		return getAllDistinctTrucksFromTransportCompanies().contains(truck);
	}

	// ------------------------------------------------------------
	// PRIVATE METHODS
	// ------------------------------------------------------------

	/**
	 * Envia notificaciones por correo electrónico a los clientes cuyas órdenes
	 * están asociadas al buque especificado, informándoles que el buque está cerca.
	 *
	 * @param ship Buque que está cerca de la terminal gestionada.
	 */
	private void sendShipProximityNotifications(Ship ship) {
		// Se crea una lista que contenga todas las órdenes, tanto de exportación como
		// de importación.
		List<Order> orders = new ArrayList<>();
		orders.addAll(exportOrders);
		orders.addAll(importOrders);
		// Se filtran las órdenes que tienen en comun el mismo viaje asociado.
		List<Order> shipRelatedOrders = orders.stream().filter(o -> o.getTrip().equals(ship.getTrip())).toList();
		// Se envia una notificación por mail al cliente de cada orden relacionada con
		// el buque.
		shipRelatedOrders.forEach(
				order -> order.getClient().sendMailAboutShipInminentArrival(this, order.getClient(), "Ship is near"));
	}

	/**
	 * Envia notificaciones de llegada a los clientes asociados a las órdenes
	 * especificadas que están relacionadas con el buque especificado.
	 *
	 * @param ship   Buque que ha llegado a la terminal gestionada.
	 * @param orders Lista de órdenes para las cuales se enviarán las notificaciones
	 *               de llegada.
	 */
	private void sendArrivalNotificationsToClients(Ship ship, List<? extends Order> orders) {
		orders.stream().filter(order -> order.getTrip().equals(ship.getTrip()))
				.forEach(t -> sendArrivalNotificationToClient(t));
	}

	/**
	 * Envia una notificación de llegada a un cliente específico asociado a una
	 * orden.
	 *
	 * @param order Orden para la cual se enviará la notificación de llegada.
	 * @throws Exception
	 */
	private void sendArrivalNotificationToClient(Order order) {
		Client client = order.getClient();
		LocalDateTime arrivalDate = order.getTrip().calculateEstimatedArrivalDateToTerminal(this);
		client.sendMailAboutShipArrival(this, client, arrivalDate.toString());
	}

	/**
	 * Envía facturas a clientes de órdenes de exportación asociadas al buque que ha
	 * partido.
	 *
	 * @param ship Buque que ha partido de la terminal gestionada.
	 */
	private void sendInvoicesForExportOrders(Ship ship) {
		List<ExportOrder> filteredOrders = exportOrders.stream().filter(e -> e.getTrip().equals(ship.getTrip()))
				.toList();
		filteredOrders.forEach(e -> sendBillingToClient(e, e.departureDate()));
	}

	/**
	 * Envía la facturación para una orden de importación específica.
	 *
	 * @param importOrder Orden de importación para la cual se enviará la
	 *                    facturación.
	 */
	private void sendBillingToClient(Order order, LocalDateTime dateToArrival) {
		order.getClient().sendMailAboutBill(this, order.getClient(), new Bill(dateToArrival, order));
	}

	/**
	 * Registra el servicio de almacenamiento excedido para una orden específica si
	 * ha excedido las 24 horas en la terminal gestionada.
	 *
	 * @param order         Orden para la cual se registrará el servicio de
	 *                      almacenamiento excedido.
	 * @param dateToArrival Fecha estimada de llegada a la terminal gestionada.
	 */
	private void registerExcessStorageService(Order order, LocalDateTime dateToArrival) {
		// Se busca el turno que esta asociado a la orden.
		Turn turn = turns.stream().filter(t -> t.getOrder().equals(order)).findFirst().orElse(null);
		// Se calcula las horas que estuvo la carga en la terminal gestionada.
		Long hoursInTerminal = Math.abs(Duration.between(turn.getDate(), dateToArrival).toHours());
		// Si superó las 24 horas, se le añade le servicio a la orden.
		if (hoursInTerminal.intValue() > 24) {
			// Se calcula la cantidad de horas de almacenamiento excedido.
			Integer excessHours = (int) (hoursInTerminal - 24);
			// Se le agrega el servicio de almacenamiento excedido a la orden.
			order.registerService(new ExcessStorage(excessStorageCost, excessHours));
		}
	}

	/**
	 * Registra el servicio de electricidad al inicio para órdenes.
	 *
	 * @param orders          Lista de órdenes.
	 * @param arrivalDateShip Fecha de llegada del buque a la terminal gestionada.
	 */
	private void registerStartElectricityService(List<? extends Order> orders, LocalDateTime arrivalDateShip) {
		List<? extends Order> ordersFiltered = orders.stream().filter(o -> o.getLoad().consumesElectricity()).toList();
		ordersFiltered.forEach(o -> registerElectricityService(o, costPerKw, arrivalDateShip));
	}

	/**
	 * Registra el fin del servicio de electricidad para una lista de órdenes.
	 *
	 * @param orders           Lista de órdenes para las cuales se registrará el fin
	 *                         del servicio de electricidad.
	 * @param dateToEndService Fecha y hora para establecer como fin del servicio de
	 *                         electricidad.
	 */
	private void registerEndOfElectricityService(List<? extends Order> orders, LocalDateTime dateToEndService) {
		List<Electricity> electricitys = orders.stream().flatMap(o -> o.getServices().stream())
				.filter(s -> s.isElectricyService()).map(s -> (Electricity) s).toList();

		electricitys.forEach(e -> e.setEndConnection(dateToEndService));
	}

	/**
	 * Registra el servicio de electricidad para una orden específica.
	 *
	 * @param order           Orden para la cual se registrará el servicio de
	 *                        electricidad.
	 * @param costPerKw       Costo por kilovatio para el servicio de electricidad.
	 * @param arrivalDateShip Fecha de llegada del buque a la terminal gestionada.
	 */
	private <T extends Order> void registerElectricityService(Order order, Double costPerKw,
			LocalDateTime arrivalDateShip) {
		order.registerService(new Electricity(costPerKw, arrivalDateShip));
	}

	/**
	 * Registra el servicio de pesaje para una orden específica.
	 *
	 * @param order Orden para la cual se registrará el servicio de pesaje.
	 */
	private void registerWeighService(Order order) {
		order.registerService(new Weigh(weighingCost));
	}

	/**
	 * Obtiene una lista de todos los conductores distintos de las compañías de
	 * transporte.
	 *
	 * @return Lista de conductores distintos.
	 */
	private List<Driver> getAllDistinctDriversFromTransportCompanies() {
		return truckTransportCompanies.stream().flatMap(t -> t.getDrivers().stream()).distinct().toList();
	}

	/**
	 * Obtiene una lista de todos los camiones distintos de las compañías de
	 * transporte.
	 *
	 * @return Lista de camiones distintos.
	 */
	private List<Truck> getAllDistinctTrucksFromTransportCompanies() {
		return truckTransportCompanies.stream().flatMap(t -> t.getTrucks().stream()).distinct().toList();
	}

	/**
	 * Calcula y devuelve la fecha estimada de llegada de un viaje a la terminal
	 * gestionada.
	 *
	 * @return La fecha estimada de llegada del viaje a la terminal gestionada.
	 * @throws Exception
	 */
	private LocalDateTime calculateEstimatedArrivalDateToManagedTerminal(Trip trip) {
		return trip.calculateEstimatedArrivalDateToTerminal(this);
	}

	private void registerShipperIfNew(Shipper shipper) {
		if (!shippers.contains(shipper)) {
			shippers.add(shipper);
		}
	}

	private void registerConsigneeIfNew(Consignee consignee) {
		if (!consignees.contains(consignee)) {
			consignees.add(consignee);
		}
	}

	public Turn findTurnByOrder(Order order) {
		return turns.stream().filter(t -> t.getOrder().equals(order)).findFirst()
				.orElseThrow(() -> new RuntimeException("Turn not found."));
	}

	/**
	 * Establece la fecha estimada para el turno, basándose en la llegada del buque
	 * a la terminal gestionada y ajustando la fecha en función de un delta de
	 * horas.
	 *
	 * @param order      Orden (exportación o importación) para la cual se
	 *                   establecerá la fecha del turno.
	 * @param hoursToAdd Número de horas que se agregarán a la llegada del buque
	 *                   para obtener la fecha del turno.
	 * @throws Exception Si hay algún problema en el cálculo de la fecha.
	 */
	private void setTurnDate(Order order, Integer hoursToAdd) {
		LocalDateTime estimatedArrival = calculateEstimatedArrivalDateToManagedTerminal(order.getTrip());
		LocalDateTime time = estimatedArrival.plus(hoursToAdd, ChronoUnit.HOURS);
		turns.add(new Turn(time, order));
	}

}
