# PuzzleBattle
This application, Puzzle Battle, is created using java language. It is designed by using javafx classes which helps build amazing screens and animations. All created games witch all screens are based on javafx classes. It should give players option to play simple games online with each other and easily compare their results. Players, with their profiles, can find another friends in a play.

## About



## Parts of software

### Core

### Server

### Client
Client application is used by players to play games from our offer throught internet. They can achieve lot of experiences during play. Then their score will be stored. Best players should be on the top of game table. Today they can only play two games called Four in a row and Ballbouncer. Later more games will be added.

<br>

Users can register themselves and create their own profiles with different nicknames. Nickname is player's name in a game. It is mandatory field. Password must be written twice and must be always same. Email address is useful, and should be added too.

<br>

If they want, they can add additional information, for example
their name, surname and date of birth. Age will be created from their date of birth. Players can add their photo too.
![Additional information screen](https://github.com/gyurix/PuzzleBattle/blob/master/doc/resources/AdditionalInformation.png)

<br>

Their profile is displayed in another screen. On top is player's nick. On the left basic information are displayed. On the right photo should be positioned. There is only representation of player's face, if player hasn't added his photo yet. With all information looks like as this representation:
![Player profile screen](https://github.com/gyurix/PuzzleBattle/blob/master/doc/resources/playerProfile.png)

<br>

After creating their account they can log in with their nick and password throught login screen.
![Login screen](https://github.com/gyurix/PuzzleBattle/blob/master/doc/resources/login.png)

<br>

After successful log in player visits game menu. Here he can view best score which was achieved, or best players, who played this game with certain score. Relogin, playing games are possible too. Game menu for two games mentioned above looks like this:
![Game menu screen](https://github.com/gyurix/PuzzleBattle/blob/master/doc/resources/gameMenu.png)


## Protocol

### Packet format

#### Unencrypted packet format
| Length  | Packet ID | Packet Data        |
|---------|-----------|--------------------|
| 4 bytes | 1 byte    | \<length\>-1 bytes

#### Encrypted packet format
| Length  | Encrypted data   |
|---------|------------------|
| 4 bytes | \<length\> bytes |


After decrypting the encrypted data, we get the bytes of the unencrypted packet format.


### Authentication process

**Keywords**
- **IV** = Initial vector used at AES 256 encryption
- **C** = Client
- **S** = Server

**Process**
1. Client generates **RSA 2048** keys
2. **C ••► S:** Handshake packet (unencrypted, including the generated public key) 
3. Server generates **AES 256** key, random encryption and decryption IV
4. **S ••► C:** Handshake packet (encrypted using the received public key, includes
the generated AES 256 key, encryption key, decryption key)
5. Client decrypts the received packet with it's **RSA 2048** private key
6. **C ••► S:** Login packet (encrypted using the received enryption key
together with the new IV we are going to use for encryption next time)
7. Server decrypts the received packet with the encryption key, what he sent to client,
and remembers the new IV, which will be used for the next decryption process)   
8. **S ••► C:** Login result packet (encrypted using the decrypt key sent to client together
with the new IV we are going to use for the encryption next time)


### List of packets 

## Database


### Used entities and their relations

![](resources/db-tables.png)
