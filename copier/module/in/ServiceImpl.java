package ${pkg}.${module?lower_case};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(${module}Model.NAME + ".service")
public class ${module}ServiceImpl implements ${module}Service {
    @Autowired
    protected ${module}Dao ${moduleName}Dao;
}
