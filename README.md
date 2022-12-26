## 说明

###导入、导出csv

**使用的jar包**
```xml
<dependency>
    <groupId>net.sf.supercsv</groupId>
    <artifactId>super-csv</artifactId>
    <version>2.4.0</version>
</dependency>
```

Super CSV官网：https://super-csv.github.io/super-csv/index.html

导出:localhost:8091/csv/export  
导入:localhost:8091/csv/import

###导入、导出excel  
**使用的jar包**
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.3</version>
</dependency>
```

EasyExcel是阿里巴巴开源的一个excel处理框架，以使用简单、节省内存著称。
EasyExcel能大大减少占用内存的主要原因是在解析Excel时没有将文件数据一次性全部加载到内存中，而是从磁盘上一行行读取数据，逐个解析。

alibaba-easyexcel与其它框架的区别： 
Apache poi、jxl等处理Excel的框架，他们都存在一个严重的问题就是非常的耗内存。
如果你的系统并发量不大的话可能还行，但是一旦并发上来后一定会OOM或者JVM频繁的full gc。
而EasyExcel采用一行一行的解析模式，并将一行的解析结果以观察者的模式通知处理（AnalysisEventListener）。

easyexcel官网：https://easyexcel.opensource.alibaba.com/docs/current/  

导出:localhost:8092/easyexcel/download  
导入:localhost:8092/easyexcel/upload