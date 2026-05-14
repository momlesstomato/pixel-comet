package com.cometproject.server.game.nuxs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Describes NUX gift behavior for the Comet subsystem.
 */
public class NuxGift {
    /**
     * Enumerates reward type values used by the Comet subsystem.
     */
    public enum RewardType {
        ITEM,
        CURRENCY,
        BADGE,
        REWARD1,
        REWARD2,
        REWARD3,
        ROCK,
        PAPER,
        SCISSORS
    }

    private int id;
    private int pageType;
    private RewardType type;
    private String name;
    private String icon;
    private String productdata;
    private String currencyCode;
    private List<String> data = new ArrayList<>();

    /**
     * Creates a NUX gift instance for the Comet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param pageType Page type supplied by the caller.
     * @param icon Icon supplied by the caller.
     * @param name Name supplied by the caller.
     * @param productdata Productdata supplied by the caller.
     * @param data Data supplied by the caller.
     */
    public NuxGift(int id, String type, int pageType, String icon, String name, String productdata, String data){
        this.id = id;
        switch (type.toLowerCase()){
            case "item":
                this.type = RewardType.ITEM;
            break;
            case "reward1":
                this.type = RewardType.REWARD1;
                break;
            case "reward2":
                this.type = RewardType.REWARD2;
                break;
            case "reward3":
                this.type = RewardType.REWARD3;
                break;
            case "rock":
                this.type = RewardType.ROCK;
                break;
            case "paper":
                this.type = RewardType.PAPER;
                break;
            case "scissors":
                this.type = RewardType.SCISSORS;
                break;
            case "badge":
                this.type = RewardType.BADGE;
                break;
            default:
                this.type = RewardType.CURRENCY;
                this.currencyCode = type;
                break;
        }
        this.pageType = pageType;
        this.icon = icon;
        this.name = name;
        this.productdata = productdata;
        Collections.addAll(this.data, data.split(","));
    }

    /**
     * Returns the id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() { return this.id; }
    /**
     * Returns the page type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPageType() { return this.pageType; }
    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public RewardType getType() { return this.type; }
    /**
     * Returns the name for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() { return this.name; }
    /**
     * Returns the icon for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getIcon() { return this.icon; }
    /**
     * Returns the productdata for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getProductdata() { return this.productdata; }
    /**
     * Returns the currency code for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCurrencyCode() { return this.currencyCode; }
    /**
     * Returns the random data for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRandomData() {
        int max = this.data.size() - 1;
        int min = 0;

        return this.data.get( (int)Math.floor(Math.random() * (max - min)) + min );
    }
}
