package fr.romainmoreau.gassensor.client.zph01;

import java.io.IOException;

import fr.romainmoreau.gassensor.client.common.GasSensorEvent;
import fr.romainmoreau.gassensor.client.common.GasSensorEventListener;
import fr.romainmoreau.gassensor.client.common.GasSensorExceptionHandler;
import fr.romainmoreau.gassensor.client.common.JSerialCommGasSensorReader;

public class JSerialCommZph01GasSensorClient extends Zph01GasSensorClient {
	public JSerialCommZph01GasSensorClient(String description, String portName,
			GasSensorEventListener<GasSensorEvent> gasSensorEventListener,
			GasSensorExceptionHandler gasSensorExceptionHandler) throws IOException {
		super(description, c -> new JSerialCommGasSensorReader(c, portName), gasSensorEventListener,
				gasSensorExceptionHandler);
	}
}
