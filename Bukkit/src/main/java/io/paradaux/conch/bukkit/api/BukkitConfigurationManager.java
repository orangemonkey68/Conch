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

package io.paradaux.conch.bukkit.api;

import io.paradaux.conch.common.api.ConfigurationManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.CachedEventSettings;
import io.paradaux.conch.common.api.config.CachedServerSettings;
import io.paradaux.conch.common.api.config.ConfigurationLoader;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import io.paradaux.conch.common.api.exceptions.NoSuchResourceException;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public class BukkitConfigurationManager extends ConfigurationManager {

    private CachedServerSettings generalSettings;
    private CachedEventSettings eventSettings;
    private CachedBotSettings botSettings;

    private ConfigurationLoader loader = getConfigurationLoader();
    private Path configurationDirectory;

    /**
     * BukkitConfigurationLoader uses Sponge's Configurate to load the HOCON values in the three settings files.
     *
     * @param configurationDirectory The directory in which the configuration files are being saved to.
     */
    public BukkitConfigurationManager(Path configurationDirectory) {
        super(configurationDirectory);
        this.configurationDirectory = configurationDirectory;
    }

    @Override
    public void deployResources() {
        try {
            if (!loader.doesBotSettingsExist()) {
                exportResource(ConfigurationLoader.BOT_SETTINGS_FILE_NAME, loader.getBotSettingsPath().toString());
            }

            if (!loader.doesGeneralSettingsExist()) {
                exportResource(ConfigurationLoader.SETTINGS_FILE_NAME, loader.getGeneralSettingsPath().toString());
            }

            if (!loader.doesEventSettingsExist()) {
                exportResource(ConfigurationLoader.EVENT_SETTINGS_FILE_NAME, loader.getEventSettingsPath().toString());
            }
        } catch (NoSuchResourceException exception) {
            I18NLogger.error("configuration.deploy-failure", exception.getMessage());
        }

    }

    @Override
    public void loadConfigurationFiles() {
        try {
            generalSettings = loader.loadGeneralSettings();
            eventSettings = loader.loadEventSettings();
            botSettings = loader.loadBotSettings();
        } catch (ConfigurateException exception) {
            I18NLogger.error(""); // TODO add error for failure to load configuration
            return;
        }

        ConfigurationUtil.loadConfigurationValues(generalSettings, eventSettings, botSettings);
    }

    @Override
    public void checkConfigurationVersions() {

    }
}
