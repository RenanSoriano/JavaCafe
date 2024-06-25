# Café Management System

![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Sumário
- [Introdução](#introdução)
- [Recursos](#recursos)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Executando a Aplicação](#executando-a-aplicação)
- [Manual de Utilização](#manual-de-utilização)
  - [Gerenciamento de Produtos](#gerenciamento-de-produtos)
  - [Gerenciamento de Vendas](#gerenciamento-de-vendas)
  - [Relatório de Vendas](#relatório-de-vendas)
  - [Alertas de Estoque Baixo](#alertas-de-estoque-baixo)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contato](#contato)

## Introdução
O Café Management System é uma aplicação JavaFX para gerenciamento de vendas e estoque de um café. A aplicação permite adicionar, atualizar, remover produtos, processar vendas, gerar notas fiscais, visualizar relatórios de vendas e alertar quando os níveis de estoque estão baixos.

## Recursos
- Adicionar, atualizar e remover produtos do inventário
- Processar vendas e gerar notas fiscais
- Atualizar o estoque automaticamente após cada venda
- Alertar quando o estoque de produtos está baixo
- Visualizar relatório de vendas

## Pré-requisitos
- Java 11 ou superior
- JavaFX SDK
- IDE (IntelliJ IDEA recomendada)

## Instalação
1. Clone o repositório:
    ```bash
    git clone https://github.com/usuario/cafe-management.git
    ```

2. Configure o projeto na IDE:
    - Abra a IDE IntelliJ IDEA.
    - Selecione "Open or Import" e navegue até o diretório clonado.
    - Certifique-se de que a SDK do Java e o JavaFX estejam configurados corretamente.

3. Baixe o JavaFX SDK:
    - Faça o download do JavaFX SDK de [Gluon](https://gluonhq.com/products/javafx/).
    - Extraia o conteúdo e configure as bibliotecas no seu projeto.

4. Configure as bibliotecas do JavaFX:
    - Vá para `File -> Project Structure -> Libraries` e adicione o caminho do JavaFX SDK.

## Executando a Aplicação
1. Execute a classe principal `Main`:
    - Navegue até a classe `Main.java`.
    - Execute a classe principal para iniciar a aplicação.

## Manual de Utilização

### Gerenciamento de Produtos
- **Adicionar Produto**:
    - Navegue até a aba "Products".
    - Insira o nome, preço e quantidade do produto.
    - Clique no botão "Add".

- **Atualizar Produto**:
    - Selecione um produto na tabela.
    - Modifique o nome, preço ou quantidade.
    - Clique no botão "Update".

- **Remover Produto**:
    - Selecione um produto na tabela.
    - Clique no botão "Delete".

### Gerenciamento de Vendas
- **Adicionar Item ao Pedido**:
    - Navegue até a aba "Sales".
    - Insira o ID do produto e a quantidade.
    - Clique no botão "Add Item".

- **Completar Venda**:
    - Adicione todos os itens desejados ao pedido.
    - Clique no botão "Complete Sale" para finalizar a venda.
    - O estoque será atualizado automaticamente e uma nota fiscal será gerada.

### Relatório de Vendas
- **Visualizar Relatório de Vendas**:
    - Na aba "Sales", clique no botão "Sales Report".
    - Um relatório com todas as notas fiscais será exibido.

### Alertas de Estoque Baixo
- A aplicação verifica automaticamente os níveis de estoque durante a inicialização e após cada operação de atualização, adição ou venda de produtos.
- Se algum produto tiver quantidade menor ou igual a 5, um alerta será exibido.


