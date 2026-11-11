# Scénario d'essais : Ajouter un module d'hyperpropulsion

La fonctionnalité d'ajout d'un module d'hyperpropulsion existe déjà.

1. Élaborer une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe.
   Écrire la stratégie dans [le fichier de questions](../questions/question-add-hyperpropulsion-module.md).
2. Implémenter les tests décrits dans la stratégie d'essais en respectant les bonnes pratiques vues en classe.
3. Respecter les [critères des tests multi-composants avec Cucumber](./criteres/criteres-cucumber.md).

## Critères d'acceptations

_Rappel: Votre scénario d'essais doit être basé sur les critères d'acceptation. Ceux-ci peuvent être un peu différents de l'exercice original du TP1._

- Le champ `id` est obligatoire.
- Le champ `powerLevel` est obligatoire.
- Le champ `activationDays` est obligatoire.
- Le champ `activationDate` est obligatoire.
- L’identifiant du module doit être unique dans la croisière.
- L’identifiant du module doit respecter le format `HY-{1 à 3 chiffres}-{1 lettre majuscule}`. 
- Le niveau de puissance doit être un entier strictement positif.
- Le temps d’activation doit être un entier strictement positif.
- Le module ne peut être ajouté qu'à une croisière existante.
- Le module doit être validé par le système externe de stabilité.
- L’activation et la désactivation du module doivent être durant la durée de la croisière.
- Il ne peut y avoir qu’un seul module d’hyperpropulsion actif à la fois pour une croisière.
- Un module d'hyperpropulsion respectant ces critères doit être ajouté à la croisière.
- Un module d'hyperpropulsion ajouté doit pouvoir être récupéré par son ID dans la même croisière.
- Les erreurs appropriées doivent être retournées par l’API (voir détails plus bas).

## Détails de la fonctionnalité

### Routes API et Erreurs possibles

Les routes API ci-dessous sont déjà implémentées depuis le TP1.

#### POST /cruises/{cruiseId}/hyperdrive-modules

```
{
    "id": "HY-77-V"::string,
    "powerLevel": 85::number,
    "activationDays": 2::number,
    "activationDate": "2085-01-26"::string(date, yyyy-MM-dd)
}
```

Réponses:

HTTP 201 CREATED

Si un des champs est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```
(`<parameterName>` est le nom du champ manquant: id, powerLevel, activationDays, activationDate)
_NOTE: un int est considéré absent s'il est égal à 0 dans la requête._

Si le niveau de puissance est négatif (< 0):

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Power level must be a positive number"
}
```

Si le temps d'activation est négatif (< 0):

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Activation days must be a positive number"
}
```

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
"error": "CRUISE_NOT_FOUND",
"description": "Cruise not found"
}
```

Si l'id n'est pas dans le bon format:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_HYPERDRIVE_ID_FORMAT",
    "description": "Invalid hyperdrive module id format"
}
```

Si le module existe déjà pour la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "MODULE_ALREADY_EXISTS",
    "description": "Hyperdrive module already exists in the cruise"
}
```

Si l'activation ou la désactivation n'est pas durant la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_ACTIVATION_DATE",
    "description": "Activation date or deactivation date is outside the cruise timeframe"
}
```

Si un autre module est déjà actif à cette date:

HTTP 400 BAD REQUEST

```
{
    "error": "MODULE_CONFLICT",
    "description": "Another hyperdrive module is already active at this time"
}

```

Si le système de validation externe ne valide pas le module:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_HYPERDRIVE_MODULE",
    "description": "Hyperdrive module invalid"
}
```


#### GET /cruises/{cruiseId}

Si la croisière existe:

HTTP 200 OK

```
{
    "id": "JUPITER_MOONS_EXPLORATION_2085"::string,
    "departureDateTime": "2085-01-25T12:00"::string(dateTime, format ISO),
    "endDateTime": "2085-02-01T12:00"::string(dateTime, format ISO),
    "hyperdriveModules": [
        {
            "id": "HY-77-V"::string,
            "powerLevel": 85::number,
            "activationDateTime": "2085-01-26T01:00"::string(dateTime, format ISO),
            "deactivationDateTime": "2085-01-28T01:00"::string(dateTime, format ISO),
            "maintenances": [
                {
                    "duration": 3::int,
                    "employeeIds": [
                        "ABB328",
                        "ABC128",
                        ...
                    ]
                },
                ...
            ]
        },
        ...
    ],
    "crewMembersIds": [
        "ABB328"::string,
        "ABC128"::string,
        ...
    ]
}
```