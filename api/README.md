# Social-Network-API
Api desenvolvida com o intuito de ser utilizada como controlador de redes sociais

&nbsp;

&nbsp;

## Docker

### Recursos para Desenvolvimento
Rodando Redis no docker para testes de desenvolvimento
```shell script
sudo docker run -p 6379:6379 redis 
```

## Consul
Rodando
> docker run -d --name consul -p 8500:8500 -p 8600:8600 consul

Configuração:
```yaml
consul:
  # Optional properties
  # endpoint for consul (defaults to localhost:8500)
  endpoint: localhost:8500
  # service port
  servicePort: 8080
  # check interval frequency
  checkInterval: 1 second
```
>Acesse http://localhost:8500/


### Deploy
Build da imagem
```shell script
sudo docker build -t araujo/sna:latest .
```

 

&nbsp;

&nbsp;
## Endpoints

#### **POST** */social-network*
Adiciona uma rede social

Payload:
```json
{
  "name": "Twitter"
}
```
*Retorno:*
* 200: Rede social criada
* 409: Rede social já existente
```json
{
  "id": 1,
  "name": "Twitter"
}
```

&nbsp;
#### **GET** */social-network/{name}*
Busca uma rede social

*Retorno:*
* 200: Rede social encontrada
* 404: Rede social não encontrada
```json
{
  "id": 1,
  "name": "Twitter"
}
```

&nbsp;
### User

#### **POST** */social-network/{name}/user*
Adiciona um usuário à rede social

Payload:
```json
{
  "name": "Mussum"
}
```
*Retorno:*
* 201: Usuário criado
* 404: Rede social não encontrada
* 409: Usuário já existente
```json
{
  "id": 8,
  "name": "Mussum",
  "creation-date": "2019-07-28 12:05:10.887"
}
```

&nbsp;
#### **GET** */social-network/{name}/user*
Lista todos os usuários da rede social

*Retorno:*
* 200: Lista encontrada
* 404: Rede social não encontrada
```json
[
    {
      "id": 8,
      "name": "Mussum",
      "creation-date": "2019-07-28 12:05:10.887"
    },
    {
      "id": 9,
      "name": "Didi",
      "creation-date": "2019-07-28 12:15:26.021"
    }
]
```

&nbsp;
#### **GET** */social-network/{name}/user/{id}*
Busca usuário na rede social

*Retorno:*
* 200: Usuário encontrado
* 404: Rede social não encontrada
* 404: Usuário não encontrado
```json
{
  "id": 8,
  "name": "Mussum",
  "creation-date": "2019-07-28 12:05:10.887"
}
```

&nbsp;
### POSTs

#### **POST** */social-network/{name}/user/{id}/post*
Adiciona um post à rede social
Payload:
```json
{
    "text": "Hojis foi incrívis"
}
```
*Retorno:*
* 201: Criada
* 404: Rede social não encontrada
* 404: Usuário não encontrado
```json
{
    "id": 99,
    "creation-date": "2019-07-28 13:32:56.876",
    "social-network": {
      "id": 1,
      "name": "Twitter"
    }
}
```

&nbsp;
#### **PUT** */social-network/{name}/user/{id}/post/{id}*
Atualiza um post na rede social
Payload:
```json
{
    "text": "Hojis foi incrívis"
}
```
*Retorno:*
* 200: Atualizado
* 404: Rede social não encontrada
* 404: Usuário não encontrado
* 404: Post não encontrado
```json
{
    "id": 99,
    "creation-date": "2019-07-28 13:32:56.876",
    "social-network": {
      "id": 1,
      "name": "Twitter"
    }
}
```

&nbsp;
#### **GET** */social-network/{name}/user/{id}/post/{id}*
Busca um post específico do usuário

*Retorno:*
* 200: Encontrado
* 404: Rede social não encontrada
* 404: Usuário não encontrado
* 404: Post não encontrado
```json
{
    "id": 99,
    "user": 88,
    "userName": "Mussum",
    "text": "Lorem ipsum"
}
```

&nbsp;
#### **DELETE** */social-network/{name}/user/{id}/post/{id}*
Remove um post da rede social

Payload:
```json
{
    "id": 99
}
```
Retorno:
* 200: Removido
* 404: Rede social não encontrada
* 404: Usuário não encontrado
* 404: Post não encontrado
```json
{
    "id": 99
}
```

&nbsp;
#### **GET** */social-network/{name}/user/{id}/post*
Busca todos os posts do usuário

Retorno:
* 200: Listagem encontrada
* 404: Rede social não encontrada
* 404: Usuário não encontrado
```json
[
    {
        "id": 92,
        "time": "2019-07-28 14:45:50.987",
        "text": "Lorem ipsum"
    },
    {
        "id": 93,
        "time": "2019-07-28 14:50:51.012",
        "text": "Lorem ipsum thanos"
    }
]
```

&nbsp;

&nbsp;

## Future Releases
#### **DELETE** */social-network/{id}/user/{id}*
Desativa um usuário existente
* 200: Usuário removido
* 404: Usuário não encontrado
```json
{
  "id": 8,
  "name": "Mussum",
  "creation-date": "2019-07-28 12:05:10.887",
  "deactivate-date": "2019-07-28 15:12:45.512"
}
```

&nbsp;
#### **DELETE** */social-network/{name}*
Remove uma rede social existente

*Retorno:*
* 200: Rede social removida
* 404: Rede social não encontrada
```json
{
  "id": 1,
  "name": "Twitter"
}
```
