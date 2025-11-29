# 🌾 ConectaHub - Sistema Completo de Gestão de Distribuição de Sementes

Sistema web full-stack para gerenciamento e rastreamento de distribuição de sementes para agricultores, com autenticação JWT, geração de relatórios PDF e dashboard em tempo real.

## 📋 Sobre o Projeto

O **ConectaHub** é uma plataforma completa que conecta distribuidores de sementes aos agricultores, permitindo controle total de envios, rastreamento de lotes em tempo real, gestão de estoque automatizada, gerenciamento de fornecedores e geração de relatórios operacionais em PDF.

### ✨ Principais Funcionalidades

#### 🔐 Autenticação & Segurança
- Sistema de login e cadastro com JWT (JSON Web Token)
- Criptografia de senhas com BCrypt
- Proteção de rotas com Spring Security
- Gestão de permissões (USER/ADMIN)
- Sessão stateless com tokens de 2 horas

#### 📦 Gestão de Envios
- Registro de novos envios com geração automática de código de lote
- Busca de agricultores com autocomplete
- Validação automática de estoque antes do envio
- Atualização de status (CRIADO → EM_TRANSITO → ENTREGUE → CONFIRMADO)
- Histórico completo de movimentação

#### 🔍 Rastreamento em Tempo Real
- Timeline vertical com histórico detalhado
- Busca por código de lote
- Status coloridos e intuitivos
- Informações completas do destinatário e produto

#### 📊 Dashboard Inteligente
- Cards com métricas em tempo real:
  - Envios em trânsito
  - Entregas do dia
  - Taxa de confirmação
- Feed de atividades recentes (últimas 10 movimentações)
- Saudação personalizada por período do dia

#### 🏪 Controle de Estoque
- Monitoramento automático de quantidade disponível
- Indicadores visuais de status:
  - 🟢 **Disponível** (estoque normal)
  - 🟡 **Estoque Baixo** (≤ nível mínimo)
  - 🔴 **Sem Estoque** (quantidade = 0)
- Busca integrada ao backend
- Registro de última entrada

#### 🏢 Gestão de Fornecedores
- CRUD completo (Create, Read, Update, Delete)
- Modal para cadastro/edição
- Validação de CNPJ com formatação automática
- Interface responsiva com ações inline

#### 📄 Geração de Relatórios
- Relatórios em PDF profissionais
- Filtros personalizáveis:
  - Período (data início/fim)
  - Município específico
  - Cliente/Agricultor
  - Tipo de semente
- Download automático do documento
- Tabela formatada com logo e estatísticas

## 🛠️ Tecnologias Utilizadas

### Backend (Java Spring Boot)

```xml
├── Spring Boot 3.3.1
├── Spring Security (JWT Authentication)
├── Spring Data JPA (Hibernate)
├── MySQL Connector
├── Lombok (Redução de boilerplate)
├── OpenPDF (Geração de relatórios)
├── Auth0 JWT (4.4.0)
└── Maven (Gerenciamento de dependências)
```

**Principais Bibliotecas:**
- `spring-boot-starter-web` - API REST
- `spring-boot-starter-security` - Autenticação e autorização
- `spring-boot-starter-data-jpa` - Persistência com JPA
- `mysql-connector-j` - Driver MySQL
- `openpdf (1.3.30)` - Criação de PDFs
- `jjwt (0.11.5)` - Tokens JWT
- `java-jwt (4.4.0)` - Validação de tokens

### Frontend (HTML/CSS/JavaScript)

```
├── HTML5 (Estrutura semântica)
├── CSS3 (Grid, Flexbox, Animações)
├── JavaScript ES6+ (Async/Await, Fetch API)
├── Font Awesome 6.5.1 (Ícones)
└── Google Fonts - Poppins (Tipografia)
```

### Banco de Dados

- **MySQL 8.0+** - Banco relacional
- **JPA/Hibernate** - ORM (Object-Relational Mapping)
- **Transações ACID** - Garantia de integridade
- **Pessimistic Locking** - Controle de concorrência em estoque

## 🗂️ Arquitetura do Backend

### Estrutura de Pacotes

