# TechChallenge_lanchonete Fase 2

# Passos para execução do projeto

1. Clone o repositório.
2. Certifique-se de ter o Docker-Desktop com kubernetes habilidado ou minikube instalado na sua máquina.
3. No diretório raiz do projeto, abra o terminal.
4. Execute os seguintes comandos em ordem para subir a aplicação:

1 - Comando, para gerar a imagem do Dockerfile para ser usada pelo kubernetes
 ```shell
   docker build -t lanchonete-app:1.0 .
```

2 - Comando, para rodar os manifestos.yaml na seguencia correta.

```shell
  kubectl apply -k .
```

## Testes

Depois que a Aplicação buildar e subir

- Para consultar as rotas da API, acesse: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- Você pode usar o Postman ou o Swagger para testar as requisições.

## Cadastro do Cliente

Para cadastrar um cliente, faça uma requisição POST para a rota `http://localhost:8080/cliente/create`.

### Exemplo de requisição:

```http
POST http://localhost:8080/cliente/create
Content-Type: application/json

{
  "nomeCliente": "Nome do Cliente",
  "email": "cliente@example.com",
  "cpf": "12345678900"
}
```

## Identificação do Cliente via CPF

Para identificar um cliente com base no CPF, faça uma requisição POST para a rota `http://localhost:8080/cliente/busca/cpf/email`.

### Exemplo de requisição:

```http
POST http://localhost:8080/cliente/busca/cpf/email
Content-Type: application/json

{
  "cpf": "12345678900"
}
```

## Criação, edição e remoção de produtos

Para criar um produto, faça uma requisição POST para a rota `http://localhost:8080/produto/create`.

"categoria" -> é um enum valores aceitos (LANCHE, ACOMPANHAMENTO, BEBIDA, SOBREMESA)

### Exemplo de requisição:

```http
POST http://localhost:8080/produto/create
Content-Type: application/json

{
  "nomeProduto": "Nome do Produto",
  "categoria": "LANCHE",
  "descricaoProduto": "Descrição do Produto",
  "preco": 10.5,
  "imagem": "url-da-imagem"
}
```

Para editar um produto existente, faça uma requisição PUT para a rota `http://localhost:8080/produto`.

### Exemplo de requisição:

```http
PUT http://localhost:8080/produto
Content-Type: application/json

{
  "id": 1,
  "nomeProduto": "Nome do Produto Atualizado",
  "categoria": "ACOMPANHAMENTO",
  "descricaoProduto": "Nova descrição do Produto",
  "preco": 15.9,
  "imagem": "nova-url-da-imagem"
}
```

Para remover um produto, faça uma requisição DELETE para a rota `http://localhost:8080/produto/{id}`, onde `{id}` é o ID do produto a ser removido.

**Regra:** Não é possível apagar um produto que está em uso em um pedido em aberto não finalizado.

### Exemplo de requisição:

```http
DELETE http://localhost:8080/produto/1
```

Para buscar produtos por categoria, faça uma requisição GET para a rota `http://localhost:8080/produto/categoria/{categoria}`, onde `{categoria}` é a categoria desejada.

### Exemplo de requisição:

```http
GET http://localhost:8080/produto/categoria/LANCHE
```


## Pedidos / Pagamento

Obs.: O status do Pedido é um enum com valores aceitos (AGUARDANDO_PAGAMENTO, RECEBIDO, PREPARACAO, PRONTO, FINALIZADO)

### Criar um Pedido

Para criar um pedido, faça uma requisição POST para a rota `http://localhost:8080/pedido/create`.

Obs.: O ID do cliente é opcional.

Exemplo de requisição:

```http
POST http://localhost:8080/pedido/create
Content-Type: application/json

{
  "cliente": 0,
  "listaProduto": [0],
  "observacao": "Observação do Pedido"
}
```
:red_circle:**Fase 2 -> Checkout do Pedido:**
 após a criação do pedido, ira retornar os dados sobre o pedido com seu id e QrCode para pagamento.

**Fluxo de Pedido / Pagamento:**
Assim que o pedido é criado, o status do Pedido é definido como <b>AGUARDANDO_PAGAMENTO</b>.<br>Em seguida, é feita uma requisição para uma API de pagamento (simulação), que retorna um `qr_data` com o código para gerar um QR code.


:red_circle:**Fase 2 -> Webhook para receber confirmação de pagamento aprovado ou pagamento recusado.**

Após o pagamento ser realizado, a notificação é recebida na rota `http://localhost:8080/notificacoes/pagamento`, onde o status do pedido é atualizado para RECEBIDO se o pagamento for aprovado e entra na fila.

**Regra:** Os 5 primeiros pedidos com status <b>RECEBIDO</b> são alterados para <b>PREPARACAO</b>.

Quando um pedido é <b>FINALIZADO</b>, o próximo pedido na fila com status <b>RECEBIDO</b> é movido para <b>PREPARACAO</b>.


:red_circle:**Fase 2 -> Atualizar o status do pedido:**
aguarando pagamento para recebido e feito de forma automatica apos pagamento.
Para os outros Status utilizar as rotas abaixo, Pedido Pronto e Pedido Finalizado.


### Pedido Pronto

Rota para alterar o status do pedido para PRONTO.

Método: PUT
Rota: http://localhost:8080/pedido/pronto/{id} (passar o ID do pedido na rota)

### Pedido Finalizado

Rota para alterar o status do pedido para FINALIZADO.

Método: PUT
Rota: http://localhost:8080/pedido/finalizado/{id} (passar o ID do pedido na rota)

### Listar os pedidos

Método: GET
Rota: http://localhost:8080/pedido/listar-todos

Retorna todos os pedidos.


### Listar os pedidos por Prioridade

:red_circle:**Fase 2 ->  A lista de pedidos deverá retornar os pedidos com suas descrições, ordenados por recebimento e por status com a seguinte prioridade: Pronto > Em Preparação > Recebido; Pedidos com status Finalizado não devem aparecer na lista.:**

Método: GET
Rota: http://localhost:8080/pedido/listar-prioridade

Retorna os pedidos por ordem de prioridade Pronto > Preparação > Recebido e ordenados por recebimento. sem os finalizados e os aguarando pagamento.


### consultar um pedido

Método: GET
Rota: http://localhost:8080/pedido/{id}

:red_circle:**Fase 2 -> Consultar status de pagamento do pedido, que informa se o pagamento foi aprovado ou não.:**

Ao passar o ID do pedido retorna as informações do mesmo  
sendo possivel ver o status do mesmo, se foi pago ou não em qual situação se encontra.
