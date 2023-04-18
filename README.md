# kekiir_masterwork
# Sproutech Java Beckend Enterprize

## _Next day power trading app_

## Felhasznalasi terulet
A villamos energia kereskedésnél nem csak az áram mennyisége, hanem az áram szállításának az idelye is rendkívül fontos. 
Mivel a villamos energia tárolása rendkívül költséges, ezért fontos, hogy az áram termelése és fogyasztása ugyan akkor történjen,
illetve hogy a termelt és fogyasztott villamosenergia mennyisége azonos legyen. 
Ehhez elengedthetettlen a termelők és a fogyasztók közötti koordináció. 
Az elsődleges villamos energia kereskedelem a
másnapi áram kereskedelem,  amikor az energiatermelők és az energiafogyasztók előre megbeszélik,
hogy mennyi áramra lesz szükségük a következő napon és mennyit kell érte fizetniük. 
Az áram mennyiségének és árának meghatározása a piac kereslet-kínálati viszonyaitól függ,
és az árakat az előző napon határozzák meg. Az energiafogyasztók és energiatermelők közötti 
megállapodásokat elektronikus piactereken kötik meg, mint például az EPEX SPOT. Ez segít
fenntartani az egyensúlyt az áramfogyasztás és -termelés között, és biztosítja, hogy nelegyen túl, 
vagy alulterhelt ahálózat. A PowerTrade segítségével egy olyan adabázist hozhatunk létre, ahol a termelők és 
fogyasztók listázhatják kapacitásaikat, vagy energiaigényüket idő és teljesítmény fügvényében. A PowerTrade 
segítségével lekérhetik a másnapi teljesítmény trendet, és megvizsgálhatják, hogy adott órában mekkora teljesítmény 
áll rendelkezésre és, vagy mekkora kínálatra számíthatnak.

## Az alkalmazás struktúrája
![DB_Schema](https://user-images.githubusercontent.com/105811419/232657598-7ca687ed-97ff-44c9-9bb1-dcd4b7186f4e.png)

## Az alkalmazás működése:

![image](https://user-images.githubusercontent.com/105811419/232667247-eb7766a5-c66b-48d1-89c4-cabe3f06f24b.png)
### A működést szemléltető ábra.

## Modellek
- Termelők(Suppliers)  ( __*/api/supplier/*__ )
    - A felhasznaló névvel és jelszóval regisztrálhat.
    - Regisztráció után bejelentkezés szükséges a további funkciók elérésére.
    - Kapacitás létrehozása
    - Kapacitás módosítása
    - Kapacitás törlése
    - Kapacitások lekérdezése
    - Azonos mérleg órában lévő energia kereslet lekérdezése

- Fogyasztók(Consumers)  ( __*/api/consumers/*__ )
    - A felhasznaló névvel és jelszóval regisztrálhat.
    - Regisztráció után bejelentkezés szükséges a további funkciók elérésére.
    - Energia kereslet létrehozása
    - Energia keresle módosítása
    - Energia keresle törlése
    - Energia keresletek lekérdezése
    - Azonos mérleg órában lévő kapacitások lekérdezése

- Kapacitás (Capacity)  tárolt adatok:
    - Energia forrás
    - Capacitás mennyisége MW-ban
    - Felhasználható kapacitás mennyisége MW-ban
    - Kapacitás ára
    - Kapacitáshoz tartozó PowerQuantitiek
    - Kapacitás kezdeti ideje
    - Kapacitás záró ideje

- Energia kereslet (Demand)  tárolt adatok:
    - Kereslet mennyisége MW-ban
    - Felhasználható kapacitás mennyisége MW-ban
    - Áram ár
    - Kereslethez tartozó DemandQuantitiek
    - Kereslet kezdeti ideje
    - Keresleg záró ideje
    
    ## Funkcionalitas
- REST interface
- Alapveto CRUD muveletek a Keresleteken és a Kapacitáspkon
- Kapacitás, vagy Kereslet POST-olása esetán, a kapacitást lebontja mérlegórányi kapacitás egységekre és hozzárendeli a kapacitás részegységeket az adott mérlegórához
- A Kapacitások és Keresletek  POST-olásának a lezárta után, minden egyes mérlegórában megállapítja a villamos áram árát, és hogy mely Termelők vagy Fogyasztók esnek ki a kerekedelemből.

## API dokumentáció:
http://localhost:8080/swagger-ui/index.html

## Project setup
| Function | Implementation |
| ------ | ------ |
| Project settings | SpringBoot (Gradle) project, JAVA 8 |
| Reposiroty support | Spring Data JPA, hibernate |
| Database | MySQL, H2 |
| Migration tool | Flyway |
| Documantation | OpenAPI standard (Swagger) |
| Containerization | Dockerfile |
| Testing | Junit4, MockMVC |
|Other| Lombok annotations, Javax validation

## Telepítési útmutató: 

1. MySQL adatbázis használatához: 

application properties file-ban a megfelelő beállítások használata illetve a következő környezeti változók felvétele:
```
${DATABASE_URL}
${DATABASE_USERNAME}
${DATABASE_PASSWORD}
```
2. Jar file buildelése:
```
./gradlew build
   ```
4. Docker container buildelése, futtatása: 

```
docker build

docker run
```

Ezután az api a 8080-as porton lesz elérhető.
    
