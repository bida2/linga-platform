# Платформа ЛинГА

Идеята на тази платформа е да обогати знанията на обикновения българин за стотиците чужди думи, които всяка година навлизат в българския език.

За тази цел, проекта използва вграденият в Google Chrome и Microsoft Edge [Web Speech API](https://developer.mozilla.org/en-US/docs/Web/API/Web_Speech_API), който е способен
да превръща текст в говор (за сега технологията е експериментална и се поддържат ограничен брой говорими езици).

# Как да пуснем проекта?

За тази цел, трябва да го импортирате в IDE като Eclipse или IntelliJ. След това трябва да създадете application.properties файл в src/main/resources папката, в който да добавите следните key-value properties:

```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url= <url до вашата PostgreSQL база от данни>
spring.datasource.username= <потребителско име за вход на вашата PostgreSQL база от данни>
spring.datasource.password= <парола за вход във вашата PostgreSQL база от данни>
```

За да създадете успешно и тестовите потребители (администратор и нормален потребител), трябва да добавите и следното към application.properties:

```
fw.normal.user.username= <потребителско име на нормален потребител>
fw.normal.user.password= <парола на нормален потребител>
fw.normal.user.email= <е-мейл адрес на нормален потребител>
fw.admin.user.username= <потребителско име на администратор>
fw.admin.user.password= <парола на администратор>
fw.admin.user.email= <е-мейл адрес на администратор>
```

**Хостната версия на проекта за демонстрационни цели може да бъде видяна на https://foreign-words-bg.herokuapp.com/ - ВНИМАНИЕ - Тъй като използвам безплатна версия на Heroku, при първоначално зареждане може да изпитате забавяне от 30 секунди до минута при зареждане - това е нормално, тъй като виртуалната машина "заспива" при 30 минути липса на какъвто и да е трафик.**
