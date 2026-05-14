/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/*    */
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes user blocked composer packets for the Snow War protocol layer.
 */
/*    */ public class UserBlockedComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private final int snowWarBlockedGame;
/*    */   
/**
 * Creates a user blocked composer instance for the Snow War game subsystem.
 *
 * @param snowWarBlockedGame Snow war blocked game supplied by the caller.
 */
/*    */   public UserBlockedComposer(int snowWarBlockedGame) {
/* 18 */     this.snowWarBlockedGame = snowWarBlockedGame;
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
/* 23 */     msg.writeInt(this.snowWarBlockedGame);
/*    */   }
/*    */ 
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 28 */     return Composers.SnowStormStartBlockTickerComposer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\UserBlockedComposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */