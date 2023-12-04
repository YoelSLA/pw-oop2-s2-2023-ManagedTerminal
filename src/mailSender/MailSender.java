package mailSender;

import java.time.LocalDateTime;

import bill.Bill;
import client.Client;
import terminal.ManagedTerminal;

public interface MailSender {

	public void sendMail(ManagedTerminal managedTerminal, Client client, Bill bill);

	public void sendMail(ManagedTerminal managedTerminal, Client client, String string);

	public void sendMail(ManagedTerminal managedTerminal, Client client, LocalDateTime date);

}
