Тестовое задание

Тестовое задание заключается в покрытие ui-автотестами подраздел Variables приложения app.latenode.com
Используемый стек: java 21, Selenide, allure report

Данные тестового пользователя для авторизации: 
логин: prokhojy@yandex.ru
пароль: qwerty123

Тест кейсы:
1. Создать переменную String с именем "str" и значением "Тестовая переменная String"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селекте "Select the type of variable" выбрать "String"
          - в поле "Name" ввести "str"
          - в поле "Value" ввести "Тестовая переменная String"
          - нажать кнопку "Save"
    Ожидаемый результат: появилось поле с Name = "str", Value = "Тестовая переменная String", Type of variable = "string"

2. Создать переменную String с именем "strNum" и значением "1"
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "String"
         - в поле "Name" ввести "strNum"
         - в поле "Value" ввести "1"
         - нажать кнопку "Save"
     Ожидаемый результат: появилось поле с Name = "strNum", Value = "1", Type of variable = "string"

3. Создать переменную String с именем " " и значением " "
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "String"
         - в поле "Name" ввести " "
         - перевести курсор в поле "Value"
   Ожидаемый результат: появилось сообщение "The variable name cannot contain spaces"

4. Создать переменную с пустым полем name
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "String"
         - нажать кнопку "Save"
   Ожидаемый результат: появилось сообщение "Заполните это поле." у поля ввода "Name"

5. Создать переменную String с именем "emptySt" и пустым полем value
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
    - в селект "Select the type of variable" выбрать "String"
    - в поле "Name" ввести "emptySt"
    - в поле "Value" ввести " "
    - нажать кнопку "Save"
    Ожидаемый результат: появилось сообщение "Заполнить это поле" у поля ввода "Value"

6. Создать переменную String с именем "str.!@3e" и значением " "
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "String"
         - в поле "Name" ввести "str.!@3e"
         - в поле "Value" ввести " "
         - нажать кнопку "Save"
   Ожидаемый результат: появилось поле с Name = "str.!@3e", Value = " ", Type of variable = "string"

7. Создать переменную String с именем более 100 символов и значением более 100 символов
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "String"
         - в поле "Name" ввести "Аещёинтерактивныепрототипыосвещаютчрезвычайноинтересныеособенностикартинывцеломоднакоконкретныевыводыразумеется"
         - в поле "Value" ввести "А ещё интерактивные прототипы освещают чрезвычайно интересные особенности картины в целом, однако ко"
         - нажать кнопку "Save"
   Ожидаемый результат: появилось поле с Name = более 100 символов, Value = более 100 символов, Type of variable = "string"

8. Создать переменную int с именем "number" и значением "123"
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "Int"
         - в поле "Name" ввести "number"
         - в поле "Value" ввести "123"
         - нажать кнопку "Save"
     Ожидаемый результат: появилось поле с Name = "number", Value = 123, Type of variable = "number"

9. Создать переменную int с именем "numOne" и значением "1"
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "Int"
         - в поле "Name" ввести "numOne"
         - в поле "Value" ввести "1"
         - нажать кнопку "Save"
   Ожидаемый результат: появилось поле с Name = "numOne", Value = 1, Type of variable = "number"

10. Создать переменную int с именем " " и значением " "
   Предусловие: авторизоваться тестовым пользователем
   Шаги: - нажать кнопку "New variable"
         - в селект "Select the type of variable" выбрать "Int"
         - в поле "Name" ввести " "
         - перевести курсор в поле "Value"
   Ожидаемый результат: появилось сообщение "The variable name cannot contain spaces"

11. Создать переменную int с именем "integer.!@3ev2" и значением " "
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Int"
          - в поле "Name" ввести "integer.!@3ev2"
          - в поле "Value" ввести " "
          - нажать кнопку "Save"
    Ожидаемый результат: появилось сообщение "Заполнить это поле" у поля ввода "Value"

