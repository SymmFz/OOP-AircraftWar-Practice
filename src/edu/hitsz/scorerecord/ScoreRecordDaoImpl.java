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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ScoreRecordDaoImpl implements ScoreRecordDao {

    private final List<ScoreRecord> scoreRecords;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Gson gson = new GsonBuilder()
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
            .create();
    private final String FILE_PATH = "scores.json";

    public ScoreRecordDaoImpl() {
        this.scoreRecords = loadFromFile();
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(this.scoreRecords, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<ScoreRecord> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new LinkedList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<LinkedList<ScoreRecord>>(){}.getType();
            List<ScoreRecord> records = gson.fromJson(reader, listType);
            if (records == null) {
                return new LinkedList<>();
            }
            return records;
        } catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    private void sortAndReRank() {
        this.scoreRecords.sort(Comparator.comparingInt(ScoreRecord::getScores).reversed());

        for (int i=0; i < this.scoreRecords.size(); i++) {
            this.scoreRecords.get(i).setRecordNo(i + 1);
        }
    }

    private String recordToString(ScoreRecord record) {
        return "第 " + record.getRecordNo() + " 名：" +
                       record.getPlayerName() + "，" +
                       record.getScores() + "，" +
                       record.getRecordTime().format(dateTimeFormatter);
    }

    @Override
    public List<ScoreRecord> getAllScoreRecords() {
        return this.scoreRecords;
    }

    @Override
    public ScoreRecord getSingleScoreRecordByNo(int recordNo) {
        for (ScoreRecord record : this.scoreRecords) {
            if (record.getRecordNo() == recordNo) {
                return record;
            }
        }
        return null;
    }

    @Override
    public void printSingleScoreRecordByNo(int recordNo) {
        for (ScoreRecord record : this.scoreRecords) {
            if (record.getRecordNo() == recordNo) {
                System.out.println(recordToString(record));
            }
        }
    }

    @Override
    public void printAllScoreRecord() {
        sortAndReRank();
        for (ScoreRecord record : this.scoreRecords) {
            System.out.println(recordToString(record));
        }
    }

    @Override
    public void addRecord(ScoreRecord scoreRecord) {
        this.scoreRecords.add(scoreRecord);
        sortAndReRank();
        saveToFile();
    }

    @Override
    public void deleteRecordByNo(int recordNo) {
        boolean removed = this.scoreRecords.removeIf(record -> record.getRecordNo() == recordNo);

        if (removed) {
            sortAndReRank();
            saveToFile();
        }
    }
}
