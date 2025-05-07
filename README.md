# Kotlin JSON Library

##Desenvolvido no âmbito da Unidade Curricular de Programação Avançada do 2º semestre do Mestrado de Engenharia Informática

##Desenvolvido por:
-Miguel Monteiro Nº 99860
-Rafaek Gésero Nº 99063

Uma biblioteca escrita em Kotlin para representar, manipular e validar dados JSON. Suporta conversão de objetos Kotlin para JSON e oferece suporte ao padrão Visitor para extensibilidade.

##Funcionalidades

- Representação de tipos JSON: `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`, `JsonArray`, `JsonObject`
- Conversão automática de classes Kotlin (incluindo `data class`) para JSON
- Filtros e mapeamentos em arrays e objetos JSON
- Validação de estruturas com `ValidatorVisitor` (validação de tipos consistentes e chaves duplicadas)

---

## Estrutura da Biblioteca

### Classes principais

- `JsonValue`: Interface comum para todos os tipos JSON
- `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`: Tipos primitivos
- `JsonArray`: Lista de elementos `JsonValue`
- `JsonObject`: Lista de pares `chave -> JsonValue`
- `JsonModel`: Converte objetos Kotlin em estruturas JSON
- `ValidatorVisitor`: Visitor que valida arrays e objetos JSON

---

## Como usar

### 1. Representar tipos JSON diretamente

```kotlin
val name = JsonString("Cristiano")
val age = JsonNumber(40)
val active = JsonBoolean(true)
val jsonObject = JsonObject(
    listOf("name" to name, "age" to age, "active" to active)
)
println(jsonObject.toJsonString()) 
// Saída: {"name": "Cristiano", "age": 40, "active": true}
```

### 2. Criar um JSON a partir de uma data class

```kotlin
data class Person(val name: String, val age: Int, val active: Boolean)

val p = Person("Cristiano", 40, true)
val json = JsonModel().toJsonModel(p)
println(json.toJsonString()) 
// Saída: {"name": "Cristiano", "age": 40, "active": true}
```

### 3. Filtrar ou mapear arrays

```kotlin
val array = JsonArray(listOf(JsonNumber(1), JsonNumber(2), JsonNumber(3)))
val filtered = array.filter { (it as JsonNumber).getValue() > 1 }
println(filtered.toJsonString()) // [2, 3]
```

### 4. Validar estrutura com `ValidatorVisitor`

```kotlin
val visitor = ValidatorVisitor()
json.accept(visitor)
println(visitor.getValidator()) // true ou false conforme a estrutura
```


