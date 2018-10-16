package com.example.raahymasif.cmpt498.Model;

public class CreatePosts {
    public String Id;
    public String Info;
    public String Location;
    public String Players;
    public String Sport;


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
