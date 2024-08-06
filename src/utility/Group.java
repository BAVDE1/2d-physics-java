package src.utility;

import src.game.Body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group<T> {
    // Key: layer, Value: amount of objects in layer
    public Map<Integer, Integer> layerValues = new HashMap<>();
    public ArrayList<T> objects = new ArrayList<>();

    public Group() {}
    public Group(ArrayList<T> objects) {
        addMul(objects);
    }

    public void addToLayer(int layer, int amount) {
        layerValues.put(layer, layerValues.containsValue(layer) ? layerValues.get(layer) + amount : amount);  // set, or get (and add)
    }

    public void takeFromLayer(int layer, int amount) {
        layerValues.put(layer, layerValues.get(layer) - amount);
        if (layerValues.get(layer) <= 0) {
            layerValues.remove(layer);  // remove if nothing in layer
        }
    }

    public void add(T obj) {
        int layer = getObjLayer(obj);
        addToLayer(layer, 1);

        int inx = 0;  // place obj at inx based on layer
        for (int l : layerValues.keySet()) {
            inx += l <= layer ? layerValues.get(l) : 0;
        }
        objects.add(inx, obj);
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
            takeFromLayer(getObjLayer(obj), 1);
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

    public void changeObjLayer(T obj, int newLayer) {
        assert obj instanceof Body;  // must be body

        takeFromLayer(((Body) obj).layer, 1);
        addToLayer(newLayer, 1);
        ((Body) obj).layer = newLayer;

        objectsIntegrity();
    }

    /**
     * Checks integrity of objects group and layers map, and fixes any issues.
     * Returns whether group order or layers were altered
     */
    public boolean checkAndFixIntegrity() {
        boolean objChanged = objectsIntegrity();
        boolean layersChanged = layersIntegrity();  // check both regardless
        return objChanged || layersChanged;
    }

    /** Check and fix integrity of objects array */
    private boolean objectsIntegrity() {
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
            addMul(incidentObjects);  // add back in popped objects
            return true;
        }
        return false;
    }

    /** Check and gix integrity of layer map */
    private boolean layersIntegrity() {
        Map<Integer, Integer> checkedLayers = new HashMap<>();
        for (T obj : objects) {
            int layer = getObjLayer(obj);
            checkedLayers.put(layer, checkedLayers.containsValue(layer) ? checkedLayers.get(layer) + 1 : 1);
        }

        boolean hasChanged = checkedLayers.entrySet().equals(layerValues.entrySet());
        if (hasChanged) {
            layerValues = checkedLayers;  // replace with accurate map
        }
        return hasChanged;
    }
}
