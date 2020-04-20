## Mina

![widokmina](https://user-images.githubusercontent.com/63725366/79580260-0e01c400-80c9-11ea-9298-db4629da7880.JPG)

- Opis obsługi gry: Mina

  - Gra polgająca na znalezieniu min na podstawie sąsiednich pól mówiących o ilości min w obrębie wskazanego pola. 
  - W początkowej fazie gracz decyduje o rozmiarze (szerokości, wysokości) pola gry oraz % natężeniu min w polu.
  - Po deklaracji gracz ma możliwość zmiany % natężenia min w zadeklarowanej mapie.
  - Gra z stoperem, nieograniczoną ilością podejść.

- Założenia przyjęte dla potrzeb projektu

  - wzorec MVC
  - projekt w oparciu o bibliotekę Swing
  - Metoda wypelniajaca sasiadujace pola ktore nie possiadaja min - metoda rekurencyjna z algorytmem flood fill
  - Mapa w postaci tablicy statycznej, w przypadku rozwiązań dynamicznych, byłaby możliwość zmiany wielkości mapy w trakcie gry.


