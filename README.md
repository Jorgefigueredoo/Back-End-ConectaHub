# 🌱 ConectaHub API - Sistema de Distribuição de Sementes

API REST completa desenvolvida com Spring Boot para gerenciar a distribuição de sementes agrícolas, conectando fornecedores, agricultores e operadores em uma plataforma centralizada.

## 📋 Sobre o Projeto

O ConectaHub API é o backend de uma solução para gerenciar a distribuição de sementes agrícolas. O sistema controla estoques, envios, rastreamento de lotes e fornece relatórios gerenciais completos.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Security** - Autenticação JWT
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados
- **JWT (JSON Web Token)** - Autenticação stateless com Auth0
- **Maven** - Gerenciamento de dependências
- **Hibernate** - ORM
- **OpenPDF** - Geração de relatórios em PDF
- **Lombok** - Redução de código boilerplate

## ✨ Funcionalidades

### 🔐 Autenticação e Autorização
- ✅ Login com JWT (email + senha)
- ✅ Registro de novos usuários
- ✅ Tokens com expiração de 2 horas
- ✅ Senhas criptografadas com BCrypt
- ✅ Controle de perfis (ADMIN, USER)

### 👨‍🌾 Gestão de Agricultores
- ✅ Busca de agricultores por nome
- ✅ Cadastro com CPF/CNPJ
- ✅ Registro de município e UF

### 🏭 Gestão de Fornecedores
- ✅ Cadastro completo (Razão Social, CNPJ)
- ✅ Listagem de fornecedores
- ✅ Atualização de dados
- ✅ Exclusão de fornecedores

### 🌾 Controle de Estoque de Sementes
- ✅ Listagem de sementes disponíveis
- ✅ Controle de quantidade em kg
- ✅ Nível mínimo de estoque
- ✅ Status automático (Disponível/Estoque Baixo/Sem Estoque)
- ✅ Bloqueio pessimista para evitar inconsistências

### 📦 Gestão de Envios
- ✅ Criação de novos envios
- ✅ Geração automática de código de lote
- ✅ Baixa automática no estoque
- ✅ Rastreamento por código de lote
- ✅ Atualização de status (Criado → Em Trânsito → Entregue → Confirmado)
- ✅ Histórico completo de movimentações

### 📊 Dashboard e Relatórios
- ✅ Estatísticas em tempo real (envios em trânsito, entregues)
- ✅ Taxa de confirmação
- ✅ Atividades recentes
- ✅ Geração de relatórios em PDF
- ✅ Filtros avançados (por agricultor, semente, município, período)

## 🔧 Configuração e Instalação

### Pré-requisitos

- JDK 17 ou superior
- MySQL 8.0+
- Maven 3.6+

### Configuração do Banco de Dados

1. **Crie o banco de dados:**
```sql
CREATE DATABASE conectahub;
USE conectahub;
```

2. **Execute os scripts SQL** (na ordem):
```bash
# 1. Estrutura das tabelas
mysql -u root -p conectahub < "Estrutura SQL.sql"

# 2. Dados de exemplo
mysql -u root -p conectahub < "Inserts SQL.sql"

# 3. Procedures e Functions (opcional)
mysql -u root -p conectahub < "Procedures e Funções SQL.sql"

# 4. Triggers (opcional)
mysql -u root -p conectahub < "Triggers SQL.sql"

# 5. Views (opcional)
mysql -u root -p conectahub < "Views SQL.sql"
```

### Configuração da Aplicação

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/conectahub.git
cd conectahub/conectahub-api
```

2. **Configure o `application.properties`:**
```properties
# Conexão com MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/conectahub?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JWT Secret
jwt.secret=minha-chave-secreta-super-segura-conectahub-2024

# Porta
server.port=8080
```

3. **Compile e execute:**
```bash
# Compilar
./mvnw clean install

# Executar
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 📡 Endpoints da API

### 🔓 Endpoints Públicos

#### Autenticação

**Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "teste@email.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Registro**
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Nome Usuario",
  "login": "usuario@email.com",
  "password": "senha123",
  "role": "USER"
}
```

#### Agricultores

**Buscar Agricultores**
```http
GET /api/agricultores/buscar?nome=Jose
```

### 🔒 Endpoints Protegidos (Requer Token JWT)

**Header obrigatório:**
```http
Authorization: Bearer {seu_token_jwt}
```

#### Fornecedores

**Listar Fornecedores**
```http
GET /api/fornecedores
```

**Criar Fornecedor**
```http
POST /api/fornecedores
Content-Type: application/json

{
  "razaoSocial": "Sementes do Agreste Ltda",
  "cnpj": "12.345.678/0001-99"
}
```

**Atualizar Fornecedor**
```http
PUT /api/fornecedores/{id}
Content-Type: application/json

{
  "razaoSocial": "Novo Nome Ltda",
  "cnpj": "12.345.678/0001-99"
}
```

**Deletar Fornecedor**
```http
DELETE /api/fornecedores/{id}
```

#### Sementes (Estoque)

**Listar Estoque**
```http
GET /api/sementes
```

#### Envios

**Criar Novo Envio**
```http
POST /api/envios
Content-Type: application/json

{
  "agricultorId": 1,
  "sementeId": 2,
  "quantidadeKg": 50.5,
  "codigoLote": "LOT-2024-001"
}
```

**Rastrear Envio**
```http
GET /api/envios/buscar/{codigoLote}
```

**Atualizar para "Em Rota"**
```http
PUT /api/envios/{id}/em-rota
```

**Confirmar Entrega**
```http
PUT /api/envios/{id}/entregue
```

#### Dashboard

**Obter Resumo**
```http
GET /api/dashboard/resumo
```

**Resposta:**
```json
{
  "emTransito": 15,
  "entreguesHoje": 8,
  "taxaConfirmacao": "98%",
  "atividades": [
    {
      "descricao": "Lote 1234: Saiu para entrega",
      "dataHora": "04/12 14:30"
    }
  ]
}
```

#### Relatórios

**Gerar Relatório PDF**
```http
POST /api/relatorios/gerar
Content-Type: application/json

{
  "agricultorId": null,
  "sementeId": null,
  "municipio": "Recife",
  "dataInicio": "2024-01-01",
  "dataFim": "2024-12-31"
}
```

Retorna um arquivo PDF para download.

## 🔐 Segurança

### CORS
Configurado para aceitar requisições de:
- `http://127.0.0.1:5500`
- `http://localhost:5500`
- `http://localhost:8080`

### JWT
- **Algoritmo:** HMAC256
- **Validade:** 2 horas
- **Chave secreta:** Configurável via `application.properties`

### Endpoints Públicos
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro
- `GET /api/agricultores/buscar` - Busca de agricultores
- `OPTIONS /**` - Pre-flight requests (CORS)

### Endpoints Protegidos
Todos os demais endpoints requerem token JWT válido.

## 📁 Estrutura do Projeto

```
conectahub-api/
├── src/main/java/com/conectahub/conectahub_api/
│   ├── config/
│   │   ├── SecurityConfig.java         # Configuração de segurança
│   │   └── SecurityFilter.java         # Filtro JWT
│   ├── controller/
│   │   ├── AgricultorController.java   # Endpoints de agricultores
│   │   ├── AutenticacaoController.java # Login e registro
│   │   ├── DashboardController.java    # Estatísticas
│   │   ├── EnvioController.java        # Gestão de envios
│   │   ├── FornecedorController.java   # CRUD de fornecedores
│   │   ├── RelatorioController.java    # Geração de relatórios
│   │   └── SementeController.java      # Controle de estoque
│   ├── dto/
│   │   ├── LoginRequestDTO.java
│   │   ├── LoginResponseDTO.java
│   │   ├── RegisterRequestDTO.java
│   │   ├── CriarEnvioRequestDTO.java
│   │   ├── DetalhesEnvioDTO.java
│   │   ├── DashboardDTO.java
│   │   └── RelatorioFiltroDTO.java
│   ├── model/
│   │   ├── Usuario.java                # Entidade de usuário
│   │   ├── Agricultor.java             # Entidade de agricultor
│   │   ├── Fornecedor.java             # Entidade de fornecedor
│   │   ├── Semente.java                # Entidade de semente
│   │   ├── Envio.java                  # Entidade de envio
│   │   ├── HistoricoEnvio.java         # Histórico de mudanças
│   │   └── StatusEnvio.java            # Enum de status
│   ├── repository/
│   │   ├── UsuarioRepository.java
│   │   ├── AgricultorRepository.java
│   │   ├── FornecedorRepository.java
│   │   ├── SementeRepository.java
│   │   ├── EnvioRepository.java
│   │   └── HistoricoEnvioRepository.java
│   ├── service/
│   │   ├── AutenticacaoService.java    # Lógica de autenticação
│   │   ├── TokenService.java           # Geração e validação JWT
│   │   ├── AgricultorService.java
│   │   ├── FornecedorService.java
│   │   ├── SementeService.java
│   │   ├── EnvioService.java           # Lógica de envios
│   │   ├── DashboardService.java
│   │   └── RelatorioService.java       # Geração de PDFs
│   └── ConectahubApiApplication.java   # Classe principal
├── src/main/resources/
│   └── application.properties          # Configurações
└── pom.xml                             # Dependências Maven
```

