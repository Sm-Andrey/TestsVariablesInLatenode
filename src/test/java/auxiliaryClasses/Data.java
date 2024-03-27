package auxiliaryClasses;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Data {
  public static final String EMAIL = "prokhojy@yandex.ru";
  public static final String NONEXISTENTEMAIL = "prokhojy@test.ru";
  public static final String PASSWORD = "qwerty123";
  public static final String FILLTHISFIELD = "Заполните это поле.";
  public static final String REQUIREDFIELD = "Required field";
  public static final String NAMEALREADYEXISTS = "The variable name already exists";
  public static final String VERIFYYOUREMAIL = "Verify your email";
  public static final String msgErrorNonExistentEmail = "Sorry, an error has occurred. Please try again later or contact tech. support.";

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

  public static Stream<Arguments> ValidEmailsForAuthTest() {
    return Stream.of(
            arguments("email@domain.com", "Валидный email"),
            arguments("firstname.lastname@domain.com", "Email содержит точку в поле адреса"),
            arguments("email@subdomain.domain.com", "Email содержит точку в субдомене"),
            arguments("firstname+lastname@domain.com", "Знак плюс разрешен"),
            arguments("email@123.123.123.123", "IP адрес валидного домена"),
            arguments("email@[123.123.123.123]", "Квадратные скобки разрешены"),
            arguments("\"email\"@domain.com", "Кавычки в адресе разрешены"),
            arguments("1234567890@domain.com", "Цифры разрешены"),
            arguments("email@domain-one.com", "Тире в доменной части разрешено"),
            arguments("_______@domain.com", "Подчеркивания в адресе разрешены"),
            arguments("email@domain.name", ".name - действительное доменное имя верхнего уровня"),
            arguments("email@domain.co.jp", "Точка в доменном имени верхнего уровня также считается допустимой (co.jp в качестве примера здесь)"),
            arguments("firstname-lastname@domain.com", "Тире в адресе разрешено"),
            arguments("üñîçøðé@example.com", "Unicode символы в адресе разрешены"),
            arguments("üñîçøðé@üñîçøðé.com", "Unicode символы в домене разрешены"),
            arguments("postbox@com", "Верхнеуровневый домен разрешен"),
            arguments("!#$%&'*+-/=?^_`{}|~@example.org", "Специальные символы в адресе разрешены"),
            arguments("x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2", "Общая длина до 254 символов"),
            arguments("first.last@[IPv6:::12.34.56.78]", "IPv6 адрес домена разрешен")
    );
  }

  public static Stream<Arguments> InvalidEmailsForAuthTest() {
    return Stream.of(
            arguments("plainaddress", "message:'Нет символы @ и домена'"),
            arguments("#@%^%#$@#$@#.com", "message:'Безпорядочный набор символов'"),
            arguments("@domain.com", "message:'Нет имени (первой части email)'"),
            arguments("example@email", "message:'Нет доменной части (второй части email)'"),
            arguments("Joe Smith <email@domain.com>", "message:'html-теги запрещены'"),
            arguments("email.domain.com", "message:'Нет @'"),
            arguments("email@domain@domain.com", "message:'Два символа @'"),
            arguments(".email@domain.com", "message:'Точка в начала адреса запрещена'"),
            arguments("email.@domain.com", "message:'Точка в конца имени запрещена'"),
            arguments("email..email@domain.com", "message:'Множество точек в имени (первой части email)'"),
            arguments("example@email…com", "message:'Множество точек в доменной части'"),
            arguments("email@domain.com (Joe Smith)", "message:'Текст после адреса запрещен'"),
            arguments("email@-domain.com", "message:'Доменная часть не может начинаться с тире'"),
            arguments("email@domain-.com", "message:'Тире не может быть в конце доменной части'"),
            arguments("email@111.222.333.44444", "message:'Невалидный формат IP адреса'"),
            arguments("123456789012345678901234567890123456789012345678901234567890@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.12345.iana.org", "message:'Длина более 254 символов'"),
            arguments("12345678901234567890123456789012345678901234567890123456789012345@iana.org", "message:'Имя более 64 символов'"),
            arguments("\"first\"last\"@iana.org", "message:'Кавычки внутри кавычек'"),
            arguments("first.last@[IPv5:::12.34.56.78]", "message:'Невалидный формат IPv6 адреса'"),
            arguments("first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org", "message:'Первая часть доменного имени не может быть более 63'")
    );
  }
}
