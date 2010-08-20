/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/network/SocketClientFactory.java $
  Language:  java
  Date:      $Date: 2010-08-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.network;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.medcare.igtl.util.Header;

/**
 * Each OpenIGTClient create its own ResponseQueueManager to queue response they
 * get from server. ResponseQueueManager will perform ResponseHandler added in
 * them queue respecting the order.
 * 
 * @author Andre Charles Legendre
 */
public class ResponseQueueManager extends Thread {
	private static String VERSION = "0.1a";

	private long sleep;
	public ConcurrentLinkedQueue<ResponseHandler> openIGT_Queue = new ConcurrentLinkedQueue<ResponseHandler>();
	private boolean alive = true;

	/***************************************************************************
	 * Default ResponseQueueManager constructor.
	 **************************************************************************/
	public ResponseQueueManager() {
		super("ResponseQueueManager");
	}

	/**
	 * Starts the thread reading responses and adding them to the responseQueue
	 */
	public void run() {
		boolean res = true;
		do {
			try {
				Thread.sleep(sleep); // Wait 100 milli before alive again
				if (!openIGT_Queue.isEmpty()) {
					// On prefere perdre des impressions que rester coince
					ResponseHandler responseHandler = (ResponseHandler) openIGT_Queue
							.poll();
					if (responseHandler != null) {
						try {
							res = responseHandler.performResponse();
							if (!res)
								System.out.println("PB responseHandler ");
						} catch (AssertionError a) {
							//TODO Add error management
							System.out.println("PB responseHandler "
									+ a.getLocalizedMessage());
						} catch (Exception e) {
							//TODO Add error management
							System.out.println("PB responseHandler "
									+ e.getLocalizedMessage());
						} finally {
							System.out.println("OK");
						}
					} else {
						System.out.println("responseHandler null");
					}
				}
			} catch (InterruptedException e) {
				System.out.println("PB dans Thread " + e.getLocalizedMessage());
			}
		} while (alive && res);
	}

	/**
	 * add a new response to the response queue
	 * 
	 * @param header
	 * 
	 * @param body
	 * 
	 */
	public void addResponse(Header header, byte[] body) {
		if (header != null) {
			openIGT_Queue.add(new ResponseHandler(header, body));
		}
	}

	/**
	 * stop the thread
	 * 
	 */
	public void destroy() {
		alive = false;
	}

	/**
	 *** Gets the current sleep time value return@ The sleep time value
	 **/
	public long getSleepTime() {
		return sleep;
	}

	/**
	 * Sets the time the listener thread will wait between actions
	 * 
	 * @param sleep
	 */
	public void setSleepTime(long sleep) {
		this.sleep = sleep;
	}

	/**
	 *** Gets the current version return@ The version value
	 **/
	public String getVersion() {
		return VERSION;
	}
}
