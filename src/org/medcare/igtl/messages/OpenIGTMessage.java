/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/messages/OpenIGTMessage.java $
  Language:  java
  Date:      $Date: 2010-18-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.messages;

import org.medcare.igtl.util.Header;

/**
 *** All messages will have to extend this class
 * 
 * @author Andre Charles Legendre
 * 
 **/

public class OpenIGTMessage {
	// ------------------------------------------------------------------------

	public String deviceName;
	public byte[] body;
	public Header header;
	public static long VERSION_2 = 2L;

	/**
	 *** Constructor to be used to create message to getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public OpenIGTMessage(String deviceName) {
		this.deviceName = deviceName;
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
		byte[] bytes = new byte[body.length + Header.LENGTH];
		System.arraycopy(header_Bytes, 0, bytes, 0, Header.LENGTH);
		System.arraycopy(body, 0, bytes, Header.LENGTH, body.length);
		return bytes;
	}
}
