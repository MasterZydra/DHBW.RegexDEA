# DHBW.RegexDEA
Projekt aus dem 4. Semester im Fach Informatik - Compilerbau

## Abgabe
**Quellcode:**
- Komponenten
- Schnittstellen
- Standalone-Tests

Ohne **main** und **IDE**-Dateien.

## Aufbau
```
               regulärer Ausdruck
                      ↓
  ┌--------------------------------------------┐
  |         rekursiver Top-Down-Parser         |
  |--------------------------------------------|
  | (erzeugt mit syntaxgesteuerter Übersetzung |
  |          einen Syntaxbaum)                 |
  └--------------------------------------------┘
                      ↓
                  Syntaxbaum
                      ↓
  ┌--------------------------------------------┐
  |               1. Visitor                   |
  |--------------------------------------------|
  |      (nullable, firstpos, lastpos          |
  └--------------------------------------------┘
                      ↓
                  Syntaxbaum
                      ↓
  ┌--------------------------------------------┐
  |                 2. Visitor                 |
  |--------------------------------------------|
  |             (followpos)                    |
  └--------------------------------------------┘
                      ↓
              follopos-Tabelle +
      firstpos(Wurzelverzeichnis d. Syntaxbaumes)
                      ↓
  ┌--------------------------------------------┐
  |                 DEA-Erzeuger               |
  └--------------------------------------------┘
                      ↓
            Übergangsmatrix des DEA
                      ↓
  ┌--------------------------------------------┐
  |              Generischer Lexer             |
  └--------------------------------------------┘
```
