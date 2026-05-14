/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes joining game failed composer behavior for the network message subsystem.
 */
/*    */ public class JoiningGameFailedComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private final int errorCode;
/*    */   
/**
 * Creates a joining game failed composer instance for the Snow War game subsystem.
 *
 * @param errorCode Error code supplied by the caller.
 */
/*    */   public JoiningGameFailedComposer(int errorCode) {
/* 17 */     this.errorCode = errorCode;
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
/* 22 */     msg.writeInt(this.errorCode);
/*    */   }
/*    */ 
/*    */   @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 27 */     return 0;
/*    */   }
/*    */ }