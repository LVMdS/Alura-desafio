# Projeto Gutendex API

Este projeto é uma aplicação em Java que utiliza a API Gutendex para buscar e armazenar informações sobre livros e autores do Project Gutenberg. A aplicação permite realizar consultas por título de livro, listar todos os livros e autores armazenados, e exibir estatísticas sobre os dados.

## Funcionalidades

### Livros
- **Busca por título:** Permite buscar um livro pelo título e salvar os dados no banco de dados.
- **Listagem de livros:** Exibe uma lista de todos os livros armazenados.

### Autores
- **Listagem de autores:** Mostra todos os autores armazenados, incluindo seus anos de nascimento e falecimento.
- **Autores vivos em determinado ano:** Lista os autores que estavam vivos em um ano fornecido pelo usuário.

### Estatísticas
- **Quantidade de livros por idioma:** Exibe a quantidade de livros armazenados em um determinado idioma.

## Tecnologias Utilizadas
- **Linguagem:** Java
- **Framework:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Dependências:**
  - Jackson para manipulação de JSON
  - Spring Data JPA para persistência
  - HttpClient para requisições HTTP

## Configuração do Ambiente

### Pré-requisitos
- Java 17+
- PostgreSQL
- Maven

### Configuração do Banco de Dados
No arquivo `application.properties`, configure as credenciais do banco de dados PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Dependências do Maven
Certifique-se de incluir as dependências necessárias no arquivo `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.0</version>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
</dependencies>
```

## Uso

### Executando a Aplicação
1. Clone este repositório.
2. Configure o banco de dados no arquivo `application.properties`.
3. Execute o comando Maven para iniciar a aplicação:
   ```
   mvn spring-boot:run
   ```

### Menu de Interação
Ao executar a aplicação, um menu interativo será exibido no terminal. As opções incluem:
- Buscar livro por título.
- Listar todos os livros.
- Listar autores.
- Listar autores vivos em determinado ano.
- Exibir estatísticas de livros por idioma.
- Sair.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença
Este projeto está sob a licença MIT. Consulte o arquivo LICENSE para mais informações.