```
conectahub-api/
│
├── src/main/java/com/conectahub/conectahub_api/
│   │
│   ├── config/                    # Configurações
│   │   ├── SecurityConfig.java    # Configuração do Spring Security
│   │   └── SecurityFilter.java    # Filtro JWT personalizado
│   │
│   ├── controller/                # Camada de Controle (Endpoints REST)
│   │   ├── AgricultorController.java
│   │   ├── AutenticacaoController.java
│   │   ├── DashboardController.java
│   │   ├── EnvioController.java
│   │   ├── FornecedorController.java
│   │   ├── RelatorioController.java
│   │   └── SementeController.java
│   │
│   ├── dto/                       # Data Transfer Objects
│   │   ├── CriarEnvioRequestDTO.java
│   │   ├── DashboardDTO.java
│   │   ├── DetalhesEnvioDTO.java
│   │   ├── LoginRequestDTO.java
│   │   ├── LoginResponseDTO.java
│   │   ├── RegisterRequestDTO.java
│   │   └── RelatorioFiltroDTO.java
│   │
│   ├── model/                     # Entidades JPA (Modelos do Banco)
│   │   ├── Agricultor.java
│   │   ├── Envio.java
│   │   ├── Fornecedor.java
│   │   ├── HistoricoEnvio.java
│   │   ├── Semente.java
│   │   ├── StatusEnvio.java       # Enum (CRIADO, EM_TRANSITO, ENTREGUE)
│   │   └── Usuario.java
│   │
│   ├── repository/                # Camada de Persistência (JPA)
│   │   ├── AgricultorRepository.java
│   │   ├── EnvioRepository.java
│   │   ├── FornecedorRepository.java
│   │   ├── HistoricoEnvioRepository.java
│   │   ├── SementeRepository.java
│   │   └── UsuarioRepository.java
│   │
│   ├── service/                   # Camada de Negócio (Lógica)
│   │   ├── AgricultorService.java
│   │   ├── AutenticacaoService.java
│   │   ├── DashboardService.java
│   │   ├── EnvioService.java
│   │   ├── FornecedorService.java
│   │   ├── RelatorioService.java
│   │   ├── SementeService.java
│   │   └── TokenService.java
│   │
│   └── ConectahubApiApplication.java  # Classe principal
│
└── src/main/resources/
    └── application.properties     # Configurações do banco e JWT
```

## 📡 Endpoints da API

### 🔓 Públicos (Sem autenticação)

```http
POST   /api/auth/login              # Realizar login
POST   /api/auth/register           # Cadastrar novo usuário
GET    /api/agricultores/buscar     # Buscar agricultores (autocomplete)
```

### 🔒 Protegidos (Requer token JWT)

#### 📦 Envios
```http
POST   /api/envios                  # Criar novo envio
GET    /api/envios/buscar/{codigo}  # Buscar detalhes e histórico
PUT    /api/envios/{id}/em-rota     # Atualizar para EM_TRANSITO
PUT    /api/envios/{id}/entregue    # Marcar como ENTREGUE
```

#### 🌾 Sementes (Estoque)
```http
GET    /api/sementes                # Listar todo o estoque
GET    /api/sementes/buscar?nome=   # Buscar por nome
```

#### 🏢 Fornecedores
```http
GET    /api/fornecedores            # Listar todos
POST   /api/fornecedores            # Criar novo
PUT    /api/fornecedores/{id}       # Atualizar existente
DELETE /api/fornecedores/{id}       # Deletar
```

#### 📊 Dashboard
```http
GET    /api/dashboard/resumo        # Buscar métricas e atividades
```

#### 📄 Relatórios
```http
POST   /api/relatorios/gerar        # Gerar PDF com filtros
```

## 🚀 Como Executar o Projeto

### Pré-requisitos

