/*
 * Main requesting class
 */
package org.medcare.igtl.network;

import java.util.ArrayList;

import org.medcare.igtl.util.Header;

/**
 * Perform request correpondind to the message received performRequest methods
 * must be adapted corresponding to the need of each use
 * 
 * @author Andre Charles Legendre
 */
public class MessageHandler {

	String err = "MessageHandler.performRequest() failed.";
	byte[] message;
	Header header;
	public enum Capability {GET_CAPABIL, GET_IMAGE, GET_IMGMETA, GET_LBMETA, GET_STATUS, GET_TRAJ, CAPABILITY, COLORTABLE, IMAGE, IMGMETA, POINT, POSITION, STATUS, STP_TDATA, STT_TDATA, TDATA, TRAJ, TRANSFORM};

	/***************************************************************************
	 * Default MessageQueueManager constructor.
	 * 
	 * @param header
	 *            of the message
	 * 
	 * @param body
	 *            of the message
	 * 
	 **************************************************************************/
	public MessageHandler(Header header, byte[] body) {
		this.header = header;
		this.message = body;
	}


	/**
	 * Perform the requestjob on the message performRequest methods must be
	 * corresponding to the need of each use
	 * 
	 * @return True if response job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest() throws Exception {
		String messageType = this.header.getDataType();
		for (Capability capablity : Capability.values())
                	if (messageType.equals(capablity.toString())) {
				return perform(messageType);
			}
		throw new AssertionError("Unknown op: " + messageType);
	}

	/**
	 * Perform the requestjob on the message performRequest methods must be
	 * corresponding to the need of each use
	 * 
	 * @param message
	 *            The message received
	 * @return True if response job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest(byte[] message) throws Exception {
		String messageType = this.header.getDataType();
		for (Capability capablity : Capability.values())
                	if (messageType.equals(capablity.toString())) {
				return perform(messageType, message);
			}
		throw new AssertionError("Unknown op: " + messageType);
	}

	public boolean perform(String messageType) {
		return false;
	}

	public boolean perform(String messageType, byte[] message) {
		return false;
	}

	/**
	 *** Gets the enum of Types implemented in this Handler
	 **/
	public ArrayList<String> getCapability() {
		ArrayList<String> capString = new ArrayList<String>();
		for (Capability capablity : Capability.values())
                	capString.add(capablity.toString());
		return capString;
	}
}
