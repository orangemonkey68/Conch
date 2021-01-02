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

package io.paradaux.hiberniadiscord.bukkit.api;

import io.paradaux.hiberniadiscord.common.api.ConfigurationManager;
import io.paradaux.hiberniadiscord.common.api.I18NLogger;
import io.paradaux.hiberniadiscord.common.api.config.ConfigurationLoader;
import io.paradaux.hiberniadiscord.common.api.exceptions.NoSuchResourceException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;

public class BukkitConfigurationManager extends ConfigurationManager {

    Plugin plugin;


    /**
     * BukkitConfigurationLoader uses Sponge's Configurate to load the HOCON values in the three settings files.
     *
     * @param configurationDirectory The directory in which the configuration files are being saved to.
     */
    public BukkitConfigurationManager(Path configurationDirectory, Plugin plugin) {
        super(configurationDirectory);
        this.plugin = plugin;
    }

    @Override
    public void deployResource() {
        File configurationDir = plugin.getDataFolder();
        if (!plugin.getDataFolder().exists()) {
            boolean result = configurationDir.mkdir();

            if (!result) {
                I18NLogger.error("configuration.make-plugin-directory-failure");
            }
        }

        ConfigurationLoader loader = getConfigurationLoader();

        try {
            if (!loader.doesBotSettingsExist()) {
                exportResource(ConfigurationLoader.BOT_SETTINGS_FILE_NAME);
            }

            if (!loader.doesGeneralSettingsExist()) {
                exportResource(ConfigurationLoader.BOT_SETTINGS_FILE_NAME, "");
            }

            if (!loader.doesEventSettingsExist()) {
                exportResource(ConfigurationLoader.BOT_SETTINGS_FILE_NAME, "");
            }
        } catch (NoSuchResourceException exception) {
            I18NLogger.error("configuration.deploy-failure", exception.getMessage());
        }

    }

    @Override
    public void loadConfigurationFiles() {

    }

    @Override
    public void checkConfigurationVersions() {

    }
}