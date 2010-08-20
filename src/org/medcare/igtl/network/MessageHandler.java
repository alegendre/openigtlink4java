/*
 * Main requesting class
 */
package org.medcare.igtl.network;

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
	 * adapted corresponding to the need of each use
	 * 
	 * @return True if request job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest() throws Exception {
		String messageType = this.header.getDataType();
		if (messageType.equals("GET_CAPABIL")) {
			// GetCapabilityMessage
		} else if (messageType.equals("GET_IMAGE")) {
			// GetImageMessage
		} else if (messageType.equals("GET_IMGMETA")) {
			// GetImageMetaMessage
		} else if (messageType.equals("GET_LBMETA")) {
			// GetLabelMetaMessage
		} else if (messageType.equals("GET_STATUS")) {
			// GetStatusMessage
		} else if (messageType.equals("GET_TRAJ")) {
			// GetTrajectoryMessage
		} else if (messageType.equals("CAPABILITY")) {
			// CapabilityMessage
		} else if (messageType.equals("COLORTABLE")) {
			// ColorTableMessage
		} else if (messageType.equals("IMAGE")) {
			// ImageMessage
		} else if (messageType.equals("IMGMETA")) {
			// ImageMetaMessage
		} else if (messageType.equals("POINT")) {
			// PointMessage
		} else if (messageType.equals("POSITION")) {
			// PositionMessage
		} else if (messageType.equals("STATUS")) {
			// StatusMessage
		} else if (messageType.equals("STP_TDATA")) {
			// StopTrackingDataMessage
		} else if (messageType.equals("STT_TDATA")) {
			// StopTrackingDataMessage
		} else if (messageType.equals("TDATA")) {
			// TrackingDataMessage
		} else if (messageType.equals("TRAJ")) {
			// TrajectoryMessage
		} else if (messageType.equals("TRANSFORM")) {
			// TransformMessage
		} else {
			// UnknownMessage
		}
		return false;
	}

	/**
	 * Perform the requestjob on the message performRequest methods must be
	 * adapted corresponding to the need of each use
	 * 
	 * @param message
	 *            The message to be performed
	 * @return True if request job performed successfull
	 * @throws Exception
	 */
	public boolean performRequest(byte[] message) throws Exception {
		String messageType = this.header.getDataType();
		if (messageType.equals("GET_CAPABIL")) {
			// GetCapabilityMessage
		} else if (messageType.equals("GET_IMAGE")) {
			// GetImageMessage
		} else if (messageType.equals("GET_IMGMETA")) {
			// GetImageMetaMessage
		} else if (messageType.equals("GET_LBMETA")) {
			// GetLabelMetaMessage
		} else if (messageType.equals("GET_STATUS")) {
			// GetStatusMessage
		} else if (messageType.equals("GET_TRAJ")) {
			// GetTrajectoryMessage
		} else if (messageType.equals("CAPABILITY")) {
			// CapabilityMessage
		} else if (messageType.equals("COLORTABLE")) {
			// ColorTableMessage
		} else if (messageType.equals("IMAGE")) {
			// ImageMessage
		} else if (messageType.equals("IMGMETA")) {
			// ImageMetaMessage
		} else if (messageType.equals("POINT")) {
			// PointMessage
		} else if (messageType.equals("POSITION")) {
			// PositionMessage
		} else if (messageType.equals("STATUS")) {
			// StatusMessage
		} else if (messageType.equals("STP_TDATA")) {
			// StopTrackingDataMessage
		} else if (messageType.equals("STT_TDATA")) {
			// StopTrackingDataMessage
		} else if (messageType.equals("TDATA")) {
			// TrackingDataMessage
		} else if (messageType.equals("TRAJ")) {
			// TrajectoryMessage
		} else if (messageType.equals("TRANSFORM")) {
			// TransformMessage
		} else {
			// UnknownMessage
		}
		return false;
	}
}
