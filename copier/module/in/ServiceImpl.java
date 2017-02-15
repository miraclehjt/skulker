package ${pkg}.${module?lower_case};

import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author ${author}
 */
@Service(${module}Model.NAME + ".service")
public class ${module}ServiceImpl implements ${module}Service {
    @Inject
    private ${module}Dao ${moduleName}Dao;
}
