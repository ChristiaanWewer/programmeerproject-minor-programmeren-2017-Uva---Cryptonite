# Geavanceerd Design Document

# Geavanceerde sketch
![alt text](https://github.com/ChristiaanWewer/programmeerproject-minor-programmeren-2017-Uva---Cryptonite/blob/master/doc/AdvancedProjectDesign.png?raw=true "Advanced Application Design Cryptonite")

# Project infrastructuur

## class namen
| Class namen |
| --- |
| MainCryptoActivity |
| MainCryptoFragment |
| FavoriteCryptoFragment |
| ListenerCryptoFragment |
| InfoCryptoActivity |
| TextInfoCryptoFragment |
| GraphInfoCryptoFragment |
| CoinWebSocket |
| coinListModel |
| coinListenerListModel |
| SQLLiteDatabse |

## classen
| MainCryptoActivity |
| --- |
| searchCoin() |
| refreshList() |
| goToFavorites() |
| goToListener() |
| loadRecentCoins() |
| onFail() |

| MainCryptoFragment |
| --- |
| loadRecentCoins() |
| onLongClickListener() |

| FavoriteCryptoFragment |
| --- |
| loadFavoriteCoins() |
| onFavoriteClickListener() |
| onLongClickListener() |

| ListenerCryptoFragment |
| --- |
| loadListenerCoins() |
| onListenerClickListener() |
| onLongClickListener() |

| InfoCryptoActivity |
| --- |
| |

| TextInfoCryptoFragment |
| --- |
| cryptoDataWebStream() |
| graphButtonListener() |

| GraphInfoCryptoFragment |
| --- |
| infoButtonListener() |
| graphViewListener() |

| CoinWebSocket |
| --- |
| getLiveCryptoCoinData() |

| coinListModel |
| --- |
| getCryptoCoinNumber() |
| getCryptoCoinName() |
| getCryptoCoinPrice() |
| getCryptoCoinPriceChange() |
| setCryptoCoinNumber() |
| setCryptoCoinName() |
| setCryptoCoinPrice() |
| setCryptoCoinPriceChange() |

| coinListenerListModel |
| --- |
| getCryptoCoinNumber() |
| getCryptoCoinName() |
| getCryptoCoinPrice() |
| getCryptoCoinHighPrice() |
| getCryptoCoinLowPrice() |
| setCryptoCoinNumber() |
| setCryptoCoinName() |
| setCryptoCoinPrice() |
| setCryptoCoinHighPrice() |
| setCryptoCoinLowPrice() |

| SQLLiteDatabse |
| --- |
| add() |
| selectAll() |
| delete() |

# Externe data
## API's
- coinmarketcap api: https://coinmarketcap.com/api/
  lijst van cryptomunten en haar prijzen ophalen
- cryptocompare api: https://www.cryptocompare.com/api/
  historische prijzen ophalen van cryptomunten

Data wordt in een datamodel gezet waar een ArrayList van gemaakt wordt. 
Voor Data als favorieten en een luisteraar wordt een database gemaakt waar dit in opgeslagen wordt.

## Libraries
- ASyncHttpRequest: http://loopj.com/android-async-http/
  JSON API ophalen
- GraphView: http://www.android-graphview.org/
  grafieken bekijken van prijzen
- SQLLiteDatabse
  favorieten en luisteraars opslaan
 



