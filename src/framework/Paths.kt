package framework

/**
 * Anotação para mapear classes ou funções para um caminho HTTP.
 *
 * Usada para definir o endpoint associado a um controlador ou método.
 *
 * @property name Nome do endpoint para o mapeamento HTTP.
 */

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mapping(val name: String)

/**
 * Anotação para indicar que um parâmetro de função deve ser preenchido
 * com um valor da query string do pedido HTTP.
 *
 * Utilizado para parâmetros que representam query parameters.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Param

/**
 * Anotação para indicar que um parâmetro de função deve ser preenchido
 * com um valor extraído do path do pedido HTTP.
 *
 * Usado para variáveis definidas na URL.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path
