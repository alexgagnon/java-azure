package org.example;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class RedisBackedCacheIntTest {

  // container {
  @Container
  public GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("postgres")).withExposedPorts(5432);
  // }

  @BeforeEach
  public void setUp() {
    String address = redis.getHost();
    Integer port = redis.getFirstMappedPort();

    System.out.println(String.format("%s:%s", address, port));

    // Now we have an address and port for Redis, no matter where it is running
    // underTest = new RedisBackedCache(address, port);
  }

  @Test
  public void testSimplePutAndGet() {
    assertEquals("example", "example");
  }
}
