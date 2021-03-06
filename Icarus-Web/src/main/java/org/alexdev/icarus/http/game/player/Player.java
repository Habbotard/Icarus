package org.alexdev.icarus.http.game.player;

import org.alexdev.icarus.http.util.Util;

public class Player {


    private int id;
    private String name;
    private String figure;
    private int credits;
    private int duckets;
    private String email;
    private String mission;
    private long lastOnline;
    private String ssoTicket;
    private int rank;
    private long joinDate;

    public Player(int id, String username, String figure, int credits, int duckets, String email, String mission, long lastOnline, int rank, long joinDate) {
        this.id = id;
        this.name = username;
        this.figure = figure;
        this.credits = credits;
        this.duckets = duckets;
        this.email = email;
        this.mission = mission;
        this.lastOnline = lastOnline;
        this.rank = rank;
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getDuckets() {
        return duckets;
    }

    public void setDuckets(int duckets) {
        this.duckets = duckets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getMission() {
        return mission;
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public String getReadableLastOnline() {
        return Util.getDateAsString(this.lastOnline);
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getSsoTicket() {
        return ssoTicket;
    }

    public void setSsoTicket(String ssoTicket) {
        this.ssoTicket = ssoTicket;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean hasHouskeeping() {
        return this.rank > 5;
    }

    public long getJoinDate() {
        return joinDate;
    }

    public String getReadableJoinDate() {
        return Util.getDateAsString(this.joinDate);
    }

    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }
}
