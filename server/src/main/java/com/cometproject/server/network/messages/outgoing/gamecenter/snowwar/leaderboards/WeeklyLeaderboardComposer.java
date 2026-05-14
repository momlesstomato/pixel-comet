/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.leaderboards;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.protocol.messages.MessageComposer;
/*    */ import java.util.List;
/*    */ 
/**
 * Describes weekly leaderboard composer packets for the Snow War protocol layer.
 */
/*    */ public class WeeklyLeaderboardComposer
/*    */   extends MessageComposer {
/*    */   List<GamePlayer> data;
/*    */   private int gameId;
/*    */   
/**
 * Creates a weekly leaderboard composer instance for the Snow War game subsystem.
 *
 * @param gameId Game id supplied by the caller.
 */
/*    */   public WeeklyLeaderboardComposer(int gameId) {
/* 15 */     this.gameId = gameId;
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
/* 21 */     int i = 1;
/*    */     
/* 23 */     msg.writeInt(2012);
/* 24 */     msg.writeInt(0);
/* 25 */     msg.writeInt(0);
/* 26 */     msg.writeInt(0);
/* 27 */     msg.writeInt(23);
/* 28 */     msg.writeInt(this.data.size());
/* 29 */     for (GamePlayer player : this.data) {
/* 30 */       msg.writeInt(player.getId());
/* 31 */       msg.writeInt(player.getPoints());
/* 32 */       msg.writeInt(i++);
/* 33 */       msg.writeString(player.getUsername());
/* 34 */       msg.writeString(player.getFigure());
/* 35 */       msg.writeString(player.getGender().toUpperCase());
/*    */     } 
/* 37 */     msg.writeInt(0);
/* 38 */     msg.writeInt(this.gameId);
/*    */   }
/*    */ 
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 43 */     return 855;
/*    */   }
/*    */ }