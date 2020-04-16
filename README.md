# Mina
===============Opis obsługi gry: Mina=============

Gra polgająca na znalezieniu min na podstawie sąsiednich pól mówiących o ilości min w obrębie wskazanego pola. 
W początkowej fazie gracz decyduje o rozmiarze (szerokości, wysokości) pola gry oraz % natężeniu min w polu.
Po deklaracji gracz ma możliwość zmiany % natężenia min w zadeklarowanej mapie.
Gra z stoperem, nieograniczoną ilością podejść.

======Założenia przyjęte dla potrzeb projektu====== 

- wzorec MVC
- projekt w oparciu o bibliotekę Swing
- Metoda wypelniajaca sasiadujace pola ktore nie possiadaja min - metoda rekurencyjna z algorytmem flood fill
- Mapa w postaci tablicy statycznej, w przypadku rozwiązań dynamicznych, byłaby możliwość zmiany wielkości mapy w trakcie gry.


