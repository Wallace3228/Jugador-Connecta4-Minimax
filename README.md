# 🎮 Jugador Connecta 4 amb Algorisme MiniMax - Java

Aquest projecte implementa un jugador intel·ligent per al joc **Conecta 4** utilitzant l'algorisme **MiniMax** amb opció de **poda Alfa-Beta**. El jugador avalua moviments òptims mitjançant una heurística basada en la posició de les fitxes al tauler.

## ⚙️ Com Executar

1. **Compilació**:
   ```bash
   javac -d . JugadorMiniMax.java
   ```

2. **Execució dins l'entorn del joc**:
   - Integra la classe `JugadorMiniMax` en un entorn de joc Conecta 4
   - Configura la profunditat de recerca i l'ús de poda al crear la instància:
     ```java
     JugadorMiniMax jugador = new JugadorMiniMax(profunditat, usarPoda);
     ```

## 🧠 Funcionalitats Principals

### 1. Algorisme MiniMax
- **Cerca exhaustiva** de moviments òptims
- **Suport per a poda Alfa-Beta** per optimitzar el rendiment
- Control de **profunditat de recerca** configurable

### 2. Sistema Heurístic
- Matriu d'avaluació basada en posicions estratègiques:
  ```java
  public int[][] taulaHeuristica = {
      {3, 4, 5, 7, 7, 5, 4, 3},
      {4, 6, 8,10,10, 8, 6, 4},
      {5, 8,11,13,13,11, 8, 5},
      {7,10,13,16,16,13,10, 7},
      {7,10,13,16,16,13,10, 7},
      {5, 8,11,13,13,11, 8, 5},
      {4, 6, 8,10,10, 8, 6, 4},
      {3, 4, 5, 7, 7, 5, 4, 3}
  };
  ```
- Assigna valors més alts a posicions centrals
- Considera tant atac com defensa

### 3. Poda Alfa-Beta
- **Reducció significativa** de nodes avaluats
- **Comparativa de rendiment** per diferents profunditats:

| Profunditat | Nodes sense poda | Nodes amb poda |
|-------------|------------------|----------------|
| 2           | 49              | 29             |
| 3           | 343             | 158            |
| 4           | 2401            | 1050           |
| 5           | 16807           | 5500           |

## 📊 Comparativa d'Algorismes

| Criteri              | Minimax amb Poda Alfa-Beta       | Minimax sense Poda                          |
|----------------------|----------------------------------|---------------------------------------------|
| **Avantatges** ✅    |                                  |                                             |
| Eficiència           | Redueix nodes                    | Explora tot                                 |
| Temps de càlcul      | Optimitzat                       | Lent en profunditats grans                  |
| Adaptabilitat        | Eficaç a profunditats altes      | Veu moviments menys evidents, pero + costós |
| **Desavantatges** ❌ |                                  |                                             |
| Complexitat          | Més complex (alpha/beta)         | Senzill de programar                        |
| Eficàcia baixa       | Menys impacte a poca profunditat | Ideal per a poca profunditat                |
| Rendiment            | Millora a llarg termini          | Molt lent en profunditat                    |


## 📌 Conclusions

- Implementació **eficient** de MiniMax amb poda Alfa-Beta
- Sistema heurístic **equilibrat** entre atac i defensa
- **Reducció dràstica** de nodes avaluats gràcies a la poda
- Capaç de **vèncer** a jugadors aleatoris i algorismes bàsics

## 👥 Autors

- **Alex Matilla Santos**
- **Alejandro Saiz Talavera**

Projecte desenvolupat com a part de l'assignatura de **Projecte de programació**




