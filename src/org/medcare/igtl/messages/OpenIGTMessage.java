package org.medcare.igtl.messages;

import org.medcare.igtl.util.Header;

/**
 *** All messages will have to extend this class
 **/

public class OpenIGTMessage {
	// ------------------------------------------------------------------------

	private String deviceType;
	private String deviceName;
	private byte[] body;
	private Header header;
	private static long VERSION_2 = 2L;

	/**
	 *** Destination Constructor
	 *** 
	 * @param deviceType
	 *            Device Type
	 * @param deviceName
	 *            Device Name
	 * @param body
	 *            bytes array containing message body
	 **/
	public OpenIGTMessage(String deviceType, String deviceName, byte body[]) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.body = body;
		this.header = new Header(VERSION_2, deviceType, deviceName, body);
	}

	/**
	 *** Unique device name.
	 *** 
	 * @return The name of the device
	 **/
	public String getDeviceName() {
		return this.deviceName;
	}

	/**
	 *** Device type.
	 *** 
	 * @return The type of the device
	 **/
	public String getDeviceType() {
		return this.deviceType;
	}

	/**
	 *** header.
	 *** 
	 * @return bytes array containing the header of the message
	 **/
	public Header getHeader() {
		return this.header;
	}

	/**
	 *** body.
	 *** 
	 * @return bytes array containing the body of the message
	 **/
	public byte[] getBody() {
		return this.body;
	}
}
