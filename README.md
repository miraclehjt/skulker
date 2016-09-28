偷懒工具，主要用于生成开发过程中标准的文件，采用模板替换的方式实现。

# 生成标准功能模块
生成Controller、Model、Service、Dao等文件，并且限定Dao仅允许本包访问。如：
```java
package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @auth lpw
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // 生成标准功能模块。
        Module.copy("HelloWorld", "org.lpw.skulker.demo");
    }
}
```
将自动生成HelloWorldCtrl、HelloWorldModel、HelloWorldService、HelloWorldServiceImpl、HelloWorldDao、HelloWorldDaoImpl等类，其包名为org.lpw.skulker.demo.helloworld。如：
```java
package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @auth lpw
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // 生成标准功能模块。
        Module.copy("HelloWorld", "org.lpw.skulker.demo", "org.lpw.tephra.dao.model");
    }
}
```
