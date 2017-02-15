package ${pkg}.${module?lower_case};

import ${tephra}.ctrl.context.Request;
import ${tephra}.ctrl.execute.Execute;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author ${author}
 */
@Controller(${module}Model.NAME + ".ctrl")
@Execute(name = "<#list packages as pkg><#if (pkg_index>2)>/${pkg}</#if></#list>/${module_name?replace("_","-")}/", key = ${module}Model.NAME, code = "0")
public class ${module}Ctrl {
    @Inject
    private Request request;
    @Inject
    private ${module}Service ${moduleName}Service;
}
