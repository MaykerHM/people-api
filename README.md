# people-api
Person and contacts register API

### Person Endpoints:

#### GET /persons?name=&document=&birthdateStart=&birthdateEnd=

Example URI:
```
{{base_url}}/person?name=Maria&document=743.064.464-33&birthdateStart=2000-04-11&birthdateEnd=2019-09-19
```

---

#### GET /persons/{id}

Example URI:
```
{{base_url}}/person/808d9973-747b-40c3-ab62-4e8c9297da9c
```

---

#### POST /persons
 
Example request body:
```json
{
    "name": "Paulo",
    "document": "39678653044",
    "birthdate": "2003-04-18",
    "contacts": [
        {
            "name": "Mae",
            "phoneNumber": "99999955656",
            "email": "mae@email.com"
        }
    ]
}
```

---

#### PUT /persons

Example request body:
```json
{
    "id": "808d9973-747b-40c3-ab62-4e8c9297da9c",
    "name": "Paulo",
    "document": "234.120.030-34",
    "birthdate": "2000-10-23"
}
```

---

#### DELETE /persons/{id}

Example URI:
```
{{base_url}}/person/3ddaa562-8b74-49b4-9695-a31ee2ef2af9
```

---


### Contact Endpoints:

#### POST /contacts

Example request body:
```json
{
    "personId": "808d9973-747b-40c3-ab62-4e8c9297da9c",
    "name": "Fulano Teste",
    "phoneNumber": "99999955656",
    "email": "teste@email.com"
}
```

---

#### PUT /contacts

Example request body:
```json
{
    "id": "2fee7d9d-6970-4c75-88e4-758eb76a4abb",
    "name": "Fulano Teste Atualizado",
    "phoneNumber": "99999955656",
    "email": "teste@email.com"
}
```

---

#### DELETE /contacts/{id}

Example URI:
```
{{base_url}}/contacts/3319c7cd-c595-492e-b4fd-73301c02234b
```

---

#### Links for Testing People API

Postman exported: https://bit.ly/3Oxt1my

People API URL: https://people-api-horm.onrender.com
