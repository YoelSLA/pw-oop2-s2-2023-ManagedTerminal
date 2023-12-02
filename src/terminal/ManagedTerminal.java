package terminal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.Position;

import bill.Bill;
import client.Client;
import client.Consignee;
import client.Shipper;
import driver.Driver;
import load.Load;
import order.ExportOrder;
import order.ImportOrder;
import order.Order;
import orderValidation.ExportValidation;
import orderValidation.ImportValidation;
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

public class ManagedTerminal implements Terminal {

	private List<Consignee> consignees;
	private List<ExportOrder> exportOrders;
	private List<ImportOrder> importOrders;
	private List<Shipper> shippers;
	private List<ShippingLine> shippingLines;
	private List<TruckTransportCompany> truckTransportCompanies;
	private Routing routing;
	private Double costPerKw;
	private Double costPerBigLoad;
	private Double costPerSmallLoad;
	private Double excessStorageCost;
	private Double weighingCost;

	public ManagedTerminal(Routing routing) {
		this.consignees = new ArrayList<Consignee>();
		this.exportOrders = new ArrayList<ExportOrder>();
		this.importOrders = new ArrayList<ImportOrder>();
		this.shippers = new ArrayList<Shipper>();
		this.shippingLines = new ArrayList<ShippingLine>();
		this.truckTransportCompanies = new ArrayList<TruckTransportCompany>();
		this.routing = routing;
		setCostPerKw(0.0);
		setCostPerBigLoad(0.0);
		setCostPerSmallLoad(0.0);
		setExcessStorageCost(0.0);
		setWeighingCost(0.0);
	}

	// ----------------------------------
	// GETTERS
	// ----------------------------------

	public List<Consignee> getConsignees() {
		return consignees;
	}

	public List<ExportOrder> getExportOrders() {
		return exportOrders;
	}

	public List<ImportOrder> getImportOrders() {
		return importOrders;
	}

	@Override
	public String getName() {
		return "Puerto de Buenos Aires";
	}

	@Override
	public Position getPosition() {
		return new Position(-34.5795823299825, -58.373877081937);
	}

