plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
}

base.archivesName = project.extra["archives_base_name"] as String
version = project.extra["mod_version"] as String
group = project.extra["maven_group"] as String

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_version"]}")

    implementation("com.google.code.findbugs:jsr305:3.0.2")

    val imguiVersion = project.extra["imguiVersion"] as String
    implementation("io.github.spair:imgui-java-binding:$imguiVersion")
    shadow("io.github.spair:imgui-java-binding:$imguiVersion")
    implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
    shadow("io.github.spair:imgui-java-lwjgl3:$imguiVersion")

    implementation("io.github.spair:imgui-java-natives-windows:$imguiVersion")
    shadow("io.github.spair:imgui-java-natives-windows:$imguiVersion")
    implementation("io.github.spair:imgui-java-natives-linux:$imguiVersion")
    shadow("io.github.spair:imgui-java-natives-linux:$imguiVersion")
    implementation("io.github.spair:imgui-java-natives-macos:$imguiVersion")
    shadow("io.github.spair:imgui-java-natives-macos:$imguiVersion")
}

tasks.processResources {
    inputs.property("version", project.extra["mod_version"])
    inputs.property("minecraft_version", project.extra["minecraft_version"])
    inputs.property("loader_version", project.extra["loader_version"])
    inputs.property("fabric_version", project.extra["fabric_version"])
    filesMatching("fabric.mod.json") {
        expand(inputs.properties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}

tasks.shadowJar {
    configurations = listOf(project.configurations.getByName("shadow"))
    dependencies {
        exclude(dependency("org.lwjgl:lwjgl"))
        exclude(dependency("org.lwjgl:lwjgl-glfw"))
        exclude(dependency("org.lwjgl:lwjgl-opengl"))
    }
}

tasks.remapJar {
    dependsOn(tasks.shadowJar)
    inputFile = tasks.shadowJar.get().archiveFile
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks.jar.get().archiveFile) {
                classifier = "dev"
            }
        }
    }
    repositories {
        mavenLocal()
    }
}
