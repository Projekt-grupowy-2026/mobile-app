# mobile-app

Aplikacja mobilna systemu logistycznego – interfejs dla **kierowcy** realizującego trasę.

## Za co odpowiada

- Wyświetlanie przydzielonego planu trasy (na mapie i jako lista odcinków i postojów).
- Rozpoczynanie i kończenie realizacji trasy oraz potwierdzanie postojów.
- Pobieranie lokalizacji GPS i prędkości z telefonu i przesyłanie ich do serwera.
- Kontrola udostępniania danych przez kierowcę.

## Powiązane repozytoria

- [backend](https://github.com/Projekt-grupowy-2026/backend) – serwer i API, z którego korzysta aplikacja.
- [frontend](https://github.com/Projekt-grupowy-2026/frontend) – aplikacja webowa dla dyspozytora i klienta.

## Dokumentacja

Szczegółowy opis roli repozytorium w projekcie znajduje się w katalogu [`docs/`](docs/).

## Stack technologiczny

Aplikacja rozwijana jest w modelu hybrydowym, łączącym wydajność rozwiązań natywnych z elastycznością frameworków cross-platformowych.

*   **Natywnie (Android):** Kotlin (Jetpack Compose) oraz Java (Android Studio).
*   **Cross-platform:** React Native (wsparcie dla iOS oraz współdzielone moduły biznesowe).
*   **Wspierane platformy:** Android (API 26+), iOS (14.0+).
*   **Komunikacja:** REST API (Retrofit / Axios), WebSockets (do powiadomień o zmianach trasy w czasie rzeczywistym).
*   **Baza danych:** Room / SQLite (do przechowywania danych w trybie offline).

## Lokalizacja i praca w tle

Aplikacja została zaprojektowana z myślą o niezawodnym śledzeniu floty w trudnych warunkach terenowych.

*   **Częstotliwość wysyłania:** Lokalizacja GPS oraz aktualna prędkość są przesyłane do systemu co **30 sekund**.
*   **Praca w tle:** Wykorzystanie **Foreground Service** z powiadomieniem pozwala na nieprzerwane zbieranie danych GPS, nawet gdy aplikacja nie jest na pierwszym planie lub urządzenie jest zablokowane.
*   **Brak zasięgu (Offline Mode):** W przypadku utraty połączenia z siecią (np. w tunelach lub na terenach pozamiejskich), aplikacja buforuje współrzędne w lokalnej bazie danych. Po odzyskaniu dostępu do internetu, zgromadzone punkty są przesyłane zbiorczo do serwera z zachowaniem poprawnej chronologii.

## Biblioteka map

Do wizualizacji trasy i nawigacji wykorzystujemy:

*   **Google Maps SDK for Android/iOS:** Główny silnik renderujący mapy.
*   **react-native-maps:** Komponent integrujący mapy w części cross-platformowej aplikacji.
*   **Google Directions API:** Do wyznaczania i optymalizacji trasy przejazdu.
