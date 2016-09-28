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
        Module.copy("HelloWorld", "org.lpw.skulker.demo", null, null,
                new String[][]{{"c_name", "String"}, {"c_time", "java.sql.Timestamp"}});
    }
}
```
将自动生成HelloWorldCtrl、HelloWorldModel、HelloWorldService、HelloWorldServiceImpl、HelloWorldDao、HelloWorldDaoImpl等类，其包名为org.lpw.skulker.demo.helloworld。

生成的HelloWorldModel如下：
```java
package org.lpw.skulker.demo.helloworld;

import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.tephra.dao.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Component(HelloWorldModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = HelloWorldModel.NAME)
@Table(name = "t_demo_hello_world")
public class HelloWorldModel extends ModelSupport {
    static final String NAME = "skulker.demo.hello-world";

    private String name;
    private Timestamp time;

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = "c_time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
```
