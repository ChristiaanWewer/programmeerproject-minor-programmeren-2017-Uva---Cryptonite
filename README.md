# programmeerproject*minor*programmeren*2017*Uva***Cryptonite
Programmeerproject app voor de minor programmeren

# Project Info
Naam: Christiaan Wewer

Studentnummer: 11943858

Soort applicatie: Android applicatie

Naam applicatie: Cryptonite

Omschrijving: Applicatie om  prijzen van cryptomunten te bekijken

# Copyright statement
Dit project is ontwikkeld door Christiaan W. en is ook eigendom van Christiaan W. Zie de License file voor meer info.


# Doelgroep
Als een persoon in kwestie cryptomunten heeft en de koersen actief in de gaten wil houden, heeft deze gene hier een Android applicatie voor nodig.

# Oplossing
Een applicatie waarmee de prijzen van cryptomunten realtime in garfiekvorm bekeken kunnen worden. Ook kan er bij een bepaalde stijging/daling een notificatie gestuurd worden.

## Screenshots
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/Overview.png" alt="Screenshot Cryptonite main/search" width="200px">
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/Favorites.png" alt="Screenshot Cryptonite favorite" width="200px">
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/Searchresults.png" alt="Screenshot Cryptonite main/search" width="200px">
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/SpecificInfo.png" alt="Screenshot Cryptonite specific info" width="200px">
<img src="https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/Graph.png" alt="Screenshot Cryptonite graph info" width="200px">


## Project Data
### API's
* coinmarketcap api: https://coinmarketcap.com/api/
  lijst van cryptomunten en haar prijzen ophalen
* cryptocompare api: https://www.cryptocompare.com/api/
  historische prijzen ophalen van cryptomunten

### Libraries
* ASyncHttpRequest: http://loopj.com/android*async*http/
  JSON API ophalen
* GraphView: http://www.android*graphview.org/
  grafieken bekijken van prijzen
* SQLLiteDataBase
 favorieten opslaan
  
## Vergelijking andere app
### Crypto Market
Kan muntlijsten weergeven, heeft onderaan een balk waarmee naar favorieten geswitcht kan worden en bovenin is een balk met zoekfunctie, volgorde functie en driepuntsmenu met koopfunctie er in.
Als er op een munt geklikt wordt kan er basale info bekeken worden en een grafiek. Ook is er een bovenbalk met daarin een favorieten knop en een knop naar de Redditpagina en de coinwebsite.

Ik denk dat bij het beginscherm dit efficienter kan voor mijn app door de onder* en bovenbalk te combineren. De manier om specifieke cryptomuntdata te bekijken zal er uiteindelijk ongeveer hetzelfde uit komen te zien. Deze applicatie onderscheid zich omdat de data realtime is, er een functie die bij bepaalde coinwaarde een notificatie stuurt en daarnaast gratis en zonder reclame is.
  
## Mogelijke technische problemen
* GraphView library zou mogelijkerwijs niet goed werkend gekregen kunnen worden
* ASyncHttpRequest library zou mogelijkerwijs niet zo goed werkend gekregen kunnen worden
* API's worden aangepast tijdens het project
* munt(namen) van beide API's komen niet overeen
* WebSockets implementatie


  
 
  
