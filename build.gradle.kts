import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.8"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.4"
}

group = "dev.rollczi"
version = "1.0.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.panda-lang.org/releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    implementation("dev.rollczi:litecommands-bukkit:${Versions.LITECOMMANDS}")
    implementation("dev.rollczi:litecommands-adventure:${Versions.LITECOMMANDS}")

    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:${Versions.OKAERI_CONFIGS}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${Versions.OKAERI_CONFIGS}")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:${Versions.OKAERI_CONFIGS}")

    testImplementation(platform("org.junit:junit-bom:${Versions.JUNIT}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:${Versions.ASSERTJ}")
    testImplementation("org.awaitility:awaitility:${Versions.AWAITILITY}")
}

val pluginName = "LiteEnchants"
val packageName = "dev.rollczi.liteenchants"


paper {
    name = pluginName
    version = project.version.toString()
    website = "https://rollczi.dev/"
    author = "Rollczi"
    apiVersion = "1.19"

    // Plugin main class (required)
    main = "$packageName.$pluginName"
    bootstrapper = "$packageName.${pluginName}Bootstrap"
    hasOpenClassloader = false
}

tasks.shadowJar {
    archiveFileName.set("$pluginName v${project.version}.jar")

    listOf(
        "panda.std",
        "dev.rollczi.litecommands",
        "net.jodah.expiringmap",
//        "eu.okaeri.configs",
        "dev.piotrulla.craftinglib",
    ).forEach { relocate(it, "$packageName.libs.$it") }

    exclude("org/intellij/lang/annotations/**", "org/jetbrains/annotations/**", "META-INF/**")
}

tasks.runServer {
    minecraftVersion("1.21")
}