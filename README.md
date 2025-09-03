# Selenium Lab №1 – Login Automation Test

##  Товч танилцуулга
Энэ лабораторийн зорилго нь **Selenium WebDriver (Java) + JUnit 5 + WebDriverManager** ашиглан MUST оюутны системд (`https://student.must.edu.mn`) автоматжуулсан **login → modal (popup) хаах → оюутны код шалгах → logout** урсгалыг тестлэх юм.  
Нэвтрэх нэр, нууц үг нь `.env` файл эсвэл орчны хувьсагчаас уншина (`dotenv-java` ашиглаж байна).

---

##  Төслийн бүтэц

├── pom.xml
├── .gitignore
├── .env # MUST_USER, MUST_PASS энд хадгална (commit хийхгүй)
└── src/
└── test/
└── java/com/example/LoginTest.java

---

## ⚙️ Урьдчилсан шаардлага
- Java 17+
- Maven 3+
- Chrome эсвэл Chromium суусан байх

---

## 🚀 Ажиллуулах
1) Төслийн root дээр `.env` файл үүсгэнэ:
   ```dotenv
   MUST_USER=B222270810
   MUST_PASS=MyPassword

2) Тестийг ажиллуулах:

mvn clean test

3) Хэрэв .env ашиглахгүйгээр команд дээрээс дамжуулах бол:

MUST_USER=B222270810 MUST_PASS=MyPassword mvn clean test

эсвэл

mvn clean test -DMUST_USER=B222270810 -DMUST_PASS=MyPassword


## Тестийн урсгал

1) Login – Username/Password талбаруудыг бөглөж “Нэвтрэх” товч дарах

2) Modal хаах – Login дараа гарч ирэх Bootstrap modal-ийн backdrop дээр JS click хийж хаах

3) Assertions

    - Хуудас дээр “Хувийн мэдээлэл” харагдах ёстой

    - “Оюутны код: …” блок дахь .text-ийн утга нь MUST_USER-тэй яг тэнцүү байх ёстой

4) Logout – “Гарах” товчийг дарж, login талбар/“Нэвтрэх” текст дахин харагдахыг баталгаажуулах


## Ашигласан гол locator-ууд

- Username: input#UserName, input[name='UserName']

- Password: input#Password, input[name='Password']

- Login button: input[type='submit'][value='Нэвтрэх'], button[type='submit']

- Modal backdrop: div.modal-backdrop

- Оюутны код (XPath):

//div[@class='title' and normalize-space()='Оюутны код:']
     /following-sibling::div[contains(@class,'text')]
     
Logout: //span[normalize-space()='Гарах']/ancestor::a