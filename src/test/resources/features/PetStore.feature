Feature: Gestión de órdenes en PetStore
  Como automatizador principal
  Quiero automatizar las pruebas de creación y consulta de órdenes
  Para garantizar la integridad del API de Store

  Background:
    Given La base url del servicio PetStore está configurada en "https://petstore.swagger.io/v2"

  @EscenarioCreacion
  Scenario Outline: Creación de una nueva orden en el Store (POST /store/order)
    Given Tengo los datos de la orden con petId <petId>, quantity <quantity>, shipDate "<shipDate>", status "<status>" y complete <complete>
    When envío una solicitud POST a "/store/order" con el body de la orden
    Then la respuesta debe tener un status code <expectedStatusCode>
    And el body de la respuesta debe contener "petId" <petId>
    And el body de la respuesta debe contener "quantity" <quantity>
    And el body de la respuesta debe contener "status" "<status>"
    And almaceno el "id" de la orden en una variable global para su posterior consulta

    Examples:
      | petId | quantity | shipDate             | status | complete | expectedStatusCode |
      | 5     | 2        | 2024-12-31T10:00:00Z | placed | true     | 200                |

  @EscenarioConsulta
  Scenario Outline: Consulta de la orden previamente creada (GET /store/order/{orderId})
    Given Tengo el "orderId" previamente almacenado de la creación de la orden
    When envío una solicitud GET a "/store/order/{orderId}"
    Then la respuesta debe tener un status code <expectedStatusCode>
    And el body de la respuesta debe contener "id" igual al orderId
    And el body de la respuesta debe contener "petId" <petId>
    And el body de la respuesta debe contener "quantity" <quantity>
    And el body de la respuesta debe contener "status" "<status>"

    Examples:
      | petId | quantity | status  | expectedStatusCode |
      | 5     | 2        | placed  | 200                |
