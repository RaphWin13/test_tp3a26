# Critères relatifs aux tests multi-composants Cucumber
Ces critères sont évalués pour tous vos tests cucumbers.

### Critères généraux
* Tous les comportements décrits dans la section "Détails de la fonctionnalité" doivent être testés.
    * **Vous n'avez pas à tester les comportements qui ne sont pas inclus dans la section "Détails de la fonctionnalité" de ce fichier.**
* Les tests doivent être multi-composants avec l'API et la base de données en mémoire.
* Les messages d'erreurs n'ont pas à être validés, seulement le code HTTP et le nom de l'erreur.
* Comme toujours, seul la croisière JUPITER_MOONS_EXPLORATION_2085 existe par défaut dans l'application.
    * La capacité d'une expérience zéro gravité est de 10.
    * Vous avez le droit d'utiliser les constantes dans `UspaceMain` en lien avec la croisière par défaut.

### Critères Cucumber
* Les tests Cucumber doivent être écrits en français (vous devez utiliser les mots clés en français: Fonctionnalité, Etant donné, Quand, etc).
    * Les ENUM, les noms de champs et les noms d'erreurs peuvent être en anglais.
* Un scénario = Un test
* Les titres des scénarios doivent être clairs et auto-descriptifs.
* Le contenu des scénarios doit être facilement lisible.
* Le contenu des scénarios doit suivre la formule Given, When, Then (ou Étant donné, Quand, Alors).
* Les définitions d'étapes (Step Definitions) ne doivent pas avoir de duplications de code excessives et doivent être réutilisables.
* Tous les tests doivent s'exécuter et passer. **Si les tests ne passent pas ou ne sont pas exécuté par `mvn test` à la racine du projet, la note de 0 sera attribué à cet exercice.**
