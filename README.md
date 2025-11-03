# Grupo

- Daniel da Silva Barros | RM 556152
- Luccas de Alencar Rufino | RM 558253
- Raul Clauson | RM 555006

---

# 🏍️ Sistema Mottu Vision - Backend (Sprint 4)

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)
![Oracle](https://img.shields.io/badge/Oracle-19c-red?logo=oracle)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?logo=apachemaven)
![License](https://img.shields.io/badge/License-FIAP-yellow)

Sistema backend completo para gerenciamento de pátio de motos, desenvolvido com **Spring Boot**, oferecendo:
- **API REST** para aplicativo mobile (React Native)
- **Interface Web** com Thymeleaf para administração
- **Integração com PL/SQL** via stored procedures
- **Autenticação e Autorização** com Spring Security

---

## 📑 Índice

- [🚀 Tecnologias e Dependências](#-tecnologias-e-dependências)
- [🗄️ Estrutura do Banco de Dados](#%EF%B8%8F-estrutura-do-banco-de-dados)
- [🏗️ Arquitetura do Sistema](#%EF%B8%8F-arquitetura-do-sistema)
- [🔐 Usuários de Teste](#-usuários-de-teste)
- [🚀 Como Executar](#-como-executar-1)
- [📡 API REST Endpoints](#-api-rest-endpoints)
  - [Motos](#motos)
  - [Essenciais](#essenciais-dados-de-referência)
  - [Relatório via Procedure ](#relatório-via-stored-procedure-)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🔧 Fluxos de Negócio](#-fluxos-de-negócio-implementados)
- [🛡️ Segurança e Autenticação](#%EF%B8%8F-segurança-e-autenticação)
- [📱 Integração com Mobile](#-integração-com-mobile-app)
- [⚡ Integração com PL/SQL](#-integração-com-plsql-stored-procedures)
- [📊 Dados Iniciais](#-dados-iniciais-seed-data)
- [🐛 Troubleshooting](#-troubleshooting)
- [🎯 Funcionalidades Principais](#-funcionalidades-principais)
- [📝 Notas de Versão](#-notas-de-versão)

---

## 🚀 Tecnologias e Dependências

### Core Framework
- **Spring Boot 3.5.6** - Framework principal
- **Java 17** - Linguagem de programação
- **Maven** - Gerenciamento de dependências

### Segurança e Dados
- **Spring Security** - Autenticação e autorização baseada em roles
- **Spring Data JPA** - Persistência de dados
- **Oracle JDBC Driver (ojdbc11)** - Conexão com Oracle Database

### Frontend Web
- **Thymeleaf** - Template engine
- **Thymeleaf Layout Dialect** - Layouts reutilizáveis
- **Thymeleaf Spring Security** - Integração com segurança
- **Bootstrap 5** - Framework CSS (via CDN)

### Banco de Dados
- **Flyway 9.22.3** - Versionamento e migração de schema
- **Oracle Database 19c** - Banco de dados principal

## 🗄️ Estrutura do Banco de Dados

### Tabelas Principais
- `usuario` - Usuários do sistema com roles
- `moto` - Registro das motos
- `patio` - Pátios de armazenamento
- `zona` - Zonas dentro dos pátios
- `status` - Status das motos
- `status_grupo` - Grupos de status

### Relacionamentos
- Moto → Zona (ManyToOne)
- Moto → Pátio (ManyToOne)
- Moto → Status (ManyToOne)
- Status → StatusGrupo (ManyToOne)

### Stored Procedures (PL/SQL)
- **`PKG_MOTO.exibir_json`** - Lista motos em formato JSON via DBMS_OUTPUT
- **`PKG_MOTO.converter_para_json(id)`** - Converte uma moto específica para JSON
- **`PKG_MOTO.calcular_totais_servico`** - Calcula totais por zona
- **`obter_relatorio_completo(p_cursor OUT SYS_REFCURSOR)`** - Retorna relatório completo via REF CURSOR (usado pela API REST)

### Migrations Flyway
- `V1__create_tables.sql` - Criação inicial das tabelas
- `V1.1__create_tables_after_baseline.sql` - Ajustes pós-baseline
- `V2__seed_users.sql` - Usuários de teste
- `V3__seed_status_grupos.sql` - Grupos de status
- `V4__seed_status_and_data.sql` - Status e dados iniciais
- `V5__update_operador_role.sql` - Ajuste de permissões do operador

## 🏗️ Arquitetura do Sistema

### Dual Architecture (API REST + Web)

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENTES                                 │
├──────────────────────────┬──────────────────────────────────┤
│   Mobile App             │   Web Browser                    │
│   (React Native)         │   (Thymeleaf)                    │
└────────────┬─────────────┴──────────────┬───────────────────┘
             │                            │
             │ REST API                   │ HTTP/HTML
             │                            │
┌────────────▼────────────────────────────▼───────────────────┐
│              SPRING BOOT APPLICATION                        │
├─────────────────────────────────────────────────────────────┤
│  REST Controllers        │  Thymeleaf Controllers           │
│  - MotoRestController    │  - MotoController                │
│  - EssenciaisRest...     │  - PatioController               │
│  - RelatorioRest...      │  - StatusController              │
│                          │  - ZonaController                │
├─────────────────────────────────────────────────────────────┤
│                    Services Layer                           │
│  - MotoMapperService (DTO conversions)                      │
│  - MotoProcedureService (SQL procedures)                    │
├─────────────────────────────────────────────────────────────┤
│                    Data Layer                               │
│  - JPA Repositories (CRUD)                                  │
│  - JDBC Template (Procedures)                               │
└────────────┬────────────────────────────────────────────────┘
             │
┌────────────▼────────────────────────────────────────────────┐
│              ORACLE DATABASE 19c                            │
│  - Tables (moto, status, zona, patio, usuario)              │
│  - Packages (PKG_MOTO, PKG_USUARIO, PKG_AUDITORIA)          │
│  - Procedures (obter_relatorio_completo)                    │
└─────────────────────────────────────────────────────────────┘
```

## 🔐 Usuários de Teste

| Usuário | Senha | Role | Permissões |
|---------|-------|------|------------|
| admin | password | ROLE_ADMIN | Todas as operações (CRUD completo) |
| operador | password | ROLE_OPERADOR | Movimentar motos e alterar status |
| user | password | ROLE_USER | Apenas visualização |

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Oracle Database 

### 1. Configuração do Banco de Dados

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configuração do banco Oracle
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=SeuUsuario
spring.datasource.password=SuaSenha
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver

# Configuração JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect

# Configuração Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

### 3. Execução

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd sprint-3-java

# Compile e execute
mvn clean install
mvn spring-boot:run
```

### 4. Acesso

#### Interface Web (Thymeleaf)
- **URL**: http://localhost:8080
- **Login**: admin / password (para acesso completo)
- **Login**: operador / password (para mudanças de status e zonas)
- **Login**: user / password (para visualização)

#### API REST (Mobile)
- **Base URL**: http://localhost:8080/api
- **Autenticação**: Spring Security (session-based)
- **Formato**: JSON

---

## 📡 API REST Endpoints

### Motos

| Método | Endpoint | Descrição | Body/Params |
|--------|----------|-----------|-------------|
| `GET` | `/api/motos/json` | Lista todas as motos com detalhes completos | - |
| `GET` | `/api/motos/json/{id}` | Busca moto por ID | - |
| `POST` | `/api/motos/json` | Cria nova moto | `MotoCreateRequest` |
| `PUT` | `/api/motos/json/{id}` | Atualiza moto existente | `MotoUpdateRequest` |
| `DELETE` | `/api/motos/json/{id}` | Remove moto por ID | - |



## 📁 Estrutura do Projeto

```
sprint-3-java/
├── src/
│   ├── main/
│   │   ├── java/com/mottu/sprint3/
│   │   │   ├── config/                    # Configurações
│   │   │   │   ├── SecurityConfig.java    # Spring Security config
│   │   │   │   └── WebConfig.java         # CORS e configurações web
│   │   │   │
│   │   │   ├── controller/                # Controllers
│   │   │   │   ├── [REST - Mobile API]
│   │   │   │   │   ├── MotoRestController.java          # CRUD motos (API)
│   │   │   │   │   ├── EssenciaisRestController.java    # Dados essenciais
│   │   │   │   │   └── RelatorioRestController.java     # Relatório 
│   │   │   │   │
│   │   │   │   ├── [Thymeleaf - Web]
│   │   │   │   │   ├── MainController.java              # Dashboard
│   │   │   │   │   ├── LoginController.java             # Login page
│   │   │   │   │   ├── MotoController.java              # CRUD motos (web)
│   │   │   │   │   ├── MotoMovimentacaoController.java  # Movimentação
│   │   │   │   │   ├── PatioController.java             # CRUD pátios
│   │   │   │   │   ├── StatusController.java            # CRUD status
│   │   │   │   │   ├── StatusGrupoController.java       # CRUD grupos
│   │   │   │   │   ├── ZonaController.java              # CRUD zonas
│   │   │   │   │   └── CustomErrorController.java       # Página de erro
│   │   │   │
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   │   ├── request/               # DTOs de entrada (API)
│   │   │   │   │   ├── MotoCreateRequest.java
│   │   │   │   │   ├── MotoUpdateRequest.java
│   │   │   │   │   ├── IdentificadorRequest.java
│   │   │   │   │   ├── ModeloRequest.java
│   │   │   │   │   ├── PatioRequest.java
│   │   │   │   │   ├── StatusRequest.java
│   │   │   │   │   ├── StatusRequestUpdate.java
│   │   │   │   │   ├── ZonaRequest.java
│   │   │   │   │   └── ZonaRequestUpdate.java
│   │   │   │   │
│   │   │   │   ├── response/              # DTOs de saída (API)
│   │   │   │   │   ├── MotoResponse.java
│   │   │   │   │   ├── EssenciaisResponse.java
│   │   │   │   │   ├── RelatorioResponse.java            # Relatório principal
│   │   │   │   │   ├── TotalStatusGrupoResponse.java     # Agregação
│   │   │   │   │   ├── TotalStatusResponse.java          # Agregação
│   │   │   │   │   ├── ZonasRelatorioResponse.java       # Por zona
│   │   │   │   │   ├── IdentificadorResponse.java
│   │   │   │   │   ├── ModeloResponse.java
│   │   │   │   │   ├── PatioResponse.java
│   │   │   │   │   ├── StatusResponse.java
│   │   │   │   │   ├── StatusGrupoResponse.java
│   │   │   │   │   └── ZonaResponse.java
│   │   │   │   │
│   │   │   │   └── [DTOs Thymeleaf]       # DTOs para web
│   │   │   │       ├── MotoDto.java
│   │   │   │       ├── PatioDto.java
│   │   │   │       ├── StatusDto.java
│   │   │   │       ├── StatusGrupoDto.java
│   │   │   │       └── ZonaDto.java
│   │   │   │
│   │   │   ├── model/                     # Entidades JPA
│   │   │   │   ├── Moto.java
│   │   │   │   ├── Patio.java
│   │   │   │   ├── Status.java
│   │   │   │   ├── StatusGrupo.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Zona.java
│   │   │   │
│   │   │   ├── repository/                # Repositórios JPA
│   │   │   │   ├── MotoRepository.java
│   │   │   │   ├── PatioRepository.java
│   │   │   │   ├── StatusRepository.java
│   │   │   │   ├── StatusGrupoRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── ZonaRepository.java
│   │   │   │
│   │   │   ├── service/                   # Serviços
│   │   │   │   ├── MotoMapperService.java        # Conversão DTOs
│   │   │   │   ├── MotoProcedureService.java     # SQL Procedures ⭐
│   │   │   │   └── UserDetailsServiceImpl.java   # Autenticação
│   │   │   │
│   │   │   └── util/
│   │   │       └── DateUtils.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties     # Configuração principal
│   │       ├── db/migration/              # Scripts Flyway (versionamento)
│   │       │   ├── V1__create_tables.sql
│   │       │   ├── V1.1__create_tables_after_baseline.sql
│   │       │   ├── V2__seed_users.sql
│   │       │   ├── V3__seed_status_grupos.sql
│   │       │   ├── V4__seed_status_and_data.sql
│   │       │   └── V5__update_operador_role.sql
│   │       ├── procedures/                # Stored Procedures
│   │       │   └── relatorio_procedure.sql        # obter_relatorio_completo ⭐
│   │       ├── static/                    # CSS, JS
│   │       │   ├── css/
│   │       │   └── js/
│   │       └── templates/                 # Thymeleaf HTML
│   │           ├── login.html
│   │           ├── dashboard.html
│   │           ├── error.html
│   │           └── layout/
│   │               └── layout.html
│   │
│   └── test/                              # Testes
│       └── java/com/mottu/sprint3/
│
└── pom.xml                                # Maven dependencies
```

## 🔧 Fluxos de Negócio Implementados

### 1. Movimentação de Motos
- **Endpoint**: `POST /moto/move`
- **Validações**:
  - Não pode mover para o mesmo local
  - Motos entregues não podem ser movidas
  - Motos em reparo têm restrições específicas
- **Interface**: Modal com seleção de pátio e zona

### 2. Alteração de Status
- **Endpoint**: `POST /moto/change-status`
- **Validações**:
  - Não pode alterar para o mesmo status
  - Motos entregues não podem ter status alterado
  - Motos em reparo só podem ir para status específicos
- **Interface**: Modal com seleção de novo status

## 🛡️ Segurança e Autenticação

### Spring Security Configuration

#### Roles e Hierarquia
- **ROLE_ADMIN**: Acesso total (CRUD + API + Web)
- **ROLE_OPERADOR**: Operações de movimentação e alteração de status
- **ROLE_USER**: Apenas visualização

#### Proteção de Rotas Web (Thymeleaf)
```java
// ADMIN - CRUD completo
.requestMatchers("/moto/save", "/patio/save", "/zona/save", 
                 "/status/save", "/status-grupo/save").hasRole("ADMIN")
.requestMatchers("/moto/delete/**", "/patio/delete/**").hasRole("ADMIN")

// ADMIN e OPERADOR - Movimentação e status
.requestMatchers("/moto/move/**", "/moto/change-status/**")
    .hasAnyRole("ADMIN", "OPERADOR")

// Todos autenticados - Dashboard
.requestMatchers("/", "/dashboard").authenticated()
```

#### Proteção de Rotas API (REST)
```java
// API REST endpoints
.requestMatchers("/api/**").authenticated()
```

#### CORS Configuration
```java
// Configurado para aceitar requisições do mobile app
allowedOrigins: "*"
allowedMethods: GET, POST, PUT, DELETE, OPTIONS
```

## 🧪 Validações Implementadas

### DTOs com Validações
- **MotoDto**: Pelo menos um campo de identificação (placa/chassi/QR)
- **PatioDto**: Nome obrigatório (2-100 caracteres)
- **ZonaDto**: Nome obrigatório + letra única maiúscula
- **StatusDto**: Nome obrigatório + grupo obrigatório
- **StatusGrupoDto**: Nome obrigatório (2-100 caracteres)

### Tratamento de Erros
- Validações são tratadas com `BindingResult`
- Mensagens de erro/sucesso via `RedirectAttributes`
- Feedback visual nos formulários

## 📱 Integração com Mobile App

### Arquitetura de Comunicação
O backend foi projetado para servir simultaneamente:
1. **Interface Web** (Thymeleaf) - Para administração interna
2. **API REST** (JSON) - Para aplicativo mobile React Native

### Fluxo de Dados Mobile → Backend
```
Mobile App (React Native)
  ↓ HTTP/JSON
Backend API REST (/api/*)
  ↓ Service Layer
  ├─ MotoMapperService → Conversão DTOs
  ├─ MotoProcedureService → SQL Procedures ⭐
  └─ JPA Repositories → CRUD
  ↓
Oracle Database
```

### Características da API REST
- ✅ **Endpoints RESTful** seguindo padrões HTTP
- ✅ **DTOs Nested** para estruturas complexas
- ✅ **Autenticação Session-based** com Spring Security
- ✅ **CORS habilitado** para desenvolvimento mobile
- ✅ **Respostas JSON** com estrutura consistente

---

## ⚡ Integração com PL/SQL (Stored Procedures)

### MotoProcedureService ⭐

Service especializado que executa procedures SQL via `JdbcTemplate`:

```java
@Service
public class MotoProcedureService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Chama procedure que retorna REF CURSOR
    public RelatorioResponse obterRelatorioViaProcedure() {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall(
                "{call obter_relatorio_completo(?)}"
            );
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();
            
            // Processa ResultSet e constrói RelatorioResponse
            ResultSet rs = (ResultSet) cs.getObject(1);
            // ... agregação em memória
            
            return response;
        });
    }
}
```

### Benefícios da Procedure
- ⚡ **Performance**: 1 query vs múltiplas (4+ queries antes)
- 📊 **Processamento no Banco**: Oracle faz joins e agregações
- 🔄 **Manutenibilidade**: Lógica centralizada no banco
- ✅ **Demonstração**: Integração Java + PL/SQL

### Como Executar a Procedure Manualmente
```sql
-- No Oracle SQL Developer
CREATE OR REPLACE PROCEDURE obter_relatorio_completo(
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT 
            m.id,
            m.placa,
            m.chassi,
            m.qr_code,
            TO_CHAR(m.data_entrada, 'YYYY-MM-DD"T"HH24:MI:SS') as data_entrada,
            TO_CHAR(m.previsao_entrega, 'YYYY-MM-DD"T"HH24:MI:SS') as previsao_entrega,
            m.fotos,
            m.observacoes,
            m.valor_servico,
            m.modelo,
            s.id as status_id,
            s.nome as status_nome,
            s.cor as status_cor,
            sg.id as status_grupo_id,
            sg.nome as status_grupo_nome,
            sg.descricao as status_grupo_descricao,
            z.id as zona_id,
            z.nome as zona_nome,
            z.letra as zona_letra,
            p.id as patio_id,
            p.nome as patio_nome,
            p.capacidade as patio_capacidade
        FROM moto m
        JOIN status s ON m.status_id = s.id
        JOIN status_grupo sg ON s.status_grupo_id = sg.id
        JOIN zona z ON m.zona_id = z.id
        JOIN patio p ON m.patio_id = p.id
        ORDER BY m.id;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Nenhuma moto encontrada');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erro ao gerar relatório: ' || SQLERRM);
END obter_relatorio_completo;
/
```

---

## 📊 Dados Iniciais (Seed Data)

O sistema é populado automaticamente via Flyway migrations:

### Usuários (V2__seed_users.sql)
- **admin** / password - ROLE_ADMIN
- **operador** / password - ROLE_OPERADOR
- **user** / password - ROLE_USER

### Grupos de Status (V3__seed_status_grupos.sql)
1. Entrada
2. Processamento
3. Saída
4. Manutenção
5. Aguardando

### Status por Grupo (V4__seed_status_and_data.sql)
- **Entrada**: Recebida, Registrada
- **Processamento**: Em Inspeção, Em Avaliação, Documentação Pendente, Em Avaliação Técnica, Aguardando Peças
- **Saída**: Pronta para Entrega, Em Rota de Entrega, Entregue
- **Manutenção**: Necessita Reparo, Em Reparo
- **Aguardando**: Aguardando Cliente, Aguardando Documentos, Aguardando Aprovação

### Zonas e Pátios (V4)
- **4 Zonas**: Norte (N), Sul (S), Leste (L), Oeste (O)
- **4 Pátios**: Principal, Secundário, Manutenção, Expedição
- **Motos de Exemplo**: 5 motos com diferentes status e localizações

## 🐛 Troubleshooting

### Problemas Comuns

#### 1. Erro de Conexão com Oracle
```bash
# Verifique se o Oracle está rodando
lsnrctl status

# Teste conexão
telnet localhost 1521

# Verifique credenciais no application.properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

#### 2. Erro de Migração Flyway
```bash
# Flyway não consegue rodar migrations
# Solução: Limpe o schema e rode novamente
DROP USER seu_usuario CASCADE;
CREATE USER seu_usuario IDENTIFIED BY senha;
GRANT CONNECT, RESOURCE TO seu_usuario;

# No application.properties, habilite baseline
spring.flyway.baseline-on-migrate=true
```

#### 3. Porta 8080 Já em Uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /F /PID <PID>

# Linux/Mac
lsof -ti:8080 | xargs kill -9

# Ou configure outra porta no application.properties
server.port=8081
```

#### 4. API REST Retorna 500 (Erro no Relatório)
```bash
# Verifique se a procedure foi criada
SELECT object_name, status 
FROM user_objects 
WHERE object_name = 'OBTER_RELATORIO_COMPLETO';

# Se não existir, execute:
@src/main/resources/procedures/relatorio_procedure.sql
```

#### 5. Mobile Não Conecta na API
```bash
# Verifique CORS no WebConfig.java
# Verifique IP no mobile (api.ts):
baseURL: 'http://SEU_IP:8080/api/'

# Teste endpoint direto no navegador:
http://localhost:8080/api/motos/json
```

#### 6. Erro de Compilação (DTOs)
```bash
# Se aparecer erro de classes públicas em arquivo único:
# Certifique-se que TotalStatusGrupoResponse, TotalStatusResponse 
# e ZonasRelatorioResponse estão em arquivos separados
```

### Logs Úteis
```properties
# application.properties - Ativar logs detalhados

# SQL queries
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Flyway migrations
logging.level.org.flywaydb=DEBUG

# Aplicação
logging.level.com.mottu.sprint3=DEBUG

# Spring Security
logging.level.org.springframework.security=DEBUG
```

---

## 🎯 Funcionalidades Principais

### ✅ Implementado

| Feature | API REST | Web (Thymeleaf) | Observações |
|---------|----------|-----------------|-------------|
| **CRUD Motos** | ✅ | ✅ | Completo com validações |
| **CRUD Status/Grupos** | ❌ | ✅ | Apenas web |
| **CRUD Zonas** | ❌ | ✅ | Apenas web |
| **CRUD Pátios** | ❌ | ✅ | Apenas web |
| **Movimentação de Motos** | ✅ | ✅ | Web apenas |
| **Alteração de Status** | ✅ | ✅ | Ambos |
| **Relatório Completo** | ✅  | ✅ | Via procedure SQL |
| **Dados Essenciais** | ✅ | ❌ | Para dropdowns mobile |
| **Autenticação** | ✅ | ✅ | Spring Security |
| **Dashboard** | ❌ | ✅ | Visualização web |

---

## 🔑 Tecnologias Chave

### Backend
- **Spring Boot 3.5.6** - Framework base
- **Spring Data JPA** - ORM para entities
- **JdbcTemplate** - Para stored procedures
- **Spring Security** - Autenticação/Autorização
- **Thymeleaf** - Template engine
- **Flyway** - Database migrations

### Database
- **Oracle Database 19c** - RDBMS principal
- **PL/SQL Packages** - Procedures e functions
- **REF CURSOR** - Retorno de resultsets

### Integrações
- **React Native App** - Cliente mobile
- **REST API** - Comunicação JSON
- **JDBC Driver** - Conexão Oracle

---

## 📚 Documentação Adicional

### Scripts Úteis
```bash
# Compilar sem rodar testes
mvn clean compile

# Executar apenas testes
mvn test

# Gerar JAR
mvn package

# Executar JAR gerado
java -jar target/sprint3-0.0.1-SNAPSHOT.jar

# Skip tests e compilar
mvn clean install -DskipTests
```

### Configurações Importantes
- **Port**: 8080 (configurável em `application.properties`)
- **Context Path**: `/` (raiz)
- **Session Timeout**: 30 minutos
- **Max File Upload**: 10MB
- **Database Pool**: HikariCP (padrão Spring Boot)

---

## 👥 Equipe

- **Daniel da Silva Barros** | RM 556152
- **Luccas de Alencar Rufino** | RM 558253  
- **Raul Clauson** | RM 555006

---

## 📝 Notas de Versão

### Sprint 4 (Atual)
- ✅ API REST completa para mobile
- ✅ Integração com stored procedure SQL
- ✅ DTOs nested para estruturas complexas
- ✅ Relatório via REF CURSOR (alta performance)
- ✅ CORS configurado para mobile
- ✅ Arquitetura dual (API + Web)

### Sprint 3
- Interface web Thymeleaf
- CRUD completo de entidades
- Spring Security com roles
- Flyway migrations
- Validações de negócio

---

**Desenvolvido com muito ☕ e ❤️ para o curso Java Advanced - FIAP**  
**Projeto**: Sistema Mottu Vision - Gerenciamento de Pátio de Motos  
**Stack**: Spring Boot + Oracle + React Native  
**Ano**: 2025
