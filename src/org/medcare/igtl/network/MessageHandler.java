/*
 * Main requesting class
 */
package org.medcare.igtl.network;

import org.medcare.igtl.util.Header;

/**
 * Perform request correpondind to the message received
 * performRequest methods must be adapted corresponding to the need of each use
 *
 * @author Andre Charles Legendre
 */
public class MessageHandler {

	String err = "MessageHandler.performRequest() failed.";
	byte[] message;
	Header header;
	int IGTL_STATUS_INVALID = 0;
	int IGTL_STATUS_OK = 1;
	int IGTL_STATUS_UNKNOWN_ERROR = 2;
	int IGTL_STATUS_PANICK_MODE = 3; /* emergency */
	int IGTL_STATUS_NOT_FOUND = 4; /* file, configuration, device etc */
	int IGTL_STATUS_ACCESS_DENIED = 5;
	int IGTL_STATUS_BUSY = 6;
	int IGTL_STATUS_TIME_OUT = 7; /* Time out / Connection lost */
	int IGTL_STATUS_OVERFLOW = 8; /* Overflow / Can't be reached */
	int IGTL_STATUS_CHECKSUM_ERROR = 9; /* Checksum error */
	int IGTL_STATUS_CONFIG_ERROR = 10; /* Configuration error */
	int IGTL_STATUS_RESOURCE_ERROR = 11; /* Not enough resource (memory, storage etc) */
	int IGTL_STATUS_ILLEGAL_INSTRUCTION = 12; /* Illegal/Unknown instruction */
	int IGTL_STATUS_NOT_READY = 13; /* Device not ready (starting up)*/
	int IGTL_STATUS_MANUAL_MODE = 14; /* Manual mode (device does not accept commands) */
	int IGTL_STATUS_DISABLED = 15; /* Device disabled */
	int IGTL_STATUS_NOT_PRESENT = 16; /* Device not present */
	int IGTL_STATUS_UNKNOWN_VERSION = 17; /* Device version not known */
	int IGTL_STATUS_HARDWARE_FAILURE = 18; /* Hardware failure */
	int IGTL_STATUS_SHUT_DOWN = 19; /* Exiting / shut down in progress */

	int TYPE_ENTRY_ONLY = 1; //Trajectory type
	int TYPE_TARGET_ONLY = 2; //Trajectory type
	int TYPE_ENTRY_TARGET = 3; //Trajectory type

	/***************************************************************************
	 * Default MessageQueueManager constructor.
	 * @param header of the message
	 * 
	 * @param body of the message
	 * 
	 **************************************************************************/
	public MessageHandler(Header header, byte[] body) {
		this.header = header;
		this.message = body;
	}

	/**
	 * Perform the requestjob on the message
         * performRequest methods must be adapted corresponding to the need of each use
	 * 
	 * @return True if request job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest() throws Exception {
		String messageType = this.header.getDeviceType();
		if (messageType.equals("GET_CAPABIL")) {
			//GetCapabilityMessage
		} else if (messageType.equals("GET_IMAGE")) {
			//GetImageMessage
		} else if (messageType.equals("GET_IMGMETA")) {
			//GetImageMetaMessage
		} else if (messageType.equals("GET_LBMETA")) {
			//GetLabelMetaMessage
		} else if (messageType.equals("GET_STATUS")) {
			//GetStatusMessage
		} else if (messageType.equals("GET_TRAJ")) {
			//GetTrajectoryMessage
		} else if (messageType.equals("CAPABILITY")) {
			//CapabilityMessage
		} else if (messageType.equals("COLORTABLE")) {
			//ColorTableMessage
		} else if (messageType.equals("IMAGE")) {
			//ImageMessage
		} else if (messageType.equals("IMGMETA")) {
			//ImageMetaMessage
		} else if (messageType.equals("POINT")) {
			//PointMessage
		} else if (messageType.equals("POSITION")) {
			//PositionMessage
		} else if (messageType.equals("STATUS")) {
			//StatusMessage
		} else if (messageType.equals("STP_TDATA")) {
			//StopTrackingDataMessage
		} else if (messageType.equals("STT_TDATA")) {
			//StopTrackingDataMessage
		} else if (messageType.equals("TDATA")) {
			//TrackingDataMessage
		} else if (messageType.equals("TRAJ")) {
			//TrajectoryMessage
		} else if (messageType.equals("TRANSFORM")) {
			//TransformMessage
		} else {
			//UnknownMessage
		}
		return false;
	}

	/**
	 * Perform the requestjob on the message
         * performRequest methods must be adapted corresponding to the need of each use
	 * 
	 * @param message
	 *            The message to be performed
	 * @return True if request job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest(byte[] message) throws Exception {
		String messageType = this.header.getDeviceType();
		if (messageType.equals("GET_CAPABIL")) {
			//GetCapabilityMessage
		} else if (messageType.equals("GET_IMAGE")) {
			//GetImageMessage
		} else if (messageType.equals("GET_IMGMETA")) {
			//GetImageMetaMessage
		} else if (messageType.equals("GET_LBMETA")) {
			//GetLabelMetaMessage
		} else if (messageType.equals("GET_STATUS")) {
			//GetStatusMessage
		} else if (messageType.equals("GET_TRAJ")) {
			//GetTrajectoryMessage
		} else if (messageType.equals("CAPABILITY")) {
			//CapabilityMessage
		} else if (messageType.equals("COLORTABLE")) {
			//ColorTableMessage
		} else if (messageType.equals("IMAGE")) {
			//ImageMessage
		} else if (messageType.equals("IMGMETA")) {
			//ImageMetaMessage
		} else if (messageType.equals("POINT")) {
			//PointMessage
		} else if (messageType.equals("POSITION")) {
			//PositionMessage
		} else if (messageType.equals("STATUS")) {
			//StatusMessage
		} else if (messageType.equals("STP_TDATA")) {
			//StopTrackingDataMessage
		} else if (messageType.equals("STT_TDATA")) {
			//StopTrackingDataMessage
		} else if (messageType.equals("TDATA")) {
			//TrackingDataMessage
		} else if (messageType.equals("TRAJ")) {
			//TrajectoryMessage
		} else if (messageType.equals("TRANSFORM")) {
			//TransformMessage
		} else {
			//UnknownMessage
		}
		return false;
	}
}
