package auxiliaryClasses;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Data {
  public static final String EMAIL = "prokhojy@yandex.ru";
  public static final String PASSWORD = "qwerty123";

  public static final String FILLTHISFIELD = "Заполните это поле.";
  public static final String REQUIREDFIELD = "Required field";
  public static final String NAMEALREADYEXISTS = "The variable name already exists";

  public static Stream<Arguments> positiveTestData() {
    return Stream.of(
            arguments("String", "str", "Тестовая переменная String", "string"),
            arguments("String", "strNum", "1", "string"),
            arguments("String", "str.!@3e", " ", "string"),
            arguments("String", "Аещёинтерактивныепрототипыосвещаютчрезвычайноинтересныеособенностикартинывцеломоднакоконкретныевыводыразумеется",
                    "А ещё интерактивные прототипы освещают чрезвычайно интересные особенности картины в целом, однако ко", "string"),
            arguments("Int", "number", "123", "number"),
            arguments("Int", "numOne", "1", "number"),
            arguments("Int", "integer.!@3e", "2147483647", "number"),
            arguments("Bool", "isTrue", "true", "bool"),
            arguments("Bool", "isFalse", "false", "bool"),
            arguments("JSON", "name", "\"Andrey\"", "json"),
            arguments("JSON", "json.!@3e", "\"\"", "json"));

  }

  public static Stream<Arguments> negativeTestDataWithSpaces() {
    return Stream.of(
            arguments("String", " ", " ", "The variable name cannot contain spaces"),
            arguments("Int", " ", " ", "The variable name cannot contain spaces"),
            arguments("Bool", " ", " ", "The variable name cannot contain spaces"),
            arguments("JSON", " ", " ", "The variable name cannot contain spaces"));
  }

}
