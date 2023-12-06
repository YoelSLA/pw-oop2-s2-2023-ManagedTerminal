package mailSender;

import bill.Bill;
import client.Client;
import terminal.ManagedTerminal;

public interface MailSender {
	
	public void sendMailAboutBill(ManagedTerminal managedTerminal, Client client, Bill bill);
	
	public void sendMailAboutShipInminentArrival(ManagedTerminal managedTerminal, Client client, String string);

	public void sendMailAboutShipArrival(ManagedTerminal managedTerminal, Client client, String String);

}
