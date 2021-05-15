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

package emily.command.creator;

import emily.command.meta.AbstractCommand;
import emily.db.controllers.CBotVersionChanges;
import emily.db.controllers.CBotVersions;
import emily.db.model.OBotVersion;
import emily.db.model.OBotVersionChange;
import emily.main.DiscordBot;
import emily.main.Launcher;
import emily.main.ProgramVersion;
import emily.permission.SimpleRank;
import emily.templates.Templates;
import emily.util.Emojibet;
import emily.util.Misc;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ChangeLogAdminCommand extends AbstractCommand {

    @Override
    public String getDescription() {
        return "manage the changelog";
    }

    @Override
    public String getCommand() {
        return "cla";
    }

    @Override
    public boolean isListed() {
        return true;
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "cla <version> <type> <message>     //adds a change to <version> of <type> with <message>",
                "cla current <type> <message>       //shortcut for current version",
                "cla next <type> <message>          // ^ next version",
                "cla types",
                "cla <version> publish <true/false> //publish the log for version (or not)"
        };
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public String execute(DiscordBot bot, String[] args, MessageChannel channel, User author, Message inputMessage) {
        if (!bot.security.getSimpleRank(author).isAtLeast(SimpleRank.CREATOR)) {
            return Templates.no_permission.formatGuild(channel);
        }
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "types":
                    return printTypes();
            }
        }
        if (args.length < 3) {
            return Templates.invalid_use.formatGuild(channel);
        }
        ProgramVersion v;
        OBotVersion version;
        if (args[0].equalsIgnoreCase("current")) {
            v = Launcher.getVersion();
            version = CBotVersions.findBy(v);
            if (version.id == 0) {
                version.id = CBotVersions.insert(v, null);
            }
        } else if (args[0].equalsIgnoreCase("next")) {
            v = CBotVersions.versionAfter(Launcher.getVersion()).getVersion();
            if (Launcher.getVersion().isHigherThan(v)) {
                return Templates.command.cla.version_not_found.formatGuild(channel, args[0]);
            }
            version = CBotVersions.findBy(v);
        } else {
            v = ProgramVersion.fromString(args[0]);
            version = CBotVersions.findBy(v);
            if (version.id == 0) {
                version.id = CBotVersions.insert(v, null);
            }
        }
        if (args[1].equals("publish")) {
            CBotVersions.publish(v, Misc.isFuzzyTrue(args[2]));
            return "Published " + v.toString() + " " + (Misc.isFuzzyTrue(args[2]) ? Emojibet.OKE_SIGN : Emojibet.X);
        }
        OBotVersionChange.ChangeType changeType = OBotVersionChange.ChangeType.fromCode(args[1]);
        if (changeType.equals(OBotVersionChange.ChangeType.UNKNOWN)) {
            return Templates.command.cla.type_unknown.formatGuild(channel, args[1]);
        }
        String description = Misc.joinStrings(args, 2);
        if (description.length() < 5) {
            return Templates.command.cla.desc_short.formatGuild(channel);
        }
        CBotVersionChanges.insert(version.id, changeType, description);
        return Emojibet.THUMBS_UP;
    }

    private String printTypes() {
        StringBuilder ret = new StringBuilder("The following changelog types exist:\n\n");
        for (OBotVersionChange.ChangeType type : OBotVersionChange.ChangeType.values()) {
            ret.append(String.format("%s %s\n", type.getCode(), type.getTitle()));
        }
        return ret.toString();
    }
}