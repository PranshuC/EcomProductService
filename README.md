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

References : <br>
https://elasticsearch-cheatsheet.jolicode.com/ <br>
https://medium.com/level-up-roadmap/getting-started-with-spring-boot-data-opensearch-3acde70492a8 <br>
https://github.com/opensearch-project/spring-data-opensearch <br>
https://medium.com/@irene-zhou/rdbms-vs-elasticsearch-e1e8dd11818 <br>
https://aws.amazon.com/blogs/architecture/text-analytics-on-aws-implementing-a-data-lake-architecture-with-opensearch/


