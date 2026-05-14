package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes player exited game arena composer behavior for the network message subsystem.
 */
/*    */ public class PlayerExitedGameArenaComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private final HumanGameObject player;
/*    */   
/**
 * Creates a player exited game arena composer instance for the Snow War game subsystem.
 *
 * @param player Player supplied by the caller.
 */
/*    */   public PlayerExitedGameArenaComposer(HumanGameObject player) {
/* 18 */     this.player = player;
/*    */   }
/*    */ 
/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 */
/*    */   @Override
/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 */
/*    */   public void compose(IComposer msg) {
/* 23 */     msg.writeInt(this.player.userId);
/* 24 */     msg.writeInt(this.player.objectId);
/*    */   }

/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 29 */     return Composers.SnowStormOnPlayerExitedArenaComposer;
/*    */   }
/*    */ }
