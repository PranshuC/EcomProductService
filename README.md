# ECommerce Project - Product Service

### Building REST APIs - 2 [19-10-23]
1. Created the models
2. Created the required DTOs
3. Created the controller
4. Created the service layer
5. Implemented RestTemplate
6. Invoked call to 3rd party (FakeStoreAPI) to get data
7. Returned response from controller

### Building REST APIs - 3 [23-10-23] -> CRUD APIs, Exception Handling
1. Construction Injection
2. CRUD APIs - getProductById, getAllProducts, createProduct
3. Updated the service layer for new APIs
4. Wrote a small intro to Controller Advice

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