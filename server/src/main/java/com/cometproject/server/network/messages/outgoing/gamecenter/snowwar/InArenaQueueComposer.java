/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/*    */
/**
 * Describes in arena queue composer packets for the Snow War protocol layer.
 */
/*    */ public class InArenaQueueComposer
/*    */   extends MessageComposer
/*    */ {
/*    */   private final int position;
/*    */   
/**
 * Creates a in arena queue composer instance for the Snow War game subsystem.
 *
 * @param position Position supplied by the caller.
 */
/*    */   public InArenaQueueComposer(int position) {
/* 12 */     this.position = position;
/*    */   }
/*    */ 
/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 */
/*    */       @Override
/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 */
/*    */   public void compose(IComposer msg) {
/* 17 */     msg.writeInt(this.position);
/*    */   }
        /**
         * Returns the id for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
/*    */   public short getId() {
/* 21 */     return Composers.SnowInArenaQueueMessageComposer;
/*    */   }
/*    */ }
