<p align="center">
  <img src="./docs/icon_app.png" width="200" alt="Portfolio - Logo" />
</p>
  <p align="center">Projeto de portfólio para o envio de mensagens</p>
  <!--[![Backers on Open Collective](https://opencollective.com/nest/backers/badge.svg)](https://opencollective.com/nest#backer)
  [![Sponsors on Open Collective](https://opencollective.com/nest/sponsors/badge.svg)](https://opencollective.com/nest#sponsor)-->

## Descrição

Bem-vindo ao meu aplicativo de mensagens! Aqui você poderá enviar e receber mensagens de forma
rápida e fácil. Desfrute de uma experiência de comunicação simples e eficiente em um único
aplicativo. Fique conectado(a) e compartilhe suas mensagens de maneira conveniente com este app
intuitivo e amigável.

## Dependência entre módulos

<p align="center">
  <img src="./docs/modules.png" width="600" />
</p>

## Encriptar e decriptar o body

Toda requisição que contém body em sua requisição e/ou resposta será encriptado ao sair do device e
decriptado ao chegar do device

### Geração da chave

Processo de geração de chaves para realizar a comunicação com o servidor
<p align="center">
  <img src="./docs/geracao-key.png" width="600" />
</p>

### Encripta os dados requisição

Processo de encriptação dos dados enviados ao servidor.
<p align="center">
  <img src="./docs/encrypted.png" width="600"/>
</p>

### Decripta os dados da api

Processo de decriptação dos dados recebidos pelo o servidor.
<p align="center">
  <img src="./docs/decrypted.png" width="600"/>
</p>

## Esquema das branchs

Aqui é a estrutura das branchs para o repositório.
<p align="center">
  <img src="./docs/gitflow_completo.png" width="600" />
</p>

## Tecnologias utilizadas

O app foi desenvolvido usando as seguintes tecnologias:

- LiveData
- ViewModel
- Retrofit
- Dagger
- Navigation
- Coroutines
- Roboletric
- Junit
- MockWebServer
- AndroidExpresso

## Autor

- Author - [Raphael Maracaipe](https://www.linkedin.com/in/raphaelmaracaipe)
