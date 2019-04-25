# JAVA-Retry-Annotation with GUAVA

### spring-restTemplate for RPC
RestTemplate定义了36个与REST资源交互的方法，其中的大多数都对应于HTTP的方法:

* delete() 在特定的URL上对资源执行HTTP DELETE操作
* exchange() 在URL上执行特定的HTTP方法，返回包含对象的ResponseEntity，这个对象是从响应体中映射得到的，execute()在URL上执行特定的HTTP方法，返回一个从响应体映射得到的对象
* getForEntity() 发送一个HTTP GET请求，返回的ResponseEntity包含了响应体所映射成的对象 
* getForObject() 发送一个HTTP GET请求，返回的请求体将映射为一个对象
* postForEntity() POST 数据到一个URL，返回包含一个对象的ResponseEntity，这个对象是从响应体中映射得到的 
* postForObject() POST 数据到一个URL，返回根据响应体匹配形成的对象
* headForHeaders() 发送HTTP HEAD请求，返回包含特定资源URL的HTTP头
* optionsForAllow() 发送HTTP OPTIONS请求，返回对特定URL的Allow头信息
* postForLocation() POST 数据到一个URL，返回新创建资源的URL 
* put() PUT 资源到特定的URL

### Guava-retry
Guava retryer在支持重试次数和重试频度控制基础上，能够兼容支持多个异常或者自定义实体对象的重试源定义，让重试功能有更多的灵活性。Guava Retryer也是线程安全的，入口调用逻辑采用的是Java.util.concurrent.Callable的call方法

* 入口：java.util.concurrent.Callable
* 重试策略定义：Retryer<String> retry = RetryerBuilder.<String>newBuilder().build();
* 监听接口：RetryListener
* 异常ExecutionException和RetryException

### 基于注解的Guava-retry
类似于spring-retrying，使用Aop完成基于guava-retry的重试注解方式：

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### GUAVA单接口限流

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

#### tips
* @PathVariable 获取URl中"?"之前的参数
* @RequestParam 获取URl中"?"之后key="value"中对应key的value的值