package dev.rollczi.liteenchants.config;

import dev.rollczi.liteenchants.reload.Reloadable;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConfigService implements Reloadable {

    private final File dataFolder;
    private final Map<Class<?>, OkaeriConfig> configs = new HashMap<>();
    private final MiniMessage miniMessage;

    public ConfigService(File dataFolder, MiniMessage miniMessage) {
        this.dataFolder = dataFolder;
        this.miniMessage = miniMessage;
    }

    public <T extends OkaeriConfig> T load(T config, String fileName) {
        T configFile = ConfigManager.initialize(config);

        configFile
            .withConfigurer(new YamlBukkitConfigurer(), new SerdesCommons(), new ConfigSerdes(miniMessage) /*new SerdesBukkit(), new ConfigSerdes()*/)
            .withBindFile(new File(this.dataFolder, fileName))
            .withRemoveOrphans(true)
            .saveDefaults()
            .load(true);

        this.configs.put(configFile.getClass(), configFile);

        return configFile;
    }

    @Override
    public void reload() {
        for (OkaeriConfig config : this.configs.values()) {
            config.load();
        }
    }

    public void save() {
        for (OkaeriConfig config : this.configs.values()) {
            config.save();

            if (config instanceof Reloadable reloadable) {
                reloadable.reload();
            }
        }
    }

    public <T extends OkaeriConfig> T getConfig(Class<T> clazz) {
        return clazz.cast(this.configs.get(clazz));
    }

}