12. Создать переменную int с именем "integer.!@3e" и значением max int = "2147483647"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Int"
          - в поле "Name" ввести "integer.!@3e"
          - в поле "Value" ввести "2147483647"
          - нажать кнопку "Save"
    Ожидаемый результат: появилось поле с Name = "integer.!@3e", Value = 2147483647, Type of variable = "number"

13. Создать переменную Bool с именем "isTrue" и значением "true"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Bool"
          - в поле "Name" ввести "isTrue"
          - в поле "Value" ввести "true"
          - нажать кнопку "Save"
    Ожидаемый результат: появилось поле с Name = "isTrue", Value = true, Type of variable = "bool"

14. Создать переменную Bool с именем "isFalse" и значением "false"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Bool"
          - в поле "Name" ввести "isFalse"
          - в поле "Value" ввести "false"
          - нажать кнопку "Save"
    Ожидаемый результат: появилось поле с Name = "isFalse", Value = false, Type of variable = "bool"

15. Создать переменную Bool с именем " " и значением " "
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Bool"
          - в поле "Name" ввести " "
          - перевести курсор в поле "Value"
    Ожидаемый результат: появилось сообщение "The variable name cannot contain spaces"

16. Создать переменную Bool с именем "emptyBool" и пустым значением
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "Bool"
          - в поле "Name" ввести "emptyBool"
          - нажать кнопку "Save"
      Ожидаемый результат: появилось сообщение "Required field" у поля ввода "Value"

17. Создать переменную JSON с именем "name" и значением "Andrey"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "JSON"
          - в поле "Name" ввести "name"
          - в поле "Value" ввести "\"Andrey\""
          - нажать кнопку "Save"
    Ожидаемый результат: появилось поле с Name = "name", Value = "Andrey", Type of variable = "json"

18. Создать переменную JSON с именем " " и значением " "
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "JSON"
          - в поле "Name" ввести " "
          - перевести курсор в поле "Value"
    Ожидаемый результат: появилось сообщение "The variable name cannot contain spaces"

19. Создать переменную JSON с именем "json.!@3e" и значением """"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селект "Select the type of variable" выбрать "JSON"
          - в поле "Name" ввести "json.!@3e"
          - в поле "Value" ввести "\"\""
          - нажать кнопку "Save"
      Ожидаемый результат: появилось поле с Name = ".!@3e", Value = "", Type of variable = "json"

20. Отредактировать переменную String с именем "str"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - найти поле c именем "str" и нажать три точки в правой части поля
          - в выпадающем окне выбрать "Edit"
          - в селект "Select the type of variable" выбрать "JSON"
          - в поле "Name" ввести "editSTR"
          - в поле "Value" ввести "Тестовая переменная String v2"
          - нажать кнопку "Save"
    Ожидаемый результат: значения в редактируемом поле изменилось на Name = "editSTR", Value = "Тестовая переменная String v2", Type of variable = "json"

21. С помощью поисковой строки найти переменную с именем "editSTR"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - в поисковое поле ввести "editSTR"
          - Ожидаемый результат: появилось поле с Name = "editSTR", Value = "Тестовая переменная String v2", Type of variable = "json"

22. Создать дубликат переменной JSON с именем "editSTR"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - нажать кнопку "New variable"
          - в селекте "Select the type of variable" выбрать "JSON"
          - в поле "Name" ввести "editSTR"
          - в поле "Value" ввести "Тестовая переменная String v2"
          - нажать кнопку "Save"
    Ожидаемый результат: появилось сообщение об ошибке "The variable name already exists"

23. Удалить переменную с именем "editSTR"
    Предусловие: авторизоваться тестовым пользователем
    Шаги: - найти поле с именем "editSTR" и нажать три точки в правой части поля
          - в выпадающем окне с текстом "Are you sure you want to delete global variable "strTest"?" нажать "Delete"
    Ожидаемый результат: поле с переменной с именем "editSTR" пропала из списка

Так как после всех пройденных тестов остаются созданные тесты и в будущем при повторном запуске тестов могут возникать конфликты, то в AfterAll запускается 
метод, который проходит циклом и удаляет все созданные переменные.