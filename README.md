# ECommerce Project - Product Service

### Building REST APIs - 2 [19-10-23]
1. Created the models
2. Created the required DTOs
3. Created the controller
4. Created the service layer
5. Implemented RestTemplate
6. Invoked call to 3rd party (FakeStoreAPI) to get data
7. Returned response from controller


### Building REST APIs - 3 [23-10-23]
1. Construction Injection
2. CRUD APIs - getProductById, getAllProducts, createProduct
3. Updated the service layer for new APIs
4. Wrote a small intro to Controller Advice (Exception Handling)


### Building REST APIs - 4 [26-10-23]
Controller -> RequestDTO, ResponseDTO <br>
CreateProductRequestDTO -> has all attributes except "id" <br>
ProductResponseDTO -> has all the attributes <br>

Client -> RequestDTO, ResponseDTO <br>
FakeStoreCreateProductRequestDTO -> has all attributes except "id" <br>
FakeStoreProductResponseDTO -> has all the attributes <br>
(There can be scenarios where the attributes might be different. 
So, ideally 4 DTOs required)

Ex : YouTube <br>
User uploads a video -> VideoIngestionService -> VideoStore <br>
VideoUploadRequestDTO : controller -> VideoIngestionService
- name
- description
- thumbnail
- bookmarks
- videoFile
- format
- uploadedAt
- uploadedBy

VideoStoreCreateVideoRequestDTO : VideoStoreClient-> VideoStore
- name
- description
- thumbnail
- bookmarks
- videoFile
- format
- uploadedAt
- uploadedBy
- tags
- topics
- constant for algo
- List<Resolutions>

Development cycle -> local, dev (dev instances), 
UAT/QA/staging (prod replica), prod <br>
UAT -> User Acceptance Testing


### Database:Queries,Inheritance,Relations - 4 [31-10-23]
**MappedSuperClass** -> No table for parent class, but individual for each child class.
Child class gets the attributes from parent, and they become columns in child class table.

Configuration Details :<br>
ddl.auto -> create, update, verify <br>
- Create : Everytime you start the application, 
it will drop all the tables and recreate them. High chances of losing data.
- Update : Everytime you start the application, 
only the update changes are implemented.
- Verify : It does not create/update anything, 
it just verifies if the DB has all the tables/columns/mappings as mentioned in the entities. 
-> Generally used in companies, for production

Local development -> create or update<br>
Production -> verify ( generally tables via separate scripts like FlyWay, Liquibase )

CrudRepository is subset of JpaRepository <br>
JpaRepository -> CRUD + Paging + Sorting


### Database:JPA Mapping [07-11-23]
DB Fundamental : **OneToMany** -> Primary Key(PK) of 1 side, goes as Foreign Key(FK) into M side.

**Unidirectional** options :
1. *@ManyToOne* -> By default, PK of 1[Category] side goes as FK into M[Product] side.<br>
2. *@OneToMany* -> By default, creates a mapping table.<br>
With *@JoinColumn* -> Specify PK of 1 side as FK into M side.
Convention for join column "name" -> tableName_idFieldName

**Bidirectional** options :
1. No mapping table, PK of 1 side goes as FK of M side -> @JoinColumn(name = "tableName_idField")
2. Set the owning side of the relationship -> mappedBy<br>
*mappedBy* -> owner class, with the opposite side's owner attribute.<br>
Ex : Product and Category, *category* is owner. <br>
Category attribute at Product side = "category" <br>
Category side:
```java
@OneToMany(mappedBy = "category")
private List<Product> product;
```

**ManyToMany** <br>
*Unidirectional* -> nothing required, single mapping table is generated <br>
*Bidirectional* -> mappedBy is required to generate single mapping table else, will generate 2 mapping tables

1. Create tables
2. Insertion of data -> save() -> upsert (insert and update)
3. Query


### Backend Projects: Implementing Search: Paging, Sorting, Elastic Search [29-01-24]
1. UserServiceClient: connect ProductService with UserService to validateToken


### Backend Projects: Payment Microservice, Webhooks & Crons [15-02-24]
1. interface JpaRepository extends ListPagingAndSortingRepository extends PagingAndSortingRepository > 
findAll(Pageable pageable). PageRequest.of(pageNumber, PageSize) implements Pageable


### Backend Projects: Implementing Search: Paging & Sorting [17-02-24]
1. Paging : SearchController(return Page<ProductResponseDTO>), SearchRequestDto, SearchService <br>
Page object provides more information than List. Ex : totalPages, totalElements, sort etc.
2. Sorting : SortParam(SortParam, sortType); Sort - ascending by default
```java
Sort sort = Sort.by("title").ascending()
                .and(Sort.by("rating").descending());
```


