# Домашнее задание по курсу Java Pro 2024

*Студент: Валентин Левин*

## 4. ORM, Hibernate, JPA, Query DSL

[Реализация решения. Пояснение по структуре запроса поиска](#Пояснение-к-структуре-запроса-поиска)

Создать новый сервис в котором будет логика создания приюта.
Создаем сущность Shelter — это наша модель приюта для животных, и добавляем в нее основные поля:
- **name** — название приюта;
- **location** — адрес приюта;
- **capacity** — вместимость (количество мест);
- **type** — тип приюта (например, "для собак", "для кошек" можно указать 5 типов на свое усмотрение)
- **rating** — рейтинг приюта;
- **isGovernmentFunded** — флаг, указывающий, финансируется ли приют государством;
- **averageAdoptionTime** — среднее время ожидания на усыновление;
- **dailyCost** — ежедневная стоимость содержания одного животного.

На основе этой сущности создаем REST-эндпоинт для поиска приютов. Он должен поддерживать несколько фильтров и сортировку.  

**Что должны уметь фильтры:**  
- **Поиск по названию приюта:** фильтр по полю name с частичным совпадением, чтобы можно было искать, например, по подстроке.
- **Фильтр по типу и местоположению:** по полям type и location. Для type мы будем использовать точное совпадение (чтобы искать конкретно "для собак", "для кошек" и т. д.), а для location — частичное совпадение (поиск по городу или району).
- **Фильтр по диапазонам значений:** для таких полей, как capacity, rating, averageAdoptionTime и dailyCost. Нам нужно, чтобы можно было задать диапазон значений, например, вместимость от 50 до 100 мест или рейтинг от 4 и выше.
- **Логические операторы:** чтобы фильтры поддерживали AND, OR, и NOT. Например, можно искать приюты с рейтингом не ниже 4, которые находятся не в определенном районе, и с вместимостью более 50.

**Сортировка и пагинация:**  
Кроме фильтров, добавляем сортировку — пусть можно будет сортировать результаты по полям rating, capacity, и averageAdoptionTime. Также делаем пагинацию, чтобы удобно было работать с большим количеством записей.

Задание аналогично тому что делали на уроке, но с учетом всех выше правил + пагинация (так же креатив к подходу приветствуется)

В проекте реализовано описание API с использованием Swagger. После запуска интерфейс доступен по адресу [http://localhost:8081/lesson4/api/v1/docs/swagger-ui/index.html](http://localhost:8081/lesson4/api/v1/docs/swagger-ui/index.html) 

## Пояснение к структуре запроса поиска

_Возможно было велосипедостроение, но разобраться досконально с Specification, Criteria API по времени не хватило._

Гибкость запроса реализована посредством передачи параметров в виде JSON-объекта.
Запрос делится на две части: фильтры и сортировка.
```json
{
  "pageNumber": 1,
  "pageSize": 10,
  "filterParams": {
    ...
  },
  "sortParams": [
    ...
  ]
}
```

**Фильтрация данных**

Для задания фильтров логическое выражение реализуется в виде последовательности `expressionGroups` и `expressions` внутри блока `filterParams`.
- `expression` - это выражение сравнения значения одного поля кортежа данных с переданным для сравнения значением, и состоит из трех частей:
    - наименование поля `key`,
    - требуемая операция сравнения в `operation`
        - eq - операция прямого сравнения - **=**
        - in - операция проверки на вхождение - **like**
        - lt - будут выбираться значения меньше value - **<**
        - gt - будут выбираться значения больше value - **<**
    - значение, с которым будет производиться сравнение `value`.

    Для примера условие
    ```sql 
    name like '%first%'
    ```
    Описывается как
    ```json
    {
        "key": "name",
        "operation": "in",
        "value": "first"
    }
    ```
- Для связи выражений между собой используется значение в поле `joinBetween`, с возможными значениями: and, or, and not, or not.
    ```json
          {
            "joinBetween": "or",
            "expressions": [
              {
                "key": "location",
                "operation": "eq",
                "value": "almaty"
              },
              {
                "key": "location",
                "operation": "eq",
                "value": "karaganda"
              }
            ]
          }
    ```
    соответствует запросу
    ```sql
    location = 'almaty' or location = 'karaganda'
    ```

- `expressionGroup` - это множество выражений, объединенных общей логикой. Кроме того, группа которая в свою очередь может содержать другие подгруппы.
    ```json
    {
      "joinBetween": "and",
      "expressionGroups": [
        {
          "joinBetween": "or",
          "expressions": [
            {
              "key": "location",
              "operation": "eq",
              "value": "almaty"
            },
            {
              "key": "location",
              "operation": "eq",
              "value": "karaganda"
            }
          ]
        },
        {
          "joinBetween": "and not",
          "expressions": [
            {
              "key": "type",
              "operation": "eq",
              "value": "CAT"
            }
          ]
        }
      ]
    }
    ```
    соответствует запросу sql
    ```sql
    (location = 'almaty' or location = 'karaganda') and not type = 'CAT'
    ```
Если набор условий фильтра достаточно простой и не требует дополнительной вложенности условий, то весь набор условий можно задать набором `expression` на самом верхнем уровне, не используя вложенные `expressionGroup`

**Сортировка**

Для задания параметров сортировки в поле **sortParams** передается массив объектов, содержащих наименование поля и направление сортировки:   
- **key** - наименование поля, по которому будет производиться сортировка
- **sort** - направление сортировки: asc - по возрастанию, desc - по убыванию.
  Пример задания сортировки
```json
  "sortParams": [
    {
      "key": "name",
      "sort": "desc"
    },
    {
      "key": "id",
      "sort": "asc"
    }
  ]
```            
который будет соответствовать запросу
```sql
order by name desc, id asc
```
***Пагинация данных**

Для пагинации используются параметры **pageNumber** и **pageSize**. Принимаются положительные значения. Если значения не переданы, то по умолчанию будет выбрана 1-я страница данных, с количестом объектов не более 10.

Пример полного запроса:
```json
{
  "pageNumber": 1,
  "pageSize": 10,
  "filterParams": {
    "joinBetween": "and",
    "expressionGroups": [
      {
        "joinBetween": "or",
        "expressions": [
          {
            "key": "location",
            "operation": "eq",
            "value": "almaty"
          },
          {
            "key": "location",
            "operation": "eq",
            "value": "karaganda"
          }
        ]
      },
      {
        "joinBetween": "and",
        "expressions": [
          {
            "key": "type",
            "operation": "eq",
            "value": "CAT"
          }
        ]
      }
    ]
  },
  "sortParams": [
    {
      "key": "name",
      "sort": "desc"
    },
    {
      "key": "id",
      "sort": "asc"
    }
  ]
}
```
