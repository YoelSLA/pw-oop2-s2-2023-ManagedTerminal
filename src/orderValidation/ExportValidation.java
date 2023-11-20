package orderValidation;

import order.ExportOrder;
import terminal.ManagedTerminal;

/**
 * Validador de �rdenes de exportaci�n. Implementa el algoritmo del m�todo
 * validation de su superclase.
 * 
 * @author Gabriela Fascetta | Yoel Ventoso
 */
public class ExportValidation extends OrderValidation {

	public static void validateOrderFor(ExportOrder exportOrder, ManagedTerminal terminal) {
		validateDriverOf(exportOrder, terminal);
		validateTruckOf(exportOrder, terminal);

	}

	private static void validateDriverOf(ExportOrder exportOrder, ManagedTerminal terminal) {
		if (!terminal.isItRegistered(exportOrder.getDriver())) {
			throw new RuntimeException("El chofer no esta registrado en la terminal gestionada.");
		}

	}

	private static void validateTruckOf(ExportOrder exportOrder, ManagedTerminal terminal) {
		if (!terminal.isItRegistered(exportOrder.getTruck())) {
			throw new RuntimeException("El cami�n no esta registrado en la terminal gestionada.");
		}
	}

}
