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

### Hibernate/JPA

Como já visto no outro curso de Java JPA (Jakarta Persistence  API), é uma especificação que vai definir uma interface que deve ser utilizada por quem quer que venha a criar uma solução “JPA”. Atualmente o *Hibenate* é a principal implementação de JPA e é utilizada por padrão no “Spring Boot”, mas não é a única, também existe a Eclipse Link.

A ideia de uma especificação é que isso tornaria muito natural fazer a troca entre ORMs que implementam a interface do JPA (teoricamente). Outra facilidade que temos é a total abstração de SQL, sendo que qualquer manipulação de dados em um banco de dados vai acontecer através de métodos específicos e não mais através de queries SQL.

Lembrando que interações com o banco de dados em Java costuma usar o JDBC que é uma ferramenta no estilo de um driver que vai realizar a conexão com o banco, gerando um cliente e vai executar queries SQL contra esse banco, então no final das contas nosso código vai usar o ORM *Hibernate* através de métodos definidos pela interface *JPA* sendo que na sua implementação, ele vai se conectar e executar queries no banco de dados através do *JDBC*.

Para utilizar o ORM, assim como foi até agora a gente vai precisar de anotações, e a mais básica delas vai ser a `@Entity` que vai relacionar uma classe Java com uma tabela no banco de dados. Vale lembrar que ao marcar um classe com essa anotação, você fica obrigado e colocar um construtor sem argumentos na classe. Um detalhe é que uma classe sem nenhum construtor declarado, o Java acrescenta esse construtor sem argumento, o que bastaria no caso, mas caso já exista um construtor com argumentos, o Java não faz mais nada, então precisa ser feito a implementação dele manualmente.

Nessa classe você também consegue indicar que os atributos vão se relacionar a colunas da tabela. Uma boa prática é adicionar a anotação `@Column` com o argumento para o “name”, mesmo nos casos em que o nome do atributo seja o mesmo da coluna, pois em futuras manutenções, caso esse atributo seja renomeado, isso não precisará refletir no banco. 

Outra marcação de em atributo é a anotação `@Id` que vai indicar ele como sendo a chave primária da tabela. Em casos de gerenciamento automático, como um auto incremento ou geração de UUID, vai ser natural uma segunda anotação nesse atributo que é o `@GeneratedValue` com o argumento “strategy”. Um detalhe interessante é que ao contrário do outro curso, este aqui não adicionou o id no construtor uma vez que ele vai ser auto gerenciado.

O curso está usando uma estratégia diferente onde ao invés de utilizar a interface *JpaRepository*, a gente vai criar uma interface **DAO**, e então, implementar essa interface em um classe que vai representar o nosso objeto **DAO**. Essa classe vai injetar uma instância do tipo **EntityManager** e essa instância vai trazer uma séria de métodos auxiliares para a manipulação dos dados. Então é como se ela fosse um segundo nível de abstração, onde o **EntityManager** faz a abstração do SQL para linguagem de código, e a classe **DAO** faz a abstração de um código terceiro para um código próprio.

Essa classe **DAO** precisa receber a anotação `@Repository` que vai marcar a classe como um **bean** do “SpringBoot”, precisa ter um construtor para receber a injeção do **EntityRepository** e o método vai precisar receber uma anotação `@Transaction` do springframework para indicar que as queries executadas contra o banco vão ser todas executadas com sucesso para então serem confirmadas. Obviamente essa última anotação vai acontecer apenas nos caso onde vai acontecer uma alteração de dado e não no caso de apenas uma leitura.

Uma forma de utilizar o **EntityManager** para criar queries customizadas contra o banco de dados, é utilizando o método `createQuery(string)` que vai receber um texto muito próximo do que seria um SQL. Por uma característica do *Hibernate* esse texto não precisa do termo “SELECT” e pode iniciar o termo “FROM”, mas uma característica interessante é que por mais que seja um dialeto próximo do SQL, os valores usados nas entidades devem ser os nomes utilizados na classe e não o do banco de dados.

Nessa criação da query, também é possível indicar um placeholder para um valor a ser utilizado prefixando o termo com “:” e após a criação é possível fazer a configuração do valor que deve ser utilizado no lugar do placeholder.

Após todos os ajustes, executa-se a query contra o banco de dados com o método `getResultList()`, e nesses casos a resposta costuma vir em um formato de lista, uma vez que estamos executando um comando de leitura onde podem retornar vários registros.

```java
TypedQuery<Student> query = entityManager
		.createQuery("FROM Student WHERE lastName=:value1", Student.class)
    .setParameter("value1", lastName);
    
    List<Student> students = query.getResultList();
```

Por outro lado é possível criar queries de escrita também de forma customizada, por exemplo, quando se pretende fazer uma atualização em massa, ao invés de acessar todos os objetos, é possível criar uma query customizada, mas nesses casos o comando “UPDATE” é necessário além do fato de que o método que vai executar a query é o `executeUpdate()` que vai ter como retorno a quantidade de linhas afetadas pelo comando.

Uma vez que o *Hibernate* é considerado um ORM, imagina-se que ele possa não só fazer a gestão dos dados mas também da estrutura do banco, então um sistema para criar tabelas como as migrations do Prisma deveria acontecer, e acontece, mas não dessa forma como as migrations. O que acontece é que o *Hibernate* aceita uma configuração que vai indicar se ao ser executado, ele deve interagir com a estrutura do banco de dados de acordo com as anotações presentes no código. Essa configuração fica em `spring.jpa.hibernate.ddl-auto` e ela pode receber os seguinte valores

| none | Não faz nenhuma alteração estrutural |
| --- | --- |
| create-only | Apenas faz a criação de algo que tenha uma anotação, mas não existe no banco de dados |
| drop | Deleta todas as estrutura que existam no banco e tenham uma marcação |
| create | Deleta tudo que tenha uma marcação e recria completamente do zero |
| create-drop | Delete tudo, recria do zero, e ao encerrar volta a deletar tudo que tem uma marcação |
| validate | Valida a estrutura do banco de acordo com as marcações do código |
| update | Realizar atualizações de algo que já exista e foi modificado de acordo com as marcações |

Infelizmente isso parece um pouco complicado de gerenciar e muito fácil de cometer um engano, e como quase tudo deleta os itens do banco parece algo muito perigoso para ser utilizado como uma ferramenta de auxílio no processo de deploy, então basicamente essa configuração vai ficar restrita ao ambiente de desenvolvimento e de testes. É citado uma outra ferramenta para controle de migrations, então há uma luz no fim do túnel.

