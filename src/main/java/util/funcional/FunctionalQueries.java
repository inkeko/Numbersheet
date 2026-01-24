package util.funcional;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.math.BigInteger;
public class FunctionalQueries {
    // Fix, újrafelhasználható Predicate-ek
    // Igaz, ha a szám páros
    public static final Predicate<Integer> IS_EVEN = n -> n % 2 == 0;

    // Igaz, ha a szám páratlan
    public static final Predicate<Integer> IS_ODD = n -> n % 2 != 0;

    // Igaz, ha a szám pozitív
    public static final Predicate<Integer> IS_POSITIVE = n -> n > 0;

    // Igaz, ha a szám negatív
    public static final Predicate<Integer> IS_NEGATIVE = n -> n < 0;

    // Paraméterezhető Predicate-ek
    // Igaz, ha a szám nagyobb, mint a megadott minimum
    public static Predicate<Integer> greaterThan(int min) {
        return n -> n > min;
    }

    // Igaz, ha a szám kisebb, mint a megadott maximum
    public static Predicate<Integer> lessThan(int max) {
        return n -> n < max;
    }

    // Igaz, ha a szám a [min, max] tartományban van (beleértve a széleket)
    public static Predicate<Integer> between(int min, int max) {
        return n -> n >= min && n <= max;
    }

  // Curried Predicate
  // Curried verzió: először megadod a minimumot,
// és kapsz egy Predicate-et, ami ellenőrzi, hogy n > min
  public static final Function<Integer, Predicate<Integer>> greaterThanCurried =
          min -> (n -> n > min);

    // Curried verzió: először megadod a maximumot,
// és kapsz egy Predicate-et, ami ellenőrzi, hogy n < max
    public static final Function<Integer, Predicate<Integer>> lessThanCurried =
            max -> (n -> n < max);

//FUNCTION – a második nagy funkcionális építőkő
//A Function lesz az, amivel:
//• 	átalakítunk értékeket
//• 	transzformálunk listákat
//• 	előkészítjük a stream‑lekérdezéseket
//• 	és később kombináljuk Predicate‑ekkel
//Pont olyan fontos, mint a Predicate, csak más szerepben.
//Most is úgy csináljuk, mint eddig:
//• 	fix Function-ek
//• 	paraméterezhető Function-ek
//• 	curried Function-ek

 // Fix Function-ek (osztályszinten)
// Fix, újrafelhasználható Function-ök

    // A szám négyzetét adja vissza
    public static final Function<Integer, Integer> SQUARE = n -> n * n;

    // A szám abszolút értékét adja vissza
    public static final Function<Integer, Integer> ABS = n -> Math.abs(n);

    // A szám dupláját adja vissza
    public static final Function<Integer, Integer> DOUBLE = n -> n * 2;

    // Paraméterezhető Function-ök

    // Hozzáad egy adott értéket a számhoz
    public static Function<Integer, Integer> add(int value) {
        return n -> n + value;
    }

    // Megszorozza a számot egy adott értékkel
    public static Function<Integer, Integer> multiply(int factor) {
        return n -> n * factor;
    }

    // Kivon egy adott értéket a számból
    public static Function<Integer, Integer> subtract(int value) {
        return n -> n - value;
    }

    // Curried Function-ök
    // Curried összeadás: először megadod az első számot,
// és kapsz egy függvényt, ami hozzáadja a másodikat
    public static final Function<Integer, Function<Integer, Integer>> CURRIED_ADD =
            a -> (b -> a + b);

    // Curried szorzás: először megadod a szorzót,
// és kapsz egy függvényt, ami megszorozza a számot
    public static final Function<Integer, Function<Integer, Integer>> CURRIED_MULTIPLY =
            factor -> (n -> n * factor);


    //SUPPLIER – a funkcionális „értékgyár”
    //A Supplier az a funkcionális interfész, ami:
    //• 	nem kap bemenetet
    //• 	de visszaad egy értéket
    //Ez lesz a generátoraink alapja:
    //random számok, üres listák, előre definiált objektumok, stb.
    //Most is ugyanúgy építjük fel, mint a Predicate és Function részt:
    //1. 	Fix Supplier-ek
    //2. 	Paraméterezhető Supplier-ek
    //3. 	Curried Supplier-ek (igen, ilyen is van!)

    // Fix, újrafelhasználható Supplier-ek

    // Egy véletlen 0–99 közötti számot ad vissza
    public static final Supplier<Integer> RANDOM_INT =
           () -> new Random().nextInt(100);

