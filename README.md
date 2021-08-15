![Kores-SourceWriter](https://github.com/koresframework/Kores-SourceWriter/blob/master/Kores-source.png?raw=true)

[![jitpack](https://jitpack.io/v/JonathanxD/Kores-SourceWriter.svg)](https://jitpack.io/#JonathanxD/Kores-SourceWriter)
[![Discord](https://img.shields.io/discord/291407467286364164.svg)](https://discord.gg/3cQWmtj)
[![Actions](https://img.shields.io/github/workflow/status/koresframework/Kores-SourceWriter/Gradle%20Package)](https://github.com/koresframework/Kores-SourceWriter/actions)
[![Packages](https://img.shields.io/github/v/tag/koresframework/Kores-SourceWriter)](https://github.com/orgs/koresframework/packages?repo_name=Kores-BytecodeSource)

## How to use Kores-SourceWriter

Kores-BytecodeWriter is now using [GitHub Packages](https://github.com/orgs/koresframework/packages?repo_name=Kores-SourceWriter) to distribute its binary files instead of [jitpack.io](https://jitpack.io) (because jitpack still not support all JDK versions and sometimes `jitpack.yml` simply do not work).

In order to be able to download Kores-SourceWriter Artifacts, you will need to configure your global `$HOME/.gradle/gradle.properties` to store your username and a [PAT](https://github.com/settings/tokens) with `read:packages` permission:

```properties
USERNAME=GITHUB_USERNAME
TOKEN=PAT
```

Then configure your `build.gradle` as the following:

```gradle
def GITHUB_USERNAME = project.findProperty("USERNAME") ?: System.getenv("USERNAME")
def GITHUB_PAT = project.findProperty("TOKEN") ?: System.getenv("TOKEN")

repositories {
    mavenCentral()
    maven {
        url "https://maven.pkg.github.com/jonathanxd/jwiutils"
        credentials {
            username = GITHUB_USERNAME
            password = GITHUB_PAT
        }
    }
    maven {
        url "https://maven.pkg.github.com/koresframework/kores"
        credentials {
            username = GITHUB_USERNAME
            password = GITHUB_PAT
        }
    }
    maven {
        url "https://maven.pkg.github.com/koresframework/kores-sourcewriter"
        credentials {
            username = GITHUB_USERNAME
            password = GITHUB_PAT
        }
    }
}

dependencies {
    implementation("com.github.koresframework:kores:4.1.9.base") // Replace 4.1.9.base with the preferred version
    implementation("com.github.koresframework:kores-sourcewriter:4.1.11.source") // Replace 4.1.11.source with the preferred version
}
```

This is only needed because **GitHub** still not support unauthenticated artifact access.