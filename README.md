# DHBW.RegexDEA
Projekt aus dem 4. Semester im Fach Informatik - Compilerbau

## Einschränkung der Sprache der regulären Ausdrücke
Folgende Konstruktionsvorschrift soll als Grundlage dienen.  
 - jeder Buchstabe und jede Ziffer von 0 bis 9 ist ein regulärer Ausdruck
- seien zwei reguläre Ausdrücke r1 und r2 gegeben, so sind
    - r1r2 (Konkatenation)
    - r1 | r2 (Alternative)
    - ri* (Kleenesche Hülle)
    - ri+ (Positive Hülle)
    - ri? (Option)  
    ebenfalls reguläre Ausdrücke.
- Der Einfachheit halber seien Whitespaces nicht zulässig

## Aufbau
```
               regulärer Ausdruck
                      ↓
  ┌--------------------------------------------┐
  |         rekursiver Top-Down-Parser         |
  |--------------------------------------------|  (MasterZydra)
  | (erzeugt mit syntaxgesteuerter Übersetzung |
  |          einen Syntaxbaum)                 |
  └--------------------------------------------┘
                      ↓
                  Syntaxbaum
                      ↓
  ┌--------------------------------------------┐
  |               1. Visitor                   |
  |--------------------------------------------|  (FHieser)
  |      (nullable, firstpos, lastpos)         |
  └--------------------------------------------┘
                      ↓
                  Syntaxbaum
                      ↓
  ┌--------------------------------------------┐
  |                 2. Visitor                 |
  |--------------------------------------------|   (jones1008)
  |                (followpos)                 |
  └--------------------------------------------┘
                      ↓
              follopos-Tabelle +
      firstpos(Wurzelverzeichnis d. Syntaxbaumes)
                      ↓
  ┌--------------------------------------------┐
  |                 DEA-Erzeuger               |  (trigeha)
  └--------------------------------------------┘
                      ↓
            Übergangsmatrix des DEA
                      ↓
  ┌--------------------------------------------┐
  |              Generischer Lexer             |
  └--------------------------------------------┘
```

## Abgabe
**Quellcode:**
- Komponenten
- Schnittstellen
- Standalone-Tests

Ohne **main** und **IDE**-Dateien.
