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

### Spring Core

A ideia do framework é ajudar bastante na questão de como subir um servidor web, mas uma característica muito marcante do framework é a habilidade de realizar a inversão de controle (através da injeção de dependência) de forma automática. Toda essa “mágica” é possível através de anotações nas classes, atributos e métodos. Por exemplo, para indicar que uma classe é passível de ser injetada em uma outra, ela precisa receber a anotação `@Component`, e com isso o “Spring Boot” marca essa classe como **bean** (seja lá o que isso quer dizer).

Essa injeção pode ser feita de duas forma, sendo a primeira dela via construtor da classe que vai receber a dependência. Sendo assim, essa classe precisa ter um construtor, esperando receber uma instância da dependência, atribuindo ela a um atributo privado, e anotando esse construtor com `@Autowired`. (Preciso entender como fica no caso de um construtor com outras propriedades).

Outra forma de injeção é através de um método **setter**. Então ao invés de ter um construtor, a classe que irá receber a injeção deve implementar um **setter** para o atributo que vai representar a injeção e anotar esse método com o `@Autowired`. Mas um detalhe legal é que na verdade o método pode ter qualquer nome, não  precisa ser `setAttribute`, o que precisa é ter a anotação mesmo.

Segundo o curso, o “Spring” sugere a injeção via construtor pro padrão e ainda mais nos casos em que a injeção for obrigatória. Já a injeção via **setter** é para os casos onde ela vai ser “opcional” e tiver um comportamento padrão para casos em que não existir. Agora, uma coisa interessante é que a equipe de desenvolvimento não mais recomenda a injeção direta no atributo, que é como foi feito nos cursos do Nélio Alves. Aparentemente esse tipo de injeção funciona, mas deixa bem difícil de realizar testes.

Um detalhe é que por padrão o framework só monitora pacotes que esteja no mesmo ramo da aplicação ou aninhados e ele. Caso tenham pacotes que estejam em ramos paralelos ao do ponto de entrada, a classe principal vai precisar receber uma configuração extra na anotação para avisar que deve monitorar por componentes nesse pacotes também.

```java
package com.danmou.beginner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.danmou.beginner", "com.danmou.util"})
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}
}
```

Essas injeções, como são feitas automaticamente pelo framework, podem ter algumas situações diferentes, por exemplo, se a gente configurar uma dependência com o tipo de uma interface, o “Spring Boot” vai buscar por todos os **beans** que implementem essa interface para poder criar a instância e injetar. Em um caso em que uma interface for implementada por apenas uma classe, isso vai ser bem natural, mas nos casos em que mais de uma classe existir, o “Spring Boot” não sabe qual delas utilizar, e lança um erro na compilação. Para isso existe a anotação `@Qualifiers` que vai servir para indicar qual a classe deve ser utilizada. *Isso me parece meio bizarro, porque se é pra indicar a classe, bastava tipar a injeção no na classe, não?*

Além dessa, existe uma outra alternativa que é a anotação `@Primary` que você coloca em uma das classes que implementam a interface desejada. Isso vai indicar para o framework de que aquela classe é quem tem a preferência, e em momentos de injeção com múltiplas opções, ela deverá ser escolhida. Obviamente apenas uma classe pode apresentar essa marcação, caso contrário, voltamos para o mesmo problema. Essa marcação não impede que seja utilizado um qualificador na classe que vai receber a injeção, e o classificador vai sempre ter prioridade, então basicamente a anotação de primário vai ser útil nos casos em que a classe que receber a injeção não especificar a qualificação.

Por padrão, o “Spring Boot” vai verificar todas as injeções necessárias no lançamento da aplicação e então criar uma instância desses **beans** criando a relação de dependência entre elas. Esse comportamento padrão pode ser alterado ao adicionar a anotação `@Lazy` em algum **bean**, ou para todos os componentes através de uma configuração global no arquivo de propriedades. Tentar configurar a aplicação como “lazy” apenas vai melhorar a inicialização da aplicação, porém como desvantagens, pode levar a erros que serão identificados apenas nos momentos de uso, portanto, essa é uma melhoria da performance que deve ser feita com bastante cuidado.

Em relação a estratégia de criação de um **bean**, por padrão, o framework trabalha com a estratégia de *Singleton*, o que significa que ela cria uma instância para cada **bean** e sempre que houver a necessidade, ela utiliza essa mesma instância. É possível alterar essa estratégia através da anotação `@Scope(ConfigurableBeanFactory.<ENUM>)` onde uma das opções é o *Prototype*, onde para cada injeção, uma instância nova é criada.

Uma instancia de um **bean** vai ter anotações para o ciclo de vida dele (algo muito parecido para os componentes de classe do React), para isso basta você ter anotações em métodos que devam ser executados. No caso de um hook após a criação da instancia, basta adicionar a anotação `@PostConstruct` no método, já para o caso do hook pré destruição, basta adicionar a anotação `@PreDestroy`. **OBS**: para os casos de **beans** com escopo de *prototype*, o hook de destruição não é executado automaticamente.

Como comentado, um **bean** é um classe marcada que vai ser utilizada pelo framework para criar uma instância sempre que ele identificar um injeção de um tipo correspondente. Agora nos casos em que você quer ter um tipo injetado, mas esse tipo não é de uma classe da base de código, mas sim algo de uma biblioteca externa por exemplo, a geração desse tipo provavelmente pode não estar marcada.

Então para utilizar esse tipo como um **bean**, a gente precisa realizar uma estratégia de envolver esse tipo externo em um componente próprio. O curso deu como exemplo a gente criar uma classe com a anotação `@Configuration` e então criar um método nessa classe que vai criar a instância desse tipo terceiro e retornar ele. Nesse método, a gente adiciona a marcação `@Bean` e assim, esse tipo vai ficar disponível como um **bean** através do mesmo nome do método, ou através de um id personalizado adicionado na anotação.

Eu imagino que nada impediria de criar uma classe inteira para isso, mas nesse caso, para cada tipo externo você teria que ter uma classe de embrulho, e com a marcação do método, você consegue ter uma classe de configuração e um **bean** por método dentro dela.