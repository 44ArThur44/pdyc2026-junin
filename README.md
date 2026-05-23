# Event & Artist Management API (Spring Boot + Keycloak OAuth2)

## 1. PROJECT OVERVIEW
Event and Artist management backend secured with Keycloak OAuth2 (JWT). All endpoints require authentication.

## 2. TECH STACK
- Java 17
- Spring Boot 3.5.14
- PostgreSQL 16
- Keycloak 24
- OAuth2 / JWT

## 3. QUICK START
```sh
# 1. Start Keycloak
cd keycloak/
docker-compose up -d

# 2. Configure Keycloak
# - Create realm: unnoba
# - Create client: pdyc (confidential, enable standard flow)
# - Create user: testuser

# 3. Update backend
# Set client secret in src/main/resources/application.properties

# 4. Run backend
./mvnw spring-boot:run

# 5. Get token (Postman, OAuth2 Authorization Code)
# 6. Test endpoints (see below)
```

## 4. ENDPOINTS TABLE
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET    | /artists | List artists | JWT |
| POST   | /artists | Create artist | JWT |
| GET    | /events  | List events | JWT |
| POST   | /events  | Create event | JWT |
| GET    | /admin/users | List Keycloak users | JWT |
| POST   | /admin/users | Create user | JWT |
| DELETE | /admin/users/{id} | Delete user | JWT |

## 5. CONFIGURATION EXPLAINED
- **SecurityConfig**: requires JWT on all endpoints
- **JwtConfig**: validates tokens via issuer-uri
- **KeycloakAdminConfig**: client_credentials grant for backend-to-Keycloak
- **AdminUserController**: async CRUD with CompletableFuture

## 6. KEY FILES STRUCTURE
```
src/main/java/.../
├── config/
│   ├── SecurityConfig.java
│   ├── JwtConfig.java
│   └── KeycloakAdminConfig.java
├── controller/
│   ├── ArtistController.java (existing)
│   ├── EventController.java (existing)
│   └── AdminUserController.java (new)
└── ...
```

## 7. HOW TO TEST IN POSTMAN
- OAuth 2.0 → Get New Access Token
- Grant: Authorization Code
- Callback: https://oauth.pstmn.io/v1/callback
- Auth URL: http://localhost:8080/realms/unnoba/protocol/openid-connect/auth
- Token URL: http://localhost:8080/realms/unnoba/protocol/openid-connect/token
- Client ID: pdyc
- Scope: openid

## 8. TROUBLESHOOTING
- 401 → invalid/missing token
- 403 → user lacks role
- Connection refused → Keycloak not running on port 8080
- Token expired → get new token in Postman