- ☕ **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- 🐬 **MySQL 8.0+** ([Download](https://dev.mysql.com/downloads/mysql/))
- 🌐 **Navegador moderno** (Chrome, Firefox, Edge)
- 📦 **Maven 3.9+** (incluído no wrapper do projeto)

### 1. Configurar o Banco de Dados

```sql
-- Abra o MySQL Workbench ou terminal e execute:
CREATE DATABASE conectahub;
```

### 2. Configurar o Backend

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/PI-ConectaHub.git
cd PI-ConectaHub/conectahub-api
```

2. **Configure o `application.properties`:**
```properties
# Ajuste usuário e senha do MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/conectahub?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

# Hibernate cria as tabelas automaticamente
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Chave secreta do JWT (mude em produção!)
jwt.secret=minha-chave-secreta-super-segura-conectahub-2024

server.port=8080
```

3. **Execute o backend:**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

✅ Se tudo estiver correto, você verá:
```
Started ConectahubApiApplication in X seconds
```

### 3. Executar o Frontend

1. **Navegue até a pasta raiz do frontend:**
```bash
cd ../  # Volta para a raiz do projeto
```

2. **Inicie um servidor local:**

**Opção 1 - Live Server (VS Code):**
- Instale a extensão "Live Server"
- Clique com botão direito em `login.html`
- Selecione "Open with Live Server"

**Opção 2 - Python:**
```bash
python -m http.server 5500
```

**Opção 3 - Node.js:**
```bash
npx http-server -p 5500
```

3. **Acesse no navegador:**
```
http://localhost:5500/login.html
```

### 4. Popular o Banco (Opcional)

Execute estes INSERTs no MySQL para ter dados de teste:

```sql
USE conectahub;

-- Inserir sementes
INSERT INTO sementes (tipo_semente, quantidade_kg, nivel_minimo_kg, data_ultima_entrada) 
VALUES 
('Milho Híbrido', 500.00, 100.00, '2024-01-15'),
('Soja Transgênica', 800.00, 150.00, '2024-01-20'),
('Feijão Preto', 300.00, 80.00, '2024-02-01');

-- Inserir agricultores
INSERT INTO agricultores (nome, cpf_cnpj, municipio, uf) 
VALUES 
('José da Silva', '123.456.789-00', 'Gravatá', 'PE'),
('Maria Santos', '987.654.321-00', 'Bezerros', 'PE'),
('João Oliveira', '456.789.123-00', 'Caruaru', 'PE');

-- Inserir fornecedores
INSERT INTO fornecedores (razao_social, cnpj, data_criacao) 
VALUES 
('Agro Sementes Ltda', '12345678000199', NOW()),
('Sementes Premium S.A.', '98765432000188', NOW());
```

## 🔐 Fluxo de Autenticação

### 1. Cadastro de Usuário

**Frontend envia:**
```json
{
  "name": "Admin Sistema",
  "login": "admin@conectahub.com",
  "password": "senha123",
  "role": "ADMIN"
}
```

**Backend:**
- Valida se o email já existe
- Criptografa a senha com BCrypt
- Salva no banco com o role especificado

### 2. Login

**Frontend envia:**
```json
{
  "email": "admin@conectahub.com",
  "senha": "senha123"
}
```

**Backend:**
- `AuthenticationManager` valida as credenciais
- Se correto, `TokenService` gera um JWT
- Token expira em 2 horas

**Backend retorna:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Requisições Autenticadas

**Todas as requisições protegidas devem incluir:**
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Fluxo interno:**
1. `SecurityFilter` intercepta a requisição
2. Extrai o token do header `Authorization`
3. `TokenService.validarToken()` verifica assinatura e expiração
4. Se válido, define o usuário no `SecurityContext`
5. Request prossegue normalmente

## 📊 Modelo de Dados

### Diagrama de Relacionamentos (Simplificado)

```
┌─────────────┐         ┌──────────────┐
│  Usuario    │         │  Agricultor  │
├─────────────┤         ├──────────────┤
│ id          │         │ id           │
│ nome        │         │ nome         │
│ email       │         │ cpf_cnpj     │
│ senha_hash  │         │ municipio    │
│ role        │         │ uf           │
└─────────────┘         └──────┬───────┘
                               │
                               │ N
                               │
                        ┌──────▼───────┐
                        │    Envio     │
                        ├──────────────┤
                        │ id           │
                        │ codigo_lote  │
┌──────────────┐       │ quantidade   │
│   Semente    │◄──────┤ status       │
├──────────────┤   N   │ data_criacao │
│ id           │       └──────┬───────┘
│ tipo_semente │              │
│ quantidade   │              │ 1
│ nivel_minimo │              │
└──────────────┘              │
                              │ N
                       ┌──────▼──────────┐
                       │ HistoricoEnvio  │
                       ├─────────────────┤
                       │ id              │
                       │ status          │
                       │ descricao       │
                       │ data_hora       │
                       └─────────────────┘

┌──────────────┐
│  Fornecedor  │
├──────────────┤
│ id           │
│ razao_social │
│ cnpj         │
└──────────────┘
```

### Enum: StatusEnvio
```java
CRIADO      → Lote gerado no armazém
EM_TRANSITO → Saiu para entrega
ENTREGUE    → Confirmado no destino
CONFIRMADO  → Agricultor confirmou recebimento (via app)
```

## 🎨 Design System

### Paleta de Cores

| Cor | Hex | Uso |
|-----|-----|-----|
| **Azul Escuro** | `#1a3a68` | Primária (Botões, Headers) |
| **Azul Médio** | `#3a5a8a` | Secundária (Hover, Active) |
| **Bege/Amarelo** | `#ffedd0` | Cards de destaque |
| **Cinza Claro** | `#f0f2f5` | Background geral |
| **Branco** | `#ffffff` | Cards e painéis |
| **Verde** | `#28a745` | Status positivo |
| **Amarelo** | `#ffc107` | Alerta/Atenção |
| **Vermelho** | `#dc3545` | Erro/Crítico |

### Tipografia

- **Família:** Poppins (Google Fonts)
- **Pesos utilizados:** 400 (Regular), 500 (Medium), 600 (Semibold), 700 (Bold), 800 (Extrabold)
- **Tamanhos:**
  - Títulos: 28-32px
  - Subtítulos: 18-22px
  - Corpo: 15-16px
  - Pequeno: 12-14px

## 🔧 Configurações Avançadas

### CORS (Cross-Origin Resource Sharing)

O backend está configurado para aceitar requisições de:
- `http://localhost:5500` (Live Server padrão)
- `http://127.0.0.1:5500`
- `http://localhost:8080`

**Para adicionar outras origens, edite `SecurityConfig.java`:**
```java
configuration.setAllowedOrigins(List.of(
    "http://localhost:5500", 
    "http://seu-dominio.com"
));
```

### Tempo de Expiração do Token

**Para alterar (padrão: 2 horas), edite `TokenService.java`:**
```java
private Instant getExpirationDate() {
    return LocalDateTime.now()
        .plusHours(2) // ← Mude aqui
        .toInstant(ZoneOffset.of("-03:00"));
}
```

### Nível de Log

**Para ver consultas SQL detalhadas, edite `application.properties`:**
```properties
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## 🐛 Troubleshooting (Resolução de Problemas)

### ❌ Erro: "Não foi possível conectar ao banco de dados"

**Solução:**
1. Verifique se o MySQL está rodando
2. Confirme usuário e senha no `application.properties`
3. Teste a conexão manualmente:
```bash
mysql -u root -p
USE conectahub;
```

### ❌ Erro: "Token inválido ou expirado"

**Solução:**
- Faça login novamente para obter um novo token
- Tokens expiram em 2 horas por padrão

### ❌ Erro de CORS no navegador

**Solução:**
- Certifique-se de que a origem do frontend está em `SecurityConfig.java`
- Limpe o cache do navegador (Ctrl+Shift+Del)
- Tente em modo anônimo

### ❌ "Port 8080 already in use"

**Solução:**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [número] /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### ❌ Maven não reconhecido

**Solução:**
Use o wrapper incluído no projeto:
```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

## 📈 Melhorias Futuras

- [ ] Implementar paginação nos listagens
- [ ] Adicionar upload de fotos de agricultores
- [ ] Criar aplicativo mobile para confirmação de entregas
- [ ] Dashboard com gráficos (Chart.js)
- [ ] Notificações push em tempo real (WebSocket)
- [ ] Integração com APIs de rastreamento (Correios, transportadoras)
- [ ] Sistema de chat interno
- [ ] Relatórios em Excel (além de PDF)
- [ ] Modo escuro (Dark Mode)
- [ ] Internacionalização (i18n) - Português/Inglês

## 📄 Licença

Este projeto é de uso educacional para fins acadêmicos.

## 👥 Equipe de Desenvolvimento

Projeto desenvolvido como trabalho integrador do curso de Análise e Desenvolvimento de Sistemas.

Equipe:
Jorge Antonio,
Lucas Souza,
Luiz Eduardo,
Kauan Nicolas,
Matheus Paulo e 
Vínicius Trezena

**Desenvolvido com ❤️ para conectar distribuidores e agricultores através da tecnologia**

🌾 ConectaHub - Semeando o futuro digital do agronegócio
