package persistence;

import org.json.JSONObject;

public interface Saveable {
    //EFFECTS: saves current account into .json
    JSONObject toJsonObject();
}