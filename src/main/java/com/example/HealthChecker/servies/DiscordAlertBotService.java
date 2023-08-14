package com.example.HealthChecker.servies;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.security.auth.login.LoginException;
import java.util.Objects;

@Component
public class DiscordAlertBotService extends ListenerAdapter {



    public static void main(String[] args) throws LoginException {

        String token = "MTEzOTk4MDQ5MTk1MzU1NzU2NQ.GNtZv8.rbbQulOhMpNc-1SRpxP3ayyd0nDskkFNJVUXVI";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new DiscordAlertBotService());
        builder.build();

    }

    @Override
    public void onReady(ReadyEvent event) {

        sendMessageToChannel(Objects.requireNonNull(event.getJDA().getTextChannelById("1139960037972901941")),"serhat");
    }

    public void sendMessageToChannel(TextChannel channel, String message) {

        channel.sendMessage(message).queue();
    }



}
