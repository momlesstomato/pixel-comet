package com.cometproject.catalogtool;

import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.config.ConfigurationBootstrap;
import com.cometproject.server.storage.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes catalog tool behavior for the tooling subsystem.
 */
public class CatalogTool {
    private static Logger LOGGER = LoggerFactory.getLogger(CatalogTool.class.getName());

    /**
     * Executes main for this tooling contract.
     *
     * @param args Args supplied by the caller.
     */
    public static void main(String[] args) {
        if(args.length != 2) {
            LOGGER.warn("Invalid arguments, expecting pageId and furniline");
        }

        final int pageId = Integer.parseInt(args[0]);
        final String furniline = args[1];

        ConfigurationBootstrap.initialize(null);

        StorageManager.getInstance().initialize();
        ItemManager.getInstance().initialize();
        CatalogManager.getInstance().initialize();

        //parse xml
        // loop through every item with furniline = furniline and create definitions where needed
        // and create catalog items
    }
}
