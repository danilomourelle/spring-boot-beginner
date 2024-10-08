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

### Rest CRUD

A primeira parte faz toda uma explicação da biblioteca *Jackson* que é quem vai ficar fazendo a conversão entre um dado JSON, que é utilizado na comunicação entre sistemas, e o que é chamado de POJO, que nada mais é que um objeto padrão do Java. Essa biblioteca quando recebe um objeto e precisa traduzir para um JSON, ela vai utilizar os métodos **get** para pegar o dado do objeto. Já nos casos em que ele recebe um JSON e precisa montar o POJO, ele instancia o objeto com o construtor vazio, e depois faz uso dos métodos **set**.

De código mesmo, foi o padrão, onde uma classe vai receber a anotação `@RestController` que é um sub componente, portanto marca a classe como um **bean**. Uma outra anotação que pode acompanhar a classe é a anotação `@RequestMapping` passando como argumento o recurso que esse classe vai representar (uma relação com o Express do JavaScript, é como se essa anotação fosse a marcação que direciona para um roteador).

Dentro dessa classe, você vai ter os métodos onde cada um deles vai representar um endpoint (novamente com a relação, seria a parte interna do roteador). Para isso os métodos vão receber uma anotação relacionada com o método HTTP, sendo as principais as anotações `@GetMapping`, `@PostMapping`, `@PutMapping` e `@DeleteMapping` onde todos eles podem receber uma extensão que vai definir o endpoint dentro do recurso.

Na parte de manipulação de erros, aqui eu ainda estou um pouco confuso, porque tanto esse curso, quanto o do Nélio, eles criam uma classe de erro muito específica para a ocasião, mas sem definir já uma mensagem ou um HTTP status. essa classe vai estender a classe nativa *Runtime Exception*, apenas repassando a mensagem como **super**. Então, onde for identificado uma situação irregular, basta lançar essa exceção. 

Mas para que ela seja devidamente capturada e respondida, é preciso que um método com a anotação `@ExeptionHandler` seja criado, sendo que este método deve ter como argumento o tipo do erro que ele vai manipular, e dentro desse método, eles criam uma instância de uma outra classe que seria o formato de erro padrão da aplicação.

Minha confusão fica no seguinte ponto. Se fosse feito igual tenho nos projetos do SuperApp, onde eu tenho uma classe de exceção básica, que vai estender o erro nativo, mas já dar uma forma para o que vai ser a estrutura final do erro, e depois você cria classes de erros baseada no HTTP status possíveis estendendo o tipo básico, e então nesse método manipulador de erro, você coloca que ele vai receber o tipo básico dessa forma ele pegaria qualquer erro customizado, e na resposta da requisição você apenas envia o erro, sem precisar transformar um em outro.

Como validar as informações de entrada, ou capturar esse erro em expecífico?

Agora a gente está fazendo um outro projeto bem simples de CRUD, com as camadas *Controller, Service* e *DAO*, e com isso a gente tem os três subtipos de **beans**, `@RestController`, `@Service` e `@Repository`. Agora, uma coisa é que por enquanto a gente está apenas com a entidade *Employee*, o que faz com que a gente tenha ela representada nas outras camadas, mas acontece que para o repositório e o serviço, primeiro é feito uma interface, e depois a classe que implementa, mas acontece que com as “boas práticas” de responsabilidade única, porque se cria a interface, se apenas uma classe vai implementar?

Pelo menos para a parte de repositório, é onde vai entrar o que foi visto no curso do Nélio de estender a interface do **JpaRepository** na nossa interface, e o “Spring Boot” vai criar uma instância dessa interface estendida dando métodos básicos para gente. Com isso, do ponto em que o projeto estava, a gente pode excluir o pacote de *DAO* que tinha tanto a interface quanto a implementação, e podemos apenas ter o pacote *Repository* que vai conter as interfaces para cada entidade que vão estender a interface do **JpaRepository**. Ponto interessante é que elas não vão precisar da anotação `@Repository` afinal não são uma classe.

Um outro ponto interessante que apareceu aqui é a dependência `spring-boot-starter-data-rest` que vai gerar o CRUD básico para cada entidade que for utilizada para criar uma interface que instancia o **JpaRepository**. Então, no nosso projeto que tem apenas essas operações para a entidade *Employee*, a gente já nem precisa mais ter o *Controller* e o *Service*. Apenas ter a entidade e a interface do repositório já vai fazer a mágica.

