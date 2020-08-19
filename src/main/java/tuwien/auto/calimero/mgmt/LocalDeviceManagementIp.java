/*
    Calimero 2 - A library for KNX network access
    Copyright (c) 2006, 2020 B. Malinowsky

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Linking this library statically or dynamically with other modules is
    making a combined work based on this library. Thus, the terms and
    conditions of the GNU General Public License cover the whole
    combination.

    As a special exception, the copyright holders of this library give you
    permission to link this library with independent modules to produce an
    executable, regardless of the license terms of these independent
    modules, and to copy and distribute the resulting executable under terms
    of your choice, provided that you also meet, for each linked independent
    module, the terms and conditions of the license of that module. An
    independent module is a module which is not derived from or based on
    this library. If you modify this library, you may extend this exception
    to your version of the library, but you are not obligated to do so. If
    you do not wish to do so, delete this exception statement from your
    version.
*/

package tuwien.auto.calimero.mgmt;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.cemi.CEMIDevMgmt;
import tuwien.auto.calimero.knxnetip.Connection;
import tuwien.auto.calimero.knxnetip.Connection.SecureSession;
import tuwien.auto.calimero.knxnetip.KNXConnectionClosedException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection.BlockingMode;
import tuwien.auto.calimero.knxnetip.KNXnetIPDevMgmt;
import tuwien.auto.calimero.knxnetip.SecureConnection;

/**
 * Adapter for KNX property services using KNXnet/IP local device management.
 *
 * @see KNXnetIPDevMgmt
 */
public class LocalDeviceManagementIp extends LocalDeviceManagement {

	private final KNXnetIPConnection conn;

	/**
	 * Creates a new property service adapter for local device management of a KNXnet/IP server using TCP.
	 *
	 * @param connection the TCP connection to the server
	 * @param adapterClosed receives the notification if the adapter got closed
	 * @return a new local device management connection
	 * @throws KNXException on failure establishing local device management connection or failure while initializing the
	 *         property adapter
	 * @throws InterruptedException on interrupted thread while initializing the adapter
	 */
	public static LocalDeviceManagementIp newAdapter(final Connection connection,
			final Consumer<CloseEvent> adapterClosed) throws KNXException, InterruptedException {
		final var mgmt = new KNXnetIPDevMgmt(connection);
		return new LocalDeviceManagementIp(mgmt, adapterClosed, false);
	}

	/**
	 * Creates a new property service adapter for local device management of a KNXnet/IP server using UDP.
	 * <p>
	 * A note on write-enabled / read-only properties:<br>
	 * The check whether a property is read only or write enabled, is done by issuing a write request for that property.
	 * Due to the memory layout, write cycles of a memory location and similar, this might not always be desired. To
	 * enable or skip this check, <code>queryWriteEnable</code> has to be set appropriately. Currently, the write
	 * enabled check is only of interest when getting a property description {@link #getDescription(int, int, int)}.
	 *
	 * @param localEP the local endpoint of the connection, supply the wildcard address to use a local IP on the same
	 *        subnet as <code>serverCtrlEP</code> and an unused (ephemeral) port
	 * @param serverCtrlEP the remote server control endpoint used for connect request
	 * @param useNat <code>true</code> to use network address translation (NAT) aware communication, <code>false</code>
	 *        to use the default way
	 * @param queryWriteEnable <code>true</code> to check whether a property is write-enabled or read-only,
	 *        <code>false</code> to skip the check
	 * @param adapterClosed receives the notification if the adapter got closed
	 * @return a new local device management connection
	 * @throws KNXException on failure establishing local device management connection or failure while initializing the
	 *         property adapter
	 * @throws InterruptedException on interrupted thread while initializing the adapter
	 */
	public static LocalDeviceManagementIp newAdapter(final InetSocketAddress localEP,
			final InetSocketAddress serverCtrlEP, final boolean useNat, final boolean queryWriteEnable,
			final Consumer<CloseEvent> adapterClosed) throws KNXException, InterruptedException {
		return new LocalDeviceManagementIp(localEP, serverCtrlEP, useNat, adapterClosed, queryWriteEnable);
	}

