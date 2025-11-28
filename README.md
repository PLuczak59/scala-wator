# Simulation Wator en Scala

Une simulation de dynamique des populations développée en Scala utilisant des principes de programmation fonctionnelle.

## Description

Ce projet implémente le modèle Wator, une simulation écologique d'interaction prédateur-proie entre requins et thons dans un environnement océanique. Les deux espèces se déplacent, se reproduisent et interagissent selon des règles simples, créant des dynamiques de population complexes.

## Fonctionnalités

- **Déplacement des thons** : Se déplacent aléatoirement vers des cases voisines libres
- **Chasse des requins** : Les requins traquent et mangent les thons pour survivre
- **Reproduction** : Chaque espèce se reproduit après un nombre de cycles défini
- **Gestion de l'énergie** : Les requins perdent de l'énergie et meurent s'ils ne mangent pas
- **Interface graphique** : Visualisation en temps réel avec ScalaFX
- **Programmation fonctionnelle** : Implémenté entièrement en Scala avec des principes FP

## Visualisation

- 🐟 **Thons** : Affichés en vert
- 🦈 **Requins** : Affichés en rouge
- 🌊 **Océan** : Fond noir

## Architecture

### Fichiers principaux

- `Fish.scala` : Trait de base pour les espèces
- `Tuna.scala` : Logique et comportement des thons
- `Shark.scala` : Logique et comportement des requins
- `Main.scala` : Point d'entrée et boucle de simulation

### Principes de programmation fonctionnelle

- Immutabilité des données
- Fonctions pures sans effets de bord
- Pattern matching
- Collections fonctionnelles (`fold`, `map`, `filter`)

## Compilation et exécution

```bash
sbt compile
sbt run
```