### Backend Projects: Implement Sorting & ElasticSearch [22-02-24]
1. **Amazon OpenSearch Service** (ElasticSearch) : Create a domain, get the endpoint <br>
Standard create, Dev/test, Domain without standby, 1-AZ, General purpose, 
t3.small.search, 1 node, EBS:General Purpose(SSD)-gp3, 10GiB, Public access, master user.
2. **Spring Data OpenSearch** : pom.xml, application.properties, models/Product.java <br>
Document in ElasticSearch => Row in PostgreSQL
```java
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName = "products")
```
3. **OpenSearchProductRepository** extends ElasticsearchRepository extends CrudRepository,PagingAndSortingRepository
4. SearchService : Use OpenSearchProductRepository for optimized search
5. ProductServiceImpl : openSearchProductRepository.save(savedProduct) after Id generated in PostgreSQL(source of truth)
[RedisConfig.java](src/main/java/com/pranshu/ecomproductservice/config/RedisConfig.java)
References : <br>
https://elasticsearch-cheatsheet.jolicode.com/ <br>
https://medium.com/level-up-roadmap/getting-started-with-spring-boot-data-opensearch-3acde70492a8 <br>
https://github.com/opensearch-project/spring-data-opensearch <br>
https://medium.com/@irene-zhou/rdbms-vs-elasticsearch-e1e8dd11818 <br>
https://aws.amazon.com/blogs/architecture/text-analytics-on-aws-implementing-a-data-lake-architecture-with-opensearch/


### Backend Projects: Integrating Cache: Redis [27-02-24]
1. Redis : pom.xml(dependency), RedisConfig (JedisConnectionFactory->RedisConnectionFactory)
2. Run Redis in Docker. ERROR : java.net.UnknownHostException: my-redis: nodename nor servname provided
3. **Amazon ElastiCache** costly, running Redis locally. *brew services start redis* <br>
(Latency reduced from ~ 1000ms to 10ms)
4. FakeStoreProductServiceImpl(@Primary): getProductById method modified to get data from Redis, else FakeStore API.

References : <br>
https://www.baeldung.com/spring-data-redis-tutorial <br>
https://www.docker.com/blog/how-to-use-the-redis-docker-official-image/ <br>
https://aws.amazon.com/elasticache/pricing/


### Backend Project: Spring Cloud [05-03-24]
1. EcomServiceDiscovery project : Eureka server
2. eureka-client dependency, application.properties : eureka.client...=true <br>
   (spring.application.name property is required for Eureka to recognize MS)
2. Edit Configurations > Environment Variables : ${SERVER_PORT} -> dynamic diff values <br>
(Running multiple instances with different ports : Multiple registrations in Eureka server)
3. RestTemplateConfig -> common RestTemplate Bean 
4. Make a call to UserService's URL (directly) to validate inter-MS communication. <br>
(hardcoding the port in URL - not recommended : SPOF) Ex: SelfProductServiceImpl.getProductById
5. http://localhost:9090/users/1 -> http://ecomuserservice/users/1 & RestTemplate @LoadBalanced <br>
(host:port -> application name registered with Eureka : RestTemplate calls Eureka)

References : <br>
https://spring.io/guides/gs/service-registration-and-discovery <br>
https://spring.io/guides/gs/spring-cloud-loadbalancer


### Managing our Microservices: Docker and Kubernetes [14-04-24]
1. Dockerfile created.
2. Create DockerHub account, Check Docker installed & running
3. docker build -t pranshuc1/ecomproductservice:v1.0 .
4. docker run -p 5050:5050 pranshuc1/ecomproductservice:v1.0 (Test in new terminal, without making public)
5. docker push pranshuc1/ecomproductservice:v1.0 (now anyone can run this image in their system)

DockeHub - ready-to-use images

References : <br>
https://www.oracle.com/technical-resources/articles/it-infrastructure/admin-manage-vbox-cli.html <br>
"Understanding the Linux Kernel" book by Daniel P. Bovet and Marco Cesati <br>
Project Module Resume Pointers: https://docs.google.com/document/d/1MVo3lJtNm0AISNAKU2xR9Y1tOWY0KfBtgexdy2SSCec/edit <br>
HLD Prep : https://docs.google.com/spreadsheets/d/1Zed_C4BHrF2LFer1ZVpQivIU947KvWnRzR9EL1iNv_k/edit#gid=0 <br>
HLD Lectures of Scaler, ByteByteGo, YT: System Design Fight Club & Jordan has no life <br>
DSA Prep: https://docs.google.com/spreadsheets/d/1hGbRbMnkmmwbslMD89b6O4yoCW7LRpuEzVKXuzL9wKQ/edit#gid=0


### Kubernetes [21-04-24]
1. Install minikube & start the cluster
2. kubectl get pods
3. kubectl apply -f ProductServiceDeployment.yaml
4. kubectl apply -f ProductService_Service.yaml
5. kubectl delete pod productserviceee-694c4b6895-kbhpt <br>
Immediately a new instance churns up, to keep replica count same.
6. minikube dashboard <br>
GUI : Details of all the deployment & services. Check logs.

New Technology : Read the official documentation, build toy-project, build upon concepts & implement further.

References: <br>
https://minikube.sigs.k8s.io/docs/start/ <br>
https://kubernetes.io/docs/concepts/workloads/controllers/deployment/ <br>
https://kubernetes.io/docs/concepts/services-networking/service/ <br>
https://github.blog/2022-02-02-build-ci-cd-pipeline-github-actions-four-steps/ <br>