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
import emily.db.controllers.CGuild;
import emily.db.controllers.CModerationCase;
import emily.db.controllers.CUser;
import emily.db.model.OModerationCase;
import emily.guildsettings.GSetting;
import emily.handler.GuildSettings;
import emily.main.DiscordBot;
import emily.permission.SimpleRank;
import emily.templates.Templates;
import emily.util.Misc;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CaseCommand extends AbstractCommand {
    public CaseCommand() {
        super();
    }

    @Override
    public String getDescription() {
        return "Moderate the mod-cases";
    }

    @Override
    public String getCommand() {
        return "case";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "case reason <id> <message>  //sets/modifies the reason of a case",
                "case reason last <message>  //sets/modified the reason of the last added case by you",
                "case user <name/id/mention> //shows a list of cases for this user",
                "case show <id/username>     //shows case"
        };
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.PUBLIC;
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public String execute(DiscordBot bot, String[] args, MessageChannel channel, User author, Message inputMessage) {
        SimpleRank rank = bot.security.getSimpleRank(author, channel);
        Guild guild = ((TextChannel) channel).getGuild();
        if (!rank.isAtLeast(SimpleRank.GUILD_ADMIN)) {
            return Templates.no_permission.formatGuild(channel);
        }
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "reason":
                    if (args.length < 3) {
                        return Templates.invalid_use.formatGuild(channel);
                    }
                    return editReason(bot, guild, guild.getMember(author), channel, args[1], Misc.joinStrings(args, 2));
            }
        }
        return Templates.invalid_use.formatGuild(channel);
    }

    private String editReason(DiscordBot bot, Guild guild, Member moderator, MessageChannel feedbackChannel, String caseId, String reason) {
        OModerationCase oCase;
        if (caseId.equalsIgnoreCase("last")) {
            oCase = CModerationCase.findLastFor(CGuild.getCachedId(guild.getIdLong()), CUser.getCachedId(moderator.getUser().getIdLong()));
        } else {
            oCase = CModerationCase.findById(Misc.parseInt(caseId, -1));
        }
        if (oCase.id == 0 || oCase.guildId != CGuild.getCachedId(guild.getIdLong())) {
            return Templates.command.case_not_found.formatGuild(guild.getIdLong(), oCase.id);
        }
        oCase.reason = reason;
        CModerationCase.update(oCase);
        TextChannel channel = guild.getTextChannelById(GuildSettings.get(guild).getOrDefault(GSetting.BOT_CHANNEL));
        if (channel == null) {
            return Templates.config.modlog_not_found.formatGuild(guild.getIdLong());
        }
        bot.queue.add(channel.getMessageById(oCase.messageId),
                msg -> {
                    if (msg != null) {
                        bot.queue.add(msg.editMessage(new MessageBuilder().setEmbed(CModerationCase.buildCase(guild, oCase)).build()));
                    } else {
                        bot.queue.add(feedbackChannel.sendMessage(Templates.command.case_reason_modified.formatGuild(channel)));
                    }
                });

        return "";
    }
}