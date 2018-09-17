package com.example.raahymasif.cmpt498.Model;

public class User {
    private String FirstName;
    private String LastName;
    private String Password;
    private String Email;
    private String Username;

    public User() {
    }

    public User(String Fname, String Lname, String password, String email){ //String username) {
        FirstName = Fname;
        LastName = Lname;
        Password = password;
        Email = email;
        //Username = username;
    }

    public String getFirstName() {
        return FirstName;
    }
    
    public String getLastName() {return LastName;}

    public void setFirstName(String Fname) {
        FirstName = Fname;
    }
    public void setLastName(String Lname) {
        LastName = Lname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    //this will change it back to "." for use in the code
    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    /*public String getUsername() {
        return Username;
    }*/

    /*public void setUsername(String username) {
        Username = username;
    }*/
}
