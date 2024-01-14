# Projekt: Darwin World

Autorzy: Bartłomiej Stylski i Wiktor Warzecha
Numer grupy i godzina zajęć: GR nr 3, PT 08:00

# Stan projektu:

- klasa Animal:
  * posiada wszystkie wymagane atrybuty ale mało z nich używa
  * już używa właśnego genomu do poruszania się
  * konstrukcja obiektu odbywa się przy pomocy buildera

- klasa Symulation
  * posiada większość potrzebnuch atrybutów ale większośc z nich nic nie robi
  * trzeba rozwiązać problem z przypisaniem observera do mapy
  * konstrukcja obiektu przy pomocy buildera
  * początkowe pozycje zwierząt i ich kierunek wybierane losowo

- trawa
  * narazie rośnięcie nowej trawy tylko wg wzorca zalesionych równików
  * pozycje trawy losowane przy pomocy klasy GrassGenerator - potrzeba napisać testy oraz umożliwić wybór wariantu metody losowania

- klasa Globe
  * dziedziczy po klasy WorldMap
  * implementuje metode update która ma wykonywać pojedyńczy krot symulacji - narazie obsługuje tylko ruch zwierząt i rośnięcie nowej trawy
  * w konstruktorze inicjalizowana jest początkowa pozycja trawy
  * implementuje kulistą mapę i niedostępne bieguny - bieguny chyba trzeba naprawić

  
