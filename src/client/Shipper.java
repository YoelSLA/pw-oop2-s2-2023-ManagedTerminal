package client;

import bill.Bill;
import terminal.ManagedTerminal;

public class Shipper extends Client {

	public Shipper(String dni, String name) {
		super(dni, name);
	}

	@Override
	public void sendMailAboutBill(ManagedTerminal managedTerminal, Client client, Bill bill) {
		// TODO Auto-generated method stub
		bill.printInvoice();
	}

	@Override
	public void sendMailAboutShipInminentArrival(ManagedTerminal managedTerminal, Client client, String string) {
		// TODO Auto-generated method stub
		System.out.println("Your ship is closed to " + managedTerminal.getName());
	}

	@Override
	public void sendMailAboutShipArrival(ManagedTerminal managedTerminal, Client client, String String) {
		// TODO Auto-generated method stub
		System.out.println("Your ship has arrived to " + managedTerminal.getName());
	}

}
