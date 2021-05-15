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

package emily.command.poe;

import emily.command.meta.AbstractCommand;
import emily.main.DiscordBot;
import emily.modules.reddit.RedditScraper;
import emily.modules.reddit.pojo.Comment;
import emily.modules.reddit.pojo.Post;
import emily.util.Misc;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * !poeitem
 * Analyzes an item from path of exile
 */
public class PoeLabsCommand extends AbstractCommand {
    private static final Set<String> validArgs = new HashSet<>(Arrays.asList(
            "normal", "cruel", "merciless", "uber"));
    private Pattern imagePattern = Pattern.compile("(?m)(normal|uber|merciless|cruel) lab notes[\\s]*(https?:.*(png|jpg))", Pattern.MULTILINE);

    public PoeLabsCommand() {
        super();
    }

    @Override
    public String getDescription() {
        return "Attempts to find a description from reddit for the Labyrinth instance.";
    }

    @Override
    public String getCommand() {
        return "poelab";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "poelab              //lists for all difficulties",
                "poelab <difficulty> //only for that difficulty",
        };
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public String execute(DiscordBot bot, String[] args, MessageChannel channel, User author, Message inputMessage) {
        List<Post> search = RedditScraper.search("pathofexile", "title%3ADaily+Labyrinth+author%3AAutoModerator&sort=new&restrict_sr=on&t=day");

        if (!search.isEmpty()) {
            Post post = search.get(0);
            List<Comment> comments = RedditScraper.getComments(post.data.getId());
            for (Comment comment : comments) {
                if (comment.data.isOp) {
                    continue;
                }
                String searchText = comment.data.body.toLowerCase();
                if (args.length > 0) {
                    if (!validArgs.contains(args[0].toLowerCase())) {
                        return "There is no such difficulty";
                    }
                    if (!searchText.contains(args[0].toLowerCase())) {
                        continue;
                    }
                    Matcher m = imagePattern.matcher(searchText);
                    while (m.find()) {
                        if (m.group(1).equals(args[0].toLowerCase())) {
                            return "Path of Exile - Labyrinth\n\n" +
                                    post.data.title + " - **" + args[0].toLowerCase() + "**\n" + m.group(2);

                        }
                    }
                } else {
                    if (searchText.contains("normal") && searchText.contains("cruel") && searchText.contains("merciless")) {
                        return "Path of Exile -  Labyrinth\n\n" +
                                post.data.title + "\n" +
                                Misc.makeTable(comment.data.body);
                    }
                }
            }
        }
        return "Can't find labdetails :(";
    }
}