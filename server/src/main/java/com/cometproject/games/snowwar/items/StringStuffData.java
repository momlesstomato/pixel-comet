/*    */ package com.cometproject.games.snowwar.items;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
/*    */ 
/*    */ 
/**
 * Describes string stuff data behavior for the Snow War game subsystem.
 */
/*    */ public class StringStuffData
/*    */   extends ExtraDataBase
/*    */ {
/*    */   public static final int TYPE_ID = 0;
/*    */   public String extraData;
/*    */   
/**
 * Returns the type for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int getType() {
/* 13 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/**
 * Creates a string stuff data instance for the Snow War game subsystem.
 *
 * @param data Data supplied by the caller.
 */
/*    */   public StringStuffData(StuffDataReader data) {
/* 19 */     if (data == null) {
/* 20 */       this.extraData = "";
/*    */     } else {
/* 22 */       this.extraData = data.readString();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/**
 * Executes data for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public byte[] data() {
/* 28 */     if (this.extraData.isEmpty())
/*    */     {
/* 30 */       return null;
/*    */     }
/*    */     
/* 33 */     StuffDataWriter data = new StuffDataWriter(0);
/* 34 */     data.writeString(this.extraData);
/* 35 */     return data.getData();
/*    */   }
/*    */ 
/*    */   
/**
 * Updates the extra data for this Snow War game contract.
 *
 * @param data Data supplied by the caller.
 */
/*    */   public void setExtraData(Object data) {
                if (data instanceof Integer) {
                    extraData = Integer.toString((Integer) data);
                } else {
                    extraData = (String) data;
                }

    /*    */   }
/*    */ 
/*    */   
/**
 * Returns the wall legacy string for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public String getWallLegacyString() {
/* 49 */     return this.extraData;
/*    */   }
/*    */ 
/*    */   
/**
 * Executes serialize composer for this Snow War game contract.
 *
 * @param writer Writer supplied by the caller.
 */
/*    */   public void serializeComposer(IComposer writer) {
/* 54 */     writer.writeString(this.extraData);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\items\StringStuffData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */