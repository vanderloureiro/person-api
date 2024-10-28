# PersonApi

Projeto de teste take-home com API de pessoa

### Stack

<ul>
    <li>Java 21</li>
    <li>Spring Boot</li>
</ul>

### Como executar

<b>Maven</b>

Para executar via maven, é necessário baixar o zip do projeto, descompacta-lo e rodar o seguinte comando 
dentro da pasta do projeto:

``./mvnw spring-boot:run``

<b>Docker</b>

Uma imagem Docker pública foi disponibilizada no DockerHub. 
Tendo o Docker instalado na máquina, é possível rodar a aplicação com:

``docker run -p 8080:8080 vanderloureiro/person-api``

### Documentação e teste

O projeto está documentado com Swagger. Pode ser acessado e testado através de:

``http://localhost:8080/swagger-ui/index.html``

### Design e arquitetura

O projeto está estruturado usando o 
[Package By Feature](https://medium.com/@vanderloureiro/desenvolvimento-modularizado-com-pacote-por-recurso-package-by-feature-b0b237fca8ef) 
ao invés de pacotes por camadas (Controllers, Repositories, etc)

O projeto não usa DTOs e Forms devido a simplicidade da classe Person

Também foi optado pela não utilização de camadas de Services. 
As ações que alteram o estado do recurso ficaram dentro da entidade (classe rica) e 
as relacionadas à persistência ficaram centralizadas no Repository. 
O repository foi criado buscando ficar semelhante ao JpaRepository do Spring nas assinaturas.