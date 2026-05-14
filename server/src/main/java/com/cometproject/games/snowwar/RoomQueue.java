/*    */ package com.cometproject.games.snowwar;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IMessageComposer;
/*    */ import com.cometproject.server.network.sessions.Session;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes room queue behavior for the Snow War game subsystem.
 */
/*    */ public class RoomQueue
/*    */ {
/*    */   public SnowWarRoom room;
/* 13 */   public final Map<Integer, Session> players = new ConcurrentHashMap<>(10);
/*    */   
/**
 * Creates a room queue instance for the Snow War game subsystem.
 *
 * @param snowRoom Snow room supplied by the caller.
 */
/*    */   public RoomQueue(SnowWarRoom snowRoom) {
/* 16 */     this.room = snowRoom;
/*    */   }
/*    */   
/**
 * Executes broadcast for this Snow War game contract.
 *
 * @param Message Message supplied by the caller.
 */
/*    */   public void broadcast(IMessageComposer Message) {
/* 20 */     for (Session cn : this.players.values())
/* 21 */       cn.getChannel().writeAndFlush(Message); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\RoomQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */