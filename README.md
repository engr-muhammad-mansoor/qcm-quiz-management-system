# QCM Quiz Management System

A comprehensive web-based Multiple Choice Question (QCM) management system built with Spring Boot. This application allows educators and administrators to manage quiz categories, create questions, and generate multiple randomized test papers in PDF format.

## 🚀 Features

- **Category Management**: Create, read, update, and delete quiz categories
- **Question Management**: Full CRUD operations for questions with multiple choice options
- **Question Search**: Search questions by category and keyword
- **PDF Test Generation**: Generate multiple randomized test papers with configurable number of questions per category
- **Pagination**: Efficient pagination for categories and questions
- **Responsive UI**: Modern, responsive user interface built with Bootstrap 4
- **Question Randomization**: Automatic shuffling of questions and answer options in generated tests

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.0.6
- **Java Version**: Java 17
- **Database**: MySQL
- **ORM**: Spring Data JPA / Hibernate
- **Template Engine**: Thymeleaf
- **Frontend**: Bootstrap 4.3.1, HTML5, CSS3, JavaScript
- **PDF Generation**: iText7 (version 8.0.3)
- **Build Tool**: Maven

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+ (or use the included Maven wrapper)
- MySQL 5.7+ or MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code) - optional but recommended

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd helloworldhtmlcss
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE quizzqcm;
```

### 3. Configuration

Copy the example configuration file and update it with your database credentials:

**Windows/Linux:**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

**Or manually create** `src/main/resources/application.properties` with the following content:

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/quizzqcm
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

**Note**: Replace `your_database_username` and `your_database_password` with your actual MySQL credentials.

### 4. Build the Project

Using Maven wrapper (recommended):

**Windows:**
```bash
mvnw.cmd clean install
```

**Linux/Mac:**
```bash
./mvnw clean install
```

Or using Maven directly:
```bash
mvn clean install
```

### 5. Run the Application

Using Maven wrapper:

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

Or using Maven directly:
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8081`

## 📖 Usage

### Accessing the Application

Once the application is running, navigate to:
- **Home/Dashboard**: `http://localhost:8081/`
- **Categories**: `http://localhost:8081/Category`
- **Questions**: `http://localhost:8081/Question`
- **QCM Generator**: `http://localhost:8081/Qcm`

### Managing Categories

1. Navigate to the **Categories** page
2. Click **Add New Category** to create a new category
3. Use **Edit** to modify existing categories
4. Use **Delete** to remove categories (ensure no questions are associated)

### Managing Questions

1. Navigate to the **Questions** page
2. Click **Add New Question** to create a question
3. Select a category and provide:
   - Question text
   - Four multiple choice options (A, B, C, D)
4. Use **Edit** or **Delete** to manage existing questions
5. Use the search functionality to find questions by category or keyword

### Generating Test Papers

1. Navigate to the **QCM** page
2. Specify:
   - Number of tests to generate
   - Total number of questions per test
   - Number of questions from each category
3. Click **Generate** to download a ZIP file containing all test PDFs
4. Each PDF contains randomized questions with shuffled answer options

## 📁 Project Structure

```
helloworldhtmlcss/
├── src/
│   ├── main/
│   │   ├── java/com/helloword/demo/
│   │   │   ├── Category.java                 # Category entity
│   │   │   ├── CategoryController.java       # Category REST controller
│   │   │   ├── CategoryService.java          # Category business logic
│   │   │   ├── CategoryRepository.java       # Category data access
│   │   │   ├── Question.java                 # Question entity
│   │   │   ├── QuestionController.java       # Question REST controller
│   │   │   ├── QuestionService.java          # Question business logic
│   │   │   ├── QuestionRepository.java       # Question data access
│   │   │   ├── QcmController.java            # QCM test generator controller
│   │   │   ├── PDFGenerator.java             # PDF generation service
│   │   │   ├── DemoApplication.java          # Main application class
│   │   │   └── Hellocontroller.java          # Home page controller
│   │   └── resources/
│   │       ├── application.properties         # Application configuration
│   │       ├── application.properties.example # Configuration template
│   │       ├── static/
│   │       │   ├── styles.css                # Custom styles
│   │       │   └── images/                   # Static images
│   │       └── templates/
│   │           ├── hello.html                # Home page
│   │           ├── category.html             # Category list page
│   │           ├── category_form.html        # Category form
│   │           ├── question.html             # Question list page
│   │           ├── question_form.html        # Question form
│   │           ├── qcm.html                  # QCM generator page
│   │           ├── search.html               # Search results page
│   │           └── common/
│   │               └── header.html           # Common header template
│   └── test/
│       └── java/com/helloword/demo/
│           └── DemoApplicationTests.java     # Application tests
├── pom.xml                                   # Maven configuration
├── mvnw                                      # Maven wrapper (Unix)
├── mvnw.cmd                                  # Maven wrapper (Windows)
└── README.md                                 # This file
```

## 🔑 Key Components

### Entities

- **Category**: Represents a question category
  - Fields: `id`, `name`

- **Question**: Represents a multiple choice question
  - Fields: `id`, `name` (question text), `cha`, `chab`, `chac`, `chad` (choices A-D), `category` (many-to-one relationship)

### Controllers

- **Hellocontroller**: Handles home page routing
- **CategoryController**: Manages category CRUD operations
- **QuestionController**: Manages question CRUD operations and search
- **QcmController**: Handles test paper generation requests

### Services

- **CategoryService**: Business logic for category operations
- **QuestionService**: Business logic for question operations
- **PDFGenerator**: Generates PDF test papers with randomization

## 🎨 Features in Detail

### Pagination

Both Categories and Questions lists support pagination with 5 items per page for better performance and user experience.

### Search Functionality

Questions can be searched by:
- Category (dropdown filter)
- Keyword (text search in question text)

### PDF Generation

The PDF generator creates professional test papers with:
- Test series numbering
- Student name and first name fields
- Exam rules section
- Randomized questions from selected categories
- Shuffled answer options for each question
- Automatic page breaks every 6 questions

## 🧪 Testing

Run the test suite using:

```bash
mvn test
```

Or using Maven wrapper:
```bash
./mvnw test
```

## 🔒 Security Notes

- **Important**: The `application.properties` file is excluded from version control for security reasons
- Always use `application.properties.example` as a template
- Never commit sensitive database credentials to the repository
- Use environment variables or secure configuration management in production environments

## 🐛 Troubleshooting

### Database Connection Issues

- Ensure MySQL is running
- Verify database credentials in `application.properties`
- Check that the database `quizzqcm` exists
- Ensure MySQL connector dependency is properly loaded

### Port Already in Use

If port 8081 is already in use, change it in `application.properties`:
```properties
server.port=8082
```

### Build Issues

- Ensure Java 17 is installed: `java -version`
- Clean and rebuild: `mvn clean install`
- Delete `target/` folder and rebuild if needed

## 📝 License

This project is open source and available for educational and portfolio purposes.

## 👨‍💻 Author

Developed as a portfolio project showcasing Spring Boot application development skills.

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the issues page or create a pull request.

## 📞 Support

For support, please open an issue in the repository or contact the project maintainer.

---

**Note**: This application is designed for educational and portfolio demonstration purposes. For production use, consider adding authentication, authorization, input validation, error handling improvements, and comprehensive testing.

