/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
/*    */ 
/*    */ import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/*    */
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes stage load composer packets for the Snow War protocol layer.
 */
/*    */ public class StageLoadComposer
/*    */   extends MessageComposer
/*    */ {
    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
/*    */   public void compose(IComposer msg) {
/* 17 */     msg.writeInt(0);
/*    */   }
/*    */ 
/*    */       @Override
/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public short getId() {
/* 22 */     return Composers.SnowStageLoadComposer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\server\network\messages\outgoing\gamecenter\snowwar\StageLoadComposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */