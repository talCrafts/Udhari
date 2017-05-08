package org.talcrafts.udhari.neighbours.model;

/**
 * Created by ashwaghm on 15-Jan-17.
 */
public class EndPoint {
    public final String id;  // Mostly mac Address
    public String content;
    public String details;
    public String type;


    public EndPoint(String id) {
        this.id = id;
    }
}
