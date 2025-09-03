# Selenium Lab №1 – Login Automation Test

## 📌 Товч танилцуулга
Энэ лабораторийн зорилго нь **Selenium WebDriver (Java) + JUnit 5 + WebDriverManager** ашиглан MUST оюутны системд (`https://student.must.edu.mn`) автоматжуулсан **login → modal хаах → оюутны код шалгах → logout** урсгалыг тестлэх юм.  
Нэвтрэх нэр, нууц үг нь `.env` файл эсвэл орчны хувьсагчаас уншина (`dotenv-java` ашиглаж байна).

---

## 📂 Төслийн бүтэц
lab1/
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
1. Төслийн root дээр `.env` файл үүсгэнэ:
   ```dotenv
   MUST_USER=B222270810
   MUST_PASS=MyPassword


Дараах командаар тестийг ажиллуулна:
mvn clean test


Хэрэв .env ашиглахгүйгээр команд дээр дамжуулмаар бол:
MUST_USER=B222270810 MUST_PASS=MyPassword mvn clean test

эсвэл
mvn clean test -DMUST_USER=B222270810 -DMUST_PASS=MyPassword



🧪 Тестийн урсгал

Login

Username, Password талбаруудыг бөглөж “Нэвтрэх” товч дарах

Modal хаах

Bootstrap modal гарвал backdrop дээр JS click хийж хаах

Assertions

Хуудас дээр “Хувийн мэдээлэл” харагдах ёстой

“Оюутны код” дотор гарч буй текст нь .env доторх MUST_USER-тэй яг тэнцүү байх

Logout

“Гарах” товчийг дараад дахин login талбар гарч ирснийг шалгах

🔑 Ашигласан гол locator-ууд

Username: input#UserName, input[name='UserName']

Password: input#Password, input[name='Password']

Login button: input[type='submit'][value='Нэвтрэх']

Modal backdrop: div.modal-backdrop

Оюутны код:

//div[@class='title' and normalize-space()='Оюутны код:']
     /following-sibling::div[contains(@class,'text')]


Logout: //span[normalize-space()='Гарах']/ancestor::a

🛡 .gitignore жишээ
/target
.env
*.iml
.idea/