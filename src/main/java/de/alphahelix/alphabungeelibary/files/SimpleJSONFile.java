package de.alphahelix.alphabungeelibary.files;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

public class SimpleJSONFile extends File {

    public static Gson gson = new GsonBuilder().create();
    private JsonObject head = new JsonObject();

    public SimpleJSONFile(String parent, String child) {
        super(parent, child);
        if (!this.exists() && !isDirectory()) {
            try {
                getParentFile().mkdirs();
                createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefault(String path, Object value) {
        Object enteredValue = value;

        if (value instanceof String)
            enteredValue = ((String) value).replace("§", "&");

        head.add(path, gson.toJsonTree(enteredValue));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this))) {
            writer.write(gson.toJson(head));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValue(String path, Object value) {
        setDefault(path, value);
    }

    public void addValuesToList(String path, Object... value) {
        JsonArray array = new JsonArray();

        if (contains(path)) {
            array = getValue(path, JsonArray.class);
        }

        for (Object obj : value)
            array.add(gson.toJsonTree(obj));

        setValue(path, array);
    }

    public void removeValue(String path) {
        if (!contains(path)) return;

        head.remove(path);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this))) {
            writer.write(gson.toJson(head));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> ArrayList<T> getListValues(String path, Class<T> definy) {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return new ArrayList<>();


            JsonObject obj = gson.fromJson(file, JsonObject.class);
            JsonArray array = obj.getAsJsonArray(path);
            ArrayList<T> typeList = new ArrayList<>();

            for (int i = 0; i < array.size(); i++) {
                typeList.add(gson.fromJson(array.get(i), definy));
            }

            return typeList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public <T> T getValue(String path, Class<T> definy) {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return null;

            JsonObject obj = gson.fromJson(file, JsonObject.class);

            return gson.fromJson(obj.get(path), definy);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T getValue(String path, TypeToken<T> token) {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return null;

            JsonObject obj = gson.fromJson(file, JsonObject.class);

            return gson.fromJson(obj.get(path), token.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> ArrayList<T> getValues(Class<T> definy) {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return new ArrayList<>();

            JsonObject obj = gson.fromJson(file, JsonObject.class);
            ArrayList<T> list = new ArrayList<>();

            for (Map.Entry<String, JsonElement> o : obj.entrySet()) {
                list.add(gson.fromJson(o.getValue(), definy));
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> getPaths() {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return new ArrayList<>();

            JsonObject obj = gson.fromJson(file, JsonObject.class);
            ArrayList<String> list = new ArrayList<>();

            for (Map.Entry<String, JsonElement> o : obj.entrySet()) {
                list.add(o.getKey());
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean contains(String path) {
        try {
            return FileUtils.readFileToString(this, Charset.defaultCharset()).contains(path);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean jsonContains(String path) {
        try {
            String file = FileUtils.readFileToString(this, Charset.defaultCharset());

            if (file.isEmpty() || !(file.startsWith("{") || file.endsWith("}")))
                return false;

            JsonObject obj = gson.fromJson(file, JsonObject.class);

            return obj.entrySet().contains(path);
        } catch (Exception e) {
            return contains(path);
        }
    }

    public boolean isEmpty() {
        try {
            return FileUtils.readFileToString(this, Charset.defaultCharset()).isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}