Projeto de consulta de Prêmios de Produtora:

Linguagem utilizada e principais componentes:
 Java + Spring Boot + Banco de dados Embarcado H2 + Loombock + JsonProperty

Especificação do Teste

Desenvolva uma API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards. Requisito do sistema:

Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao iniciar a aplicação. Requisitos da API:
Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido, seguindo a especificação de formato definida na página 2;

Requisitos não funcionais do sistema:

1 - O web service RESTful deve ser implementado com base no nível 2 de maturidade de Richardson;
2 - Devem ser implementados somente testes de integração. Eles devem garantir que os dados obtidos estão de acordo com os dados fornecidos na proposta;
3 - O banco de dados deve estar em memória utilizando um SGBD embarcado (por exemplo, H2). Nenhuma instalação externa deve ser necessária;
4 - A aplicação deve conter um readme com instruções para rodar o projeto e os testes de integração. O código-fonte deve ser disponibilizado em um repositório git (Github, Gitlab, Bitbucket, etc).

Rodar projeto:

PROCESSO PARA EXECUÇÃO DO PROJETO:
1 - Realizar um clone do repositório  
2 - Importar o projeto no Intellij com java 11.
3 - Após importar todas as dependências, executar ou rodar em modo debug a aplicação.
(Já será executado automaticamente o processo de importação dos dados do arquivo CSV)
4 - Após isso os dados ficaram gravados no banco H2:
   http://localhost:8080/h2-console - Nome da conexão JDBC URL: jdbc:h2:./data 
5 - Para obter o produtor com maior intervalo entre dois prêmios consecutivos 
e o que obteve dois prêmios mais rápido, basta acessar a requisição: 
http://localhost:8080/produtoras/intervaloPremios

EXECUÇÃO DOS TESTES MOCADOS:
1 - Executar com JUnit a classe de testes br.com.premiosprodutora.premiosProdutora.repository.FilmesRepositoryTests
2 - Irá fazer 5 validações de testes mocados validando retorno do endpoints da api,
validando quantidade de regitros importados, validando retornos de atributos de intervalo mínimo
e intervalo máximo e também comparação total da string de retorno json(este teste é valido só para este arquivo de importacao)
