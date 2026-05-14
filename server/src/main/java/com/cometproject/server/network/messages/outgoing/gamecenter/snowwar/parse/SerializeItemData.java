/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ import com.cometproject.games.snowwar.items.BaseItem;
/*    */ import com.cometproject.games.snowwar.items.Item;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes serialize item data behavior for the Snow War game subsystem.
 */
/*    */ public class SerializeItemData
/*    */ {
/**
 * Executes parse for this network message contract.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 * @param baseItem Base item supplied by the caller.
 * @param item Item supplied by the caller.
 */
/*    */   public static void parse(IComposer msg, BaseItem baseItem, Item item) {
/* 12 */     msg.writeInt(item.extraData.getType());
/* 13 */     item.extraData.serializeComposer(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\parse\SerializeItemData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */