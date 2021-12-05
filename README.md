# Presentation

This is a simple project that contains static classes in order to simplify the use of Minecraft API.

# Download

First you need to download this project on your computer. To do so, you can use the following command line :

```git
git clone https://github.com/Pierre-Emmanuel41/minecraft-managers.git --recursive
```

and then double click on the deploy.bat file. This will deploy this project and all its dependencies on your computer. Which means it generates the folder associated to this project and its dependencies in your .m2 folder. Once this has been done, you can add the project as maven dependency on your maven project :

```xml
<dependency>
	<groupId>fr.pederobien</groupId>
	<artifactId>minecraft.managers</artifactId>
	<version>3.0_MC_1.13.2-SNAPSHOT</version>
</dependency>
```

# Classes

This project contains many managers such as BukkitManager, PlayerManager or TeamManager.