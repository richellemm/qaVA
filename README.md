# API de Gerenciamento de Itens - Projeto QA (VA 1)

Este projeto consiste em uma API REST desenvolvida com **Spring Boot** para o gerenciamento de itens, estendida para atender aos requisitos de Qualidade de Software. Foram implementadas novas regras de negócio e uma suíte de testes unitários para garantir a integridade dos dados e o tratamento correto de exceções.

---

## 🛠️ Novos Endpoints e Regras de Negócio

Foram adicionados dois novos endpoints no `ApiController` para validar cenários específicos de uso:

### 1. Atualização de Estoque (Stock)
* **Rota:** `PATCH /api/items/{id}/stock`
* **Regra de Negócio:** O sistema não permite que o estoque seja atualizado com valores negativos.
* **Comportamento:** * Se `quantity >= 0`: Atualiza o item e retorna **200 OK**.
    * Se `quantity < 0`: Lança `InvalidItemDataException` e retorna **400 Bad Request**.

### 2. Validação de Nome (Validate)
* **Rota:** `POST /api/items/validate`
* **Regra de Negócio:** O nome do item não pode ser nulo, vazio ou conter apenas espaços em branco.
* **Comportamento:**
    * Se nome for válido: Retorna **200 OK** com a mensagem "Nome válido".
    * Se nome for inválido: Lança `InvalidItemDataException` e retorna **400 Bad Request**.

---

## 🧪 Suíte de Testes Unitários

A cobertura de testes foi implementada utilizando **JUnit 5**, **Mockito** e **MockMvc**, focando em garantir que as regras de negócio sejam respeitadas.

### Casos de Teste Criados:

| Teste | Objetivo | Resultado Esperado |
| :--- | :--- | :--- |
| `whenUpdateStock_withValidQuantity` | Validar atualização de estoque positiva. | **200 OK** |
| `whenUpdateStock_withNegativeQuantity` | Validar bloqueio de estoque negativo. | **400 Bad Request** |
| `whenValidateItemName_withValidName` | Validar aceitação de nomes preenchidos. | **200 OK** |
| `whenValidateItemName_withEmptyName` | Validar bloqueio de nomes vazios/espaços. | **400 Bad Request** |

---

## 📊 Como Executar o Projeto

### Pré-requisitos
* Java 17 ou superior.
* Maven instalado (ou extensão Maven no VS Code).

### Executar Testes e Gerar Relatório de Cobertura
Para rodar todos os testes, utilize o Testing da sua IDE;

## 📊 Relatório de Cobertura (JaCoCo)

Para fins de avaliação de **Qualidade de Software**, foi gerado o relatório de cobertura de código. O print abaixo demonstra que os novos endpoints e suas respectivas regras de exceção foram 100% cobertos pelos testes unitários.

### Evidência de Cobertura:
> Ferramenta da IDE

![Test Coverage 1](java-rest-api\imagens\testCoverage-endpoints.png)

![Test Coverage 2](java-rest-api\imagens\testCoverage-invalidDataException.png)


![Test Coverage 3](java-rest-api\imagens\testCoverage-novosTestes.png)

