package terminal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import client.Consignee;
import client.Shipper;
import driver.Driver;
import geographicalPosition.GeographicalPosition;
import order.ExportOrder;
import order.ImportOrder;
import orderValidation.ExportValidation;
import routing.Routing;
import shippingCompany.ShippingCompany;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

public class ManagedTerminal extends Terminal {

	private List<Consignee> consignees;
	private List<ExportOrder> exportOrders;
	private GeographicalPosition geographicalPosition;
	private List<ImportOrder> importOrders;
	private Routing routing;
	private List<Shipper> shippers;
	private List<ShippingCompany> shippingCompanies;
	private List<TruckTransportCompany> truckTransportCompanies;

	public ManagedTerminal(String name, GeographicalPosition geographicalPosition, Routing routing) {
		super(name);
		this.consignees = new ArrayList<Consignee>();
		this.exportOrders = new ArrayList<ExportOrder>();
		this.geographicalPosition = geographicalPosition;
		this.importOrders = new ArrayList<ImportOrder>();
		this.routing = routing;
		this.shippers = new ArrayList<Shipper>();
		this.shippingCompanies = new ArrayList<ShippingCompany>();
		this.truckTransportCompanies = new ArrayList<TruckTransportCompany>();
	}

	public void assignShiftFor(ExportOrder exportOrder, LocalDateTime dateToArrive) {
		exportOrder.setDateTruck(dateToArrive);

	}

	public List<Consignee> getConsignees() {
		return consignees;
	}

	public List<ExportOrder> getExportOrders() {
		return exportOrders;
	}

	public GeographicalPosition getGeographicalPosition() {
		return geographicalPosition;
	}

	public List<ImportOrder> getImportOrders() {
		return importOrders;
	}

	public Routing getRouting() {
		return routing;
	}

	public List<Shipper> getShippers() {
		return shippers;
	}

	public List<ShippingCompany> getShippingCompanies() {
		return shippingCompanies;
	}

	public List<TruckTransportCompany> getTruckTransportCompanies() {
		return truckTransportCompanies;
	}

	public void hireExportService(ExportOrder exportOrder) {
		if (!shippers.contains(exportOrder.getShipper())) {
			shippers.add(exportOrder.getShipper());
		}
		ExportValidation.validateOrderFor(exportOrder, this);
		assignShiftFor(exportOrder, LocalDateTime.now());
		exportOrders.add(exportOrder);

	}

	public boolean isItRegistered(Driver driver) {
		return registredDrivers().contains(driver);
	}

	public boolean isItRegistered(Truck truck) {
		return registredTrucks().contains(truck);
	}

	public void registerConsignee(Consignee consignee) {
		consignees.add(consignee);
	}

	public void registerShipper(Shipper shipper) {
		shippers.add(shipper);
	}

	public void registerShippingCompany(ShippingCompany shippingCompany) {
		shippingCompanies.add(shippingCompany);
	}

	public void registerTruckTransportCompany(TruckTransportCompany truckTransportCompany) {
		truckTransportCompanies.add(truckTransportCompany);
	}

	public void setRouting(Routing routing) {
		this.routing = routing;
	}

	public void truckArrivedWithLoad(Driver driver, ExportOrder exportOrder, Truck truck, LocalDateTime arrivalDate) {
		ExportValidation.validateTruckEntry(driver, exportOrder, truck, arrivalDate);
	}

	private List<Driver> registredDrivers() {
		return truckTransportCompanies.stream().flatMap(t -> t.getDrivers().stream()).distinct()
				.collect(Collectors.toList());
	}

	private List<Truck> registredTrucks() {
		return truckTransportCompanies.stream().flatMap(t -> t.getTrucks().stream()).distinct()
				.collect(Collectors.toList());
	}

}
