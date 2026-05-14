/*    */ package com.cometproject.server.network.messages.incoming.gamecenter.snowwar.account;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IMessageComposer;
/*    */ import com.cometproject.server.network.messages.incoming.Event;
/*    */ import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.UserBlockedComposer;
/*    */ import com.cometproject.server.network.sessions.Session;
/*    */ import com.cometproject.server.protocol.messages.MessageEvent;
/*    */ 
/**
 * Describes get user blocked parser behavior for the Snow War game subsystem.
 */
/*    */ public class GetUserBlockedParser
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
/* 13 */     client.send((IMessageComposer)new UserBlockedComposer(0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\incoming\gamecenter\snowwar\account\GetUserBlockedParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */