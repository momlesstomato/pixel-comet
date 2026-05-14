/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes serialize game2 player stats data behavior for the Snow War game subsystem.
 */
/*    */ public class SerializeGame2PlayerStatsData
/*    */ {
/**
 * Executes parse for this network message contract.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 * @param Player Player supplied by the caller.
 */
/*    */   public static void parse(IComposer msg, HumanGameObject Player) {
/* 11 */     msg.writeInt(Player.score);
/* 12 */     msg.writeInt(Player.kills);
/* 13 */     msg.writeInt(0);
/* 14 */     msg.writeInt(Player.hits);
/* 15 */     msg.writeInt(0);
/* 16 */     msg.writeInt(0);
/* 17 */     msg.writeInt(0);
/* 18 */     msg.writeInt(0);
/* 19 */     msg.writeInt(0);
/* 20 */     msg.writeInt(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\parse\SerializeGame2PlayerStatsData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */