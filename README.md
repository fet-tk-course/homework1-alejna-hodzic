# Generalno

Objasnjenje implementacije zadace (nivo B) iz predmeta **Razvoj mobilnih aplikacija i servisa**.  

Objasnjenje ce slijediti redoslijed `obaveznih komponenti` iz objasnjenja zadatka B nivoa.

---

## 1. Interfejs Osoba sadrzi metode:
> `fun Identity () : String`  
> `fun Title () : String`  

---

## 2. Osnovna klasa Inzenjer

Klasa `Inzenjer` implementira interfejs `Osoba` i ima modifikator `abstract` sto znaci da se ista ne moze instancirati.

> Moguce je minimalno izmijeniti kod kako bi se modifikator mogao postaviti na open (samo je potrebno zakomentarisanu implementaciju funkcije `Info` odkomentarisati, a zakomentarisati abstract deklaraciju iste).

Posjeduje immutable polje FirstAndLastName jer se ne ocekuje da ce se to polje mijenjati, ostala polja su mutable jer se pozicija, godine iskustva i expertiza moze mijenjati (ovo je vise s logickog aspekta obzirom da nisu kreirani setteri za iste!).


Osnovne informacije o inzenjeru moguce je dobiti pozivom funkcije `EnginnerInfo`, a iste se ispisuju pozivom funkcije `PrintEngineerInfo`.
funkcija `joinToString` se koristi kako bismo elemente kolekcije spojili u jedan string.
``` code
fun EngineerInfo() : String
```
``` code
open fun PrintEngineerInfo() : Unit
```

`PrintEngineerInfo` je metod koji ima modifikator open prije implementacije funkcije sto omogucava da se isti overridea u izvedenim klasama.  

Pored pomenutih, postoji i metod `Info` ciji je modifikator `abstract` i isti se ostavlja na implementaciju u izvedenim klasama kako bi omoguco ispis dodatnih informacija koje potencijalno imaju izvedene klase.
``` code
abstract fun Info () : String
```

---

## 3. Izvedene klase

Izvedene klase `SoftverskiInzenjer` i `InzenjerElektrotehnike` nasljedjuju od osnovne klase `Inzenjer` i implementiraju apstraktni metod `Info` u kom na osnovne podatke o inzenjeru dodaju specificne podatke za konkretnu izvedenu klasu.

Zadrzale su metode interfejsa iz osnovne klase i ponovna implementacija istih nije potrebna, jer su iste u klasi inzenjer bile implementirane bez ikakvih modifikatora sto znaci da su po defaultu final i ne mogu se overrideat u izvedenim kalasama, vec su kao takve nasljedjene. 

---
### Klasa SoftverInzenjer
Ima dodatno mutable polje `NumberOfProjects`, a `Title` polje je konstantno.

`SuccessRate` metod je subjektivno napisan koristeci when loop i odredjuje nivo strucnosti inzenjera na osnovu godina iskustva i broja velikih projekata.

---

### Klasa Inzenjer Elektrotehnike
Ima dodatno mutable polje `NumberOfCertificate`, polje `Title` je konstantno.

`SuccessRate` metod je subjektivno napisan koristeci when loop i odredjuje nivo strucnosti inzenjera na osnovu godina iskustva i broja certifikata.

---

## 4. Grupisanje sa fold()
`fold` funkcija pored lambde uzima i pocetnu vrijednost akumulatora s kojim radi na pocetku lambde. Lambda ove funkcije uzima 2 elementa - akumulator i element, pri cemu akumulator ima vrijednost koja je proslijedjena kao prvi parametar fold funkcije, a element je prvi element kolekcije na kojoj se radi fold.

`fold` je pogodno koristiti kada nam je potrebno da na osnovu postojecih podataka kolekcije dodjemo do nove kolekcije ili podatka koji ovisi od **svih** elemenata kolekcije. Akumulator koji inicijalno proslijedimo u fold manimpulisemo na osnovu svakog elementa kolekcije i **ne mora biti istog tipa kao element kolekcije**

---

## 5. Odabir najiskusnijeg sa reduce()
`reduce` funkcija uzima samo lambdu. Njena lambda takodje uzima akumulator i element pri cemu u ovom slucaju **akumulator i element moraju biti istog tipa** odnosno akumulator je na pocetku prvi, a element drugi element kolekcije.

