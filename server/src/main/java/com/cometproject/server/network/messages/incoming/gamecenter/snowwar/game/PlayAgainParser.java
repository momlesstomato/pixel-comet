package com.cometproject.server.network.messages.incoming.gamecenter.snowwar.game;

import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.PlayerRematchesComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Describes play again parser behavior for the network message subsystem.
 */
public class PlayAgainParser implements Event {
  /**
   * Executes handle for this network message contract.
   *
   * @param client Client supplied by the caller.
   * @param msg Composer buffer that receives serialized protocol fields.
   * @throws Exception When the operation cannot complete.
   */
  public void handle(Session client, MessageEvent msg) throws Exception {
    SnowWarRoom room = client.snowWarPlayerData.currentSnowWar;

    if (room == null) {
      return;
    }

    room.broadcast(new PlayerRematchesComposer(client.getPlayer().getId()));
  }
}