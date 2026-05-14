package com.cometproject.server.game.pets.data;

import com.cometproject.server.utilities.RandomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes pet speech behavior for the pet subsystem.
 */
public class PetSpeech {
    private Map<PetMessageType, List<String>> messages;

    /**
     * Creates a pet speech instance for the pet subsystem.
     */
    public PetSpeech() {
        this.messages = new HashMap<>();
    }

    /**
     * Returns the message by type for this pet contract.
     *
     * @param type Type supplied by the caller.
     * @return Value exposed by the contract.
     */
    public String getMessageByType(PetMessageType type) {
        final List<String> availableMessages = messages.get(type);

        if (availableMessages == null || availableMessages.size() == 0) {
            return null;
//
//            final String genericPetMsg = PetManager.getInstance().getSpeech(-1).getMessageByType(type);
//
//            if (genericPetMsg != null) {
//                return genericPetMsg;
//            }
//
//            return null;
        }

        int index = RandomUtil.getRandomInt(0, availableMessages.size() - 1);
        return availableMessages.get(index);
    }

    /**
     * Returns the messages for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<PetMessageType, List<String>> getMessages() {
        return messages;
    }
}
