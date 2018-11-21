package com.example.raahymasif.cmpt498.Model;

public class CreatePosts {
    private String Id;
    private String Info;
    private String Location;
    private String Players;
    private String Sport;
    private String PostedBy;
    private String UsersJoined;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String postedBy) {
        PostedBy = postedBy;
    }

    public String getUsersJoined() {
        return UsersJoined;
    }

    public void setUsersJoined(String usersJoined) {
        UsersJoined = usersJoined;
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

    public CreatePosts(String info, String location, String players, String sport, String postedBy, String usersJoined, String Id){ //String username) {
        this.Id = Id;
        this.Info = info;
        this.Location = location;
        this.Players = players;
        this.Sport = sport;
        this.PostedBy = postedBy;
        this.UsersJoined = usersJoined;

    }

}
