package ${pkg}.${module?lower_case};

import ${tephra}.dao.orm.lite.LiteOrm;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author ${author}
 */
@Repository(${module}Model.NAME + ".dao")
class ${module}DaoImpl implements ${module}Dao {
    @Inject
    private LiteOrm liteOrm;
}
