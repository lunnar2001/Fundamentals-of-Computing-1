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
import emily.db.model.OReplyPattern;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * data communication with the controllers `reply_pattern`
 */
public class CReplyPattern {
    public static OReplyPattern findBy(String tag) {
        OReplyPattern record = new OReplyPattern();
        try (ResultSet rs = WebDb.get().select(
                "SELECT id, guild_id, user_id, tag, pattern, reply, created_on, cooldown  " +
                        "FROM reply_pattern " +
                        "WHERE tag = ? ", tag)) {
            if (rs.next()) {
                record = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return record;
    }

    private static OReplyPattern fillRecord(ResultSet rs) throws SQLException {
        OReplyPattern record = new OReplyPattern();
        record.id = rs.getInt("id");
        record.guildId = rs.getInt("guild_id");
        record.userId = rs.getInt("user_id");
        record.tag = rs.getString("tag");
        record.pattern = rs.getString("pattern");
        record.reply = rs.getString("reply");
        record.createdOn = rs.getTimestamp("created_on");
        record.cooldown = rs.getLong("cooldown");
        return record;
    }

    /**
     * Retrieve all the auto-replies
     *
     * @return list of replies
     */
    public static List<OReplyPattern> getAll() {
        List<OReplyPattern> list = new ArrayList<>();
        try (ResultSet rs = WebDb.get().select(
                "SELECT id, guild_id, user_id, tag, pattern, reply, created_on, cooldown  " +
                        "FROM reply_pattern")) {
            while (rs.next()) {
                list.add(fillRecord(rs));
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return list;
    }

    /**
     * Only retrieve the auto-replies that are global or for a specific guild
     *
     * @param internalGuildId the internal guild id
     * @return a list of replies
     */
    public static List<OReplyPattern> getAll(int internalGuildId) {
        List<OReplyPattern> list = new ArrayList<>();
        try (ResultSet rs = WebDb.get().select(
                "SELECT id, guild_id, user_id, tag, pattern, reply, created_on, cooldown  " +
                        "FROM reply_pattern WHERE guild_id = ? OR guild_id = 0", internalGuildId)) {
            while (rs.next()) {
                list.add(fillRecord(rs));
            }
            rs.getStatement().close();
        } catch (Exception e) {
            Logger.fatal(e);
        }
        return list;
    }

    public static void insert(OReplyPattern r) {
        try {
            r.id = WebDb.get().insert(
                    "INSERT INTO reply_pattern(guild_id, user_id, tag, pattern, reply, created_on, cooldown) " +
                            "VALUES (?,?,?,?,?,?,?)",
                    r.guildId, r.userId, r.tag, r.pattern, r.reply, new Timestamp(System.currentTimeMillis()), r.cooldown);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(OReplyPattern r) {
        try {
            r.id = WebDb.get().insert(
                    "UPDATE reply_pattern SET tag = ?, pattern = ?, reply = ?, cooldown = ? " +
                            "WHERE id = ? ",
                    r.tag, r.pattern, r.reply, r.cooldown, r.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(OReplyPattern r) {
        try {
            WebDb.get().query(
                    "DELETE FROM reply_pattern WHERE id = ? ", r.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