    // Egy új, üres ArrayList-et ad vissza
    public static final Supplier<List<Integer>> NEW_LIST =
            ArrayList::new;

    // Egy fix értéket ad vissza (pl. 42)
    public static final Supplier<Integer> CONSTANT_42 =
            () -> 42;
///Paraméterezhető Supplier-ek
// Olyan Supplier-t ad vissza, ami mindig a megadott értéket adja vissza
public static Supplier<Integer> constant(int value) {
    return () -> value;
}

// Olyan Supplier-t ad vissza, ami véletlen számot generál a [min, max] tartományban
public static Supplier<Integer> randomInRange(int min, int max) {
    return () -> new Random().nextInt(max - min + 1) + min;
}

// Curried Supplier-ek

// Curried verzió: először megadod a minimumot,
// majd kapsz egy függvényt, ami a maximumot várja,
// és végül egy Supplier-t kapsz, ami random számot generál
public static final Function<Integer, Function<Integer, Supplier<Integer>>> CURRIED_RANDOM_RANGE =
       min -> (max -> (() -> new Random().nextInt(max - min + 1) + min));

//FunctionalQueries modul felépítése
//1. 	Általános filter metódus (Predicate‑tel)
//2. 	Általános map metódus (Function‑nel)
//3. 	Általános reduce metódus
//4. 	Top N lekérdezés
//5. 	Distinct lekérdezés
//6. 	Rendezések (ASC, DESC)
//7. 	GroupingBy
//8. 	PartitioningBy
//9. 	Statisztikai lekérdezések (min, max, sum, avg — streammel)

    // Általános szűrés Predicate segítségével
// Bármilyen feltételt megadhatsz, és visszaadja a szűrt listát
    public static List<Integer> filter(List<Integer> numbers, Predicate<Integer> condition) {
        return numbers.stream()
                .filter(condition)
                .toList();
    }

    // Általános átalakítás Function segítségével
// Bármilyen transzformációt megadhatsz (pl. négyzet, abszolút érték)
    public static List<Integer> map(List<Integer> numbers, Function<Integer, Integer> mapper) {
        return numbers.stream()
                .map(mapper)
                .toList();
    }
    // Általános reduce művelet
// A  kezdeti értékből indul, és a redukáló függvény végigmegy a listán
    //A reduce második paramétere két azonos típusú értéket vár, és ugyanazt a
    // típust adja vissza — ezért kell BinaryOpetator<Integer>.
    public static Integer reduce(List<Integer> numbers, Integer identity,
                                 BinaryOperator<Integer> reducer) {
        return numbers.stream()
                .reduce(identity, reducer);
    }
        // A legnagyobb N elem visszaadása
        public static List<Integer> topN(List<Integer> numbers, int n) {
            return numbers.stream()
                    .sorted(Comparator.reverseOrder())
                    .limit(n)
                    .toList();
    }
    // Egyedi értékek visszaadása
    public static List<Integer> distinct(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .toList();
    }
    // Növekvő rendezés
    public static List<Integer> sortedAsc(List<Integer> numbers) {
        return numbers.stream()
                .sorted()
                .toList();
    }

    // Csökkenő rendezés
    public static List<Integer> sortedDesc(List<Integer> numbers) {
        return numbers.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }
    // Csoportosítás tetszőleges kulcs szerint
    public static <K> Map<K, List<Integer>> groupBy(
            List<Integer> numbers,
            Function<Integer, K> classifier) {

        return numbers.stream()
                .collect(Collectors.groupingBy(classifier));
    }
    // Két csoportra bontás Predicate alapján
    public static Map<Boolean, List<Integer>> partitionBy(
            List<Integer> numbers,
            Predicate<Integer> condition) {

        return numbers.stream()
                .collect(Collectors.partitioningBy(condition));
    }
    public static int min(List<Integer> numbers) {
        return numbers.stream().min(Integer::compareTo).orElseThrow();
    }

    public static int max(List<Integer> numbers) {
        return numbers.stream().max(Integer::compareTo).orElseThrow();
    }

    public static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    public static double average(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

//minden számot BigInteger-re alakít
//• 	BigInteger.ONE-ról indul
//• 	BigInteger::multiply művelettel szoroz
//• 	soha nem csordul túl
//• 	bármilyen nagy számot kezel
//Ez a matematikailag helyes megoldás.
    public static BigInteger productBig(List<Integer> numbers) {
        return numbers.stream()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
    }
}