	/**
	 * Creates a new secure property service adapter for local device management of a KNXnet/IP server using the TCP
	 * connection of the supplied secure session.
	 *
	 * @param session secure session with a KNXnet/IP server
	 * @param adapterClosed receives the notification if the adapter got closed
	 * @return a new local device management connection
	 * @throws KNXException if establishing the local device management connection or initializing the property adapter
	 *         fails
	 * @throws InterruptedException on interrupted thread while initializing the adapter
	 */
	public static LocalDeviceManagementIp newSecureAdapter(final SecureSession session,
			final Consumer<CloseEvent> adapterClosed) throws KNXException, InterruptedException {
		final var mgmt = SecureConnection.newDeviceManagement(session);
		return new LocalDeviceManagementIp(mgmt, adapterClosed, false);
	}

	public static LocalDeviceManagementIp newSecureAdapter(final InetSocketAddress localEP,
			final InetSocketAddress serverCtrlEP, final boolean useNat, final byte[] deviceAuthCode,
			final byte[] userKey, final Consumer<CloseEvent> adapterClosed) throws KNXException, InterruptedException {
		final KNXnetIPConnection mgmt = SecureConnection.newDeviceManagement(localEP, serverCtrlEP, useNat,
				deviceAuthCode, userKey);
		return new LocalDeviceManagementIp(mgmt, adapterClosed, false);
	}


	/**
	 * Creates a new property service adapter for local device management.
	 * <p>
	 * A note on write-enabled / read-only properties:<br>
	 * The check whether a property is read-only or write-enabled is done by issuing a write
	 * request for that property. Due to the memory layout of a KNX device, write cycles of a memory location, and
	 * similar, this might not always be desired. To enable or skip this check, the
	 * <code>queryWriteEnable</code> parameter has to be set appropriately. Currently, the write
	 * enabled check is only of interest when getting a property description
	 * {@link #getDescription(int, int, int)}.
	 *
	 * @param localEP the local endpoint of the connection, supply the wildcard address to use a
	 *        local IP on the same subnet as <code>serverCtrlEP</code> and an unused (ephemeral) port
	 * @param serverCtrlEP the remote server control endpoint used for connect request
	 * @param useNat <code>true</code> to use a network address translation (NAT) aware communication
	 *        mechanism, <code>false</code> to use the default way
	 * @param adapterClosed receives the notification about an adapter close event
	 * @param queryWriteEnable <code>true</code> to check whether a property is write-enabled or
	 *        read-only, <code>false</code> to skip that check
	 * @throws KNXException on failure establishing the local device management connection or failure
	 *         while initializing the property adapter
	 * @throws InterruptedException on interrupted thread while initializing the adapter
	 */
	protected LocalDeviceManagementIp(final InetSocketAddress localEP, final InetSocketAddress serverCtrlEP,
		final boolean useNat, final Consumer<CloseEvent> adapterClosed, final boolean queryWriteEnable)
		throws KNXException, InterruptedException {
		this(create(localEP, serverCtrlEP, useNat), adapterClosed, queryWriteEnable);
	}

	LocalDeviceManagementIp(final KNXnetIPConnection mgmt, final Consumer<CloseEvent> adapterClosed,
		final boolean queryWriteEnable) throws KNXException, InterruptedException {
		super(mgmt, adapterClosed, queryWriteEnable);
		conn = mgmt;
		conn.addConnectionListener(new KNXListenerImpl());
		init();
	}

	/**
	 * Sends a reset request to the KNXnet/IP server. A successful reset request causes the KNXnet/IP server to close
	 * the KNXnet/IP device management connection.
	 *
	 * @throws KNXConnectionClosedException on closed connection
	 * @throws KNXTimeoutException if a timeout regarding a response message was encountered
	 * @throws InterruptedException on thread interrupt
	 */
	public void reset() throws KNXConnectionClosedException, KNXTimeoutException, InterruptedException {
		send(new CEMIDevMgmt(CEMIDevMgmt.MC_RESET_REQ), BlockingMode.WaitForAck);
	}

	/**
	 * The name for this adapter starts with "Local-DM " + KNXnet/IP server control endpoint, allowing
	 * easier distinction of adapter types.
	 */
	@Override
	public String getName() {
		final InetSocketAddress remote = conn.getRemoteAddress();
		return "Local-DM " + remote.getAddress().getHostAddress() + ":" + remote.getPort();
	}

	@Override
	protected void send(final CEMIDevMgmt frame, final Object mode)
			throws KNXTimeoutException, KNXConnectionClosedException, InterruptedException {
		conn.send(frame, (BlockingMode) mode);
	}

	private static KNXnetIPDevMgmt create(final InetSocketAddress localEP, final InetSocketAddress serverCtrlEP,
			final boolean useNat) throws KNXException, InterruptedException {
		return new KNXnetIPDevMgmt(localEP, serverCtrlEP, useNat);
	}
}
