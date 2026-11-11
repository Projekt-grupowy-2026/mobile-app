# Mobile app – rola w projekcie

## 1. Kontekst

Projekt to aplikacja logistyczna wspomagająca planowanie i realizację transportu. System monitoruje lokalizację pojazdów i na bieżąco aktualizuje przewidywany czas dotarcia (ETA) do kolejnych punktów trasy.

System składa się z trzech elementów:

| Element | Repozytorium | Użytkownicy |
|---|---|---|
| Aplikacja mobilna | **mobile-app** (to repo) | kierowca |
| Back-end / serwer | [backend](https://github.com/Projekt-grupowy-2026/backend) | – |
| Aplikacja webowa | [frontend](https://github.com/Projekt-grupowy-2026/frontend) | dyspozytor, klient |

Aplikacja mobilna jest **źródłem danych o pozycji pojazdu** dla całego systemu – to z niej backend otrzymuje lokalizację i prędkość, na podstawie których liczy ETA.

## 2. Użytkownik aplikacji – kierowca

- Otrzymuje przydzielony plan trasy.
- Rozpoczyna i kończy realizację trasy.
- Widzi trasę na mapie oraz jako listę odcinków i postojów.
- Udostępnia swoją lokalizację i prędkość podczas przejazdu.
- Potwierdza rozpoczęcie i zakończenie postojów.

## 3. Funkcjonalności

### 3.1. Wyświetlanie planu trasy

- Pobranie z backendu planu przydzielonego kierowcy.
- Widok mapy z przebiegiem trasy.
- Widok listy – uporządkowana sekwencja **odcinków** i **postojów**:
  - odcinek: punkt początkowy i końcowy, dystans, planowany czas przejazdu, przewidywana godzina przyjazdu, status;
  - postój: lokalizacja, planowana godzina rozpoczęcia, czas trwania, rodzaj (rozładunek, załadunek, przerwa kierowcy, przystanek), status.
- Wyróżnienie aktualnego etapu przejazdu.

### 3.2. Obsługa realizacji przejazdu

- Rozpoczęcie i zakończenie trasy.
- Potwierdzenie rozpoczęcia i zakończenia każdego postoju.
- Potwierdzanie wykonania kolejnych etapów.
- Wysyłanie tych zdarzeń do backendu.

### 3.3. Pobieranie i przesyłanie danych o ruchu

- Odczyt lokalizacji GPS z telefonu.
- Odczyt aktualnej prędkości.
- Cykliczne przesyłanie danych do backendu w trakcie aktywnej trasy.

### 3.4. Kontrola udostępniania danych

- Kierowca decyduje, kiedy udostępnia lokalizację (np. tylko podczas realizacji trasy).
- Czytelna informacja o tym, czy udostępnianie jest aktywne.

## 4. Scenariusze użycia

### A. Transport ciężarowy

Kierowca otrzymuje trasę: Port (A) → Magazyn 1 (B) → Magazyn 2 (C) → Port (A). Rozpoczyna trasę w porcie, w B potwierdza rozpoczęcie i zakończenie rozładunku (30 min), w C analogicznie (20 min), po powrocie do A kończy trasę. Przez cały czas aplikacja wysyła pozycję i prędkość.

### B. Transport autobusowy

Kierowca autobusu widzi kurs z listą przystanków oraz planowanymi godzinami przyjazdu i odjazdu. Aplikacja na bieżąco przesyła pozycję; przy opóźnieniu kierowca widzi zaktualizowane ETA dla kolejnych przystanków.

## 5. Poza zakresem tego repozytorium

- Obliczanie ETA, przechowywanie danych, uprawnienia → `backend`.
- Planowanie tras i interfejs dyspozytora / klienta → `frontend`.

## 6. Do ustalenia

- Stack technologiczny (natywnie / cross-platform) i wspierane platformy.
- Częstotliwość wysyłania lokalizacji oraz praca w tle i przy braku zasięgu.
- Biblioteka map.
