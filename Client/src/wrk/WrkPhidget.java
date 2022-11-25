package wrk;


import com.phidget22.*;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkPhidget {

	private ItfWrkPhidget refWrk;
	private final static int VINT_RFID_SERIAL = 604449;
	private final static int VINT_HUB_SERIAL = 636313;
	//private final static int MS_TIMEOUT = 5000;

	/**
	 * Constructor of WrkPhidget
	 * @param refWrk reference of the wrk
	 */
	public WrkPhidget(ItfWrkPhidget refWrk){
		this.refWrk = refWrk;

		try {
			RFID rfid = new RFID();
			HumiditySensor hum = new HumiditySensor();

			hum.setDeviceSerialNumber(VINT_HUB_SERIAL);
			hum.setHubPort(0);

			rfid.setDeviceSerialNumber(VINT_RFID_SERIAL);
			rfid.addTagListener(new RFIDTagListener() {
				@Override
				public void onTag(RFIDTagEvent rfidTagEvent) {
					refWrk.receiveRFID(rfidTagEvent.getTag());
				}
			});
			hum.addHumidityChangeListener(new HumiditySensorHumidityChangeListener() {
				@Override
				public void onHumidityChange(HumiditySensorHumidityChangeEvent humidity) {
					try {
						Thread.sleep(20000);
						refWrk.receiveHumidity(humidity.getHumidity());
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}

				}
			});

			rfid.open();
			hum.open();
		} catch (PhidgetException e) {
		}
	}


}//end WrkPhidget