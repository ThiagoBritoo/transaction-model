# Transaction API

![Java](https://img.shields.io/badge/Java-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green)
![MySQL](https://img.shields.io/badge/MySQL-blue)

## Descrição
O projeto implementa um sistema de transações bancárias simples, utilizando Java com Spring Boot e MySQL para armazenamento de dados. Este sistema proporciona operações de gerenciamento de transações entre usuários.

## Estrutura do Projeto
O projeto possui os seguintes componentes principais:

- **TransactionController:** Controlador responsável por operações relacionadas a transações bancárias.
- **UserController:** Controlador que gerencia operações relacionadas a usuários.

## Endpoints
### TransactionController
- **GET /transactions:** Retorna todas as transações.
- **GET /transactions/{id}:** Retorna uma transação específica pelo ID.
- **GET /transactions/user/{id}:** Retorna todas as transações associadas a um usuário específico.
- **POST /transactions:** Cria uma nova transação.
- **DELETE /transactions/{id}:** Exclui uma transação pelo ID.

### UserController
- **GET /users:** Retorna todos os usuários.
- **GET /users/{id}:** Retorna um usuário específico pelo ID.
- **POST /users:** Cria um novo usuário.
- **DELETE /users/{id}:** Exclui um usuário pelo ID.
- **PUT /users/{id}:** Atualiza um usuário existente pelo ID.

## Banco de Dados
O projeto utiliza MySQL como banco de dados para armazenar informações. As configurações e migrações do banco de dados são gerenciadas automaticamente pelo Spring Boot, utilizando as anotações de persistência.

## Pré-requisitos
Java e Spring Boot configurados em seu ambiente de desenvolvimento.
Servidor MySQL configurado e acessível.

## Configuração
Clone este repositório.
Execute o aplicativo usando sua IDE favorita ou utilizando o comando ./mvnw spring-boot:run.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests.
