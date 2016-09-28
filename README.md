偷懒（代码生成）工具，主要用于生成开发过程中标准的文件，采用模板替换的方式实现。

# 生成标准功能模块
生成Controller、Model、Service、Dao、DDL等文件，并且限定Dao仅允许本包访问。如：
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
                new String[][]{{"c_name", "VARCHAR(255)", "k", "名称"},
                        {"c_type", "INT", "", "类型"},
                        {"c_time", "DATETIME", "", "时间"}});
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

    private String name; // 名称
    private int type; // 类型
    private Timestamp time; // 时间

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = "c_type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
生成的DDL如下：
```sql
DROP TABLE IF EXISTS t_demo_hello_world;
CREATE TABLE t_demo_hello_world
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_name VARCHAR(255) NOT NULL COMMENT '名称',
  c_type INT DEFAULT 0 COMMENT '类型',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk_demo_hello_world(c_id),
  KEY k_demo_hello_world_name(c_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
