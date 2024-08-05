package src.utility;

import src.game.Body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group<T> {
    public final T groupType;

    // Key: layer, Value: amount of objects in layer
    public Map<Integer, Integer> layerValues = new HashMap<>();
    public ArrayList<T> objects = new ArrayList<>();

    public Group(T type) {
        groupType = type;
    }

    public Group(T type, ArrayList<T> objects) {
        this(type);
        addMul(objects);
    }

    public void add(T obj) {
        int layer = getObjLayer(obj);
        if (!layerValues.containsValue(layer)) {
            layerValues.put(layer, 0);  // new layer
        }

        int inx = 0;  // place obj based on layer
        for (int l : layerValues.keySet()) {
            inx += l <= layer ? layerValues.get(l) : 0;
        }

        objects.add(inx, obj);
        layerValues.put(layer, layerValues.get(layer) + 1);
    }

    public void addMul(ArrayList<T> objects) {
        for (T obj: objects) {
            add(obj);
        }
    }

    private int getObjLayer(T obj) {
        return obj instanceof Body b_obj ? b_obj.layer : 0;  // special case for Body
    }

    /** removes object and stores it at end of storage list param, returns success */
    public boolean removeAtInx(int inx, ArrayList<T> storage) {
        if (inx < objects.size()) {
            T obj = objects.remove(inx);
            storage.add(obj);

            // balance layer values
            int layer = getObjLayer(obj);
            layerValues.put(layer, layerValues.get(layer) - 1);
            if (layerValues.get(layer) <= 0) {
                layerValues.remove(layer);
            }
            return true;
        }
        return false;
    }

    /** removes object and returns success */
    public boolean removeAtInx(int inx) {
        return removeAtInx(inx, new ArrayList<>());
    }

    /** Slow removal */
    public boolean removeObj(T obj) {
        for (int i = 0; i < objects.size(); i++) {
            if (obj == objects.get(i)) {
                return removeAtInx(i);
            }
        }
        return false;
    }

    /**
     * Checks integrity of group and fixes any issues.
     * Returns whether group order was altered
     */
    public boolean checkIntegrity() {
        ArrayList<Integer> incidentIndexes = new ArrayList<>();

        // find incident indexes
        int prevLayer = getObjLayer(objects.getFirst());
        for (int i = 0; i < objects.size(); i++) {
            int layer = getObjLayer(objects.get(i));

            if (layer < prevLayer) {
                incidentIndexes.add(i);  // incident!
            } else {
                prevLayer = layer;  // skip setting if incident
            }
        }

        // remove the objects at incident indexes from group
        if (!incidentIndexes.isEmpty()) {
            ArrayList<T> incidentObjects = new ArrayList<>();
            for (int i = incidentIndexes.size() - 1; i >= 0; i--) {  // remove in reverse
                int inx = incidentIndexes.get(i);
                incidentObjects.add(objects.remove(inx));
            }
            addMul(incidentObjects);
            return true;
        }
        return false;
    }
}
