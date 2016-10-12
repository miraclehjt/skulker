package ${pkg}.${module?lower_case};

import ${tephra}.ctrl.context.Request;
import ${tephra}.ctrl.execute.Execute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ${author}
 */
@Controller(${module}Model.NAME + ".ctrl")
@Execute(name = "<#list packages as pkg><#if (pkg_index>2)>/${pkg}</#if></#list>/${module_name?replace("_","-")}/", key = ${module}Model.NAME, code = "0")
public class ${module}Ctrl {
    @Autowired
    protected Request request;
    @Autowired
    protected ${module}Service ${moduleName}Service;
}