Esses endpoints vão ter como recurso no que seria o nome da entidade em lower case e no plural (como ficaria nomes compostos?), além disso os endpoints vão seguir o padrão HATEOAS que é devolver além dos dados requeridos, uma serie de outros metadados que auxiliam na navegação pela própria API, basicamente é o que acontece na API do TOA.

A parte de gestão de erro, ele não envia um corpo de reposta mas mandou bem um status de erro, quando buscamos por um id não existente, mas quando por exemplo, ao invés de colocar um número, eu coloquei uma letra, voltou um erro com corpo, mas o status 500. Isso provavelmente porque a lib que vai fazer a conversão da string na URL para o tipo numérico esperado lança um erro do tipo `Exception` que é o tipo mais baixo, e essa lib automática deve considerar esse tipo de erro como um erro interno de servidor.

### Rest Security

Aqui voltaremos a falar da dependência `spring-boot-starter-security`. Ela vai automaticamente criar um sistema de autenticação para a API exposta pelo “Spring Boot”. Por padrão, quando tentarmos acessar um endpoint válido, ela irá abrir uma tela de login padrão do framework requisitando um usuário e senha. O usuário padrão é “user” enquanto que a senha vai ser impressa nos logs de inicialização. É possível estabelecer esses dois valores através do arquivo de propriedades.

Essa biblioteca permite que seja feito o gerenciamento dos usuários cadastrados, juntamente com um sistema de autorização por perfil, um RBAC. De início, ele permite que essa definição dos usuários possa ser feito em memória, utilizando o sistema de empacotamento com as anotações `@Configuration` e `@Bean`, através da classe **InMemoryUserDetailsManager**. Embora seja possível fazer o armazenamento desses dados em um banco de dados.

Um detalhe é que na gestão de usuário feita por essa biblioteca, o usuário vai ter um *username* e um *password*, e para o campo da senha, o valor deve ser pré-fixado com um indicador do formato do dado, por exemplo, e a senha está em texto puro, o marcado será `{noop}`, já outra opção mais segura é armazenar o valor como um bcrypt, e portanto o marcador seria `{bcrypt}`.

Agora quanto ao sistema de autorização, nós podemos criar matchers que vão indicar qual perfil está autorizado a acessar um determinado endpoint. Esse matcher pode ser para qualquer método de um determinado caminho, separado por métodos HTTP, e inclusive liberar para uma lista de perfis. 

Pelos testes feitos até o momento, nós temos um comportamento padrão, quando as credenciais estão erradas, o retorno vai ser um erro com status 401, porém sem qualquer corpo, e quando a credencial está correta, mas não tem o perfil liberado para o endpoint, o retorno é um erro de status 403 e também sem nenhuma informação no corpo da resposta.

Para mudar o local de armazenamento dos dados dos usuários da memória da aplicação para um banco de dados, é preciso remover a **InMemoryUserDetailsManager** e trocar por um método que vai retornar um tipo **UserDetailsManager**, como é a classe *JdbcUserDetailManager* que deve receber um *dataSource* no seu construtor que vai ser injetado pelo framework.

Quando utilizando esse formato, ele define alguns padrões para o esquema de banco de dados que ele precisa para fazer o controle. Esses padrão foi definido no script SQL 05. Mas é possível indicar para ele qual deve ser as queries a serem utilizadas para encontrar usuários e perfis, caso seja armazenar essas informações em um esquema diferente do padrão esperado.

Bom, o código mostra como você pode ter tabelas com outros nomes tanto para a tabela em si, quanto para as colunas, mas acontece que nem tudo é tão mágico assim. Aparentemente apesar de poder trocar os nomes, você ainda vai ter a necessidade de fazer uma query onde os registros são três campos, sendo o primeiro campo representando o username, o segundo representando a senha, e o terceiro algum campo que possa ser convertido para booleano para indicar se o usuário está ativo ou não. Então você renomeia, mas fica preso a um certo tipo de padrão (por isso não gosto de frameworks).

### Spring MVC - Thymeleaf

Essa é uma dependência que vai servir para criar templates HTML para que seja feito uma aplicação web com server side rendering. Apesa de poder ser utilizada em um projeto Maven que não seja “Spring Boot”, ele foi pensado para ser utilizado dentro de um projeto “Spring Boot”. Para utilizá-lo, basta adicionar a dependência no `pom.xml`.

```xml
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-thymeleaf</artifactId>
<dependency>
```