## 🗄️ Modelo de Dados

### Principais Entidades

**Usuario**
- id (PK)
- nome
- email (unique)
- senhaHash
- role (ADMIN/USER)

**Agricultor**
- id (PK)
- nome
- cpfCnpj (unique)
- municipio
- uf

**Fornecedor**
- id (PK)
- razaoSocial
- cnpj (unique)
- dataCriacao

**Semente**
- id (PK)
- tipoSemente (unique)
- quantidadeKg
- nivelMinimoKg
- dataUltimaEntrada

**Envio**
- id (PK)
- codigoLote (unique)
- quantidadeEnviadaKg
- status (ENUM)
- dataCriacao
- agricultorId (FK)
- sementeId (FK)

**HistoricoEnvio**
- id (PK)
- status (ENUM)
- descricao
- dataHora
- envioId (FK)

## 🎯 Fluxo de Uso

### 1. Autenticação
```
1. Usuário faz login → Recebe token JWT
2. Token é enviado no header de todas as requisições protegidas
```

### 2. Criar Novo Envio
```
1. Selecionar agricultor (busca por nome)
2. Selecionar semente (listar estoque)
3. Definir quantidade em kg
4. Sistema gera código de lote
5. Baixa automática no estoque
6. Cria registro no histórico
```

### 3. Rastrear Envio
```
1. Buscar por código de lote
2. Visualizar detalhes do envio
3. Acompanhar histórico de status
4. Atualizar status conforme necessário
```

### 4. Gerar Relatório
```
1. Definir filtros (período, município, agricultor, semente)
2. Sistema busca dados filtrados
3. Gera PDF formatado com tabela completa
4. Download automático do arquivo
```

## 💾 Dados de Exemplo

Após executar os scripts SQL, o sistema terá:
- 20 Categorias de sementes
- 20 Fornecedores
- 20 Agricultores
- 20 Usuários
- 20 Tipos de sementes
- 20 Lotes de estoque

## 🛡️ Regras de Negócio

1. **Estoque**: Não permite criação de envio sem saldo suficiente
2. **Bloqueio**: Usa lock pessimista para evitar race conditions no estoque
3. **Histórico**: Registra automaticamente cada mudança de status
4. **Status do Envio**: Segue fluxo: CRIADO → EM_TRANSITO → ENTREGUE → CONFIRMADO
5. **Validação**: Campos obrigatórios validados antes de salvar

## 🚀 Deploy

### Variáveis de Ambiente

Para produção, configure:
```bash
DB_URL=jdbc:mysql://seu-host:3306/conectahub
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_longa
```

### Build para Produção

```bash
./mvnw clean package -DskipTests
java -jar target/conectahub-api-0.0.1-SNAPSHOT.jar
```

## 📝 Notas Importantes

- **JWT Secret**: Altere a chave secreta antes do deploy em produção
- **CORS**: Configure origens específicas para produção
- **Banco de Dados**: Use MySQL com encoding UTF-8
- **Timezone**: Configurado para UTC nas conexões

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte do Projeto Integrador.

## 👥 Autores

Desenvolvido como projeto acadêmico para gerenciamento de distribuição de sementes agrícolas.

---

**🌱 ConectaHub API - Conectando o campo à tecnologia**
