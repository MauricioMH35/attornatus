<p align="center">
    <a href="https://www.attornatus.com.br/" target="_blank">
        <img 
            src="https://raw.githubusercontent.com/MauricioMH35/Assets/7d7f9d0f0198052b753ad582420a67ec98de47f1/Attornatus/attornatus_logo.svg" 
            width="128" 
            alt="Attornatus Logo" />
    </a>
</p>

# AVALIAÇÃO DESENVOLVEDOR BACK-END ATTORNATUS
(DEVELOPMENT)

O objetivo deste documento é identificar seus conhecimentos quanto às tecnologias utilizadas no cotidiano de
desenvolvimento da equipe de Back-end na Attornatus Procuradoria Digital.

Esta análise propõe avaliar os seguintes temas:
- Qualidade de código
- Java, Spring boot
- API REST
- Testes

A entrega deverá ser feita da seguinte forma:
- O prazo para entrega da avaliação será de até 7 dias após envio da mesma
- Encaminhar este documento com as perguntas respondidas e com o link do código público em sua conta do GitHub
- Opcionalmente, caso você consiga fazer o build da aplicação, poderá também informar o link de acesso


## Qualidade de código

1. Durante a implementação de uma nova funcionalidade de software solicitada, quais critérios você avalia e
   implementa para garantia de qualidade de software?

<b>Resposta:</b> <i>Em contrapartida, realizo a verificação dos requisitos, do nível de dificuldade da nova funcionalidade e sua
agregação de valor.</i>

<i>Acredito que custos adicionais devem estar abaixo do que será implementado, por funcionalidades trazerem novos
pontos de manutenções e renovação durante o tempo. (Quanto tempo de vida útil a nova funcionalidade possui, vendo do
ponto de vista do tempo de vida “comercial” que a funcionalidade trará de ganhos e não perdas para a empresa).</i>

<i>Mas vendo do ponto de vista mais técnico, começo a implementar as entidades para visualizar melhor como os
comportamentos serão estabelecidos pelos requisitos, assim, parto para os testes e valido as funcionalidades,
implementando-as.</i>


2. Em qual etapa da implementação você considera a qualidade de software?

<b>Resposta:</b> <i>Em todas as etapas de implementação, pois a qualidade de software deve partir desde a nomeação
de variáveis, funções/métodos e classes, testes unitários, gerenciamento de versão de código, até mesmo ao processo de
validação dos requisitos, se estão sendo atendidos.</i>


## Desafio Java

Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir:
- Criar uma pessoa
- Editar uma pessoa
- Consultar uma pessoa
- Listar pessoas
- Criar endereço para pessoa
- Listar endereços da pessoa
- Poder informar qual endereço é o principal da pessoa

Uma Pessoa deve ter os seguintes campos:
- Nome
- Data de nascimento
- Endereço:
- Logradouro
- CEP
- Número
- Cidade

Requisitos
- Todas as respostas da API devem ser JSON
- Banco de dados H2

Diferencial
- Testes
- Clean Code

Será levado em avaliação
- Estrutura, arquitetura e organização do projeto
- Boas práticas de programação
- Alcance dos objetivos propostos.

## Created on February 16, 2023 | São Paulo - BR
