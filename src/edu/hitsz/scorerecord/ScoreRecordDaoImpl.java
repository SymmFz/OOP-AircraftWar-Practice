package edu.hitsz.scorerecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

public class ScoreRecordDaoImpl implements ScoreRecordDao {

    private static final String FILE_PATH = "scores.json";
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    return LocalDateTime.parse(in.nextString());
                }
            })
            .setPrettyPrinting()
            .create();

    // 单例模式，确保缓存一致性，通过锁确保线程安全
    private final List<ScoreRecord> scoreRecords;
    private static final ScoreRecordDaoImpl instance = new ScoreRecordDaoImpl();

    private ScoreRecordDaoImpl() {
        this.scoreRecords = loadFromFile();
    }

    public static ScoreRecordDaoImpl getInstance() { return instance; }

    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(this.scoreRecords, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized List<ScoreRecord> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<ScoreRecord>>() {
            }.getType();
            List<ScoreRecord> records = GSON.fromJson(reader, listType);
            if (records == null) {
                return new ArrayList<>();
            }
            return records;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private synchronized void reRank() {
        for (int i = 0; i < this.scoreRecords.size(); i++) {
            this.scoreRecords.get(i).setRecordNo(i + 1);
        }
    }

    private synchronized void sortAndReRank() {
        this.scoreRecords.sort(Comparator.comparingInt(ScoreRecord::getScores).reversed());
        reRank();
    }

    @Override
    public synchronized List<ScoreRecord> getAllScoreRecords() {
        return new ArrayList<>(this.scoreRecords);
    }

    @Override
    public synchronized ScoreRecord getSingleScoreRecordByNo(int recordNo) {
        for (ScoreRecord record : this.scoreRecords) {
            if (record.getRecordNo() == recordNo) {
                return record;
            }
        }
        return null;
    }

    @Override
    public synchronized void addRecord(ScoreRecord scoreRecord) {
        int insertIndex = Collections.binarySearch(this.scoreRecords, scoreRecord,
                Comparator.comparingInt(ScoreRecord::getScores).reversed());
        if (insertIndex < 0) {
            insertIndex = - insertIndex - 1;
        }

        this.scoreRecords.add(insertIndex, scoreRecord);
        reRank();
        saveToFile();
    }

    @Override
    public synchronized void deleteRecordByNo(int recordNo) {
        boolean removed = this.scoreRecords.removeIf(record -> record.getRecordNo() == recordNo);

        if (removed) {
            reRank();
            saveToFile();
        }
    }
}
