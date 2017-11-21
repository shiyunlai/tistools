package org.tis.tools.model.dto.route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbfRoute implements Serializable {

    private List<RouteState> states = new ArrayList<>();

    public List<RouteState> getStates() {
        return states;
    }

    public void setStates(List<RouteState> states) {
        this.states = states;
    }

    public void add(RouteState routeState) {
        this.states.add(routeState);
    }

    @Override
    public String toString() {
        String state = states.toString();
        return "{" +
                " " + state.substring(1, state.length() - 1) + "\n" +
                "}";
    }
}
