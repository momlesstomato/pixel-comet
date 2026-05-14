/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.games.snowwar.ComposerShit;
/*    */ import com.cometproject.games.snowwar.MessageWriter;
/*    */ import com.cometproject.games.snowwar.gameevents.MakeSnowBall;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes serialize game2 event pick snow ball behavior for the Snow War game subsystem.
 */
/*    */ public class SerializeGame2EventPickSnowBall
/*    */ {
/**
 * Executes parse for this network message contract.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 * @param evt Evt supplied by the caller.
 */
/*    */   public static void parse(IComposer msg, MakeSnowBall evt) {
/* 13 */     msg.writeInt(evt.player.objectId);
/*    */   }
/*    */   
/**
 * Executes parse for this Snow War game contract.
 *
 * @param ClientMessage Client message supplied by the caller.
 * @param evt Evt supplied by the caller.
 */
/*    */   public static void parse(MessageWriter ClientMessage, MakeSnowBall evt) {
/* 17 */     ComposerShit.add(evt.player.objectId, ClientMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\parse\SerializeGame2EventPickSnowBall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */