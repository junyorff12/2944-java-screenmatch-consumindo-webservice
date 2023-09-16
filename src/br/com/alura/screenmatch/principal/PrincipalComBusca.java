package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do filme: ");
        var busca = sc.nextLine();
        var enderecoBuscado = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+")  + "&apikey=8f2cc816";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(enderecoBuscado))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            //Gson gson = new Gson();

            //Titulo titulo = gson.fromJson(json, Titulo.class);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TituloOmdb tituloOmdb = gson.fromJson(json, TituloOmdb.class);


            Titulo titulo = new Titulo(tituloOmdb);
            System.out.println("Titulo ja convertido:");
            System.out.println(titulo);
        } catch (NumberFormatException e){
            System.out.println("Ocorreu um erro: ");
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException e){
            System.out.println("Exceção ocorrida no endereço:");
            System.out.println(e.getMessage());
        }catch (ErroDeConversaoDeAnoException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Programa finalizado corretamente!");
    }
}
