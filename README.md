# minecraft-managers

This is a simple project that contains static classes in order to simplify the use of Minecraft API.

# Register as maven dependency

It is easy to register this project as dependency for your own project. To do so, you need to download this project.
First, you need to download this project on your machine.

The easiest way to do so is to use the following git command line 

```git
git clone https://github.com/Pierre-Emmanuel41/minecraft-managers.git
```

Then, you need to run the following maven command line : 

```maven
mvn clean package install

```

Finally, in the pom.xml of your project, you have to add the following lines :

```xml
<dependency>
	<groupId>fr.pederobien</groupId>
	<artifactId>minecraft-managers</artifactId>
	<version>1.0</version>
</dependency>
```
You can now use features provided by this api in you project.

# Classes

This project contains many managers such as BukkitManager, PlayerManager or TeamManager. There is nothing special to do before using because they are static.