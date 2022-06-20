package fr.nebulo9.pulsedparty.util;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;

public class Home {
    private String name;
    private SimplifiedLocation location;

    public Home(String name, SimplifiedLocation location) {
        this.name = name;
        this.location = location;
    }

    public Home() {

    }

    public String getName() {
        return name;
    }

    public SimplifiedLocation getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(SimplifiedLocation location) {
        this.location = location;
    }
}
