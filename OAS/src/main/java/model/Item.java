package model;

import java.io.Serializable;



public class Item implements Serializable{
    final private String name;
    private String type;
    final private double startingPrice;
    final private String description;
    final private String url;
    final private String condition;
    final private String country;


    public Item( String name, String type, double startingPrice, String description, String url, String condition, String country) {
        this.name = name;
        this.type = type;
        this.startingPrice = startingPrice;
        this.description = description;
        this.url = url;
        this.condition = condition;
        this.country = country;
    }

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    public double getStartingPrice() {
        return startingPrice;
    }


    public String getDescription() {
        return description;
    }


    public String getUrl() {
        return url;
    }


    public String getCondition() {
        return condition;
    }


    public String getCountry() {
        return country;
    }


}

/**Note that our Item object is an immutable one. After we create an instance, we will not be
 able to change its state (the value of the fields, all of them are final, and no setters are
 exposed). This means that when we return a Item object to the client (local or remote), it
 will be only available for reading.
 */

