package com.xingguang.sinanya.entity;

/**
 * @author SitaNya
 * @date 2019/9/10
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class EntityGroupSwitch {
    boolean jrrp;
    boolean npc;
    boolean welcome;
    boolean gas;
    boolean bg;
    boolean tz;
    boolean simple;
    boolean ob;
    boolean deck;

    public EntityGroupSwitch() {
        this.jrrp = true;
        this.npc = true;
        this.welcome = true;
        this.gas = true;
        this.bg = true;
        this.tz = true;
        this.simple = false;
        this.ob = true;
        this.deck = true;
    }

    public boolean isDeck() {
        return deck;
    }

    public void setDeck(boolean deck) {
        this.deck = deck;
    }

    public boolean isOb() {
        return ob;
    }

    public void setOb(boolean ob) {
        this.ob = ob;
    }

    public boolean isJrrp() {
        return jrrp;
    }

    public void setJrrp(boolean jrrp) {
        this.jrrp = jrrp;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean npc) {
        this.npc = npc;
    }

    public boolean isWelcome() {
        return welcome;
    }

    public void setWelcome(boolean welcome) {
        this.welcome = welcome;
    }

    public boolean isGas() {
        return gas;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public boolean isBg() {
        return bg;
    }

    public void setBg(boolean bg) {
        this.bg = bg;
    }

    public boolean isTz() {
        return tz;
    }

    public void setTz(boolean tz) {
        this.tz = tz;
    }

    public boolean isSimple() {
        return simple;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }
}
