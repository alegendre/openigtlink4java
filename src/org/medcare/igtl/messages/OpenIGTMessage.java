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
	 *** Constructor to be used to create message to getBytes to send them
	 *** 
	 * @param deviceType
	 *            Device Type
	 * @param deviceName
	 *            Device Name
	 * @param body
	 *            bytes array containing message body
	 **/
	public OpenIGTMessage(String deviceType, String deviceName, byte[] body) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.body = body;
		this.header = new Header(VERSION_2, deviceType, deviceName, body);
	}

	/**
	 *** Constructor to be used to build messages from incoming bytes
	 *** 
	 * @param header
	 *            header of this message
	 * @param body
	 *            bytes array containing message body
	 **/
	public OpenIGTMessage(Header header, byte[] body) {
		this.header = header;
		this.deviceType = header.getDeviceType();
		this.deviceName = header.getDeviceName();
		this.body = body;
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

	/**
	 *** Bytes to be sent.
	 *** 
	 * @return bytes array containing the message
	 **/
	public byte[] getBytes() {
		byte[] header_Bytes = header.getBytes();
		byte[] bytes = new byte[body.length + 58];
		System.arraycopy(header_Bytes, 0, bytes, 0, 58);
		System.arraycopy(body, 0, bytes, 59, body.length);
		return bytes;
	}
}