Zato je `reduce` pogodna za koristiti kada nam je u cilju da pronadjemo podatak koji se odnosi na samo jedan element kolekcije, odnosno akumulator zadrzava samo jednu instancu elementa, nije moguce koristiti isti za kreiranje nove kolekcije.

---

## 6. Agregacija sa aggregate()
`aggregate` uzima samo lambdu takodje, ali njena lambda je nesto drugacija od onih koje uzimaju `fold` i `reduce`.

`aggregate` prolazi kroz kolekciju tipa `GroupingBy` koji je slican mapma, i na osnovu kljuca izvrsava operacije. Obavezno je naznaciti da **akumulator** kod ove funkcije moze imati null vrijednost. 

Akumulator i element ne moraju biti istog tipa!

`accumulate` je pogodna funkcija za izracunavanje vise vrijednosti istovremeno za razlicite grupe elemenata (koji su podijeljeni po kljucevima).

---

## 7. Ispis podataka
Implementirana je funkcija:
``` code
fun InfoOnAllEngineers(List<Inzenjer) 
```
ispisuje podatke o svim inzenjerima u listi koja joj se proslijedi.

Kako poziva funkciju `Info` na svakom od inzenjera u listi, dobiti ce i specificne podatke koji ovise o izvedenoj klasi jer je metod Info implementiran u izvedenim klasama.

Ostatak principa funkcionisanja ispisa je objasnjen u prethodnim dijelovima README.md filea.

---

## 8. Glavni tok programa
Lista inzenjera u `main` je kreirana kao immutable lista (ne mijenjamo njeno stanje nijednom operacijom) i tipa je Inzenjer, samim time kao element moze sadrzavati sve inzenjere koji su naslijedili klasu Inzenjer.


---

## Provjere ispravnosti
Provjere u mom kodu su hardkodirane i uradjene pomocu `if` statementa na osnovu podataka koji su napisani u listi u mainu.

- Kratka provjera za fold:
  provjerava da li se u listama inzenjera nalaze imena inzenjera koji imaju manje od 6 godina iskustva.
- Kratka provjera za reduce:
  provjerava da li je identitet inzenjera koji je vracen razlicit od **obje** ocekivane vrijednosti.
- Kratka provjera za aggegate:
  provjerava da li je dobijeni broj jednak rucno sabranom broju certifikata i projekata.

---

## Nacin pokretanja programa:

Otvorite pullani direktorij u `IntelliJ idea` ili `Android Studio` i pokrenite isti.

