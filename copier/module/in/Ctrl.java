package ${pkg}.${module?lower_case};

import ${tephra}.ctrl.execute.Execute;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(${module}Model.NAME + ".ctrl")
@Execute(name = "<#list packages as pkg><#if (pkg_index>2)>/${pkg}</#if></#list>/${module_name?replace("_","-")}/", code = "0")
public class ${module}Ctrl {
}