A ideia é que nessa integração que acontece automaticamente, você precisa ter um método com a anotação de mapeamento para um endpoint GET. Esse método vai receber como argumento um tipo `Model` do *org.springframework.ui* e esse modelo vai receber atributos em um estilo chave valor. Ao final o endpoint deve retornar uma string, sendo que essa string deve ser o nome do arquivo de template do thymeleaf.

Esse arquivo de template, é um arquivo HTML que vai ficar o caminho *src/main/resources/templates* e nesse template ele vai poder receber algumas anotações específicas do Thymeleaf onde se utiliza as chaves que serão configuradas com valores no modelo.

Ao ser acessado, o endpoint pode processar os dados, configurar um valor dinamicamente no atributo do modelo, e então, ao responder com a string, o Thymeleaf vai buscar pelo template, e realizar as substituições das chaves pelos valores configurados no modelo, e devolver um HTML pronto para ser renderizado pelo browser.

Esse template HTML pode ter o CSS aplicado de algumas formas, sendo tanto por link local quanto remoto. No link local, a gente pode colocar um arquivo CSS comum dentro do caminho  *src/main/resources/static*, dentro desse caminho fica qualquer arquivo estático que vá ser utilizado em uma renderização, como CSS, imagens, JavaScript, etc. Por isso a criação de sub diretórios é liberada a vontade do programador. Vale lembrar que a tag de link deve receber uma anotação própria do Thymeleaf indicando a localização final do CSS.

Fazendo a parte onde ele mostra como se comporta um formulário, foi mostrado como se utiliza os atributos do thymeleaf, mas também foi explicado como funciona os formulários com método GET e com método POST. Em um método GET, ele vai fazer uma requisição GET, mas vai adicionar na URL os dados do formulário como query. Já no método POST, vai colocar as informações no corpo da requisição e não mais na URL.

A vantagem de se usar um GET é que ter as informações na URL faz com que você consiga reproduzir essa mesma consulta sempre que precisar, por exemplo ao enviar para alguém que vai abrir e ter os mesmos dados de formulário. Já a vantagem de ter um POST é a possibilidade de se enviar arquivos binários e também não ter tanto problemas com o tamanho dos dados que vão ser adicionados na URL.

Nos mais variados inputs do HMTL, a ideia principal é que você pode adicionar um atributo `th:field` que vai representar qual o atributo do objeto modelo que vai receber o valor daquele input, sendo que os inputs que podem ter um valor de apresentação de tela, e outro para o valor sistêmico, esse valor sistêmico pode ser definido com o atributo `th:value` enquanto que o valor de tela pode ser definido através do atributo `th:text`. Na verdade esse último pega um valor que vai ser definido dinamicamente pelo Thymeleaf e colocar como o valor entre tags do HTML.

No método que vai receber os dados do formulário, é possível fazer uma validação dos dados através de uma dependência de validação. Quando associada em um método que vai receber os dados de um formulário através da anotação `@Valid` essa validação vai acontecer de forma automática. O xml da dependência que faz essa validação faz parte dos pacotes starter do “Spring Boot”.

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Com essa dependência, é possível adicionar uma série de anotações nos atributos da classe modelo que serão usado na validação (algo parecido com os método da biblioteca Zod). Além disso é possível criar a própria anotações estendendo o pacote para ser utilizado pela dependência de validação. Ao final dessas validações, é possível ter uma lista de erros encontrados, e uma vez tendo erros, re-enviar o HTML do formulário no lugar da página de confirmação.

Esses erros ficam disponíveis na integração do validador com o Thymeleaf para que seja possível mostrar a mensagem de erro após essa nova renderização.

### AOP - Aspect Oriented Program

A ideia desse conceito é quando você tem o seu fluxo de código normal, então a requisição entrando em uma camada, a lógica sendo centralizada em uma camada de serviço, e interações com a fonte de dados em uma camada DAO. Agora imagine que a gente queira aplicar algum código auxiliar entre essas camadas, por exemplo um log para indicar qual o dado está sendo utilizado na query. Você pode apenas embutir isso no método que de fato interage com o banco de dados, mas caso o sistema cresça você vai precisaria incluir esse logs em todos os cases. 

A gente poderia pensar em criar uma classe para gerir esses códigos auxiliares, mas como utilizá-la? Tentar utilizar herança pode também não ser a opção, porque Java não permite múltiplas extensões, então pode não ser mais possível extender. E tentar criar um sistema de injeção de dependência também vai ter seu pontos negativos.

