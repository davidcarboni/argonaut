package com.github.davidcarboni.argonaut;

import com.github.davidcarboni.ResourceUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test for {@code Values}.
 * Created by david on 29/05/2016.
 */
public class ValuesTest {

    static String string;
    JsonElement json;
    Values values;

    @BeforeClass
    public static void beforeClass() throws IOException {
        string = ResourceUtils.getString("/test.json");
    }

    @Before
    public void before() {
        json = Json.parse(string);
        values = new Values();
    }

    /**
     * Checks the possible paths to the root element.
     * It is expected that "" should be used, but the others work as well.
     */
    @Test
    public void shouldGetRootElement() {

        // Given
        String[] rootPaths = {"", null, "."};
        JsonElement[] results = new JsonElement[rootPaths.length];

        // When
        for (int i = 0; i < rootPaths.length; i++) {
            results[i] = values.getElement(rootPaths[i], json);
        }

        // Then
        for (int i = 0; i < results.length; i++) {
            assertNotNull(results[i]);
            assertTrue(results[i].isJsonObject());
            assertTrue(((JsonObject) results[i]).has("person"));
            assertTrue(((JsonObject) results[i]).has("books"));
        }
    }

    /**
     * Checks that getting a path to a primitive returns the String at that path.
     */
    @Test
    public void shouldGetPrimitiveAsString() {

        // Given
        String path = "person.name";

        // When
        String result = values.getString(path, json);

        // Then
        assertNotNull(result);
        assertEquals("David Carboni", result);
    }

    /**
     * Checks that null will be returned if the path points to an element, but a String is requested.
     */
    @Test
    public void shouldNotGetElementAsString() {

        // Given
        String path = "person";

        // When
        String result = values.getString(path, json);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldGetPrimitiveFromArray() throws Exception {

        // Given
        String path = "person.address.1";

        // When
        String result = values.getString(path, json);

        // Then
        assertNotNull(result);
        assertEquals("Middle Earth", result);
    }

    @Test
    public void shouldGetElementFromArray() throws Exception {

        // Given
        String path = "books.1";

        // When
        JsonElement result = values.getElement(path, json);

        // Then
        assertNotNull(result);
        assertTrue(result.isJsonObject());
        assertTrue(((JsonObject) result).has("name"));
        assertEquals("Reinventing Organizations", ((JsonObject) result).get("name").getAsString());
    }

    @Test
    public void shouldGetNullForArrayOutOfBounds() throws Exception {

        // Given
        String pathLow = "books.-1";
        String pathHigh = "books.5";

        // When
        JsonElement resultLow = values.getElement(pathLow, json);
        JsonElement resultHigh = values.getElement(pathHigh, json);

        // Then
        assertNull(resultLow);
        assertNull(resultHigh);
    }

    @Test
    public void shouldGetPrimitive() throws Exception {

        // Given
        String value = "test";
        JsonElement json = Json.parse("\"" + value + "\"");

        // When
        String result = values.getString("", json);

        // Then
        assertNotNull(result);
        assertEquals(value, result);
    }

}