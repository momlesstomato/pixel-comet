package com.cometproject.storage.api.data.currency;

/**
 * Defines stable business use-case keys that resolve to configured currency definitions.
 *
 * <p>These keys are stored in {@code currency_use_cases}. Gameplay code should use these
 * constants instead of protocol aliases so operators can remap feature currencies without code
 * changes.
 */
public final class CurrencyUseCases {
    public static final String ONLINE_REWARD_PRIMARY = "online_reward.primary";
    public static final String ONLINE_REWARD_CLUB = "online_reward.club";
    public static final String CATALOG_PROMOTION_PRIMARY = "catalog.promotion.primary";
    public static final String CATALOG_PROMOTION_SECONDARY = "catalog.promotion.secondary";
    public static final String CAMERA_SHARE = "camera.share";
    public static final String FIREWORKS_PURCHASE = "fireworks.purchase";
    public static final String CASINO_BET = "casino.bet";
    public static final String CASINO_PAYOUT = "casino.payout";
    public static final String CASINO_PAYOUT_PRIMARY = "casino.payout.primary";
    public static final String CASINO_PAYOUT_SECONDARY = "casino.payout.secondary";
    public static final String BANK_TRANSFER_PRIMARY = "bank.transfer.primary";
    public static final String BANK_TRANSFER_SECONDARY = "bank.transfer.secondary";
    public static final String ROOM_PURCHASE = "room.purchase";
    public static final String QUEST_REWARD_PRIMARY = "quest.reward.primary";
    public static final String QUEST_REWARD_SECONDARY = "quest.reward.secondary";
    public static final String QUEST_REWARD_SPECIAL = "quest.reward.special";
    public static final String CALENDAR_REWARD_PRIMARY = "calendar.reward.primary";
    public static final String CALENDAR_REWARD_SECONDARY = "calendar.reward.secondary";
    public static final String CALENDAR_REWARD_SPECIAL = "calendar.reward.special";
    public static final String CRACKABLE_REWARD_PRIMARY = "crackable.reward.primary";
    public static final String CRACKABLE_REWARD_SECONDARY = "crackable.reward.secondary";
    public static final String REWARD_COMMAND_PRIMARY = "reward_command.primary";
    public static final String REWARD_COMMAND_SECONDARY = "reward_command.secondary";
    public static final String POLL_REWARD_PRIMARY = "poll.reward.primary";
    public static final String POLL_REWARD_SECONDARY = "poll.reward.secondary";
    public static final String SUBSCRIPTION_REWARD_PRIMARY = "subscription.reward.primary";
    public static final String SUBSCRIPTION_REWARD_SECONDARY = "subscription.reward.secondary";
    public static final String EXCHANGE_PRIMARY = "exchange.primary";
    public static final String EXCHANGE_SECONDARY = "exchange.secondary";
    public static final String BATTLE_PASS_REWARD = "battle_pass.reward";
    public static final String STAFF_BUNDLE_PRIMARY = "staff_bundle.primary";
    public static final String STAFF_EVENT_PRIMARY = "staff_event.primary";
    public static final String STAFF_EVENT_SECONDARY = "staff_event.secondary";
    public static final String STAFF_EVENT_SPECIAL = "staff_event.special";
    public static final String WIRED_CONDITION_PRIMARY = "wired.condition.primary";
    public static final String WIRED_CONDITION_SECONDARY = "wired.condition.secondary";
    private CurrencyUseCases() {
    }
}
