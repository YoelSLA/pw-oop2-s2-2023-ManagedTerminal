package client;

import bill.Bill;
import terminal.ManagedTerminal;

public class Consignee extends Client {

	public Consignee(String dni, String name) {
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
//		System.out.println("Please have in mind you have until 6 hours from the ship arrival until we start charging excess storage");
	}

	@Override
	public void sendMailAboutShipArrival(ManagedTerminal managedTerminal, Client client, String String) {
		// TODO Auto-generated method stub
		System.out.println("Your ship has arrived to " + managedTerminal.getName());
	}

}
