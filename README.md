# Java Library Service Container

Very simple service container (DI)

## Example

**MyService.class**

```java
public class MyService {
  public String bar() {
    return "bar";
  }
}
```

**OtherService.class**

```java
public class OtherService {
  private MyService myService;

  public OtherService (MyService myService) {
    this.myService = myService;
  }

  public String foo() {
    return "foo";
  }
}
```

**MyServiceProvider.class**

```java
public class MyServiceProvider implements ServiceProvider<MyService> {
  @Override
  public Class<MyService> provides() {
    return MyService.class;
  }

  @Override
  public MyService provide(ServiceContainer container) {
    return new MyService();
  }
}
```

**OtherServiceProvider.class**

```java
public class OtherServiceProvider implements ServiceProvider<OtherService> {
  @Override
  public Class<OtherService> provides() {
    return OtherService.class;
  }

  @Override
  public OtherService provide(ServiceContainer container) {
    return new OtherService(container.get(MyService.class));
  }
}
```

**Main.class**

```java
public class Main {
  public static void main(String[] args) {
    ServiceContainer container = new ServiceContainer();

    container.register(new MyServiceProvider());
    container.register(new OtherServiceProvider());

    String results = container.get(OtherService.class).foo();

    System.out.println(results);
  }
}
```