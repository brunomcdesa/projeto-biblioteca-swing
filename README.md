# 📚 Sistema de Gerenciamento de Biblioteca

Um sistema de desktop para gerenciar o acervo de uma biblioteca, controlando livros, autores e editoras.

## 🎯 Objetivo

O objetivo do projeto é criar uma ferramenta para centralizar e otimizar o cadastro, a consulta e a manutenção dos registros da biblioteca.

---

## ✨ O que o sistema faz?

#### ✒️ Autores
- Cadastra, edita, lista, busca e deleta autores.

#### 🏢 Editoras
- Cadastra, edita, lista, busca e deleta editoras.

#### 📖 Livros
- Funções completas de cadastro, edição, listagem, busca e exclusão.
- **Cadastro por ISBN:** Busca e adiciona livros automaticamente pela API pública da Open Library, usando o ISBN-10 ou ISBN-13.
- **Importação em massa:** Importa uma lista de livros, autores e editoras de uma vez. Você pode usar os arquivos de exemplo da pasta `/modelo-importacao`.

---

## 🛠️ Tecnologias

* Java 8
* Java Swing (para a interface)
* Hibernate / JPA (para persistência de dados)
* PostgreSQL
* Lombok
* OkHttp (para as chamadas na API do Open Library)

---

## 🚀 Como rodar o projeto

Para executar o projeto na sua máquina, siga estes passos:

### Você vai precisar de:
- JDK 8
- PostgreSQL
- IntelliJ IDEA (ou outra IDE Java)

### 1. Banco de Dados

1.  Com o PostgreSQL rodando, crie um banco de dados chamado **`bibliotecadb`**.
2.  Verifique se o usuário e a senha do banco correspondem ao que está no arquivo `src/META-INF/persistence.xml`.

> **Aviso:** As tabelas do banco (`autor`, `editora`, etc.) são criadas automaticamente no primeiro acesso da aplicação, por causa da configuração `hibernate.hbm2ddl.auto=update` no `persistence.xml`.

### 2. Configuração da IDE (exemplo com IntelliJ)

As dependências (.jar) estão na pasta `/lib` e precisam ser adicionadas ao projeto.

1.  No IntelliJ, vá em `File` > `Project Structure...`.
2.  No menu lateral, clique em `Libraries`.
3.  Clique no `+` e em `Java`.
4.  Selecione todos os arquivos `.jar` dentro da pasta `lib` e clique em `OK`.
5.  Aplique as mudanças e feche a janela.

### 3. Executando

Pronto! Agora é só rodar a aplicação pela classe `Main.java`, que está em `src/biblioteca/Main.java`.