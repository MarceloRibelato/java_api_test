#language: pt
#Author: Marcelo Ribelato
#Version: 1.0

@regressivo
Funcionalidade: Criar e editar contas de usuários
  Como Administrador do sistema, desejo cadastrar e editar usuários no sistema.

  @post
  Cenário: Cadastrar novo usuário
    Dado que estou logado na API regres.in
    Quando envio uma requisição post com dados válidos
    Então o usuário deve ser cadastrado corretamente

  @get
  Cenário: Buscar usuário
    Dado que estou logado na API regres.in
    Quando envio uma requisição para buscar os dados do usuário com ID 2
    Então os dados do usuário devem ser retornados corretamente

  @get404
  Cenário: Buscar usuário inexistente
    Dado que estou logado na API regres.in
    Quando envio uma requisição para buscar os dados do usuário com ID 999999
    Então deve ser retornado um erro Not Found

  @put
  Cenário: Update dados do usuário
    Dado que estou logado na API regres.in
    Quando envio uma requisição para alterar os dados do usuário com ID 2
    Então os dados do usuário devem ser alterados corretamente

  @delete
  Cenário: Deletar usuário
    Dado que estou logado na API regres.in
    Quando envio uma requisição para deletar o usuário com ID 61
    Então o usuário deve ser deletado corretamente

