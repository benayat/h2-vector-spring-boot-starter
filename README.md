# In-Memory H2 Vector Spring Boot Starter

The **in-memory-h2vector-spring-boot-starter** is a Spring Boot starter that provides support for vector fields and vector similarity functions using an in-memory H2 database. 
All vector operations are performed with java vector-api, leveraging efficient SIMD operations for vector similarity calculations.
This starter simplifies the integration of vector-based operations into your Spring Boot applications.

## Features
- **In-memory vector database** : Provides an in-memory H2 'extended' database for storing and querying vector fields.
- **Simple integration**: Entities as usual - easily add vector fields to your entities using JPA.
- **Vector Similarity Functions**: Provides pre-configured functions for vector similarity operations. currently supports cosine, and l2 similarity.

## Requirements
- Jdk 22+, with JVM arg `--add-modules=jdk.incubator.vector` added.
## Installation
To include this starter in your Spring Boot project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.benayat</groupId>
    <artifactId>in-memory-h2vector-spring-boot-starter</artifactId>
    <version>0.0.4</version>
</dependency>
```
### Usage example
To define a vector field, you need to annotate a float[] field with @LOB to store the vector as a byte array, and @Convert to use a convert from float[] to byte[] and vice-versa. 
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
and then you can use any JPA repository to query the vector field for k-nearest neighbors using the cosine similarity function, with a native query.

```java
public interface TextRecordRepository extends JpaRepository<TextRecord, Long> {
    @Query(value = "SELECT * FROM text_record record ORDER BY cosine_similarity(record.vector, :vector) DESC LIMIT :k", nativeQuery = true)
    List<TextRecord> findTopKByVectorSimilarity(@Param("vector") byte[] vector, @Param("k") int k);
}
```
### License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/benayat/h2-vector-spring-boot-starter/blob/master/LICENSE.md) file for details.
### Contributing
Contributions are welcome! Please open an issue or submit a pull request for any changes.
### Author
Benaya Trabelsi.  
### contact
email: benaya7@gmail.com
