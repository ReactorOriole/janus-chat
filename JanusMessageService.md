# Introduction #

Janus Message service is an abstract class that can be extended to any subclass that needs to send network messages. It provides abstract methods that can be called with relevant information. There are `sendHelloServerMessage`, `sendServerMessage`, and `sendClientMessage`. These methods are used to communicate with a server entity and a client entity in an easy and simple way. It seems that the Janus Message Service may change to Janus Client Message Service, but that is yet to be determined.


# Details #

## sendHelloServerMessage ##
`sendHelloServerMessage` is used to communicate to the Server that a client has come online with a user. It sends off an XML message in the following format:
```
<message>
     <type>1</type>
     <sn>[Screen Name]</sn>
     <clientip>[IP Address]</clientip>
</message>
```
where the `type` indicates it is a helloServerMessage, `sn` is the screen name of the client that just signed on, and `clientip` is the IP address of the client. The method signature for `sendHelloServerMessage` is the following:
```
static boolean sendHelloServerMessage( String screenName, String ipAddress )
```
The method is called statically with a String screen name and a string ip address and returns a boolean of whether the message succeeded.

## sendServerMessage ##
`sendServerMessage` is used to send an XML message to the server. Currently it acts as helper method, and if that continues to hold true it will change to a private method as opposed to public. `sendServerMessage` takes in a document that contains an XML message, reads the server configurations from an XML file (this is used to hotchange server information), and sends the XML document off to the server. The signature for `sendServerMessage` is the following:
```
static boolean sendServerMessage( Document document )
```
Again, the return type is a boolean to indicate whether the message was sent correctly.

## sendClientMessage ##
`sendClientMessage` is used to send a message to a client. It creates the following XML to send to the designated client:
```
<message>
     <fromIP>[IP Address]</fromIP>
     <chatMessage>[Message]</chatMessage>
</message>
```
where `fromIP` contains the sender's IP and the `chatMessage` is the message being sent. The signature for `sendClientMessage` is the following:
```
static boolean sendClientMessage( String clientIP, String myIP, String chatMessage ) throws IOException
```
`sendClientMessage` throws an IOException if there is an error closing the connection to the client. Again, it returns a boolean to indicate the success of the message that was sent. `clientIP` is the destination IP address, `myIP` is the source IP address, and `chatMessage` is the text message that is being sent to the client. `sendClientMessage` creates the XML message from the given inputs and sends it off to the destination client.