A questão é, imagina que você cria uma classe de log que coloca em todos os DAOs. Agora se por acaso surge mais um código auxiliar, você precisaria voltar em todos os DAOs e injetar mais uma dependência e chamar essas dependência em uma classe de código principal, "sujando" o código.

A sugestão é o que se chama de Aspect Oriented Program (AOP), onde você teria componentes que seriam executados automaticamente em determinadas situações configuráveis. É como se você tivesse um sistema de hooks, onde você configura que para cada parte de um código, algo em outro local deve acontecer sem ter uma chamada explicita para isso.

Essa estratégia dá como vantagem o ponto de que todo código auxiliar fica concentrado em um local, então não precisa depois ficar caçando código relacionado pelos vários DAOs. Outra vantagem é que se esse código não está na sua camada principal, isso vai deixar o código mais limpo. E como comentado, essa é uma ferramenta configurável nessas classes complementares, então por exemplo, caso você já tenha um projeto sem essa estratégia e resolva implementá-la, você teoricamente não precisaria alterar o seu código principal.

Alguns conceitos que existem nesse conceito são:
- **Aspect**: o módulo que vai representar esse agrupamento auxiliar (log, security, audit), acredito que vai ser representado por uma classe;
- **Advice**: a ação que deve ser realizada, acredito que vai ser representada por um método;
- **Join Point**: indicação do momento em que essa ação deve acontecer, me parece que é indicado com uma anotação;
- **Pointcut**: indicação de a quem esse auxiliar vai se ligar, acredito que vai ser um parâmetro da anotação.

Nós podemos indicar como **Join Point** que o **Advice** aconteça antes, após (_finally_), após sucesso, após falha, e ambos antes e após. Já em relação ao **Pointcut** nós temos uma gama de configurações, porém todas elas serão apenas uma string, portanto sem Intellisense. A ideia é que você tem como opções indicar o modificador, retorno, classe, método (com parâmetros) e exceções do alvo do advice. Dessas opções, apenas o retorno e o método são obrigatórios, dessa forma, para os opcionais, caso eles não sejam configurados eles vão ser considerados como wildcards. Por exemplo, se não passarmos o nome da classe, ele vai atuar em todos os métodos de mesmo nome, independente de em qual classe o método esteja.

Além disse, ainda existe uma possibilidade de utilizar um wildcard no nome do método, por exemplo `add*` vai disparar para todo método que inicie com add, como addUser, addAccount... O mesmo valor para o tipo do retorno que é obrigatório, mas aceita o valor `*` para indicar qualquer tipo de retorno.

Um detalhe interessante é que caso a classe seja indicada, não basta apenas colocar o nome da classe, mas sim todo o nome do pacote ao qual ela pertence.

Quando se trata de indicar os parâmetros de um método, você tem como opção colocar `()` para um método sem parâmetro, `(*)` para um método com um parâmetro de qualquer tipo, e `(..)` para métodos com qualquer quantidade de parâmetros de qualquer tipo.

Com todas essas opções de wildcards, a gente consegue montar uma tratativa bem ampla no qual a gente consegue aplicar o advice para qualquer método de um pacote. Para isso basta utilizar `"execution(* com.danmou.beginner.dao.*.*(..))"` que basicamente coloca um wildcard para informação de retorno, de classes no pacote desejado, de método nas classes e de qualquer parametrização que venha a ser utilizada.

É possível criar um **Pointcut** reutilizável com uma anotação. Dessa forma caso você precise aplicá-lo em mais de um advice, não precisa ter um sistema de copia e cola, mas basta referencia o método que tem a anotação. Também é possível fazer a combinação de **Pointcut** utilizando a simbologia de `&& || !` igual ao que se faz em uma expressão `if()`.

Quando você tem um **Aspect** com vários **Advices**, eles serão aplicado em uma ordem indefinida. Mas caso seja necessário implementar uma ordem de execução nos **Advices** (lembre-se que **Aspects** deveriam ser códigos auxiliares e portanto dificilmente precisariam de ordem), o recomendo e separá-los em em **Aspects** diferentes, e então ordenar os **Aspects** com a anotação `@Order` que vai receber um número inteiro.

