# programmeerproject-minor-programmeren-2017-Uva---Cryptonite
Programmeerproject app voor de minor programmeren

# Project Info
Naam: Christiaan Wewer
Studentnummer: 11943858
Soort applicatie: Android applicatie
Naam applicatie: Cryptonite
Omschrijving: Applicatie om prijzen van cryptomunten te bekijken

# Doelgroep
Als een persoon in kwestie cryptomunten heeft en de koersen actief in de gaten wil houden, heeft deze gene hier een Android applicatie voor nodig.

# Oplossing
Een applicatie waarmee de prijzen van cryptomunten in garfiekvorm bekeken kunnen worden.

# Functies Applicatie
## Hoofdfuncties
- Lijst van munten bekijken met huidige prijzen en prijsveranderingen
- Lijst van favorieten
- Scherm met muntgrafiek en muntdata

## Eventuele Functies
- Websockets om realtime data op te vragen in plaats van refreshfunctie
- Achtergrondservice die een notificatie stuurt bij een bepaalde prijsverandering

## Design Applicatie
![alt text](https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/ProjectDesign.png?raw=true "Application Design Cryptonite")

## Project Data
### API's
- coinmarketcap api: https://coinmarketcap.com/api/
  lijst van cryptomunten en haar prijzen ophalen
- cryptocompare api: https://www.cryptocompare.com/api/
  historische prijzen ophalen van cryptomunten

### Libraries
- ASyncHttpRequest: http://loopj.com/android-async-http/
  JSON API ophalen
- GraphView: http://www.android-graphview.org/
  grafieken bekijken van prijzen
  
 
  
