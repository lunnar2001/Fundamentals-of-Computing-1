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

package emily.modules.reddit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import emily.modules.reddit.pojo.CommentData;
import emily.modules.reddit.pojo.InitialDataComment;

import java.lang.reflect.Type;

/**
 * Created by Siddharth Verma on 13/5/16.
 */
public class CommentDataDeserializer implements JsonDeserializer<CommentData> {
    @Override
    public CommentData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        boolean isOp = false;

        String author = null;
        if (jsonObject.get("author") != null)
            author = jsonObject.get("author").getAsString();

        String body = null;
        if (jsonObject.get("selftext") != null) {
            body = jsonObject.get("selftext").getAsString();
            isOp = true;
        } else if (jsonObject.get("body") != null)
            body = jsonObject.get("body").getAsString();

        Long created = null;
        if (jsonObject.get("created") != null)
            created = jsonObject.get("created").getAsLong();


        Long created_utc = null;
        if (jsonObject.get("created_utc") != null)
            created_utc = jsonObject.get("created_utc").getAsLong();


        String subreddit = null;
        if (jsonObject.get("subreddit") != null)
            subreddit = jsonObject.get("subreddit").getAsString();

        Integer score = null;
        if (jsonObject.get("score") != null)
            score = jsonObject.get("score").getAsInt();

        String id = null;
        if (jsonObject.get("id") != null)
            id = jsonObject.get("id").getAsString();

        JsonElement replies = null;
        if (jsonObject.get("replies") != null)
            replies = jsonObject.get("replies");

        InitialDataComment comment = null;

        if (replies != null && !(replies instanceof JsonPrimitive)) {
            comment = context.deserialize(jsonObject.get("replies"), InitialDataComment.class);
        }

        CommentData commentData = new CommentData();
        commentData.isOp = isOp;
        commentData.author = author;
        commentData.body = body;
        commentData.created = created;
        commentData.created_utc = created_utc;
        commentData.subreddit = subreddit;
        commentData.score = score;
        commentData.id = id;
        commentData.replies = comment;

        return commentData;
    }
}
