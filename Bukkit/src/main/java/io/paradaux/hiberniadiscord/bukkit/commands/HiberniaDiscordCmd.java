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

package io.paradaux.hiberniadiscord.commands;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HiberniaDiscordCmd implements CommandExecutor {


    public String colorise(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("hiberniadiscord.admin")) {
//            sender.sendMessage(Locale.colorise(locale.getSevereNoPermission()));
            return true;
        }

        if (args.length <= 0) {
//            sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordDefault())));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload": {
//                HiberniaDiscord.updateConfigurationCache();
//                HiberniaDiscord.updateLocaleCache();
//                sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordReloadSuccess())));
                return true;
            }

            // TODO: implement
            case "discord2mc": {
                sender.sendMessage(ChatColor.RED + "Unimplemented.");
                break;
            }

            default: {
                sender.sendMessage(ChatColor.RED + "/hdiscord <reload/discord2mc>");
                return true;
            }
        }
        return true;

    }

}