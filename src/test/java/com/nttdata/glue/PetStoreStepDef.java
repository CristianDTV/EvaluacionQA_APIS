package com.nttdata.glue;

import com.nttdata.steps.PetStoreStep;
import com.nttdata.utils.SharedData;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class PetStoreStepDef {

    private PetStoreStep petStoreStep = new PetStoreStep();

    @ParameterType("true|True|false|False")
    public boolean bool(String value) {
        return Boolean.parseBoolean(value);
    }

    @Given("La base url del servicio PetStore está configurada en {string}")
    public void laBaseUrlDelServicioPetStoreEstaConfiguradaEn(String baseUrl) {
        petStoreStep.setBaseUrl(baseUrl);
    }

    @Given("Tengo los datos de la orden con petId {int}, quantity {int}, shipDate {string}, status {string} y complete {bool}")
    public void tengoLosDatosDeLaOrden(int petId, int quantity, String shipDate, String status, boolean complete) {
        petStoreStep.setOrderData(petId, quantity, shipDate, status, complete);
    }

    @When("envío una solicitud POST a {string} con el body de la orden")
    public void envioUnaSolicitudPOST(String endpoint) {
        petStoreStep.createOrder(endpoint);
    }

    @When("envío una solicitud GET a {string}")
    public void envioUnaSolicitudGET(String endpoint) {
        String fullEndpoint = endpoint.replace("{orderId}", String.valueOf(SharedData.getOrderId()));
        petStoreStep.getOrderById(fullEndpoint);
    }

    @Then("la respuesta debe tener un status code {int}")
    public void laRespuestaDebeTenerUnStatusCode(int statusCode) {
        petStoreStep.verifyStatusCode(statusCode);
    }

    @Then("el body de la respuesta debe contener \"petId\" {int}")
    public void elBodyDeLaRespuestaDebeContenerPetId(int petId) {
        petStoreStep.verifyResponseField("petId", petId);
    }

    @Then("el body de la respuesta debe contener \"quantity\" {int}")
    public void elBodyDeLaRespuestaDebeContenerQuantity(int quantity) {
        petStoreStep.verifyResponseField("quantity", quantity);
    }

    @Then("el body de la respuesta debe contener \"status\" {string}")
    public void elBodyDeLaRespuestaDebeContenerStatus(String status) {
        petStoreStep.verifyResponseField("status", status);
    }

    @Then("almaceno el \"id\" de la orden en una variable global para su posterior consulta")
    public void almacenoElIdDeLaOrden() {
        petStoreStep.storeOrderId();
    }

    @Given("Tengo el \"orderId\" previamente almacenado de la creación de la orden")
    public void tengoElOrderIdPreviamenteAlmacenado() {
        petStoreStep.getStoredOrderId();
    }

    @Then("el body de la respuesta debe contener \"id\" igual al orderId")
    public void elBodyDeLaRespuestaDebeContenerIdIgualOrderId() {
        long storedId = petStoreStep.getStoredOrderIdValue();
        petStoreStep.verifyResponseField("id", storedId);
    }
}