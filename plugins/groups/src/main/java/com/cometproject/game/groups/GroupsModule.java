package com.cometproject.game.groups;

import com.cometproject.api.caching.Cache;
import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.IGroupService;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.modules.BaseModule;
import com.cometproject.api.modules.PluginGuiceModule;
import com.cometproject.api.server.IGameService;
import com.cometproject.common.caching.LastReferenceCache;
import com.cometproject.game.groups.services.GroupService;
import com.cometproject.storage.api.StorageContext;

public class GroupsModule extends BaseModule {

    private GroupService groupService;

    public GroupsModule(ModuleConfig config, IGameService gameService) {
        super(config, gameService);
    }

    @Override
    public void setup() {
        final Cache<Integer, IGroupData> groupDataCache = new LastReferenceCache<>(
                60000, (86400 * 1000), null, this.getGameService().getExecutorService());

        final Cache<Integer, IGroup> groupCache = new LastReferenceCache<>(
                60000, (86400 * 1000),
                (id, group) -> {
                    group.dispose();
                }, this.getGameService().getExecutorService());

        this.groupService = new GroupService(groupCache, groupDataCache, null,
                StorageContext.getCurrentContext().getGroupRepository(),
                StorageContext.getCurrentContext().getGroupMemberRepository(),
                StorageContext.getCurrentContext().getGroupForumRepository());

//        this.registerMessage(new JoinGroupMessageEvent(this.messageHandler::joinGroup));
    }

    @Override
    public void initialiseServices(GameContext gameContext) {
        gameContext.setGroupService(this.groupService);
    }

    /**
     * Exposes the group service through the plugin child injector.
     *
     * @return The plugin Guice module for the groups plugin.
     */
    @Override
    public PluginGuiceModule getGuiceModule() {
        return new PluginGuiceModule(this.getGameService()) {
            @Override
            protected void configure() {
                bind(IGroupService.class).toInstance(GroupsModule.this.groupService);
                bind(GroupService.class).toInstance(GroupsModule.this.groupService);
                bind(IGameService.class).toInstance(getGameService());
                bind(GroupsModule.class).toInstance(GroupsModule.this);
            }
        };
    }
}

