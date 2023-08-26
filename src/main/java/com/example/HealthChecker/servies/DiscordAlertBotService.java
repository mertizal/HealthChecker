package com.example.HealthChecker.servies;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import javax.security.auth.login.LoginException;

@Component
public class DiscordAlertBotService {

    private final String token = "MTEzOTk4MDQ5MTk1MzU1NzU2NQ.GH1Yht.SJjaquB77R5PSf5rdTbwtnMB3SMkcvIVgij9bs";

    public void sendMessageToChannel(String channelId, String message) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new MessageSender(channelId, message));
        builder.build();
    }

    private static class MessageSender extends ListenerAdapter {
        private String channelId;
        private String message;

        public MessageSender(String channelId, String message) {
            this.channelId = channelId;
            this.message = message;
        }

        @Override
        public void onReady(ReadyEvent event) {
            TextChannel channel = event.getJDA().getTextChannelById(channelId);
            if (channel != null) {
                channel.sendMessage(message).queue();
            }
            event.getJDA().shutdown();
        }
    }
}
