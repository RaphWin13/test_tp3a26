# Cucumber: Créer les tests Cucumber pour la réservation d'une expérience zéro gravité

Vous devez faire les tests multi-composants de la réservation d'une expérience zéro gravité en utilisant Cucumber.

## Utiliser la base de données en mémoire
Contrairement à l'application réelle, les tests Cucumber doivent utiliser la base de données en mémoire. 
En effet, il n'est pas nécessaire de tester la persistance des données avec la base de données réelle lors des tests Cucumber. 
Ce n'est pas le but de ces tests qui ne sont pas des tests de bout-en-bout.

Pour utiliser la base de données en mémoire, utilisez la méthode `ConfigurationServerRest.useInMemoryRepositories()` avant de démarrer le serveur.


## Critères

Respecter les [critères des tests multi-composants avec Cucumber](./criteres/criteres-cucumber.md).

## Détails de la fonctionnalité

Le résultat de la réservation d'une expérience zéro gravité dépend du type de voyageur:

ADULT:
* Si la requête est valide, peut toujours réserver une expérience zéro gravité
* Gagne le badge `ZERO_G`, s'il ne le possède pas déjà.

CHILD:
* Peut réserver une expérience zéro gravité seulement si un ADULTE ou un SENIOR de sa réservation a préalablement réservé la même expérience
* Gagne le badge `MINI_ZERO_G` au lieu de `ZERO_G`, s'il ne le possède pas déjà.

SENIOR:
* Si la requête est valide, peut toujours réserver une expérience zéro gravité
* Gagne le badge `STILL_GOT_IT` (s'il ne le possède pas déjà) en plus de `ZERO_G`(s'il ne le possède pas déjà).

### Route API et Erreurs possibles

POST /cruises/{cruiseId}/bookings/{bookingId}/travelers/{travelerId}/zeroGravityExperiences

```
{
    "experienceBookingDateTime": "2084-04-08T12:30:00"::string(dateTime, format ISO)
}
```

Réponses:

HTTP 200 OK

Si la réservation n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "TRAVELER_NOT_FOUND",
    "description": "Traveler not found"
}
```

Si le voyageur n'existe pas dans la réservation:

HTTP 404 NOT FOUND

```
{
    "error": "TRAVELER_NOT_FOUND",
    "description": "Traveler not found"
}
```

Si la date de réservation est après le départ de la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_BOOKING_TIME",
    "description": "Zero gravity experience booking time must be before the cruise departure time."
}
```

Si le voyageur a déjà réservé une expérience zéro gravité:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_ALREADY_BOOKED",
    "description": "Zero gravity experience already booked by traveler"
}
```

Si l'expérience zéro gravité est complète:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_FULL",
    "description": "Zero gravity experience is full"
}
```

Si un enfant essaie de réserver une expérience zéro gravité sans qu'un adulte ou un senior de sa réservation ait préalablement réservé la même expérience:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_CHILD_CRITERIA",
    "description": "Child cannot book zero gravity experience without an adult or a senior of the same booking."
}
```