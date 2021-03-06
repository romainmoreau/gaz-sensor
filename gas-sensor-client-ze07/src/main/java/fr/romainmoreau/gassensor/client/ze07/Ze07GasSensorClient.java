package fr.romainmoreau.gassensor.client.ze07;

import java.io.IOException;

import fr.romainmoreau.gassensor.client.common.AbstractGasSensorClient;
import fr.romainmoreau.gassensor.client.common.ByteUtils;
import fr.romainmoreau.gassensor.client.common.ChecksumGasSensorEventValidator;
import fr.romainmoreau.gassensor.client.common.ChecksumUtils;
import fr.romainmoreau.gassensor.client.common.FixedLengthGasSensorEventAnalyser;
import fr.romainmoreau.gassensor.client.common.GasSensing;
import fr.romainmoreau.gassensor.client.common.GasSensorEvent;
import fr.romainmoreau.gassensor.client.common.GasSensorEventListener;
import fr.romainmoreau.gassensor.client.common.GasSensorExceptionHandler;
import fr.romainmoreau.gassensor.client.common.GasSensorReaderFactory;
import fr.romainmoreau.gassensor.client.common.GenericGasSensorEvent;

public class Ze07GasSensorClient extends AbstractGasSensorClient<GasSensorEvent> {
	public Ze07GasSensorClient(String description, GasSensorReaderFactory<GasSensorEvent> gasSensorReaderFactory,
			GasSensorEventListener<GasSensorEvent> gasSensorEventListener,
			GasSensorExceptionHandler gasSensorExceptionHandler) throws IOException {
		super(description != null ? Ze07.SENSOR_NAME + description : Ze07.SENSOR_NAME, gasSensorReaderFactory,
				gasSensorEventListener, gasSensorExceptionHandler,
				new FixedLengthGasSensorEventAnalyser(Ze07.EVENT_LENGTH), new ChecksumGasSensorEventValidator(
						Ze07.CHECKSUM_LENGTH, event -> new byte[] { ChecksumUtils.notSum(event) }),
				Ze07.HEADER);
	}

	@Override
	protected GasSensorEvent eventToGasSensorEvent(byte[] event) {
		return new GenericGasSensorEvent(new GasSensing(Ze07.CO_DESCRIPTION,
				ByteUtils.highByteLowByteToBigDecimal(event[4], event[5]).multiply(Ze07.CO_MULTIPLICAND),
				Ze07.CO_UNIT));
	}
}