> U slucaju problema, program mozete naci na [linku](https://pl.kotl.in/ezouODmn8)

---

## Ispis u konzoli pri pokretanju programa
U konzoli ce najprije biti ispisane informacije o svim inzenjerima u listi, potom rezultati poziva funkcija u kojima su koristene funkcije `fold`, `reduce` i `aggregate`.

Ispis:

``` code
First and last name: Alan Turing
Title: Softverski inzenjer
Years of experience: 15
Expertise: Cryptography, Math, Algorithms
Number of projects: 7

First and last name: Nikola Tesla
Title: Inzinjer elektrotehnike
Years of experience: 65
Expertise: Electrical engineering, AC systems, Resonance and oscillation systems
Number of certificates: 5

First and last name: John Doe
Title: Inzinjer elektrotehnike
Years of experience: 2
Expertise: F#, Matlab
Number of certificates: 4

First and last name: Rowan Atkinson
Title: Inzinjer elektrotehnike
Years of experience: 6
Expertise: Electrical engineering, Circuit Design
Number of certificates: 2

First and last name: Jane Doe
Title: Softverski inzenjer
Years of experience: 3
Expertise: F#, C++, Assembly, Git
Number of projects: 2

First and last name: Dennis Ritchie
Title: Softverski inzenjer
Years of experience: 44
Expertise: Operating Systems, Assembly, C
Number of projects: 10

First and last name: Linus Torvalds
Title: Softverski inzenjer
Years of experience: 34
Expertise: Git, Operating Systems, C, C++, Assembly
Number of projects: 5

Cryptography: Alan Turing
Math: Alan Turing
Algorithms: Alan Turing
Electrical engineering: Nikola Tesla, Rowan Atkinson
AC systems: Nikola Tesla
Resonance and oscillation systems: Nikola Tesla
Circuit Design: Rowan Atkinson
Assembly: Dennis Ritchie, Linus Torvalds
Operating Systems: Dennis Ritchie, Linus Torvalds
C: Dennis Ritchie, Linus Torvalds
C++: Linus Torvalds
Git: Linus Torvalds

Most experienced Softverski inzenjer:
First and last name: Dennis Ritchie
Title: Softverski inzenjer
Years of experience: 44
Expertise: Operating Systems, Assembly, C
Number of projects: 10

Most experienced Inzinjer elektrotehnike:
First and last name: Nikola Tesla
Title: Inzinjer elektrotehnike
Years of experience: 65
Expertise: Electrical engineering, AC systems, Resonance and oscillation systems
Number of certificates: 5


Total number of projects and certificates amongst forwarded engineers is 35
```


## Napomena o koristenju AI alata

### Prvi:
**prompt:**
*is validation of data when initializing a type in Kotlin done via if statement in init block or is there another standard way to do so? Brief explanation!*

**answer:**
> Good question — in Kotlin, validating data when initializing a type (class) is commonly done in the init block, often using require() or check() functions instead of plain if statements.
> Use require() inside the init block for validating constructor parameters:
> ``` code
> class User(val name: String, val age: Int) {
>    init {
>       require(name.isNotBlank()) { "Name cannot be blank" }
>       require(age >= 0) { "Age must be non-negative" }
>   }}
> ```
> Why require()?
> - It throws an IllegalArgumentException if the condition is false.
> - It’s idiomatic Kotlin for validating arguments.
> - check() is similar but throws IllegalStateException (used for internal state validation rather than arguments).

### Drugi:
**prompt:**
*is it possible to find a key in map if it exists and get value associated with it,  but of theres no that key to add that key in the map and  add the value associated with it? Is there a way to do that in one line withough checking it manually and then using put function to add another element?*

**answer:**
> Yes — Kotlin has a clean one-liner for exactly that case! 🎯
> You can use the getOrPut() function on mutable maps.
> Here’s how it works:
> ``` code
> val map = mutableMapOf<String, Int>()
> val value = map.getOrPut("key") { 42 }
> ```
> ✅ What happens:
> - If "key" exists, it returns the existing value.
> - If "key" does not exist, it calls the lambda ({ 42 }), adds that value to the map under "key", and returns it.
> ✅ Summary:
> > Use getOrPut(key) { defaultValue } — it’s the idiomatic Kotlin one-liner to retrieve a value if present, or insert and return a default if absent.

### Treci
**prompt:**
*is it possible to cast base class into derived class without loosing any f the data if i have a var of base class but initialized with derived class? (in kotlin)
brief explanation*
**answer:**
> Yes — if the base-class variable actually holds a derived-class instance, you can safely cast it back to the derived type (using `as` or `as?`) without losing data, since the full derived object is still there.

### Cetvrti:
**prompt:**
*when using aggregate function on GroupingBy collection is accumulator used for each of the keys separately? for example if i had 2 keys is accumulator different for eah of them? they dont share the same one?*

**answer:**
> When you use an aggregate function (like aggregate(), fold(), or reduce()) on a Grouping created by groupingBy, each key has its own separate accumulator.
> Example:
``` code
val list = listOf("apple", "apricot", "banana", "blueberry")

val result = list.groupingBy { it.first() }  // Groups by first letter
    .aggregate { key, accumulator: Int?, element, first ->
        if (first) element.length
        else accumulator!! + element.length
    }

println(result)
```
> Output: 
``` code 
{a=12, b=14} 
```
> ✅ Summary:
> In Kotlin’s groupingBy + aggregate (or fold, reduce), each key has its own accumulator that’s maintained separately. They never share or interfere with each other.

### Peti:
**prompt:**
``` code
val projekti = Engineers.groupingBy { it.Title }.aggregate{ key, acc: Int?, element, first ->
        if (key == "Inzenjer Elektrotehnike"){
            acc+(element as InzenjerElektrotehnike).NumberOfCertificates
        } else (key == "Softverski Inzenjer"){
            acc+(element as SoftverskiInzenjer).NumberOfProjects
        }
        acc
    }
    return projekti["Softverski Inzenjer"]+projekti["Inzenjer Elektrotehnike"]
}
```
*why do i keep getting an exception, what am i missing here?
brief explanation*

**answer:**
> The main issue is that your `aggregate` lambda doesn’t return the updated accumulator — the last line `acc` just returns the old value. Also, `acc` can be `null` for the first element, so you need to initialize it (`acc ?: 0`). Finally, your `else` shouldn’t have a condition; use `else if` or `when`. Fixing these will prevent the `NullPointerException` and incorrect aggregation.


