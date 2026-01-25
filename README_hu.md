# NumberSheet Projekt – Magyar leírás

A NumberSheet egy oktatási és gyakorlati célokra készült Java alkalmazás.  
Véletlenszerű számokat generál, különféle funkcionális lekérdezéseket végez rajtuk, majd PDF riportot készít az eredményekből.  
A projekt célja a Java modern funkcionális eszközeinek (Stream API, lambdák, BigInteger, PDFBox) gyakorlása és bemutatása.

---

## 📦 Fő funkciók

### 🔢 1. Számgenerálás
A `NumberGenerator` modul a konfiguráció alapján véletlenszerű egész számokat hoz létre.

- Tartomány: 1–999
- Mennyiség: konfigurációból (`config.properties`)
- Kimenet: `List<Integer>`

---

### 🧮 2. Funkcionális lekérdezések
A `FunctionalQueries` modul különféle műveleteket biztosít:

- **Szűrések**: páros, páratlan, nagyobb mint X, tartományok
- **Átalakítások**: négyzet, duplázás, hozzáadás
- **Reduce műveletek**: összeg, szorzat
- **BigInteger szorzat**: túlcsordulásmentes szorzás
- **Statisztikák**: min, max, sum, average
- **Rendezések**: növekvő, csökkenő
- **Top N**: legnagyobb N elem
- **Csoportosítás**: even/odd
- **Particionálás**: predicate alapján

---

### 📄 3. PDF riport generálás
A `PdfCreator` modul PDF dokumentumot készít a generált számokról és a lekérdezések eredményeiről.

A PDF tartalmazza:

- **Fejléc** (minden oldalon)
- **Tartalom** (lekérdezések eredményei)
- **Lábléc** (oldalszám, dátum)
- **BigInteger szorzat**
- **Többoldalas támogatás**

A PDFBox 3.x könyvtárat használja.

---

### ⚙️ 4. Konfiguráció
A `config.properties` fájlban állítható:
numbers.count=1000 pdf.output=output.pdf


A `ConfigLoader` betölti és validálja az értékeket.

---

### 🛠️ 5. Hibakezelés
A projekt saját `AppException` és `ErrorHandler` modult használ:

- konfigurációs hibák
- fájlkezelési hibák
- PDF generálási hibák
- logikai hibák

---

## 🚀 Futtatás

A projekt belépési pontja:
ProgramLauncher.start();


A futás során:

1. Betölti a konfigurációt
2. Generálja a számokat
3. Formázza az oldalakat
4. PDF-et készít
5. Kiírja a lekérdezések eredményeit

---

## 📚 Használt technológiák

- Java 17+
- Maven
- PDFBox 3.x
- Stream API
- BigInteger
- Saját funkcionális interfészek és lekérdezések

---


---

## 📝 Tervek a jövőre

- Fejlettebb PDF riport (táblázatok, grafikonok)
- Logolás SLF4J + Logback segítségével
- Számlaszerű PDF-ek generálása (külön projekt)
- PDF visszaolvasása és adatkinyerés
- Statisztikai modul bővítése (medián, módusz, szórás)

---

## 👤 Készítette

Csaba – tanulás, gyakorlás és fejlődés céljából.  
A projekt folyamatosan bővül és fejlődik.

inkeko@gmail.com

