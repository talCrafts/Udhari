package org.talcrafts.udhari.neighbours.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashwaghm on 19-Jan-17.
 */

public class EndPointContent {

    protected List<EndPoint> ITEMS = new ArrayList<>();
    protected Map<String, EndPoint> ITEM_MAP = new HashMap<>();

    public void addItem(EndPoint endPoint) {
        if (!ITEM_MAP.containsKey(endPoint.id)) {
            ITEM_MAP.put(endPoint.id, endPoint);
            ITEMS.add(endPoint);
        }
    }
}
