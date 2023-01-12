package com.stephenspol.server.connect.util;

public class Trade {

    private Slot input1;
    private Slot input2;
    private Slot output;

    private boolean tradeDisabled;
    private int tradeUses;
    private int maxTradeUses;

    private int xp;

    private int specialPrice;
    private float priceMultiplier;
    private int demand;
    
    public Trade(Slot input1, Slot output, boolean tradeDisabled, int tradeUses, int maxTradeUses, int xp, int specialPrice, float priceMultiplier, int demand) {
        this(input1, output, null, tradeDisabled, tradeUses, maxTradeUses, xp, specialPrice, priceMultiplier, demand);
    }

    public Trade(Slot input1, Slot output, Slot input2, boolean tradeDisabled, int tradeUses, int maxTradeUses, int xp, int specialPrice, float priceMultiplier, int demand) {
        this.input1 = input1;
        this.output = output;
        this.input2 = input2;

        this.tradeDisabled = tradeDisabled;
        this.tradeUses = tradeUses;
        this.maxTradeUses = maxTradeUses;

        this.xp = xp;

        this.specialPrice = specialPrice;
        this.priceMultiplier = priceMultiplier;
        this.demand = demand;
    }

    public Slot getInput1() {
        return input1;
    }

    public Slot getInput2() {
        return input2;
    }

    public boolean hasInput2() {
        return input2 != null;
    }

    public Slot getOutput() {
        return output;
    }

    public boolean tradeDisabled() {
        return tradeDisabled;
    }

    public int getTradeUses() {
        return tradeUses;
    }

    public int getMaxTradeUses() {
        return maxTradeUses;
    }

    public int getXP() {
        return xp;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

    public float getPriceMultiplier() {
        return priceMultiplier;
    }

    public int getDemand() {
        return demand;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Input 1: " + input1 + ", Input 2: " + (hasInput2() ? input2 : "false") + ", Output: " + output);
        sb.append("\nTrade Disabled: " + tradeDisabled + ", Trade Uses: " + tradeUses + ", Max Trade Uses: " + maxTradeUses);
        sb.append("\nXP: " + xp + ", Special Price: " + specialPrice + ", Price Multiplier: " + priceMultiplier + ", Demand: " + demand);

        return sb.toString();
    }
    
}
