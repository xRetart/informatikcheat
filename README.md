# Minecraft Cheat

Der Minecraft Cheat ist eine Modifikation des Spiels Minecraft.
Der Cheat bietet zusätzliche Funktionen, die das Spiel bequemer machen,
sowie andere Vorteile bieten. Der Cheat ist allerdings nicht zum Online Spielen gedacht,
sondern sollte lediglich im Einzelspieler oder auf privaten Servern genutzt werden.
Der Cheat bietet ein schlichtes und übersichtliches Menu zum Umschalten der einzelnen Funktionen.

# Funktionen

* PermaWalk: Der Spieler läuft automatisch die ganze Zeit ohne eine Taste drücken zu müssen.
  Das Laufen lässt sich auch nicht durch z.B. Schreiben im Chat unterbrechen
  und eignet sich daher gut für lange Strecken.
* PermaSprint: Immer wenn der Spieler läuft, sprintet er/sie.
  Die Funktion lässt sich besonders gut mit PermaWalk kombinieren
  und ist genauso zur Bequemlichkeit gedacht.
* AutoEat: Wenn der Spieler unter eine Grenze von Hungerpunkten fällt, wird automatisch
  etwas aus der Hotbar gegessen.
* AutoFish: Beim Angeln wird für den Spieler automatisch die Angel ausgeworfen
  und wieder eingeholt sobald ein Fisch gebissen hat.
* AutoHeal: Wenn der Spieler unter eine Grenze von Lebenspunkten fällt, wird automatisch
  ein goldener Apfel aus der Hotbar gegessen, welcher dem Spieler einen Regenerationseffekt gibt.
* Fly: Der Spieler erhält die Möglichkeit zu fliegen.
  Man kann mit der Springen-Taste aufwärts und mit der Schleichen-Taste abwärts fliegen.
  Gleichzeitig hat man eine deutlich erhöhte Geschwindigkeit.
* KillAura: Alle Entitäten in der Reichweite des Spielers werden automatisch angegriffen.
  Die Funktion ist besonders geeignet, wenn man sich keine Gedanken um Monster machen möchte.
* AntiFallDamage: Sobald der Spieler sich in Gefahr befindet Fallschaden zu bekommen,
  springt die Funktion ein und platziert unter dem Spieler einen Eimer Wasser,
  was den Fallschaden vollständig abfängt.
  Eignet sich, um Unfälle mit der Fly Funktion zu verhindern.
* InstantLoot: Statt, dass der Spieler jeden Gegenstand einzeln aus einer Truhe nehmen muss,
  erledigt es diese Funktion automatisch einem Bruchteil einer Sekunde.
  Diese Funktion ist vor allem beim Erkunden sehr hilfreich und erspart eine Menge Zeit.
* SwitchTool: Diese Funktion wählt automatisch für den Spieler das passende Werkzeug aus,
  wenn auf einen Block geguckt wird bzw. das beste Schwert, wenn ein Monster angeguckt wird.

# Voraussetzungen

* einen Minecraft Account
* OpenJDK-17
* ein Terminal

# Nutzung

1. Öffne ein Terminal und navigiere in diesen Ordner.

```shell
cd DEIN_INSTALLATIONSORT/informatikcheat
```

2. Starte mit den Client mit Gradle (kann beim ersten mal sehr lange dauern).

```shell
./gradlew runClient
```

3. Trete einer Welt bei (erstelle ggf. eine neue).
4. Öffne das Cheat Menu mit der Taste `u`
   (die Taste kann in den Einstellungen umbelegt werden).
5. Links-klicke den Knopf, auf dem der Name der gewünschten Funktion steht.

Die gewünschte funktion ist nun aktiviert (sie lässt sich durch
erneutes Links-klicken im Menu deaktivieren)