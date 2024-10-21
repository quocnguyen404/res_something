package repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.file.Paths;

import common.AppConstant;

public abstract class DynamicRepository<T,T1> {
    protected int DLF;
    protected String DATA_FOLDER;
    protected LocalDate currentDate;
    protected Map<T,T1> dataMap;

    public void updateRepository(LocalDate localDate) {
        if(!localDate.equals(currentDate)) {
            currentDate = localDate;
            loadAllDataFromFile();
        }
    }
    
    public List<T1> getDataList() {
        return new ArrayList<T1>(dataMap.values());
    }

    protected void loadAllDataFromFile() {
        String file = DATA_FOLDER+currentDate+AppConstant.DATA_SUFFIX;
        Path path = Paths.get(file);
        dataMap = new HashMap<>();
        try {
            if(Files.exists(path)) {
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if(tokens.length != DLF)
                            throw new Exception("Data format wrong");
                        T1 obj = dataToObject(tokens);
                        putObjectToMap(obj);
                    }
                }
            } else {
                Files.createFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveObject(T1 obj) {
        String fileDir = DATA_FOLDER+currentDate+AppConstant.DATA_SUFFIX;
        File file = new File(fileDir);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            putObjectToMap(obj);
            writer.write(objectToData(obj));
            writer.newLine();
        } catch(Exception e) {
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
        String dataPath = DATA_FOLDER+currentDate+AppConstant.DATA_SUFFIX;
        File tempFile = new File(DATA_FOLDER+currentDate+AppConstant.DATA_TEMP_SUFFIX);
        try (BufferedReader reader = new BufferedReader(new FileReader(dataPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (comparePrimaryKey(tokens[0], obj)) {
                    putObjectToMap(obj);
                    writer.write(objectToData(obj));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!new File(dataPath).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(dataPath))) {
                throw new IOException("Could not rename temp file");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteObject(T1 obj) {
        String dataPath = DATA_FOLDER+currentDate+AppConstant.DATA_SUFFIX;
        File tempFile = new File(DATA_FOLDER+currentDate+AppConstant.DATA_TEMP_SUFFIX);
        try (BufferedReader reader = new BufferedReader(new FileReader(dataPath));
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
            if (!new File(dataPath).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(dataPath))) {
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
