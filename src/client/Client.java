package client;

import mailSender.MailSender;

public abstract class Client implements MailSender {

	private String dni;
	private String name;

	public Client(String dni, String name) {
		this.dni = dni;
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

}
