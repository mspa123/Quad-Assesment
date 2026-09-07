# Trivia Quiz

Een full stack trivia quizz applicatie

De applicatie haalt vragen op uit Open Trivia API. De spring Boot backend zorgt ervoor dat correcte niet zichtbaar zijn voor de frontend.

## Technologieen

- Java 26
- Spring Boot
- React
- TypeScript
- Vite
- JUnite
- Mockito
- Postman

## Functionaliteit
- Trivia vragen ophalen en shufflen.
- Correcte antwoorden verbergen voor de frontend.
- Correcte antwoorden tijdelijk service side opslaan.
- Antwoorden controleren via api = /checkanswers
- Score teruggeven
- Service unit tests

## Backend starten
Open een terminal in de hoofdmap van het project.

### Windows
./mvnw spring-boot:run

De backend draait op http://localhost:8080

## tests
Open een terminal in de hoofdmap van het project
mvn test

## frontend install & start

npm install

npm run dev

De frontend draait op http://localhost:5173