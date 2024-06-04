# In-Memory H2 Vector Spring Boot Starter

The **in-memory-h2vector-spring-boot-starter** is a Spring Boot starter that provides support for vector fields and vector similarity functions using an in-memory H2 database. 
This starter simplifies the integration of vector-based operations into your Spring Boot applications.

## Features

- ** In-memory vector database ** : Provides an in-memory H2 'extended' database for storing and querying vector fields.
- **Simple integration**: Entities as usual - easily add vector fields to your entities using JPA.
- **Vector Similarity Functions**: Provides pre-configured functions for vector similarity operations. currently supports only cosine similarity.

## Installation
To include this starter in your Spring Boot project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>in-memory-h2vector-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
## Usage example
```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class TextRecord {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Lob
    @Convert(converter = FloatArrayToByteArrayConverter.class)
    private float[] vector;
    private String text;
}
```
and then you can use any JPA repository to query the vector field for k-nearest neighbors using the cosine similarity function.

```java
public interface TextRecordRepository extends JpaRepository<TextRecord, Long> {
    @Query(value = "SELECT * FROM text_record record ORDER BY cosine_similarity(record.vector, :vector) DESC LIMIT :k", nativeQuery = true)
    List<TextRecord> findTopKByVectorSimilarity(@Param("vector") byte[] vector, @Param("k") int k);
}
```
