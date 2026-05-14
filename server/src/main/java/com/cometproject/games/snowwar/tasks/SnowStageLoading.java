/*    */ package com.cometproject.games.snowwar.tasks;
/*    */ 
/*    */

import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.StageStillLoadingComposer;

import java.util.Collection;

/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ 
/*    */ 
/**
 * Describes snow stage loading behavior for the Snow War game subsystem.
 */
/*    */ public class SnowStageLoading
/*    */ {
/**
 * Executes exec for this task scheduling contract.
 *
 * @param room Room participating in the operation.
 */
/*    */   public static void exec(SnowWarRoom room) {
/* 14 */     Collection<HumanGameObject> playersLoaded = room.getStageLoadedPlayers();
/*    */     
/* 16 */     if (playersLoaded != null) {
/* 17 */       room.broadcast(new StageStillLoadingComposer(playersLoaded));
/*    */       
/* 19 */       if (!playersLoaded.isEmpty()) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 24 */     for (HumanGameObject player : room.players.values()) {
/* 25 */       if (!player.stageLoaded) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 30 */     room.STATUS = 3;
/*    */   }
/*    */ }