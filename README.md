# 🚀 CustomerConnect - Sistema de Gerenciamento de Clientes

O **CustomerConnect** é uma API RESTful desenvolvida com **Spring Boot**, responsável pelo gerenciamento de clientes, permitindo operações completas de CRUD com regras de negócio bem definidas, validações e suporte a paginação e filtros.

---

## 📌 Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- Docker
- Beekeeper Studio

---

## 📖 Sobre o Projeto

O CustomerConnect é um sistema robusto de gerenciamento de clientes que permite:

- Cadastro de clientes
- Consulta com filtros personalizados
- Atualização de dados
- Exclusão de registros

Além disso, o sistema foi projetado seguindo boas práticas de arquitetura em camadas, garantindo organização, escalabilidade e fácil manutenção.

---

## 🔐 Regras de Negócio

### 📋 Dados Cadastrais
Cada cliente possui:
- Nome completo
- CPF
- Email
- Telefone celular
- Data de criação
- Data de atualização

### 🚫 Cadastro Único
O sistema garante que não existam clientes com:
- CPF duplicado
- Email duplicado
- ID duplicado

### 🔍 Busca Flexível
- Busca por **CPF**
- Busca por **Email**
- Paginação de resultados
- Ordenação por data de criação

---

## 🔗 Endpoints da API

### ➕ Criar Cliente
**POST /customers**

**Parâmetros:**
```json
{
  "fullName": "Nome Completo",
  "cpf": "00000000000",
  "email": "email@email.com",
  "phoneNumber": "999999999"
}
