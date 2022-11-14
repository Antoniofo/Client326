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
	private final static int MS_TIMEOUT = 5000;

	public WrkPhidget(){
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
					refWrk.receiveHumidity(humidity.getHumidity());
				}
			});

			rfid.open(MS_TIMEOUT);
			hum.open(MS_TIMEOUT);
		} catch (PhidgetException e) {
		}
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize()
	  throws Throwable{

	}
}//end WrkPhidget