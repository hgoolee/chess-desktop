# Chess for Desktop
![](/images/Chess%20for%20Desktop_Main.png)
* This is a chess game which can be played on desktop (both on Windows and Mac OS X). This program has been developed by *Java Swing* GUI.
* This game follows the standard chess rule (FIDE Laws of Chess). Please refer to [FIDE Handbook](https://handbook.fide.com/) for detailed information on up-to-date rules.


## Table of contents
1. [Description](#description)
1. [How to get started](#how-to-get-started)
   1. [Prerequisites](#prerequisites)
   1. [How to install Maven plug-in on IDE](#how-to-install-maven-plug-in-on-ide)
   1. [Importing the project into Eclipse IDE](#importing-the-project-into-eclipse-ide)
   1. [Importing the project into IntelliJ IDEA](#importing-the-project-into-intellij-idea)
1. [Features](#features)
   1. [Basic chess moves](#basic-chess-moves)
   1. [Special moves - Castling](#special-moves---castling)
   1. [Special moves - En Passant](#special-moves---en-passant)
   1. [Stalemate](#stalemate)
   1. [Pawn promotion](#pawn-promotion)
   1. [Save and load game](#save-and-load-game)
   1. [Undo move](#undo-move)
   1. [Reverse board](#reverse-board)
   1. [Connect to online](#connect-to-online)
1. [Troubleshooting and FAQ](#troubleshooting-and-faq)
1. [Built With](#built-with)
1. [Additional Information](#additional-information)


## Description
"Chess for Desktop" is a great program to learn chess and try new tactics for all level of players: Beginners, Intermediate, and Advanced.

As this game is initially designed for two players, playing with a partner is highly recommended. The player who goes first becomes "White" and the player who moves next becomes "Black". In every single turn, each player is given 60 seconds, and if the player does not move within the given time, the opponent automatically gets the turn.


## How to get started
### Prerequisites
* JDK 8 (Preferred but not mandatory)
* Recommended: Eclipse IDE (Other IDEs such as IntelliJ IDEA can also be used)
* Fork this repository to your GitHub account and clone the fork to your computer

### How to install Maven plug-in on IDE
* You may skip this part if Maven plug-in is already installed on your IDE. However, if you are using an old version, you might need to install it first.
1. Open Eclipse
1. Go to `Help` > `Eclipse Marketplace` and search by Maven
1. Click `Install` button at `Maven Integration for Eclipse` section
1. Follow the instruction step by step
1. After successful installation, go to `Window` > `Preferences`
1. Check that Maven is enlisted at left panel

For IntelliJ IDEA users, refer to [Maven - Help | IntelliJ IDEA - JetBrains](https://www.jetbrains.com/help/idea/maven-support.html).

### Importing the project into Eclipse IDE
* Prerequisite: Make sure you have Maven plug-in installed in your Eclipse
1. Open Eclipse
1. Click `File` > `Import` and type Maven in the search box under `Select an import source`
1. Select `Existing Maven Projects` and click `Next`
1. Click `Browse` and select the folder that is the root of the Maven project (probably contains the `pom.xml` file)
1. Click `Next` > `Finish`

### Importing the project into IntelliJ IDEA
1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
1. From the Welcome screen, click `Import Project`
1. Navigate to your Maven project and select the top-level folder, then click `OK`
1. Select `Import Project from external model` > `Maven` and click `Next`
1. Select `Import Maven projects automatically` and leave the other fields with default values
1. Keep clicking `Next` and ensure that your `src` folder is checked
1. Rename the project if you want and click `Finish`

**Should you have any issues launching the game, please refer to one of these websites below.**

  * [Eclipse](https://www.eclipse.org/) | [IntelliJ](https://www.jetbrains.com/idea/) | [Maven](https://maven.apache.org/)


## Features
Here are some introduction on each feature to help you get used to chess. Be sure to read carefully so that you can fully enjoy the game.

Below is a screenshot of the in-game window.

![](/images/Chess%20for%20Desktop_InGame.png)

### Basic chess moves


### Special moves - Castling


### Special moves - En Passant


### Stalemate


### Pawn promotion


### Save and load game
While playing the game, you can save the game if you want to pause for a while. Then you can continue playing at a later time from the point where you have saved.

Please refer to [Troubleshooting and FAQ](#troubleshooting-and-faq) for instructions on how to save and load game.

### Undo move


### Reverse board


### Connect to online
Coming Soon!


## Troubleshooting and FAQ
**Q. Main page and chess pieces are not shown properly. How can I solve this issue?**

There are `res` and `src` on root(top) directory.
The `res` contains all images used by the program. You can use `getResource` function to retrieve the image and assign to `Swing` Object. For example, the following code will assign `res/logo.jpg` to the image icon of `this`.

```
this.setIconImage(new ImageIcon(getClass().getResource("/logo.jpg")).getImage());
```

To make sure `resource` is working properly, you should set up the project structure like the following instruction. First, click `res` directory. Second, set as `Resources`. That’s it! All images of pieces in the `res/pieces` directory have already been prepared.

**Q. How can I save my game? And how can I load an existing game?**
* To save (This feature only works while you are playing the game)
  1. Go to the right side of the screen and click `Save Game`
  1. Choose the directory and the name of file
  1. Click `Save`
  1. Go to the upper left side of the screen and click `Game` > `Exit`
  1. Click `Yes` and go to the directory to check whether the file has been successfully saved

* To load
  1. Run the game
  1. Click `Load Game` on main page
  1. Choose the directory of the file and click `Open`
  1. You can now continue the game from the saved point

* Alternatively, you can load the game while playing the game. However, once you load an existing game, your current game will not be saved. *(Not Recommended)*
  1. Go to the right side of the screen and click `Load Game`
  1. Choose the directory of the file and click `Open`
  1. You can now continue the game from the saved point

**Please feel free to contact us if you have any questions or need further assistance.**


## Built With
* Maven 3.3.9
* Eclipse Neon.3 (4.6.3)
* Libraries and API used: Java AWT, Java Swing
* Some parts of this application is based on the source code provided by [Oracle](/LEGAL_NOTICE.txt).

## Additional information
* [Developer Guide](/DeveloperGuide.md) : Information about how this program is designed
* Contributors
  * Lee Hyung Goo
  * Jung Sung Oh
