package com.nttdata.steps;

import com.nttdata.model.Order;
import com.nttdata.utils.SharedData;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PetStoreStep {

    private Order order;
    private Response response;
    private long storedOrderId;

    public void setBaseUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    public void setOrderData(int petId, int quantity, String shipDate, String status, boolean complete) {
        order = new Order();
        order.setId(5); // Forzar un ID válido entre 1 y 10
        order.setPetId(petId);
        order.setQuantity(quantity);
        order.setShipDate(shipDate);
        order.setStatus(status);
        order.setComplete(complete);
        System.out.println("Datos de la orden configurados: " + order);
    }

    public void createOrder(String endpoint) {
        response = given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(endpoint)
                .then()
                .extract().response();

        //depuración
        System.out.println("Respuesta del POST:");
        response.prettyPrint();
    }


    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals("El código de estado es incorrecto", expectedStatusCode, response.getStatusCode());
    }

    public void verifyResponseField(String fieldName, Object expectedValue) {
        Object actualValue = response.jsonPath().get(fieldName);
        assertEquals("El campo " + fieldName + " no coincide", expectedValue, actualValue);
    }

    public void storeOrderId() {
        long orderId = response.jsonPath().getLong("id");
        System.out.println("OrderId devuelto por el API: " + orderId);

        if (orderId < 1 || orderId > 10) {
            throw new RuntimeException("El ID devuelto no está en el rango permitido (1-10): " + orderId);
        }

        SharedData.setOrderId(orderId);
        System.out.println("OrderId almacenado: " + SharedData.getOrderId());
    }


    public void getStoredOrderId() {
        storedOrderId = SharedData.getOrderId();
        System.out.println("OrderId recuperado desde SharedData: " + storedOrderId);

        if (storedOrderId <= 0) {
            throw new RuntimeException("El orderId recuperado no es válido.");
        }
    }


    public void getOrderById(String endpoint) {
        if (storedOrderId <= 0) {
            throw new RuntimeException("El orderId no está inicializado.");
        }
        response = given()
                .contentType("application/json")
                .when()
                .get(endpoint + "/" + storedOrderId)
                .then()
                .extract().response();

        System.out.println("Respuesta del GET:");
        response.prettyPrint();
    }

    public long getStoredOrderIdValue() {
        return storedOrderId;
    }
}
