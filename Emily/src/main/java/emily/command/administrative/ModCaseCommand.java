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

package emily.command.administrative;

import emily.command.meta.CommandVisibility;
import emily.command.meta.AbstractCommand;
import emily.main.DiscordBot;
import emily.permission.SimpleRank;
import emily.templates.Templates;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ModCaseCommand extends AbstractCommand {
    public ModCaseCommand() {
        super();
    }

    @Override
    public String getDescription() {
        return "Modcases";
    }

    @Override
    public String getCommand() {
        return "modcase";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "kick <user>            //kicks user"
        };
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "case"
        };
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.PUBLIC;
    }

    @Override
    public String execute(DiscordBot bot, String[] args, MessageChannel channel, User author, Message inputMessage) {
        SimpleRank rank = bot.security.getSimpleRank(author);
        return Templates.not_implemented_yet.formatGuild(channel);
    }
}