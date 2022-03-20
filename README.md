
### Необходимые компоненты

* Docker

### Установка

* Скачайте официальный образ mysql
```sh
docker pull mysql
```
* Запустите контейнер базы данных
```sh
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=survey-service-api -d mysql:latest
```
* Склонируйте репозиторий
```sh
git clone https://github.com/MeowthPerry/survey-service-api.git
cd survey-service-api
```
* Запустите приложение
```sh
mvn spring-boot:run
```
* Чтобы редактировать опросы, нужно создать аккаунт админа
```sh
docker exec -it mysql bash -l
mysql -p # введите пароль "root"
use survey-service-api
insert into users(status, email, password, role, username) values ('ACTIVE', 'admin@', '$2a$10$/ADdfSlJuRv9XNFxplgaje.3CXd6feB.nwXemgvNPqotfwou8DrPG', 'ROLE_ADMIN', 'admin');
```
### Использование
* Все запросы админа должны сопровождаться хедером Authorization c действующим токеном (см. скрин ниже). Чтобы получить access token и refresh token необходимо послать запрос на 'http://localhost:8080/auth/login' с username и password в формате JSON
![1][1]
![3][3]
* Чтобы обновить access token с помощью refresh token'а нужно послать запрос на 'http://localhost:8070/auth/relogin' с указанным refresh token'ом
![2][2]
* Для создания опроса (POST: http://localhost:8080/admin/survey)
![4][4]
* Для изменения опроса (PUT: http://localhost:8080/admin/survey/{id опроса}) с указанием полей, которые нужно изменить. Поле "дата старта" изменить невозможно
![5][5]
* Для удаления опроса (DELETE: http://localhost:8070/admin/survey/{id опроса})
![6][6]
* Для создания вопроса (POST: http://localhost:8070/admin/survey/{id опроса}/question)
![7][7]
![8][8]
![9][9]
* Для изменения вопроса (PUT: http://localhost:8070/admin/survey/{id опроса}/question/{id вопроса}) с указанием полей, которые нужно изменить
![10][10]
* Для удаления вопроса (DELETE: http://localhost:8070/admin/survey/{id опроса}/question/{id вопроса})
![11][11]
* Для получения списка активных опросов (GET: http://localhost:8070/participant/survey). Хедер Authorization не нужен
![12][12]
```sh
[
    {
        "id": 2,
        "created": "2022-03-20T20:17:51.426+00:00",
        "updated": "2022-03-20T20:17:51.426+00:00",
        "status": "ACTIVE",
        "name": "Ваши предпочтения",
        "startDate": "2022-03-18",
        "expirationDate": "2022-03-21",
        "description": "Опрос поможет нам узнать больше о Вас",
        "questions": [
            {
                "id": 2,
                "created": "2022-03-20T20:18:59.521+00:00",
                "updated": "2022-03-20T20:18:59.521+00:00",
                "status": "ACTIVE",
                "content": "Какие цвета вам нравятся?",
                "questionType": "MULTIPLE_CHOICE",
                "answerOptions": [
                    {
                        "id": 1,
                        "created": "2022-03-20T20:18:59.524+00:00",
                        "updated": "2022-03-20T20:18:59.524+00:00",
                        "status": null,
                        "content": "красный"
                    },
                    {
                        "id": 2,
                        "created": "2022-03-20T20:18:59.529+00:00",
                        "updated": "2022-03-20T20:18:59.529+00:00",
                        "status": null,
                        "content": "синий"
                    },
                    {
                        "id": 3,
                        "created": "2022-03-20T20:18:59.531+00:00",
                        "updated": "2022-03-20T20:18:59.531+00:00",
                        "status": null,
                        "content": "зеленый"
                    }
                ]
            },
            {
                "id": 3,
                "created": "2022-03-20T20:20:53.484+00:00",
                "updated": "2022-03-20T20:25:26.519+00:00",
                "status": "ACTIVE",
                "content": "Вы любите гулять?",
                "questionType": "SINGLE_CHOICE",
                "answerOptions": [
                    {
                        "id": 10,
                        "created": "2022-03-20T20:25:26.513+00:00",
                        "updated": "2022-03-20T20:25:26.513+00:00",
                        "status": null,
                        "content": "красный"
                    },
                    {
                        "id": 11,
                        "created": "2022-03-20T20:25:26.515+00:00",
                        "updated": "2022-03-20T20:25:26.515+00:00",
                        "status": null,
                        "content": "синий"
                    },
                    {
                        "id": 12,
                        "created": "2022-03-20T20:25:26.517+00:00",
                        "updated": "2022-03-20T20:25:26.517+00:00",
                        "status": null,
                        "content": "белый"
                    }
                ]
            },
            {
                "id": 6,
                "created": "2022-03-20T20:29:08.596+00:00",
                "updated": "2022-03-20T20:29:08.596+00:00",
                "status": "ACTIVE",
                "content": "Расскажите про ваши хобби",
                "questionType": "TEXT",
                "answerOptions": []
            }
        ]
    }
]
```
* Для прохождения опроса (POST: http://localhost:8080/participant/surveyTaking/{id участника}/survey/{id опроса})
![13][13]
```sh
{
    "answers":[
        {
            "questionId":3,
            "answerUnits":[
                {
                    "content":"да"
                }
            ]
        },
        {
            "questionId":6,
            "answerUnits":[
                {
                    "content":"Пение, спорт"
                }
            ]
        },
        {
            "questionId":2,
            "answerUnits":[
                {
                    "content":"красный"
                },
                {
                    "content":"белый"
                }
            ]
        }
    ]
}
```
* Для получения пройденных пользователем опросов (GET: http://localhost:8070/participant/surveyTaking/{id участника}) с детализацией по ответам
![14][14]
```sh
[
    {
        "id": 1,
        "created": "2022-03-20T20:36:44.254+00:00",
        "updated": "2022-03-20T20:36:44.254+00:00",
        "status": null,
        "survey": {
            "id": 2,
            "created": "2022-03-20T20:17:51.426+00:00",
            "updated": "2022-03-20T20:17:51.426+00:00",
            "status": "ACTIVE",
            "name": "Ваши предпочтения",
            "startDate": "2022-03-18",
            "expirationDate": "2022-03-21",
            "description": "Опрос поможет нам узнать больше о Вас",
            "questions": [
                {
                    "id": 2,
                    "created": "2022-03-20T20:18:59.521+00:00",
                    "updated": "2022-03-20T20:18:59.521+00:00",
                    "status": "ACTIVE",
                    "content": "Какие цвета вам нравятся?",
                    "questionType": "MULTIPLE_CHOICE",
                    "answerOptions": [
                        {
                            "id": 1,
                            "created": "2022-03-20T20:18:59.524+00:00",
                            "updated": "2022-03-20T20:18:59.524+00:00",
                            "status": null,
                            "content": "красный"
                        },
                        {
                            "id": 2,
                            "created": "2022-03-20T20:18:59.529+00:00",
                            "updated": "2022-03-20T20:18:59.529+00:00",
                            "status": null,
                            "content": "синий"
                        },
                        {
                            "id": 3,
                            "created": "2022-03-20T20:18:59.531+00:00",
                            "updated": "2022-03-20T20:18:59.531+00:00",
                            "status": null,
                            "content": "зеленый"
                        }
                    ]
                },
                {
                    "id": 3,
                    "created": "2022-03-20T20:20:53.484+00:00",
                    "updated": "2022-03-20T20:25:26.519+00:00",
                    "status": "ACTIVE",
                    "content": "Вы любите гулять?",
                    "questionType": "SINGLE_CHOICE",
                    "answerOptions": [
                        {
                            "id": 10,
                            "created": "2022-03-20T20:25:26.513+00:00",
                            "updated": "2022-03-20T20:25:26.513+00:00",
                            "status": null,
                            "content": "красный"
                        },
                        {
                            "id": 11,
                            "created": "2022-03-20T20:25:26.515+00:00",
                            "updated": "2022-03-20T20:25:26.515+00:00",
                            "status": null,
                            "content": "синий"
                        },
                        {
                            "id": 12,
                            "created": "2022-03-20T20:25:26.517+00:00",
                            "updated": "2022-03-20T20:25:26.517+00:00",
                            "status": null,
                            "content": "белый"
                        }
                    ]
                },
                {
                    "id": 6,
                    "created": "2022-03-20T20:29:08.596+00:00",
                    "updated": "2022-03-20T20:29:08.596+00:00",
                    "status": "ACTIVE",
                    "content": "Расскажите про ваши хобби",
                    "questionType": "TEXT",
                    "answerOptions": []
                }
            ]
        },
        "participantId": 1,
        "answers": [
            {
                "id": 3,
                "created": "2022-03-20T20:36:44.270+00:00",
                "updated": "2022-03-20T20:36:44.270+00:00",
                "status": null,
                "answerUnits": [
                    {
                        "id": 3,
                        "created": "2022-03-20T20:36:44.272+00:00",
                        "updated": "2022-03-20T20:36:44.272+00:00",
                        "status": null,
                        "content": "красный"
                    },
                    {
                        "id": 4,
                        "created": "2022-03-20T20:36:44.273+00:00",
                        "updated": "2022-03-20T20:36:44.273+00:00",
                        "status": null,
                        "content": "белый"
                    }
                ],
                "question": {
                    "id": 2,
                    "created": "2022-03-20T20:18:59.521+00:00",
                    "updated": "2022-03-20T20:18:59.521+00:00",
                    "status": "ACTIVE",
                    "content": "Какие цвета вам нравятся?",
                    "questionType": "MULTIPLE_CHOICE",
                    "answerOptions": [
                        {
                            "id": 1,
                            "created": "2022-03-20T20:18:59.524+00:00",
                            "updated": "2022-03-20T20:18:59.524+00:00",
                            "status": null,
                            "content": "красный"
                        },
                        {
                            "id": 2,
                            "created": "2022-03-20T20:18:59.529+00:00",
                            "updated": "2022-03-20T20:18:59.529+00:00",
                            "status": null,
                            "content": "синий"
                        },
                        {
                            "id": 3,
                            "created": "2022-03-20T20:18:59.531+00:00",
                            "updated": "2022-03-20T20:18:59.531+00:00",
                            "status": null,
                            "content": "зеленый"
                        }
                    ]
                }
            },
            {
                "id": 1,
                "created": "2022-03-20T20:36:44.259+00:00",
                "updated": "2022-03-20T20:36:44.259+00:00",
                "status": null,
                "answerUnits": [
                    {
                        "id": 1,
                        "created": "2022-03-20T20:36:44.263+00:00",
                        "updated": "2022-03-20T20:36:44.263+00:00",
                        "status": null,
                        "content": "да"
                    }
                ],
                "question": {
                    "id": 3,
                    "created": "2022-03-20T20:20:53.484+00:00",
                    "updated": "2022-03-20T20:25:26.519+00:00",
                    "status": "ACTIVE",
                    "content": "Вы любите гулять?",
                    "questionType": "SINGLE_CHOICE",
                    "answerOptions": [
                        {
                            "id": 10,
                            "created": "2022-03-20T20:25:26.513+00:00",
                            "updated": "2022-03-20T20:25:26.513+00:00",
                            "status": null,
                            "content": "красный"
                        },
                        {
                            "id": 11,
                            "created": "2022-03-20T20:25:26.515+00:00",
                            "updated": "2022-03-20T20:25:26.515+00:00",
                            "status": null,
                            "content": "синий"
                        },
                        {
                            "id": 12,
                            "created": "2022-03-20T20:25:26.517+00:00",
                            "updated": "2022-03-20T20:25:26.517+00:00",
                            "status": null,
                            "content": "белый"
                        }
                    ]
                }
            },
            {
                "id": 2,
                "created": "2022-03-20T20:36:44.266+00:00",
                "updated": "2022-03-20T20:36:44.266+00:00",
                "status": null,
                "answerUnits": [
                    {
                        "id": 2,
                        "created": "2022-03-20T20:36:44.268+00:00",
                        "updated": "2022-03-20T20:36:44.268+00:00",
                        "status": null,
                        "content": "Пение, спорт"
                    }
                ],
                "question": {
                    "id": 6,
                    "created": "2022-03-20T20:29:08.596+00:00",
                    "updated": "2022-03-20T20:29:08.596+00:00",
                    "status": "ACTIVE",
                    "content": "Расскажите про ваши хобби",
                    "questionType": "TEXT",
                    "answerOptions": []
                }
            }
        ]
    }
]
```


[1]: screenshots/1.png
[2]: screenshots/2.png
[3]: screenshots/3.png
[4]: screenshots/4.png
[5]: screenshots/5.png
[6]: screenshots/6.png
[7]: screenshots/7.png
[8]: screenshots/8.png
[9]: screenshots/9.png
[10]: screenshots/10.png
[11]: screenshots/11.png
[12]: screenshots/12.png
[13]: screenshots/13.png
[14]: screenshots/14.png
