# Report
Christiaan Wewer
11943858

## Korte beschrijving
Met deze app kan er met behulp van een lijst de 100 populairste cryptomunten bekeken worden. Per munt kan er relevante informatie bekeken worden. Dat is met behulp van een lijst en een grafiek. Daarnaast is er een favorieten optie waar munten aan toegevoegd kunnen worden en kan er gezocht worden naar munten.
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/Overview.png" alt="Screenshot Cryptonite main activity" width="200px">

## design overview
Als de app geopend wordt, komt er een lijst van coins tevoorschijn. Als hier op geklikt wordt, wordt specifieke informatie weergegeven. Wordt op in dit scherm op de knop 'Graph' geklikt, dan wordt er een grafiek weergegeven. Wordt er bij de coinlijst lang op een coin gedrukt, dan wordt deze toegevoegd aan favorieten. De bovenste balk van het beginscherm bevat een zoekbalk, refreshknop en favorietenknop. Deze spreken voor zich.

## Technisch design

### Activities
#### CryptoniteActivity
Dit is de hoofd activity. Vanuit deze activity wordt de fragment CoinOverviewListFragment aangeroepen bij het openen. Ook worden de favorietenknop, searchbar aangestuurd. Vanuit de favorietenknop wordt de FavoriteCoinListFragment aangestuurd.
#### SpecificCoinInfoActvity
Dit is de activity voor specifieke coin informatie. Hierin wordt bij start de SpecificCoinInfoFragment geladen. Deze start als er op een coin gedrukt wordt vanuit de CoinOverviewListFragment en FavoriteCoinListFragment.

### Fragments
#### CoinOverviewListFragment
Deze fragment start bij het starten van de app. Deze fragment stuurt de NetworkAndMergeInfoClass aan om zo de volledige lijst van coins op te halen met alle info er in. Dan wordt dit via de CryptoAdapter in de listview geladen. Als er de bundle search meegegeven wordt, zal er niet NetworkAndMergeInfoClass gebruikt worden, maar wordt de bestaande lijst van coins doorzocht met een query.

#### FavoriteCoinListFragment
Deze fragment wordt geïnitialiseerd als er op de menuknop voor favorieten gedrukt wordt. Dan zal het uit de FavoriteCoinDatabase de coins laden met de benodigde info om via de NetworkAndMergeInfoClass de lijst aan te vullen met alle benodigde info.

#### SpecificCoinInfoFragment
Deze fragment wordt aangeroepen vanuit de SpecificCoinInfoActvity. Deze fragment laat de actuele specifieke coin informatie zien. Dat wordt om de 30 seconden herladen via een timertask die asynchroon werkt. Dat wordt opgehaald met een jsonrequest. 

#### SpecificCoinGraphFragment
Deze fragment wordt aangeropene vanuit de SpecificCoinInfoFragment. Hier wordt de close prijs van de afgelopen 7 dagen in een grafiek geladen. Deze wordt om de 100 seconden herladen.

### Handlers
#### ResponseHandler
Interface handler om de NetworkAndMergeInfoClass functionaliteit niet dubbel te hebben bij de FavoriteCoinListFragment en CoinOverviewListFragment.

### Data models
#### CryptoCoinDataModel
Data model met de functionaliteit om de basis coin informatie vanuit een json array in te laden. Wordt gebruikt om in een lijst van cryptomunten te zetten in CoinOverviewListFragment en FavoriteCoinListFragment.

#### SpecificCryptoCoinDataModel
Data model met de functionaliteit om de coin informatie vanuit een json object in te laden. Wordt gebruikt om een een model aan te maken zodat dit mooi weergegeven kan worden in de SpecificCoinInfoFragment.

### Helper
#### NetworkAndMergeInfoClass
Hiermee kan er een request gestuurd worden om basisinformatie op te halen. Dit wordt in een ArrayList van CryptoCoinDataModellen gestopt. Daarna worden er URL's gegenereerd vanuit deze ArrayList die met zo min mogelijk URL's zoveel mogelijk Coins kunnen ophalen. Deze URL's zijn zo lang mogelijk als mag van de API. Met behulp van deze API's worden netwerkrequests gestuurd om de overige coininfo op te halen. Dit wordt toegevoegd aan de arraylist met coins. Echter is niet iedere coin ondersteund door de API en wordt dus niet weergegeven. Tijdens het mergen van de data wordt daar rekenening mee gehouden. De CoinOverviewListFragment gebruikt dit en de FavoriteCoinListFragment ook, al zal deze de eerste netwerkrequests overslaan.

### Database
#### FavoriteCoinDatabase
SQL database waar de favorieten in opgeslagen worden. Dit gaat per coin enkel om de rank, symbool en naam.

### Adapter
#### CryptoAdapter
Adapter voor CoinOverviewListFragment en FavoriteCoinListFragment.

## uitdagingen
Een uitdaging van het project was het mergen van informatie van coins. De API Cryptocompare kan namelijk alleen een lijst van coins opvragen waar geen prijzen in staan. Ook haalt deze API veel te veel coins op. De API van Coinmarketcap kan dat wel, echter hebben zij nog geen historische data in hun API. Andere API's bieden niet genoeg requests aan of waren onvolledig. Vanwege deze reden heb ik het zo geprogrammeerd dat er eerst een lijst aangemaakt wordt dat gevuld wordt met de eerste 100 coins van cryptocompare. In deze lijst wordt per coin echter enkel de rang, symbool en naam opogeslagen. Daarna wordt dit zoals beschreven staat bij de technische details bijgewerkt met de prijzen van de API van Cryptocompare. Dit was best wel lastig gezien er paar coins niet in Cryptocompare staan of er is in het algeheel geen specifieke informatie van gezien de coin (nog) in de ICO fase zit.
Iets wat ik nog niet eerder gebruikt had was een interface. Om dat te implementeren was in het begin zoeken. Op deze manier kan er een klasse asynchroon lopen wat code mooier maakt.
Een ander soort uitdaging was vooral om kleine bugjes er uit te halen bij de search, refresh knoppen enz. wat het een soepel en goed samenwerkend algeheel maakt.

## Compromissen
Het was eerst de bedoeling om een websocket te implementeren en een achtergrond luisteraar. De websocket implementatie gaf alleen een error door, wat had kunnen liggen aan de API of de implementatie. Uiteindelijk is dit veranderd door een timertask die op de om de x-tijd een netwerkrequest verstuurd.
Om een achtergrond luisteraar te implementeren was uiteindelijk te weinig tijd. Ook om aan alle eventuele functionaliteit te werken was te weinig tijd. 
Een gunstig compromis was de bij uitdagingen beschreven methodiek om de informatie van de coins de mergen. Als ik dit had gedaan zoals in het design document staat, zou de coin informatie afwijken van de specifieke info. Echter ben ik hierdoor niet toegekomen aan de knop om aan favorieten toe te voegen bij een specifieke 
De enige twee aanpassing met het design document op basis van het uiterlijk is dat er bij de grafiek geen terugknop is. Daarvoor kan namelijk gewoon de terugknop van Android gebruikt worden.
