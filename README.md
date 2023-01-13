# server-connect
An attempt to have a proxy site between a Minecraft client and server. This will make it
possible to inspect and modify Minecraft protocols during a connection to a server.
Since this is still in early development, Standalone mode is only available making it possible
to connect to a Minecraft server without having to run a Minecraft client.

> Currently configured to work only with **Minecraft server V1.16.5**.

## Getting Started

### Requirements
- Operating System - Windows (Not tested on other platforms but most likely works)
- Java 17 64-bit or later
- Maven 3.x.x (from your package manager on Linux / OSX
  ([Homebrew](https://github.com/Homebrew/brew)) or
  [from the jar](https://maven.apache.org/install.html) for any OS)

### Build
```bash
$ mvn clean package
```

### Run
```bash
$ mvn exec:java
```

## Testing Locally

### Minecraft Server Setup
1. Download Jar: [Minecraft Server 1.16.5](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-16-5).
2. Move the jar into a dedicated directory as it will create many files.
3. Open a Shell/CMD and `cd` into the directory.
4. Run the following command: `java -jar server.jar`.
5. An error will be thrown regarding eula. Read the [eula](https://account.mojang.com/documents/minecraft_eula)
and open the file named `eula.txt` that is in the same directory. Change the line `eula=false` to `eula=true` 
and save to agree to the eula.
6. Open another file called `server.properties`. Here you can change many configurations for the server such as
your port. We will be looking for the configuration `online-mode`. This is important as server-connect cannot do 
encryption yet. Change `online-mode:true` to `online-mode:false`. Look for `network-compression-threshold` and change
the value to -1 to disable compression. This app currently does not support compression either. Save the file.
7. Finally, run this command: `java -Xmx1024M -Xms1024 -jar server.jar --nogui`. `Xmx` flag is for max memory
size and `Xms` flag is initial memory size. `nogui` flag prevents the gui from opening. All flags are optional
except for the `jar` flag.

> By default, this server is run on the ip address `127.0.0.1` and port `25565`.

## Features

### Modes
| Implemented             | Feature           | Stage             |
|-------------------------|-------------------|-------------------|
| :ballot_box_with_check: | Standalone Client | Early Development |
| :x:                     | Proxy             | Planned           |

### Networking
| Implemented         | Feature           | Stage     |
|---------------------|-------------------|-----------|
| :white_check_mark:  | AuthServer Api    | Developed |
| :white_check_mark:  | SessionServer Api | Developed |

### Connection with Server
| Implemented             | Feature               | Stage            |
|-------------------------|-----------------------|------------------|
| :x:                     | Compression           | Planned          |
| :x:                     | Encryption            | Planned          |
| :ballot_box_with_check: | ClientBound Protocols | Late Development |
| :white_check_mark:      | ServerBound Protocols | Developed        |

