/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.leaderboards;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.protocol.messages.MessageComposer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes last week leaderboard composer packets for the Snow War protocol layer.
 */
/*    */ public class LastWeekLeaderboardComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   List<GamePlayer> data;
/*    */   private int gameId;
/*    */   
/**
 * Creates a last week leaderboard composer instance for the Snow War game subsystem.
 *
 * @param gameId Game id supplied by the caller.
 */
/*    */   public LastWeekLeaderboardComposer(int gameId) {
/* 18 */     this.gameId = gameId;
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
/* 24 */     int i = 1;
/*    */     
/* 26 */     msg.writeInt(2018);
/* 27 */     msg.writeInt(0);
/* 28 */     msg.writeInt(0);
/* 29 */     msg.writeInt(-1);
/* 30 */     msg.writeInt(14);
/* 31 */     msg.writeInt(this.data.size());
/* 32 */     for (GamePlayer player : this.data) {
/* 33 */       msg.writeInt(player.getId());
/* 34 */       msg.writeInt(player.getPoints());
/* 35 */       msg.writeInt(i++);
/* 36 */       msg.writeString(player.getUsername());
/* 37 */       msg.writeString(player.getFigure());
/* 38 */       msg.writeString(player.getGender().toUpperCase());
/*    */     } 
/*    */     
/* 41 */     msg.writeInt(0);
/*    */     
/* 43 */     msg.writeInt(this.gameId);
/*    */   }
/*    */ 
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 48 */     return 188;
/*    */   }
/*    */ }