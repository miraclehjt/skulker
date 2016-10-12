package ${pkg}.${module?lower_case};

import ${tephra}.dao.orm.lite.LiteOrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ${author}
 */
@Repository(${module}Model.NAME + ".dao")
class ${module}DaoImpl implements ${module}Dao {
    @Autowired
    protected LiteOrm liteOrm;
}
