package com.example.gutendex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GutendexApplication implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    public static void main(String[] args) {
        SpringApplication.run(GutendexApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Buscar livro por título e salvar");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Listar todos os autores");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a linha

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    livroService.buscarELancarLivroPorTitulo(titulo);
                }
                case 2 -> livroService.listarLivros();
                case 3 -> livroService.listarAutores();
                case 4 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}

@Entity
class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private int numeroDownloads;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    // Getters e setters omitidos por brevidade
}

@Entity
class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    // Getters e setters omitidos por brevidade
}

@Repository
interface LivroRepository extends JpaRepository<Livro, Long> {
}

@Repository
interface AutorRepository extends JpaRepository<Autor, Long> {
}

@Service
class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void buscarELancarLivroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + titulo;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode results = rootNode.get("results");

            if (results.isArray() && results.size() > 0) {
                JsonNode firstBook = results.get(0);
                Livro livro = new Livro();
                livro.setTitulo(firstBook.get("title").asText());
                livro.setIdioma(firstBook.get("languages").get(0).asText());
                livro.setNumeroDownloads(firstBook.get("download_count").asInt());

                JsonNode authorsNode = firstBook.get("authors");
                if (authorsNode.isArray() && authorsNode.size() > 0) {
                    JsonNode authorNode = authorsNode.get(0);
                    Autor autor = new Autor();
                    autor.setNome(authorNode.get("name").asText());
                    autor.setAnoNascimento(authorNode.get("birth_year").isNull() ? null : authorNode.get("birth_year").asInt());
                    autor.setAnoFalecimento(authorNode.get("death_year").isNull() ? null : authorNode.get("death_year").asInt());

                    livro.setAutor(autor);
                }

                livroRepository.save(livro);
                System.out.println("Livro salvo com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o título fornecido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar ou salvar o livro: " + e.getMessage());
        }
    }

    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            livros.forEach(livro -> System.out.println("Título: " + livro.getTitulo()));
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
        } else {
            autores.forEach(autor -> System.out.println("Nome: " + autor.getNome()));
        }
    }
}
