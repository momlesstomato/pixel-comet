/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.leaderboards;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.protocol.messages.MessageComposer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/**
 * Describes friends leaderboard composer packets for the Snow War protocol layer.
 */
/*    */ public class FriendsLeaderboardComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private int gameId;
/*    */   List<GamePlayer> data;
/*    */   
/**
 * Creates a friends leaderboard composer instance for the Snow War game subsystem.
 *
 * @param gameId Game id supplied by the caller.
 * @param playerId Player id supplied by the caller.
 */
/*    */   public FriendsLeaderboardComposer(int gameId, int playerId) {
/* 17 */     this.gameId = gameId;
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
/* 23 */     int i = 1;
/*    */     
/* 25 */     msg.writeInt(2012);
/* 26 */     msg.writeInt(0);
/* 27 */     msg.writeInt(0);
/* 28 */     msg.writeInt(0);
/* 29 */     msg.writeInt(23);
/*    */     
/* 31 */     msg.writeInt((this.data.size() > 3) ? 3 : this.data.size());
/* 32 */     for (GamePlayer player : this.data) {
/* 33 */       msg.writeInt(1);
/* 34 */       msg.writeInt(player.getPoints());
/* 35 */       msg.writeInt(i++);
/* 36 */       msg.writeString(player.getUsername());
/* 37 */       msg.writeString(player.getFigure());
/* 38 */       msg.writeString(player.getGender().toUpperCase());
/* 39 */       if (i == 4)
/*    */         break; 
/*    */     } 
/* 42 */     msg.writeInt(1);
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
/* 48 */     return 1376;
/*    */   }
/*    */ }