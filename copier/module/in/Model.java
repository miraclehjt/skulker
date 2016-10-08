package ${pkg}.${module?lower_case};

import ${tephra}.dao.model.Jsonable;
import ${modelSupport}.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
<#if types?? && (types?size>0)>

<#list types as type>
import ${type};
</#list>
</#if>

/**
 * @author lpw
 */
@Component(${module}Model.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = ${module}Model.NAME)
@Table(name = "t<#list packages as pkg><#if (pkg_index>2)>_${pkg}</#if></#list>_${module_name}")
public class ${module}Model extends ModelSupport {
    static final String NAME = "<#list packages as pkg><#if (pkg_index>1)>${pkg}.</#if></#list>${module_name?replace("_","-")}";
<#if columns?? && (columns?size>0)>

<#list columns as column>
    private ${column.javaType} ${column.field}; // ${column.comment}
</#list>
<#list columns as column>

    @Jsonable
    @Column(name = "${column.name}")
    public ${column.javaType} get${column.method}() {
        return ${column.field};
    }

    public void set${column.method}(${column.javaType} ${column.field}) {
        this.${column.field} = ${column.field};
    }
</#list>
</#if>
}
