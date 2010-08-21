/*
 * Main responseing class
 */
package org.medcare.igtl.network;

import java.util.ArrayList;

import org.medcare.igtl.messages.StatusMessage;
import org.medcare.igtl.util.Header;

/**
 * Perform response data correpondind to the message received performResponse
 * methods must be adapted corresponding to the need of each use
 * 
 * @author Andre Charles Legendre
 */
public class ResponseHandler {

	String err = "ResponseHandler.performResponse() failed.";
	byte[] body;
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
		this.body = body;
	}

	/**
	 * Perform the responsejob performResponse methods must be adapted
	 * corresponding to the need of each use
	 * 
	 * @return True if response job performed successfull
	 * @throws Exception received by perform method
	 */
	public boolean performResponse() throws Exception {
		String messageType = this.responseHeader.getDataType();
		boolean result = false;
		for (Capability capablity : Capability.values())
			if (messageType.equals(capablity.toString())) {
				return result = perform(messageType);
			}
		manageError(messageType, StatusMessage.STATUS_NOT_FOUND);
		throw new AssertionError("Unknown op: " + messageType);
	}

	/**
	 * Perform the responsejob  this method must be adapted for each use
	 * 
	 * @param messageType
	 *            The messageType
	 * @return True if response job performed successfull
	 * @throws Exception CrcException will be thrown at crc check during message unpacking
	 * Other exceptions can be thrown 
	 */
	public boolean perform(String messageType) throws Exception {
		//FIXME Will have to create a new message corresponding to the type
		return false;
	}

	/**
	 * manage error this method must be adapted for each use
	 * 
	 * @param messageType
	 *            The messageType
	 * @param status
	 *            The status
	 * @return True if response job performed successfull
	 * @throws Exception
	 */
	public boolean manageError (String messageType, int status)throws Exception  {
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
