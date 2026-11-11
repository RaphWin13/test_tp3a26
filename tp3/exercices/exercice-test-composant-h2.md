# Tests composant: Accès aux données avec H2

1. Faire les tests composant pour `HibernateAccountRepository` et `HibernateCruiseRepository`.
2. Répondre aux questions dans le [fichier de questions](../questions/question-test-composant-h2.md).

### Astuce
Pour réinitialiser la BD entre chaque test, vous pouvez utiliser: 
```
    @AfterEach
    void tearDownSession() {
        HibernateUtil.shutdown();
    }
```