package org.d3.core.session;

import java.util.Collection;

import org.d3.core.packet.Packet;

public interface Room {
	
	public void playerPrepare();
	
	public void playerUnPrepare();
	
	public void startGame();
	
	public void broadcast(Packet pkt);
	
//	public boolean addSession(PlayerSession session);
//	
//	public void removeSession(PlayerSession session);
	
	public boolean joinRoom(PlayerSession session);
	
	public void leaveRoom(PlayerSession session);
	
	public Collection<Player> getPlayers();
	
}
