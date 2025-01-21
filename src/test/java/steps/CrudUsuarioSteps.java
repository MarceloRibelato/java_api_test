package steps;

import api.ApiRequest;
import io.cucumber.java.es.Dado;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Então;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import api.ApiHeaders;
import org.json.JSONObject;
import utils.JsonUtils;
import utils.PropertiesUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CrudUsuarioSteps extends ApiRequest {

    PropertiesUtils prop = new PropertiesUtils();
    JsonUtils jsonUtils = new JsonUtils();


    @Dado("que estou logado na API regres.in")
    public void que_estou_logado_na_api_de_cadastro() throws IOException, JSONException {
        uri = prop.getProp("uri_regres") + "login";
        headers = ApiHeaders.setEmptyHeaders();
        body = jsonUtils.parseJSONFile("login.json");
        super.POST();
        token = response.jsonPath().getString("token");
    }

    @Quando("envio uma requisição post com dados válidos")
    public void envio_um_requeste_com_dados_validos() throws IOException, JSONException {
        uri = prop.getProp("uri_regres") + "users";
        headers =  ApiHeaders.setHeaderUsers(token);
        body = jsonUtils.parseJSONFile("create_user.json");
        super.POST();
    }

    @Então("o usuário deve ser cadastrado corretamente")
    public void o_usuario_de_ser_casdatrado_corretamente() throws JSONException {
        assertEquals(body.getString("name"), response.jsonPath().getString("name"));
        assertEquals(body.getString("job"), response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("id"));
        assertEquals(HttpStatus.SC_CREATED, response.statusCode());

    }


    @Quando("envio uma requisição para deletar o usuário com ID {int}")
    public void envio_um_requeste_para_deletar_o_usuario(Integer id) throws IOException {
        uri = prop.getProp("uri_regres") + "users/" + id;
        headers =  ApiHeaders.setHeaderUsers(token);
        body= new JSONObject();
        super.DELETE();
    }
    @Então("o usuário deve ser deletado corretamente")
    public void o_usuario_de_ser_deletado_corretamente() {
        assertEquals(HttpStatus.SC_NO_CONTENT, response.statusCode());
    }

    @Quando("envio uma requisição para buscar os dados do usuário com ID {int}")
    public void envio_uma_requisição_para_buscar_os_dados_do_usuário_com_id(Integer id) {
        uri = prop.getProp("uri_regres") + "users/" + id;
        headers =  ApiHeaders.setHeaderUsers(token);
        body= new JSONObject();
        super.GET();
    }
    @Então("os dados do usuário devem ser retornados corretamente")
    public void os_dados_do_usuário_devem_ser_retornados_corretamente() {
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("Content-Type=application/json; charset=utf-8",response.headers().get("Content-Type").toString());
        assertEquals("Janet", response.jsonPath().getString("data.first_name"));
    }

    @Quando("envio uma requisição para alterar os dados do usuário com ID {int}")
    public void envio_uma_requisição_para_alterar_os_dados_do_usuário_com_id(Integer id) throws IOException {
        uri = prop.getProp("uri_regres") + "users/" + id;
        headers =  ApiHeaders.setHeaderUsers(token);
        body = jsonUtils.parseJSONFile("create_user.json");
        super.PUT();
    }
    @Então("os dados do usuário devem ser alterados corretamente")
    public void os_dados_do_usuário_devem_ser_alterados_corretamente() {
        assertEquals(body.getString("name"), response.jsonPath().getString("name"));
        assertEquals(body.getString("job"), response.jsonPath().getString("job"));
        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Então("deve ser retornado um erro Not Found")
    public void deve_ser_retornado_um_erro_Not__Found() {
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

}
