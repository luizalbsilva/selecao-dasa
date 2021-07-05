# PROJETO DE AVALIAÇÃ0 - DASA

| Linguagem | Java |
| --------- | ---- |
| Tipo | Avaliação de backend |
| Requisitos | https://github.com/dasa-health/simple-test-backend/blob/master/README.md |

# Documentação

## Divisão do sistema

O sistema foi desenvolvido usando Java (com Springboot 2.48) e banco de dados Postgres. Foi separado em quatro módulos, 
de acordo com a tabela abaixo:

| MÓDULO | Descrição |
| ------ | --------- |
| dasa-receituario-models | Modelos e Scripts das migrações do Liquibase |
| dasa-receituario-dao | Daos ( Repositórios ) |
| dasa-receituario-rest | Serviços e Controllers do serviço. Escuta na porta 8080.  |
| dasa-receituario-restfull | Apenas exposição dos DAOs e dados através de sprint-data-rest e HAL. Escuta na porta 8081. |


## Requisitos de Infra ( Banco de Dados )

Para rodar o sistema, deverá criar no Postgres um banco de dados `dasa` com usuario e senha `postgres` -- OU 
usar o docker-compose file no projeto, bastando, para isso, digitar:

`
    docker-compose up
`

Você poderá acessar o banco pela porta 5432.

Foi colocado na porta 53267 o (pgAdmin4 - usuario: postgres@localhost.com / senha: postgres). Para acessar o banco, 
utilize "`db`" como host. Todas essas configuraçÕes podem ser alteradas no docker-compose.yml.


### Configurações de banco

No caso de preferir seu próprío banco de dados e configurar o sistema manualmente, poderá fazê-lo no arquivo 
`src/main/resources/application.properties` do projeto correspondente (dasa-receituario-rest ou dasa-receituario-restfull)


### Estrutura

Modelo ER: docs/db/dasa.vpp (Arquivo Visual Paradigm - [diponível aqui](https://www.visual-paradigm.com/download/))

Todas as tabelas serão construídas em tempo de execução ( usando liquibase ). As tabelas de controle se encontram
no schema público e as tabelas do sistema no schema `receituário`. 

