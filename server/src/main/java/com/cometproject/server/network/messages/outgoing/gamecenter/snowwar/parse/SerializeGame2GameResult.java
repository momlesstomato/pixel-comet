/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.games.snowwar.SnowWarRoom;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes serialize game2 game result behavior for the Snow War game subsystem.
 */
/*    */ public class SerializeGame2GameResult
/*    */ {
/**
 * Executes parse for this network message contract.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 * @param arena Arena supplied by the caller.
 */
/*    */   public static void parse(IComposer msg, SnowWarRoom arena) {
/* 11 */     msg.writeBoolean(true);
/* 12 */     msg.writeInt(arena.Result);
/* 13 */     msg.writeInt(arena.Winner);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\parse\SerializeGame2GameResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */