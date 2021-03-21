/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.conch.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.config.EventConfiguration;
import io.paradaux.conch.velocity.managers.TaskManager;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

public class PlayerChatEventListener extends GenericListener {

    private final DiscordManager discord;
    private final TaskManager tasks;
    private final String networkName;
    private final String avatarApi;
    private final String messagePrefix;
    private final EventConfiguration config;

    private final boolean debug;
    public PlayerChatEventListener(DiscordManager discord, TaskManager tasks, String networkName, String avatarApi, String messagePrefix,
                                   EventConfiguration config, boolean debug) {
        this.discord = discord;
        this.tasks = tasks;
        this.networkName = networkName;
        this.avatarApi = avatarApi;
        this.messagePrefix = messagePrefix;
        this.config = config;
        this.debug = debug;
    }

    @Subscribe(order = PostOrder.NORMAL)
    public void onPlayerChat(PlayerChatEvent event) {

        Player player = event.getPlayer();

        Optional<ServerConnection> server = event.getPlayer().getCurrentServer();
        String serverName = "";

        if (server.isPresent()) {
            serverName = server.get().getServer().getServerInfo().getName();
        }

        String userName = parseCustomPlaceholders(player, networkName, serverName, avatarApi, config.getWebhookUsernameFormat());
        String messageContent = parseCustomPlaceholders(player, networkName, serverName, avatarApi, event.getMessage());


        if (userName == null || messageContent == null) {
            I18NLogger.error("Something went wrong. AsyncPlayerChatEvent null.");
            return;
        }

        // Only send the message if it starts with the configured prefix (if applicable)
        if (messagePrefix != null) {
            if (!messageContent.startsWith(messagePrefix)) {
                return;
            }

            // Remove the prefix from the message.
            messageContent = messagePrefix.replace(messagePrefix, "");
        }

        if (debug) {
            I18NLogger.rawInfo("{} has sent a message in chat which will be relayed to the discord webhook.", player.getUsername());
        }

        final String msg = messageContent;
        tasks.newTask(() -> {
            discord.sendDiscordMessage(userName, parseAvatarApi(player, avatarApi), msg);
        }).schedule();
    }

}