	public Routing getRouting() {
		return routing;
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

	// ------------------------------------------------------------
	// SERVICE POR CLIENTS
	// ------------------------------------------------------------

	public List<Trip> searchTrips(Search search) {
		// Obtengo todos los viajes de cada una de los lineas navieras.
		List<Trip> allTrips = shippingLines.stream().flatMap(s -> s.getTrips().stream()).toList();
		return search.filterTrips(allTrips);
	}

	public LocalDateTime nextDepartureDateTo(Terminal terminal) {
		return null;

	}

	public Integer timeItTakesToGetTo(ShippingLine shippingLine, Terminal destiny) {
		return null;
	}

	// ------------------------------------------------------------
	// REGISTRATION METHODS
	// ------------------------------------------------------------

	public void registerConsignee(Consignee consignee) {
		consignees.add(consignee);
	}

	public void registerShipper(Shipper shipper) {
		shippers.add(shipper);
	}

	public void registerShippingCompany(ShippingLine shippingLine) {
		shippingLines.add(shippingLine);

	}

	public void registerTruckTransportCompany(TruckTransportCompany truckTransportCompany) {
		truckTransportCompanies.add(truckTransportCompany);
	}

	// ------------------------------------------------------------
	// PROCESS OF EXPORT ORDER
	// ------------------------------------------------------------
	public void hireExportService(ExportOrder exportOrder) {
		// Se verifica si el shipper esta registrado en la terminal gestionada, caso
		// contrario se lo registra.
		registerShipperIfNew((Shipper) exportOrder.getClient());
		// Se debe validar que el chofer y el camión esten registrados en la terminal
		// gestionada.
		ExportValidation.validateOrderInTerminal(this, exportOrder);
		// Se le asigna al turno del shipper que contiene la fecha una estimación de 6
		// horas antes de que llegue el buque a la
		// terminal gestionada.
		setTurnDateForExportOrder(exportOrder);
		// Se registra la orden de exportación en la terminal gestionada.
		exportOrders.add(exportOrder);
	}

	public void truckArrivedWithLoad(ExportOrder exportOrder, Driver driver, Truck truck, LocalDateTime dateToArrival) {
		// Se debe validar que el chofer y el camión esten registrados en la terminal
		// gestionada.
		ExportValidation.validateOrderInTerminal(this, exportOrder);
		// Se debe validar que el chofer y el camión sean los informados por el
		// consginee.
		ExportValidation.validateDriverAndTruckWithClientInfo(exportOrder, driver, truck);
		// Se debe validar que la hora del turno no difiera de más de 3 horas con la
		// hora de llegada del camión.
		ExportValidation.validateShiftTiming(exportOrder, dateToArrival);
		// Se debe agregar el servicio de pesado para cualquier carga.
		registerWeighService(exportOrder);
		// Se debe agregar el servicio electrico solamente para el contenedor reefer.
		registerEndOfElectricityService(List.of(exportOrder), dateToArrival);
	}

	private void registerShipperIfNew(Shipper shipper) {
		if (!shippers.contains(shipper)) {
			shippers.add((Shipper) shipper);
		}
	}

	private void setTurnDateForExportOrder(ExportOrder exportOrder) {
		LocalDateTime estimatedArrival = exportOrder.getTrip().calculateArrivalDateToTerminal(this);
		exportOrder.getTurn().setDate(estimatedArrival.minus(6, ChronoUnit.HOURS));
	}

	// ------------------------------------------------------------
	// PROCESS OF IMPORT ORDER
	// ------------------------------------------------------------
	public void hireImportService(ImportOrder importOrder) {
		// Se verifica si el consignee esta registrado en la terminal gestionada, caso
		// contrario se lo registra.
		registerConsgineeIfNew((Consignee) importOrder.getClient());
		// Se debe validar que el chofer y el camiÃ³n esten registrados en la terminal
		// gestionada.
		ExportValidation.validateOrderInTerminal(this, importOrder);
		// Se registra la orden de importación en la terminal gestionada.
		importOrders.add(importOrder);
	}

	public void truckLeaveWithLoad(ImportOrder importOrder, Driver driver, Truck truck, LocalDateTime dateToArrival) {
		// Se debe validar que el chofer y el camión esten registrados en la terminal
		// gestionada.
		ImportValidation.validateOrderInTerminal(this, importOrder);
		// Se debe validar que el chofer y el camión sean los informados por el
		// consginee.
		ImportValidation.validateDriverAndTruckWithClientInfo(importOrder, driver, truck);
		// Se le registra el servicio de exceso de almacenamiento si corresponde.
		registerExcessStorageService(importOrder, dateToArrival);
		// Se le debe registar el fin del servicio de electricidad, solamente a las
		// ordenes que tenga una carga Reefer.
		registerEndOfElectricityService(List.of(importOrder), dateToArrival);
		// Se le enviara la facturación con el desglose de los servicios aplicados con
		// la fecha y el monto de cada uno.
		// Ademas, de la facturación del viaje en si mismo, que consta de la sumatoria
		// de todos los tramos realizados por el buque para la entrega correspondiente.
		sendBilling(importOrder);
	}

	private void registerConsgineeIfNew(Consignee consginee) {
		if (!consignees.contains(consginee)) {
			consignees.add(consginee);
		}
	}

	// ------------------------------------------------------------
	// SHIP COMMMUNICATION
	// ------------------------------------------------------------
	// ------------------------------
	// PUBLIC METHODS
	// ------------------------------
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

//		ship.startWorking();
		// Obtenemos la fecha en la que llegó el buque a la terminal gestionada.
		LocalDateTime arrivalDateShip = ship.getTrip().calculateArrivalDateToTerminal(this);

		// Registramos la fecha de finalización del servicio eléctrico para órdenes de
		// exportación.
		registerEndOfElectricityService(exportOrders, arrivalDateShip);

		// Registramos el servicio eléctrico al inicio para órdenes de importación con
		// cargas Reefer.
		registerStartElectricityService(importOrders, arrivalDateShip);

		// Establecemos la fecha del turno para todas las órdenes de importación.
		setTurnDateForImportOrders(importOrders, arrivalDateShip);

		sendArrivalNotificationsToClients(ship, importOrders);

		// Notificamos al barco su llegada.
//		ship.depart();
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
		// Avisamos a todos los consignees que su carga llegó en la determinada fecha.
		notifyConsigneesAboutCargoDeparture(ship);
	}

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

	// ------------------------------
	// PRIVATE METHODS
	// ------------------------------
	/**
	 * Envía facturas a clientes de órdenes de exportación asociadas al buque que ha
	 * partido.
	 *
	 * @param ship Buque que ha partido de la terminal gestionada.
	 */
	private void sendInvoicesForExportOrders(Ship ship) {
		exportOrders.stream().filter(e -> e.getTrip().equals(ship.getTrip())).forEach(e -> sendBilling(e));
	}

	/**
	 * Envía la facturación para una orden de importación específica.
	 *
	 * @param importOrder Orden de importación para la cual se enviará la
	 *                    facturación.
	 */
	private void sendBilling(Order order) {
		Client client = order.getClient();
		Bill bill = order.getBill();
		client.sendMail(this, client, bill);
	}

	/**
	 * Notifica a los shippers sobre la salida de sus cargas en la fecha actual.
	 *
	 * @param ship Buque que ha partido de la terminal gestionada.
	 */
	private void notifyConsigneesAboutCargoDeparture(Ship ship) {
		exportOrders.stream().filter(e -> e.getTrip().equals(ship.getTrip())).forEach(
				e -> e.getClient().sendMail(this, e.getClient(), ship.getTrip().calculateArrivalDateToTerminal(this)));
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
				.forEach(this::sendArrivalNotificationToClient);
	}

	/**
	 * Envia una notificación de llegada a un cliente específico asociado a una
	 * orden.
	 *
	 * @param order Orden para la cual se enviará la notificación de llegada.
	 */
	private void sendArrivalNotificationToClient(Order order) {
		Client client = order.getClient();
		LocalDateTime arrivalDate = order.getTrip().calculateArrivalDateToTerminal(this);
		client.sendMail(this, client, arrivalDate.toString());
	}

