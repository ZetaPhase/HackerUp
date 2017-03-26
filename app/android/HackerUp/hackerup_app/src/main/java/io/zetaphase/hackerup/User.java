package io.zetaphase.hackerup;

/**
 * Created by Dave Ho on 3/25/2017.
 */
public class User {
    private String name;
    private String userid;
    private double distance;

    public User(String name, String userid, double distance) {
        this.name = name;
        this.userid = userid;
        this.distance = distance;
    }

    public String getName() {
        return this.name;
    }

    public String getUserid() {
        return this.userid;
    }

    public double getDistance() {
        return this.distance;
    }

}
