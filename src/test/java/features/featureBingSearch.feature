Feature: Búsqueda y Navegación en Bing

  @testBing
  Scenario: Búsqueda y clic en el primer enlace
    Given Ingreso a la página de inicio de Bing
    When Busco el texto "Hola cucumber"
    And Hago clic en el primer resultado de la búsqueda
    Then Valido que he navegado a una nueva página