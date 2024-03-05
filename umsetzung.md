# Was haben wir erreicht?

* Die Must-Haves, für die wir uns am Anfang entschieden haben, also eine gute Menge sinnvoller Funktionen,
  haben wir erfolgreich umgesetzt.
  Insgesamt haben wir 10 nützliche Funktionen (siehe README.md#Funktionen) implementiert.
* Wir hatten auch die Zeit für ein Nice-To-Have: ein visuelles Overlay als Interface.
  Ein Nice-To-Have, welches wir nicht umsetzen konnten waren eine innovative neue Funktion zu entwickeln.
  Wir dachten zuerst unsere Methode für die Funktion AntiFallDamage ist neu,
  aber später hat sich herausgestellt, dass es das doch schon so ähnlich gibt.
* Unsere Eingrenzung, den Cheat nicht auf öffentlichen Server funktioniert zu lassen, haben wir eingehalten.
* Wo wir mit mehr Zeit noch dran arbeiten würden, ist das Overlay.
  Dieses ist im Moment noch etwas steif und gibt wenig Feedback an den Nutzer.

# Schwierigkeiten

* Fabric API hat einige Probleme verursacht.
  Einerseits bietet es sehr viele moderne Funktionen und APIs,
  aber andererseits ist die Dokumentation nicht sehr zugänglich.
  Das hat dazu geführt, dass wir einen großen Teil der Zeit mit Funktionen/Klassen suchen oder sogar raten verbracht
  haben, statt mit tatsächlichem Programmieren.
  Beim nächsten mal könnte man auch die etwas ältere Alternative Forge API in Betracht ziehen.
* Für das Overlay haben wir die von Fabric eingebaute API genutzt, welche nicht sehr responsiv ist.
  Stattdessen hätte man auch eine 3rd Party Framework benutzten können, was allerdings auch mehr Zeit beansprucht hätte.

# Ablauf

#### 08.01.: Arbeitsauftrag und Ideenfindung

Am 08.01 bekamen wir zuerst unseren Arbeitsauftrag zur freien Erstellung eines Projekts mit fast unbegrenzten
Möglichkeiten, solange es im Rahmen der Informatik ist.
Am 08.01 und 09.01 beschäftigten wir uns zuerst mit der Idee unseres Projektes.

#### bis zum 16.01.: Ideen zur Umsetzung

Nachdem wir auf die Idee der Erstellung eines Minecraft Cheats als Projekt gekommen sind, mussten wir uns nun
tiefgründig damit beschäftigen, wie wir unser Projekt umsetzen wollen, um zukünftige Komplikationen in der Theorie der
Umsetzung zu vermeiden. In dieser Woche beschlossen wir, mit welchen Hilfsmitteln wir unser Projekt umsetzen und
beschlossen unsere Entwicklungsumgebung. Als IDE verwenden wir Intellij Idea und als Programmiersprache Kotlin, welche
auf Java basiert. Um unser Programm in Minecraft umzusetzen, verwenden wir die Fabric API.

#### bis zum 23.01.: erste laufende Mod

In dieser Woche versuchten wir mit der Fabric API eine funktionierende Mod, erstmals ohne Funktionen,
zum Laufen in Minecraft zu bekommen. Dazu haben wir die Template Mod von Fabric genommen (siehe Quellen).
Das hat auch so funktioniert wie wir uns das vorgestellt haben.

#### bis zum 30.01.: Was genau wollen wir erreichen?

Zunächst beschäftigten wir uns damit, was wir überhaupt alles für Funktionen wollen und was für uns mit unseren
Fähigkeiten wie gut möglich ist.

#### bis zum 13.02.: Erste Features

Hier programmierten wir erste kleine Features um herauszufinden,
ob die entwickelte Mod und unsere Entwicklungsumgebung gut genug miteinander funktionieren um unsere Ziele zu erfüllen.
Wir haben erstmals uns mit der Event-API von Fabric befasst und damit erste Versionen von PermaWalk/Sprint umgesetzt.
Dazu mussten wir uns zuerst mit Kotlin befassen, was sich einfacher rausgestellt hat als erwartet.

#### bis zum 20.02.: Richtige Features

Nun, da es uns gelungen ist kleine Features in das Spiel einzubringen haben wir nun begonnen richtige Features, die wir
später auch im endgültigen Cheat haben wollen zu programmieren. Hier haben wir den Großteil aller Funktionen
programmiert: AntiFallDamage, Fly, AutoHeal/Eat/Fish.

#### bis zum 27.02.: Overlay im Spiel

Da unser bisheriges „Overlay“ um Cheats zu aktivieren oder zu deaktivieren der integrierte Chat des Spiels war und
dieser relativ umständlich und langsam zur Aktivierung von Cheats funktioniert, mussten wir uns nun um ein eigenes
Overlay im Spiel kümmern.
Dazu haben wir die von Fabric angebotene Screen-API verwendet, welche relativ gut verständlich war.

#### bis zum 05.03.: Letzte Features und Einbringen unserer Nice-to-Haves

Die Must-Haves hatten wir bereits. In der letzten Woche haben wir uns dann um die Nice-To-Haves gekümmert.
Wir haben die zusätzlichen Funktionen InstantLoot und SwitchToll implementiert.
Zum Schluss haben wir unserem Projekt noch einen letzten Schliff gegeben und haben Fehler behoben,
sowie die Präsentation vorbereitet.

# Quellcode

Unser Quellcode befindet sich in `src/client/kotlin/com.cheat`.
Wir haben ihn so simpel wie möglich strukturiert. Unser "Haupt-File"
ist `InformatikCheatClient.kt` in dem der Client definiert wird und wo der Einstiegspunkt liegt.
Der Client benutzt das Menu, welches in `Menu.kt` definiert ist.
Sowohl das Menu, als auch de Client greifen auf das Package `features` zu.
In `features` wird ein generisches Interface für alle Funktionen, sowie die einzelnen Funktionen festgelegt.
Zusätzlich gibt es das `utility` package, welches nützliche Methoden zusammenfasst, die in mehreren Funktionen gebraucht
werden.
Für zusätzliche Informationen gibt es ausführliche Kommentare im Code selbst.

# Selbsteinschätzung

Unsere ursprüngliche Rollenverteilung war:

* Rubens: Präsentationen, Overlay
* Richard: Fabric API, interne Logik
* Kolja: Dokumentation, interne Logik

In der Praxis haben wir nicht so scharf zwischen den Rollen getrennt, sondern jeder hat überall mal geholfen,
wenn er Zeit hatte. Trotzdem haben wir alle unsere Hauptrolle erfüllt und gleich viel zum Projekt beigetragen.

# Quellen

* Fabric: https://fabricmc.net/wiki/
* Kotlin: https://kotlinlang.org/docs/home.html