Esse número vai representar a ordem com que o **Aspect** será executado. **Advices** de um mesmo **Aspect** não serão ordenados. O número das ordenações não precisam seguir um valor sequencial, assim como podem aceitar valores negativos. (Aqui imagino que a melhor forma de numerar é seguindo a estratégia de engenharia de processos, em que se numera de 10 em 10 para que caso o processo tenha um passo acrescentado é possível encaixá-lo sem a necessidade de renumerar todos os passos).

- *Podemos reutilizar o mesmo pointcut em mais de um aspect?* 355
Sim podemos, para isso basta ter o **Pointcut** declarado em algum **Aspect** e quanto for usá-lo em um outro, ao invés de apenas fazer uma referência o nome do método, deve-se utilizar a referência global da classe no projeto.

- *Conseguimos ter acesso aos parâmetros de um método?*
Sim, não só dos parâmetros mas como de alguns outros dados também. Na declaração do método que vai representar o **Advice**, é possível colocar um parâmetro com o tipo *JoinPoint* que vai ser passado automaticamente pelo framework no momento de sua execução. Dentro desse objeto vamos ter alguns métodos que vão tornar possível recuperar informações do método na cadeia principal da aplicação. Por exemplo, para acessar a assinatura da função de disparou o **Advice** nós podemos chamar `(MethodSignature) theJoinPoint.getSignature()`. Já para pegar os argumentos, podemos pegar como um array fazendo `Object[] args = theJoinPoint.getArgs()`. Mas nesse caso, a gente precisa se atentar com os tipos de cada um dos elementos. Aqui no Java também temos o operador `instanceof`.

Caso o **Joint Point** utilizado seja do tipo `@AfterReturning`, o **Advice** pode ser utilizado para pós processamento de dados. Isso quer dizer que ele consegue interceptar os dados de uma função do caminho principal e realizar algum tipo de processamento antes que ele seja devolvido para de fato quem a chamou. Isso parece o tipo de ferramenta muito poderosa, mas que pode ser traiçoeira, afinal você vai ter alguém modificando dados, sem estar explicitamente ligada na linha de processamento. 

Um detalhe experimentando é que na verdade para esse tipo de advice, caso o argumento seja um objeto, o que ele recebe é a referência desse objeto, então alterações nos dados dessa referência, acabam por refletir em quem quer que tenha acesso a essa referência, mas no caso de um argumento um valor primitivo, o advice acaba recebendo uma cópia e portanto qualquer modificação realizada no advice vai ficar restrita a esse escopo. Esse tipo de advice não tem como retornar um valor, mas aparentemente o `@Around` tem, então veremos mais pra frente.

Caso o **Joint Point** utilizado seja do tipo `@AfterThrowing` ele vai funcionar de forma semelhante ao anterior, mas apenas nos casos em que o alvo do advice lançar uma exceção. Da mesma forma é possível ter acesso ao erro para fazer algum tipo de avaliação, ou notificação. Temos que destacar que a exceção não vai ser parada, ela apenas vai ser capturada, lida, mas após o fim da execução do Advice, ela vai seguir o seu caminho. Novamente, parece que o `@Around` consegue parar a propagação da exceção.

Caso o **Joint Point** utilizado seja do tipo `@After` ele vai funcionar de forma semelhante ao `@Before`, mas nesse caso ele vai ser executado depois do alvo, independente do resultado, se sucesso ou se falha. Foi passado que ele não tem acesso à exceção em casos de falha, então ela não pode ser utilizada como parte da lógica, assim como dados de sucesso, uma vez que, de novo, esse advice vai ser executado independente do resultado. 

Caso o **Joint Point** utilizado seja do tipo `@Around` ele funciona como se fosse uma combinação do `@After` com o `@Before`. Pode ser usado para pré e pós processamento de dados, para a cronometrar o um processo específico. Porém, ao invés de ser executado tanto antes como depois do método alvo, o que acontece é que o advice vai ser executado antes do método alvo, e ele vai ter acesso ao _ProceedingJoinPoint_ que é um objeto que vai permitir chamar o método alvo como parte de sua execução e então ter acesso aos seus resultados. É como se ele virasse um envoltório do método alvo. Um detalhe é que como ele chama esse método alvo que pode lançar uma exceção, é interessante que isso seja feito dentro de um bloco try/catch, e nesses casos é possível até que ele intercepte a exceção, podendo apenas propagar ou bloquear. No caso so bloqueio, precisa tomar um certo cuidado caso se queira retornar algo no lugar da exceção, já que o retorno deve ser sempre do mesmo tipo da função alvo, mesmo que o advice indique que vai retornar `Object`.
