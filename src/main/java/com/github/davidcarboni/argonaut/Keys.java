package com.github.davidcarboni.argonaut;

import com.github.davidcarboni.ResourceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Playing with simple Json templating in Java.
 */
public class Keys {

    private Set<String> getKeys(JsonElement json) {
        Set<String> keys = new TreeSet<>();
        String key = "";
        add(key, json, keys);
        return keys;
    }

    void add(String key, JsonElement json, Set<String> keys) {
        if (json.isJsonPrimitive()) add(key, (JsonPrimitive) json, keys);
        if (json.isJsonArray()) add(key, (JsonArray) json, keys);
        if (json.isJsonObject()) add(key, (JsonObject) json, keys);
    }

    /**
     * Adds the path to a {@link JsonPrimitive} to the set of found paths.
     * @param path The path to this {@link JsonPrimitive}.
     * @param json The Json of this {@link JsonPrimitive}.
     * @param keys The set of paths to add the path of this primitive to.
     */
    void add(String path, JsonPrimitive json, Set<String> keys) {
        String name = path;
        JsonElement value = json;
        keys.add(name);
    }

    /**
     * Searches through the indices of a {@link JsonArray}, recursing to find paths to primitive values.
     * @param path The path to this {@link JsonArray}.
     * @param json The Json of this {@link JsonArray}.
     * @param keys The set of paths to (eventually) add any results to.
     */
    void add(String path, JsonArray json, Set<String> keys) {
        String arrayKey = path;
        for (int i = 0; i < json.size(); i++) {
            add(arrayKey + "." + i, json.get(i), keys);
        }
    }

    /**
     * Searches through the members of a {@link JsonObject}}, recursing to find paths to primitive values.
     * @param path The path to this {@link JsonObject}.
     * @param json The Json of this {@link JsonObject}.
     * @param keys The set of paths to (eventually) add any results to.
     */
    void add(String path, JsonObject json, Set<String> keys) {
        for (Map.Entry<String, JsonElement> member : json.entrySet()) {
            String memberName = member.getKey();
            JsonElement memberJson = member.getValue();
            String memberKey = key(path, memberName);
            add(memberKey, memberJson, keys);
        }
    }

    /**
     * Adds the given name to the given base path.
     *
     * @param path The base path to add the name to.
     * @param name  The name to add to the end of the path.
     * @return If the base is blank, "{@code key}". If the base is not blank, "{@code base.key}".
     */
    String key(String path, String name) {
        return (StringUtils.isNotBlank(path) ? path + "." : "") + name;
    }
}

