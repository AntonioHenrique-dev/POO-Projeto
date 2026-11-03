# POO-Projeto — Sistema de Gestão de Condomínio

## 01 - Módulos iniciais

1. Cadastro de morador
2. Cadastro de unidade / apartamento
3. Cadastro de funcionário
4. Cadastro de visitantes
5. Controle de acesso (entrada e saída)
6. Cadastro de áreas comuns e reservas
7. Gestão de ocorrências
8. Gestão de encomendas
9. Gestão de veículos
10. Relatórios administrativos
11. Gestão de vagas de estacionamento

-------------------------------------------------------------------------------------------------------------------------------------------------------

## 02 - Definição de Personas

### Com acesso ao sistema (login)

| Perfil        | Acesso      | O que faz                                                                 |
|---------------|-------------|---------------------------------------------------------------------------|
| Administrador | Total       | Cadastros, relatórios, configurações                                      |
| Síndico       | Gerencial   | Ocorrências, áreas comuns, relatórios                                     |
| Porteiro      | Operacional | Visitantes, acessos, encomendas, veículos                                 |
| Morador       | Próprio     | Reservar áreas, ver encomendas, autorizar visitantes, abrir ocorrências   |

### Sem login (registrados pelo porteiro ou pelo administrador)

- Visitante
- Entregador
- Faxineiro
- Equipe de manutenção

> **Pet** não é persona, pois não usa o sistema. Passa a ser um dado vinculado ao morador (entidade `PET` no modelo de dados).

-------------------------------------------------------------------------------------------------------------------------------------------------------

## 03 - Diagrama de Casos de Uso

<img width="2603" height="2444" alt="mermaid-diagram-2026-11-02-201716" src="https://github.com/user-attachments/assets/60d5ff07-8a24-4d9f-b4b1-35c6195b2905" />

-------------------------------------------------------------------------------------------------------------------------------------------------------

## 04 - Modelo de dados

### Proposta inicial (v1)

<img width="4187" height="429" alt="mermaid-diagram-2026-10-27-205743" src="https://github.com/user-attachments/assets/a9e54afc-45df-43c3-829f-88a87a0273a8" />

### Proposta revisada (v2)

<img width="6388" height="3024" alt="mermaid-diagram-2026-11-02-201806" src="https://github.com/user-attachments/assets/c73390df-bc20-4bdc-a730-c5e0af220c83" />

### Ajustes em relação à v1

- **Chaves estrangeiras no lugar de texto:** `ap` em Morador virou `unidade_id`; `Morador` em Unidade saiu (a relação fica do lado do morador); `dono_vei` e `numero_ap_donp` em Veículos viraram `morador_id` e `vaga_id`.
- **Ligações que faltavam:** Ocorrência aponta para morador e unidade; Encomenda aponta para unidade e para o funcionário que recebeu; Visitante aponta para o morador que autorizou (`autorizado_por`).
- **Nomes e tipos:** `name` → `nome`; `salario` passou de `float` para `decimal`; padronizado `string` minúsculo; status de Ocorrência deixou de ser `boolean` para permitir mais de dois estados.
- **Reservas:** nova entidade `RESERVA` (morador, área, data e horário).
- **Vagas (módulo 11):** nova entidade `VAGA`, vinculada à unidade e ao veículo.
- **Acesso:** passou a registrar entrada/saída de visitantes e quem registrou.
- **Perfis de login:** nova entidade `USUARIO` com o perfil de acesso (admin, síndico, porteiro, morador).
- **Pet:** saiu das personas e virou entidade ligada ao morador.

-------------------------------------------------------------------------------------------------------------------------------------------------------
