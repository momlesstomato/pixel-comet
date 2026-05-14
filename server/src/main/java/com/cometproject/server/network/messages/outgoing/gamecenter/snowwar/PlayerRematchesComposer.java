package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes player rematches composer behavior for the network message subsystem.
 */
/*    */ public class PlayerRematchesComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private final int playerId;
/*    */   
/**
 * Creates a player rematches composer instance for the Snow War game subsystem.
 *
 * @param playerId Player id supplied by the caller.
 */
/*    */   public PlayerRematchesComposer(int playerId) {
/* 17 */     this.playerId = playerId;
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
/* 22 */     msg.writeInt(this.playerId);
/*    */   }
/*    */
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 27 */     return Composers.SnowStormUserRematchedComposer;
/*    */   }
/*    */ }
