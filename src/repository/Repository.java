package repository;

import java.util.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Repository<T,T1> {
    protected int DLF;
    protected String DATA_PATH;
    protected String TEMP_DATA_PATH;
    protected Map<T,T1> dataMap;

    public List<T1> getDataList() {
        return new ArrayList<T1>(dataMap.values());
    }

    protected void loadAllDataFromFile() {
        dataMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length != DLF)
                    throw new Exception("User data format wrong");    
                T1 obj = dataToObject(tokens);
                putObjectToMap(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveObject(T1 obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_PATH, true))) {
            putObjectToMap(obj);
            writer.write(objectToData(obj));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T1 findObjectByKey(T key) {
        if(dataMap.containsKey(key)) {
            return dataMap.get(key);
        }
        return null;
    }

    public void updateObject(T1 obj) {
        File tempFile = new File(TEMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (comparePrimaryKey(tokens[0], obj)) {
                    // Write updated obj
                    putObjectToMap(obj);
                    writer.write(objectToData(obj));
                } else {
                    // Write original obj
                    writer.write(line);
                }
                writer.newLine();
            }

            // Delete the original file and rename the temp file
            if (!new File(DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteObject(T1 obj) {
        File tempFile = new File(TEMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (!comparePrimaryKey(tokens[0], obj)) {
                    removeObjectFromMap(obj);
                    writer.write(line);
                    writer.newLine();
                }
            }
            if (!new File(DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean comparePrimaryKey(String token, T1 obj);
    protected abstract void removeObjectFromMap(T1 obj);
    protected abstract void putObjectToMap(T1 obj);
    protected abstract T1 dataToObject(String[] tokens);
    protected abstract String objectToData(T1 obj);
}
