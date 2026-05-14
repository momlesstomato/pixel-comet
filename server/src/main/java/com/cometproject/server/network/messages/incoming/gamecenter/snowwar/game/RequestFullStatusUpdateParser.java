/*    */ package com.cometproject.server.network.messages.incoming.gamecenter.snowwar.game;
/*    */ 
/*    */ import com.cometproject.games.snowwar.SnowWarRoom;
/*    */ import com.cometproject.server.network.messages.incoming.Event;
/*    */ import com.cometproject.server.network.sessions.Session;
/*    */ import com.cometproject.server.protocol.messages.MessageEvent;
/*    */ 
/**
 * Describes request full status update parser behavior for the Snow War game subsystem.
 */
/*    */ public class RequestFullStatusUpdateParser
/*    */   implements Event
/*    */ {
/**
 * Executes handle for this network message contract.
 *
 * @param client Client supplied by the caller.
 * @param msg Composer buffer that receives serialized protocol fields.
 * @throws Exception When the operation cannot complete.
 */
/*    */   public void handle(Session client, MessageEvent msg) throws Exception {
/* 12 */     SnowWarRoom room = client.snowWarPlayerData.currentSnowWar;
/* 13 */     if (room == null) {
/*    */       return;
/*    */     }
/*    */     
/* 17 */     room.fullGameStatusQueue.add(client.getChannel());
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\incoming\gamecenter\snowwar\game\RequestFullStatusUpdateParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */