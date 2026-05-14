/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.leaderboards;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/*    */
/**
 * Describes lucky looser composer packets for the Snow War protocol layer.
 */
/*    */ public class LuckyLooserComposer
/*    */   extends MessageComposer {
/*    */   private int gameId;
/*    */   
/**
 * Creates a lucky looser composer instance for the Snow War game subsystem.
 *
 * @param gameId Game id supplied by the caller.
 */
/*    */   public LuckyLooserComposer(int gameId) {
/* 11 */     this.gameId = gameId;
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
/* 16 */     msg.writeInt(2012);
/* 17 */     msg.writeInt(1);
/* 18 */     msg.writeInt(0);
/* 19 */     msg.writeInt(1);
/* 20 */     msg.writeInt(22);
/* 21 */     msg.writeInt(1);
/* 22 */     msg.writeString("Custom");
/* 23 */     msg.writeString("ch-235-1408.hd-3095-14.lg-3116-85-1408.sh-3115-1408-1408.ca-1805-64.ha-1002-1408");
/* 24 */     msg.writeString("F");
/* 25 */     msg.writeInt(1);
/* 26 */     msg.writeInt(1302);
/*    */   }
/*    */ 
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 31 */     return 3824;
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\leaderboards\LuckyLooserComposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */