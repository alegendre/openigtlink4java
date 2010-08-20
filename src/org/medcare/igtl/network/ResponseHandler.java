/*
 * Main responseing class
 */
package org.medcare.igtl.network;

import java.util.ArrayList;

import org.medcare.igtl.util.Header;

/**
 * Perform response data correpondind to the message received performResponse
 * methods must be adapted corresponding to the need of each use
 * 
 * @author Andre Charles Legendre
 */
public class ResponseHandler {

	String err = "ResponseHandler.performResponse() failed.";
	byte[] responseBody;
	Header responseHeader;
	public enum Capability {GET_CAPABIL, GET_IMAGE, GET_IMGMETA, GET_LBMETA, GET_STATUS, GET_TRAJ, CAPABILITY, COLORTABLE, IMAGE, IMGMETA, POINT, POSITION, STATUS, STP_TDATA, STT_TDATA, TDATA, TRAJ, TRANSFORM};

	/***************************************************************************
	 * Default ResponseHandler constructor.
	 * 
	 * @param header
	 *            of the response
	 * 
	 * @param body
	 *            of the response
	 * 
	 **************************************************************************/
	public ResponseHandler(Header header, byte[] body) {
		this.responseHeader = header;
		this.responseBody = body;
	}

	/**
	 * Perform the responsejob performResponse methods must be adapted
	 * corresponding to the need of each use
	 * 
	 * @return True if response job performed successfull
	 * @throws Exception
	 */
	public boolean performResponse() throws Exception {
		String messageType = this.responseHeader.getDataType();
		for (Capability capablity : Capability.values())
                	if (messageType.equals(capablity.toString())) {
				return perform(messageType);
			}
		throw new AssertionError("Unknown op: " + messageType);
	}

	/**
	 * Perform the responsejob performResponse methods must be adapted
	 * corresponding to the need of each use
	 * 
	 * @param commands
	 *            The commands to be performed
	 * @return True if response job performed successfull
	 * @throws Exception
	 */
	public boolean performResponse(byte[] commands) throws AssertionError {
		String messageType = this.responseHeader.getDataType();
		for (Capability capablity : Capability.values())
                	if (messageType.equals(capablity.toString())) {
				return perform(messageType, commands);
			}
		throw new AssertionError("Unknown op: " + messageType);
	}

	public boolean perform(String messageType) {
		return false;
	}

	public boolean perform(String messageType, byte[] commands) {
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
