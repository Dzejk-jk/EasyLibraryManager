# 📚 Library Manager
 
Konzolová aplikace pro správu knihovny napsaná v Javě.
 
## O projektu
 
Library Manager je CLI aplikace, která ti umožňuje spravovat malou knihovnu — přidávat knihy, půjčovat je uživatelům, vracet je a prohledávat kolekci. Všechna data se ukládají mezi spuštěními do JSON souboru.
 
## Funkce
 
- ➕ Přidávání knih — fyzické knihy, e-booky a audioknihy
- 👤 Vytváření a správa uživatelů
- 📖 Půjčování a vracení knih
- 🔍 Vyhledávání podle názvu nebo autora
- 💾 Perzistentní ukládání dat přes JSON soubor
 
## Použité technologie
 
- **Java 17+**
- **Jackson** — serializace/deserializace JSON
- **Maven** — správa závislostí
 
## Struktura projektu
 
```
src/
└── cz/jk/library/
    ├── Main.java
    ├── model/
    │   ├── Book.java              # Abstraktní základní třída
    │   ├── PhysicalBook.java
    │   ├── Ebook.java
    │   ├── AudioBook.java
    │   ├── User.java
    │   ├── BookStatus.java        # Enum: AVAILABLE / BORROWED
    │   ├── EbookFormat.java       # Enum: PDF / EPUB
    │   └── LibraryData.java       # Wrapper pro JSON ukládání
    ├── service/
    │   ├── LibraryService.java    # Logika správy knih
    │   ├── UserService.java       # Logika správy uživatelů
    │   └── JsonStorage.java       # Načítání/ukládání dat do JSON
    ├── ui/
    │   └── ConsoleMenu.java       # Veškerá interakce s uživatelem
    └── interfaces/
        └── Searchable.java
```
 
## Jak spustit
 
### Požadavky
- Java 17 nebo vyšší
- Maven
 
### Postup
 
```bash
# Naklonuj repozitář
git clone https://github.com/your-username/library-manager.git
cd library-manager
 
# Sestavení projektu
mvn clean package
 
# Spuštění aplikace
java -jar target/library-manager.jar
```
 
Data se automaticky ukládají při ukončení do souboru `~/library.json`.
