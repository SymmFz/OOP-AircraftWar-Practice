package edu.hitsz.scorerecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.hitsz.game.GameDifficulty;

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
//    private final List<ScoreRecord> scoreRecords;
    private final Map<GameDifficulty, List<ScoreRecord>> scoreRecordsByDifficulty;
    private static final ScoreRecordDaoImpl instance = new ScoreRecordDaoImpl();

    private ScoreRecordDaoImpl() {
        this.scoreRecordsByDifficulty = loadFromFile();
    }

    public static ScoreRecordDaoImpl getInstance() { return instance; }

    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(this.scoreRecordsByDifficulty, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized Map<GameDifficulty, List<ScoreRecord>> loadFromFile() {
        Map<GameDifficulty, List<ScoreRecord>> records = new EnumMap<>(GameDifficulty.class);
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            records.put(difficulty, new ArrayList<>());
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return records;
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type mapType = new TypeToken<EnumMap<GameDifficulty, List<ScoreRecord>>>() {
            }.getType();
            Map<GameDifficulty, List<ScoreRecord>> loadedRecords = GSON.fromJson(reader, mapType);

            if (loadedRecords != null) {
                records.putAll(loadedRecords);
            }
            return records;
        } catch (IOException e) {
            e.printStackTrace();
            return records;
        }
    }

    @Override
    public synchronized List<ScoreRecord> getAllScoreRecords(GameDifficulty difficulty) {
        return new ArrayList<>(this.scoreRecordsByDifficulty.get(difficulty));
    }

    @Override
    public synchronized ScoreRecord getSingleScoreRecordByIndex(int index, GameDifficulty difficulty) {
        List<ScoreRecord> targetList = this.scoreRecordsByDifficulty.get(difficulty);

        if (index >= 0 && index < targetList.size()) {
            return targetList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public synchronized void addRecord(ScoreRecord scoreRecord) {
        GameDifficulty difficulty = scoreRecord.getGameDifficulty();
        List<ScoreRecord> targetList = this.scoreRecordsByDifficulty.get(difficulty);

        int insertIndex = Collections.binarySearch(targetList, scoreRecord,
                Comparator.comparingInt(ScoreRecord::getScores).reversed());
        if (insertIndex < 0) {
            insertIndex = - insertIndex - 1;
        }

        targetList.add(insertIndex, scoreRecord);
        saveToFile();
    }

    @Override
    public synchronized void deleteRecordByIndex(int index, GameDifficulty difficulty) {
        List<ScoreRecord> targetList = this.scoreRecordsByDifficulty.get(difficulty);

        if (index >= 0 && index < targetList.size()) {
            targetList.remove(index);
            saveToFile();
        }
    }
}
