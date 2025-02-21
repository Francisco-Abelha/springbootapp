# Projecto de Referência de SpringBoot

## O que fazer

Este projecto serve para verificar que todas as dependências necessárias ao projecto estão a funcionar.

## Dependências

Este projecto vai usar Java17+ e Postgres, mas esses vão estar disponíveis dentro dos containers Docker. Só são necessários se quiser correr a aplicação nativamente.

### Windows

Instalar [Docker Desktop](https://docs.docker.com/desktop/install/windows-install/) e [Python 3](https://www.python.org/ftp/python/3.11.2/python-3.11.2-amd64.exe)

### Ubuntu

```
sudo apt-get install python3 python3-pip
```

Seguir [esta página](https://docs.docker.com/engine/install/ubuntu/) e os quatro passos listados [aqui](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user).

### Fedora

```
sudo dnf python3 python3-pip
```

Seguir [esta página](https://docs.docker.com/engine/install/fedora/) e os quatro passos listados [aqui](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user).


## Primeiro passo - clonar o repositorio

Faça clone do repositório (`code -> Clone with HTTPS -> Ctrl+C`)
No terminal: `git clone URLDOREPO`

## Segundo passo - executar o projeto

Deve correr os comandos listados no ficheiro `setup.sh`.

Deve correr `run.sh`.

Este comando vai iniciar dois containers:

* Um com a aplicação que existe nesta pasta.
* Uma instância de um container com Postgres

## Terceiro passo - testar o servidor

Abrir http://localhost:8080 num browser e verificar que aparece a string "Olá Mundo".

Pode ainda confirmar que consegue ver um objecto JSON em http://localhost:8080/api/author/1

## Quarto passo - criar um repositório Git

* Crie um novo repositório vazio no GitLab
* Clone o repositório para sua máquina local
* Adicione o código do projeto a pasta
* Faça push para o repositório remoto

## Quinto passo - exploração via Swagger

Abra http://localhost:8080/swagger-ui/index.html num browser para visualizar a documentação interativa da API. Use os endpoints para:

* Verificar os autores presentes.

* Adicionar um novo autor.

* Adicionar um novo livro. Certifique-se de fornecer o ID do autor ao qual o livro deve ser associado.

* Verificar se o livro foi incluído no autor. Consulte novamente o endpoint do autor para confirmar que o novo livro aparece na lista de livros desse autor.

## Atividade Extra

Como sugestão de atividade, experimentem implementar uma funcionalidade extra, por exemplo:

* Criar um endpoint para remover um livro de um autor.
* Adicionar um endpoint para atualizar os detalhes de um livro.
* Desenvolver um filtro para pesquisar livros por título ou autor.
* Os testes são executados automaticamente. Vá a classe de testes no projeto (`src/test/java/pt/ul/fc/css/example/demo/AuthorControllerTest.java`) e perceba o que eles fazem.

# FAQ

## Preciso de `sudo` para correr o `run.sh`
Tenta correr `sudo usermod -aG docker $USER` seguido de um log-out na máquina.
Ou tentar [desta forma](https://www.digitalocean.com/community/questions/how-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket)

## O Docker não instala em ubuntu.

Tentar [desta forma](https://askubuntu.com/a/1411717).

## O `run.sh` não está a correr no meu macos m1.

Tentar correr `docker ps`. Se não funcionar, [tentar isto](https://stackoverflow.com/a/68202428/28516).
Confirmar também que está instalado o Docker Desktop (`brew install --cask docker`) e não apenas a command-line tool (`brew install docker`). A aplicação Docker deve também estar a correr (icon na menubar).


## Estou em windows e o `bash setup.sh` não funciona

Correr numa bash (tanto a Git Bash, MSys2 bash ou WSL em linux) e não na Powershell, nem no CMD.exe.

## `docker compose` não funciona

`docker compose` é o comando da última versão de docker. `docker-compose` é a versão antiga. Devem actualizar o docker.
