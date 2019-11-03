# PCAP-ANALYZER
###Author : DELVILLE Francois

Usage : 

java -jar artifact.jar pcapfilename [OptionalFilter] 

-h : Display Help

OptionalFilter : "IPV4", "UDP", "TCP", "DHCP", "HTTP", "DNS", "FTP", "ARP", "ICMP"

#### Fonctionnement [FRENCH]

Le programme prend un fichier pcap en entrée et va stocker son contenue dans un tableau de byte.
Ensuite on va créer 1 par 1 chaque packet avec son typage. J'ai utilisé l'héritage pour la conception des packets (Un packet ARP hérite de Ethernet qui lui meme hérite de Packet)

La création de packet s'arrête au début de la couche applicative pour permettre de regrouper les packets en session.

Chaque session est ensuite analysé pour resortir le protocole de la couche applicative associé (HTTP / FTP etc...).

On regroupe ensuite tout les packets dans une meme liste et on les trie par date croissante.

On affiche ensuite les packets grace a l'objet Viewer. 