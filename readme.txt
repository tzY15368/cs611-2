@@ -0,0 +1,88 @@
# CS611-Assignment 3

## Generic Board Game

----
Tingzhou Yuan

tzyuan15@bu.edu

U79759599

----

## How to compile and run

----

1. `unzip cs611 && cd cs611`
2. `javac -d ./bin *.java`
3. `java -cp ./bin Main --config PATH_TO_CONFIG`

## design specific document

### Core components

- Entity
There are three types of entities in this game: hero, monster and merchant(trader). They share attributes like inventory and name, and can fight or trade against each other.

- Item
Everything in the entity's inventory can be considered as an item, be it potions, weapons, spells...

- Space
As the description mentioned, there are three types of spaces: market, common and inaccessible. They share a common Space base class which provides abstract hooks of the lifecycle of game, include entity move-in, entity move-out and produce events based on specific class implementations. For market, this means asking the player if he wants to trade, for common, this means rolling the dice to decide if there's monsters in the space.

- I/O
based on the game specifications, all the possible user inputs can be mapped to several enum keys: `W,A,S,D,M,I,Q,Y,N`, the IODriver class implements the I/O interface which provides several functions either to show user info or to get valid info from user in the form of strings. It also provides a generic menu for game development that shows a generic user-friendly CMDLINE menu to players and returns valid result.

This component is easily replaceable even in a GUI game as long as the new IODriver implemnets the IO interface.

The game specification explictly mentioned the requirement to be able to show map & current info at any time. The IODriver implemnetation has this built in without directly depending on any Game instance. Instead, two callback functions are registered upon initialization of the IODriver, the functions are called on-demand so that the info is up to date.

- EntityManager
A singleton that holdes a single copy of all the parsed Entity instances read from ConfigLoader

- ItemManager
A singleton that holdes a single copy of all the parsed Item instances read from ConfigLoader

- ConfigLoader
Hard-coding the entity & item specifications does not make sense as it makes the game non-configurable at all. The game starts, parses commandline arguments and initiates the Configloader with the config path. The ConfigLoader does NOT create the instaces on its own, instead only holding data in a hashmap then asking both entityManager and itemManager to try their best to create instances with valid formatted config data.

- Playground
Holds the player positions with reference to the space[][] created by the Spacefactory. Also handles player movements on the game-board and validates player moves.

### Design patterns

- Singleton
Both ItemManager and EntityManager has only one instance as they are the centralized managers of other objects, so they have to be singleton.

The IODriver is NOT a singleton because of the potential drawback that this would hinder the ability to scale to one with possibly MULTIPLE I/O outputs: perhaps multiple dialogues in a GUI or even a distributed environment over the network.

- AbstractFactory
Abstract factories are extensively used in this project, decoupling many of the critical parts.

Individual entiyt factories (heroes, merchants and monsters) are assigned to spaces so that: common spaces can create monsters on demand, market spaces can create traders, and the heroes are guaranteed to spawn in commonspace at (0,0) by providng the HeroEntity.

Inventory factories are provided to the constructor of Entity instances, so that traderEntities get a pre-defined inventory, monsters get nothing and heroes start with a default weapon and some gold.

Space factories are provided to the actual Game constructor so that playgrounds of any size with any probability of monsters, markets and commonspace can be generated.

- Strategy
An abstract stategy makes fight moves based on the entity's state or external input. For monsters, they can only make a move that deals damage to the enemy; For heroes, they can choose between using potion, weapons, spells or equipping armor.

For Merchants, they can only take hits in theory (but you cannot fight in a market)
Two squads fight against each other in certain order: this can be determined by providng different factories that would create different fighting strategies: one-to-one(monsters) or one-to-any-by-player designation(heroes).

- Iterator
The FightTurnStrategy returns iterators of entity pairs that would fight.

- Inversion of control
The exact implementation of showMap and showGameInfo depends on what the game provides the IODriver, inversing the control.
The Items delegate side effects handling to the Inventory class.

### Game mechanisms
Map, inventory and game status is directly accessible at any time when users are asked to input their move with the help of IODriver.

The market accepts multiple squads at a time and offers trade upon space entry. The trade happens in two phases: buy and sell. In buy phase player choose from the merchant's inv and tries to buy things; in sell phase plyers choose from his own inv and sell things at a discount.

The battle takes an iterator of pair of two entities that would fight. The iteration order is determined by the specific turn strategy supplied. During the fight, entities call the opponent's takeDamage interface to apply the effects created by the entity(potion, weapon, damage...) At the end of the fight, the squads checks if one of the sides is wiped out, and changes the game state correspondingly.