# Kotlin JSON Library

## Desenvolvido no âmbito da Unidade Curricular de Programação Avançada do 2º semestre do Mestrado de Engenharia Informática

## Desenvolvido por:

-Miguel Monteiro Nº 99860

-Rafael Gésero Nº 99063


Uma biblioteca escrita em Kotlin para representar, manipular e validar dados JSON. Suporta conversão de objetos Kotlin para JSON e oferece suporte ao padrão Visitor para extensibilidade.

## Funcionalidades

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




# Framework Json Library

### 1. Controller

#### Descrição

A classe `Controller` define um conjunto de endpoints HTTP que aceitam parâmetros através da query string e retornam dados Json, usando a bilbioteca Json desenvolvida. Cada métodp da classe está anotado com `@Mapping`, o que torna possível acessar como endpoint HTTP.

#### Funcionalidades 

- Disponibiliza vários endpoints para diferentes tipos de dados.
- Permite a conversão de parâmetros recebidos via query string em tipos Json.
- Oferece um endpoint que lista os caminhos disponíveis no `Controller`

#### Resumo dos métodos

- `paths()`: Retorna uma string que lista todos os endpoints disponíveis.
- `createObject(attributes)`: Recebe um mapa de atributos e converte para um objeto JSon.
- `number(attribute)`: Converte um número recebido como parâmetro em Json.
- `array(attributes)`: Converte uma lista de strings rcebida em Json.
- `string(attribute)`: Converte uma string em Json.
- `boolean(attribute)`: Converte um boolean em Json.


### 2. GetJSon

#### Descrição
A classe `GetJson` é o componente principal do framework HTTP que desenvolvemos. Esta classe +ermite registar os controladores anotados para expor os seus métodos como endpoints HTTP GET.

#### Funcionalidades 

- Recebe uma ou mais classes de controladores com métodos anotados com `@Mapping`.
- Para cada método anotado, cria uma rota HTTP associada, incluindo suporte para parâmetros no path.
- Usa reflexão para chamar os métodos com parâmetros extraídos da query string e do path do pedido.
- Serializa as respostas para Json quando o resultado implementa a interface `@JsonValue`.
- Implementa um servidor HTTP simples.

#### Estrutura Interna
-Route: Estrutura de dados que contém o padrão de path, variáveis de path, a instância do controlador e o método a invocar.
-buildPathRegex: Contrói regex e extrai as variáveis do path.
-start(port): Inicia o servidor HTTP numa porta específica.
-Handler: Classe interna que processa cada pedido HTTP, faz a análise dos parâmetros, converte-os para os tipos esperados e invoca o método do controlador.

#### Processamento de parâmetros
- Parâmetros anotados com `@Param` são extraídos da query string.
- Parâmetros anotados com `@Path` são extraídos do path da URL.
- Suporta conversão automática para tipos primitivos e listas desses tipos.

### Anotações de Mapeamento (`@Mapping,`, `@Param`, `@Path`)

#### Descrição
Estas anitações são usadas para definir o mapeamento entre classes e métodos e os endpoints HTTP expostos pelo servidor.

- `@Mapping`: Pode ser aplicada a classes ou funções para indicar o caminho HTTP que representa o endpoint.
- `@Param`: Usada para indicar que um parâmetro da função deve ser preenchidp com um valor da query string HTTP.
- `@Path`: Indica que um parâmetro da função deve ser preenchido com um valor retirado diretamente do path do URL. 

