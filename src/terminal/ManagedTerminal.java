package terminal;

import java.util.ArrayList;
import java.util.List;

import client.Consignee;
import client.Shipper;
import geographicalPosition.GeographicalPosition;
import order.ExportOrder;
import order.ImportOrder;
import routing.Routing;
import shippingCompany.ShippingCompany;
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

	public void addConsignee(Consignee consignee) {
		consignees.add(consignee);
	}

	public void addShipper(Shipper shipper) {
		shippers.add(shipper);
	}

	public void addShippingCompany(ShippingCompany shippingCompany) {
		shippingCompanies.add(shippingCompany);
	}

	public void addTruckTransportCompany(TruckTransportCompany truckTransportCompany) {
		truckTransportCompanies.add(truckTransportCompany);
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

	public void setRouting(Routing routing) {
		this.routing = routing;
	}

}
