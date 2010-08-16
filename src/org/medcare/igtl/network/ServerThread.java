/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/network/ServerThread.java $
  Language:  java
  Date:      $Date: 2010-08-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.medcare.igtl.util.Header;

/**
 * OpenIGTServer create one ServerThread for each client making a connection.
 * ServerThread will add messages received from client to the messageQueue to be treated then
 * MessageQueueManager will use its ServerThread to send answer to messages.
 */
public class ServerThread extends Thread {
    private java.net.Socket socket = null;
    private OutputStream outstr;
    private InputStream instr;
    private MessageQueueManager messageQueue = null;
    private boolean alive;

	/***************************************************************************
	 * Default ServerThread constructor.
	 * @param socket to listen to
	 * 
	 **************************************************************************/
    public ServerThread(java.net.Socket socket) {
	super("ServerThread");
	this.socket = socket;
	this.messageQueue = new MessageQueueManager(this);
        this.messageQueue.start();
    }

	/***************************************************************************
	 * Reader thread. Reads messages from the socket and add them to the MessageQueueManager
	 * 
	 **************************************************************************/
    public void run() {
       this.alive = true;

	try {
	    outstr = socket.getOutputStream();
	    instr = socket.getInputStream();
	    int ret_read = 0;
	    byte[] headerBuff = new byte[58];
	// System.out.println("JE LIS");
	do {
		ret_read = instr.read(headerBuff);
		if (ret_read > 0) {
			//System.out.print(new String(buff, 0, ret_read));
			Header header = new Header(headerBuff);
			byte[] bodyBuf = new byte[(int) header.getBody_size()];
			ret_read = instr.read(bodyBuf);
			if (ret_read > 0) {
				messageQueue.addMessage(header, bodyBuf);
			}
		}
	} while (alive && ret_read >= 0);
	// System.out.println("J'AI LU");
	    outstr.close();
	    instr.close();
	    socket.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	this.interrupt();
    }

	/***************************************************************************
	 * Sends bytes
	 * <p>
	 * 
	 * @throws IOException
	 *             - Exception in I/O.
	 *             <p>
	 * @param bytes
	 *            - byte[] array.
	 **************************************************************************/
	final public synchronized void sendBytes(byte[] bytes) throws IOException {
		try {
			outstr.write(bytes);
			outstr.flush();
		} catch (Exception e) {
			System.err.println("Exception while sendBytes to socket:"
					+ e.getMessage());
		}
	}
	/***************************************************************************
	 * Interrupt this thread
	 **************************************************************************/
	public void interrupt() {
		alive = false;
	}
}
