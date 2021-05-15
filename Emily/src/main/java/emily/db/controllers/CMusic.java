/*
 * Copyright 2017 github.com/kaaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emily.db.controllers;

import emily.core.Logger;
import emily.db.WebDb;
import emily.db.model.OMusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * data communication with the controllers `playlist`
 */
public class CMusic {
    public static OMusic findByYoutubeId(String youtubeCode) {
        OMusic music = new OMusic();
        try (ResultSet rs = WebDb.get().select(
                "SELECT *  " +
                        "FROM music " +
                        "WHERE youtubecode = ? ", youtubeCode)) {
            if (rs.next()) {
                music = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return music;
    }

    public static OMusic findById(int id) {
        OMusic music = new OMusic();
        try (ResultSet rs = WebDb.get().select(
                "SELECT *  " +
                        "FROM music " +
                        "WHERE id = ? ", id)) {
            if (rs.next()) {
                music = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return music;
    }

    public static OMusic findByFileName(String filename) {
        OMusic music = new OMusic();
        try (ResultSet rs = WebDb.get().select(
                "SELECT *  " +
                        "FROM music " +
                        "WHERE filename = ? ", filename)) {
            if (rs.next()) {
                music = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return music;
    }


    public static OMusic fillRecord(ResultSet resultset) throws SQLException {
        OMusic music = new OMusic();
        music.id = resultset.getInt("id");
        music.youtubecode = resultset.getString("youtubecode");
        music.filename = resultset.getString("filename");
        music.youtubeTitle = resultset.getString("youtube_title");
        music.title = resultset.getString("title");
        music.artist = resultset.getString("artist");
        music.lastplaydate = resultset.getLong("lastplaydate");
        music.banned = resultset.getInt("banned");
        music.playCount = resultset.getInt("play_count");
        music.lastManualPlaydate = resultset.getLong("last_manual_playdate");
        music.fileExists = resultset.getInt("file_exists");
        music.duration = resultset.getInt("duration");
        return music;
    }

    public static void update(OMusic record) {
        if (record.id == 0) {
            insert(record);
            return;
        }
        try {
            WebDb.get().query(
                    "UPDATE music SET  youtubecode = ?, filename = ?, youtube_title = ?, " +
                            "title = ?,artist = ?, lastplaydate = ?, banned = ?, play_count = ?, last_manual_playdate = ?, " +
                            "file_exists = ?, duration = ? " +
                            "WHERE id = ? ",
                    record.youtubecode, record.filename, record.youtubeTitle,
                    record.title, record.artist, record.lastplaydate, record.banned, record.playCount, record.lastManualPlaydate,
                    record.fileExists, record.duration, record.id
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(OMusic record) {
        if (record.id > 0) {
            update(record);
            return;
        }
        try {
            record.id = WebDb.get().insert(
                    "INSERT INTO music(youtubecode, filename, youtube_title," +
                            "title, artist, lastplaydate,play_count,last_manual_playdate,file_exists,duration, banned) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    record.youtubecode, record.filename, record.youtubeTitle.substring(0, Math.min(100, record.youtubeTitle.length())),
                    record.title, record.artist, record.lastplaydate, record.playCount, record.lastManualPlaydate, record.fileExists, record.duration, record.banned);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPlayRequest(int musicId) {
        try {
            WebDb.get().query(
                    "UPDATE music SET  last_manual_playdate = ?, play_count = play_count + 1 " +
                            "WHERE id = ? ",
                    System.currentTimeMillis() / 1000L, musicId
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<OMusic> getRecentlyPlayed(int limit) {
        List<OMusic> history = new ArrayList<>();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM music ORDER BY lastplaydate DESC LIMIT ?", limit)) {
            while (rs.next()) {
                history.add(fillRecord(rs));
            }
            rs.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}
