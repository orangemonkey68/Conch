/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.hiberniadiscord.bukkit;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.common.api.BotManager;
import io.paradaux.hiberniadiscord.common.api.DiscordManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HiberniaDiscord extends JavaPlugin {

    private static Logger logger;
    private static TaskChainFactory taskChainFactory;
    List<String> monitoredChannels = new ArrayList<>();

    String webhookUrl = "";
    
    String iconUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String webhookUrl = "https://discord.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";
    String token = "";
    String messageFormat = "";

    @Override
    public void onEnable() {
        logger = LoggerFactory.getLogger("io.paradaux.hiberniadiscord");

        taskChainFactory = BukkitTaskChainFactory.create(this);
        BotManager.initialise(token, logger, monitoredChannels, taskChainFactory, messageFormat, true);
        DiscordManager.initialise(webhookUrl, true, logger);
        DiscordManager.sendDiscordMessage("Test", iconUrl, "hello world");
    }

    @Override
    public void onDisable() {

    }

}
