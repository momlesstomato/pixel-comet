package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.landing.types.PromoArticle;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the promo articles message for the Pixel Protocol client.
 */
public class PromoArticlesMessageComposer extends MessageComposer {
    private final Map<Integer, PromoArticle> articles;

    /**
     * Creates a promo articles message composer instance for the network message subsystem.
     *
     * @param articles Articles supplied by the caller.
     */
    public PromoArticlesMessageComposer(final Map<Integer, PromoArticle> articles) {
        this.articles = articles;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PromoArticlesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(articles.size());

        for (PromoArticle article : articles.values()) {
            msg.writeInt(article.getId());
            msg.writeString(article.getTitle());
            msg.writeString(article.getMessage());
            msg.writeString(article.getButtonText());
            msg.writeInt(!article.getButtonLink().contains("http") ? 1 : article.getButtonLink().isEmpty() ? 2 : 0); // Button Type
            msg.writeString(article.getButtonLink());
            msg.writeString(article.getImagePath());
        }
    }
}
