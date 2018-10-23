package com.example.raahymasif.cmpt498.Model;

public class CreatePosts {
    private String Id;
    private String Info;
    private String Location;
    private String Players;
    private String Sport;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPlayers() {
        return Players;
    }

    public void setPlayers(String players) {
        Players = players;
    }

    public String getSport() {
        return Sport;
    }

    public void setSport(String sport) {
        Sport = sport;
    }

    public CreatePosts(){

    }

    public CreatePosts(String info, String location, String players, String sport){ //String username) {
        //this.Id = id;
        this.Info = info;
        this.Location = location;
        this.Players = players;
        this.Sport = sport;
    }

}
