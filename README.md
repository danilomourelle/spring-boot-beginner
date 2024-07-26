### Quick Start

Primeiro ponto é que aparentemente “Spring” seria uma ideologia de solução de uma organização que faz uma serie de produtos em Java. Entre esse produtos temos “Spring Framework” que vai servir de base para a maioria de outros projetos, o “Spring Cloud” que fornece uma serie de ferramentas para aplicações com deploy em nuvem, entre vários outros. O “Spring Boot” seria um desses produtos, que inclusive utiliza boa parte dos outros, uma vez que ele seria um agregador do framework “Spring” como um todo, facilitando o lançamento de uma aplicação nova.

Uma aplicação “Spring” criada com o *Spring Initializr* pode ter a definida a versão do JDK, do próprio Spring, assim como o tipo de empacotamento e o gerenciador de projeto. Entre os empacotadores nós temos a opção **Jar** e **War**, que pelo que eu entendi no pacote **Jar** ele vai compilar o código Java juntamente com um servidor Tomcat, enquanto que em um pacote **War** ele vai empacotar apenas os arquivos Java, e esse pacote deve ser manualmente incluído em um servidor existente, o qual pode já estar servindo outros aplicativos.

Já entre os gerenciadores, nós temos o **Maven** que é um gerenciador do projeto, e ele vai se basear no arquivo de configuração do `pom.xml` que faz algo semelhante ao que seria o package.json do JavaScript. Então nele vamos ter alguns dados do projeto como *Group ID, Artifact ID* e *Version*, além da informação do tipo de empacotamento que será utilizado, e também da lista de dependências do projeto.

Em JavaScript, a gente tem o repositório NPM, e também temos a CLI **npm**. Através dessa CLI a gente consegue instalar na aplicação qualquer pacote que seja necessário, através do nome do pacote. Esse pacote é baixado no próprio diretório do projeto, na pasta node_modules. Já aqui no Java, ele baixa na em um local permanente na máquina (para linux é em `~/.m2/repository`), e qualquer projeto que venha a precisar de uma dependência, usa esses mesmo artefatos. Pelo que entendi, existe uma CLI para o **Maven**, mas o Spring Boot monta um script shell que torna a instalação dessa CLI opcional. Assim, caso seja necessário adicionar alguma dependência, basta buscar o xml de identificação dela no repositório do **Maven**, e manualmente copiar no *pom.xml*.

Outras coisas que o curso mostrou de início, são algumas dependências que podem ajudar durante o desenvolvimento. Entre elas temos a *spring-boot-devtools* que quando instalada vai fazer um processo de watch no projeto e reiniciar o serviço sempre que houver alguma alteração nos arquivos. Algo como o nodemon faz para o NodeJS.

Uma outra dependência é a *spring-boot-starter-actuator* que vai disponibilizar uma série de endpoints “out-of-the-box” com algumas informações sobre o estado da aplicação. Esses endpoints podem ser liberados ou escondidos conforme as configurações das propriedades esperada pela dependência, mas todos eles serão acessados através do recurso `/actuator`.

A terceira dependência é o *spring-boot-starter-security* que vai criar automaticamente uma camada de segurança na aplicação. Assim qualquer endpoint, inclusive todos aqueles do *actuator* passarão a ser protegidos e vão exigir um usuário e senha ao serem acessados. O framework gera uma senha aleatória quando inicia, mas tanto o valor da senha quanto do usuário podem ser configurados no arquivo de propriedades.

Normalmente o XML de uma dependência deve apresentar a informação de **GAV** dessa dependência, mas quando essas são relacionadas ao “Spring Boot”, a informação de versão pode ser omitida, uma vez que no *pom.xml* você tem uma parte indicando o **parent GAV** do framework e com isso o próprio Maven consegue fazer o gerenciamento de versão entre as dependências.

Uma última parte desse arquivo *pom.xml* é a parte de plugins, que vem preenchido com um plugin que pode ser executado através dos arquivos shell que já vem no projeto quando criados via **Spring Initializr**. O projeto vai vir com dois arquivos, o mvnw.cmd para Windows, e o mvnw para sistemas Unix. Esses scripts tornam opcional a instalação da CLI, e permitem que se rode os plugins de forma direta de dentro do diretório do projeto. Em um sistema Unix, para compilar e rodar o projeto, basta rodar:

```bash
# Via plugin
./mvnw spring-boot:run

# Manualmente
./mvnw package
java -jar ./target/<application-file.jar>
```

Outra característica de um projeto “Spring Boot” é arquivo de propriedades que fica em `src/main/resources/application.properties` que pela explicação seria o equivalente a um `.env` com mais algumas outras funções, embora ele não apareça no *.gitignore*. Pelo que entendi, ele vai conseguir alterar alguns valores de configuração já esperados pelo “Spring Boot”, mas também vai aceitar alguns valores personalizados, que podem ser acessados através de um atributo de uma classe através da anotação `@Value("${prop.name}")`.

Na verdade é exatamente isso. Depois de instalar algumas extensões para “Spring Boot” no VSCode (na verdade pode ter sido apenas uma coincidência), o arquivo de propriedades ficou com IntelliSense, então basicamente quando começa a digitar, ele já indica as propriedades configuradas por algumas das dependências do framework. A extensão do “Spring Boot” também tem uma aba que mostra as propriedades em uso, embora não seja possível ver os valores.