	/**
	 * Envia notificaciones por correo electrónico a los clientes cuyas órdenes
	 * están asociadas al buque especificado, informándoles que el buque está cerca.
	 *
	 * @param ship Buque que está cerca de la terminal gestionada.
	 */
	private void sendShipProximityNotifications(Ship ship) {
		// Crear una lista que contenga todas las órdenes, tanto de exportación como de
		// importación.
		List<Order> orders = new ArrayList<>();
		orders.addAll(exportOrders);
		orders.addAll(importOrders);

		// Filtrar las órdenes que están asociadas al buque especificado.
		orders.stream().filter(order -> order.getTrip().equals(ship.getTrip())).forEach(order -> {
			// Enviar una notificación por correo electrónico al cliente.
			order.getClient().sendMail(this, order.getClient(), "Ship is near");
		});
	}

	// ------------------------------------------------------------
	// SERVICES
	// ------------------------------------------------------------
	// ------------------------------
	// PUBLIC METHODS
	// ------------------------------
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

	/**
	 * Establece el costo por kilovatio para el servicio de electricidad.
	 *
	 * @param costPerKw Costo por kilovatio a establecer.
	 */
	public void setCostPerKw(Double costPerKw) {
		this.costPerKw = costPerKw;
	}

	/**
	 * Establece el costo de pesaje para el servicio de pesaje.
	 *
	 * @param weighingCost Costo de pesaje a establecer.
	 */
	public void setWeighingCost(Double weighingCost) {
		this.weighingCost = weighingCost;
	}

	/**
	 * Establece el costo por exceso de almacenamiento.
	 *
	 * @param excessStorageCost Costo por exceso de almacenamiento a establecer.
	 */
	public void setExcessStorageCost(Double excessStorageCost) {
		this.excessStorageCost = excessStorageCost;
	}

	/**
	 * Establece el costo por carga pequeña.
	 *
	 * @param costPerSmallLoad Costo por carga pequeña a establecer.
	 */
	public void setCostPerSmallLoad(Double costPerSmallLoad) {
		this.costPerSmallLoad = costPerSmallLoad;
	}

	/**
	 * Establece el costo por carga grande.
	 *
	 * @param costPerBigLoad Costo por carga grande a establecer.
	 */
	public void setCostPerBigLoad(Double costPerBigLoad) {
		this.costPerBigLoad = costPerBigLoad;
	}

	// ------------------------------
	// PRIVATE METHODS
	// ------------------------------
	/**
	 * Registra el servicio de almacenamiento excedido para una orden específica si
	 * ha excedido las 24 horas en la terminal gestionada.
	 *
	 * @param order         Orden para la cual se registrará el servicio de
	 *                      almacenamiento excedido.
	 * @param dateToArrival Fecha estimada de llegada a la terminal gestionada.
	 */
	private void registerExcessStorageService(Order order, LocalDateTime dateToArrival) {
		// Se obtiene la fecha en la cual el buque llegó a la terminal gestionada.
		LocalDateTime dateArrivedShip = order.getTrip().calculateArrivalDateToTerminal(this);
		// Se calcula las horas que estuvo la carga en la terminal gestionada.
		Long hoursInTerminal = ChronoUnit.HOURS.between(dateArrivedShip, dateToArrival);
		// Si superó las 24 horas, se le crea y añade el servicio a la orden.
		if (hoursInTerminal > 24) {
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
		orders.stream().filter(order -> order.getLoad().getName().equals("Reefer"))
				.forEach(order -> registerElectricityService(order, costPerKw, arrivalDateShip));
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
		List<Electricity> electricitys = orders.stream().flatMap(order -> order.getServices().stream())
				.filter(service -> service.getName().equals("Electricity")).map(service -> (Electricity) service)
				.collect(Collectors.toList());
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

	// ------------------------------------------------------------
	// OTHER METHODS
	// ------------------------------------------------------------
	/**
	 * Establece la fecha del turno para órdenes de importación con la fecha de
	 * arribo del buque.
	 *
	 * @param importOrders Lista de órdenes de importación para las cuales se
	 *                     establecerá la fecha del turno.
	 * @param dateToAssign Fecha a asignar como fecha del turno.
	 */
	private void setTurnDateForImportOrders(List<ImportOrder> importOrders, LocalDateTime dateToAssign) {
		importOrders.forEach(order -> setTurnDate(order, dateToAssign));
	}

	/**
	 * Establece la fecha del turno para una orden específica de importación.
	 *
	 * @param importOrder     Orden de importación para la cual se establecerá la
	 *                        fecha del turno.
	 * @param arrivalDateShip Fecha de arribo del buque.
	 */
	private void setTurnDate(ImportOrder importOrder, LocalDateTime arrivalDateShip) {
		importOrder.getTurn().setDate(arrivalDateShip.plus(6, ChronoUnit.HOURS));
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

}