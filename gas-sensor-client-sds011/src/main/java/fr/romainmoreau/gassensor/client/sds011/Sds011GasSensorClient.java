package fr.romainmoreau.gassensor.client.sds011;

import java.io.IOException;

import fr.romainmoreau.gassensor.client.common.AbstractGasSensorClient;
import fr.romainmoreau.gassensor.client.common.ByteUtils;
import fr.romainmoreau.gassensor.client.common.FixedLengthGasSensorEventAnalyser;
import fr.romainmoreau.gassensor.client.common.GasSensing;
import fr.romainmoreau.gassensor.client.common.GasSensorEvent;
import fr.romainmoreau.gassensor.client.common.GasSensorEventListener;
import fr.romainmoreau.gassensor.client.common.GasSensorExceptionHandler;
import fr.romainmoreau.gassensor.client.common.GasSensorReaderFactory;
import fr.romainmoreau.gassensor.client.common.GenericGasSensorEvent;

public class Sds011GasSensorClient extends AbstractGasSensorClient<GasSensorEvent> {
	public Sds011GasSensorClient(String description, GasSensorReaderFactory<GasSensorEvent> gasSensorReaderFactory,
			GasSensorEventListener<GasSensorEvent> gasSensorEventListener,
			GasSensorExceptionHandler gasSensorExceptionHandler) throws IOException {
		super(description != null ? Sds011.SENSOR_NAME + description : Sds011.SENSOR_NAME, gasSensorReaderFactory,
				gasSensorEventListener, gasSensorExceptionHandler,
				new FixedLengthGasSensorEventAnalyser(Sds011.EVENT_LENGTH), new Sds011GasSensorEventValidator(),
				Sds011.HEADER);
	}

	@Override
	protected GasSensorEvent eventToGasSensorEvent(byte[] event) {
		return new GenericGasSensorEvent(new GasSensing(Sds011.PM2_5_DESCRIPTION,
				ByteUtils.highByteLowByteToBigDecimal(event[3], event[2]).scaleByPowerOfTen(-1), Sds011.PM_UNIT),
				new GasSensing(Sds011.PM10_DESCRIPTION,
						ByteUtils.highByteLowByteToBigDecimal(event[5], event[4]).scaleByPowerOfTen(-1),
						Sds011.PM_UNIT));
	}
}
