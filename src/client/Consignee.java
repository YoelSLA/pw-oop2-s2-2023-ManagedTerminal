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
		
	}

	@Override
	public void sendMailAboutShipInminentArrival(ManagedTerminal managedTerminal, Client client, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMailAboutShipArrival(ManagedTerminal managedTerminal, Client client, String String) {
		// TODO Auto-generated method stub
		
	}

}
