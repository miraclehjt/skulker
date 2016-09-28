package PACKAGE4.PACKAGE3.PACKAGE2.PACKAGE1.PACKAGE0;

import TEPHRA.dao.orm.lite.LiteOrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(MODULEModel.NAME + ".dao")
class MODULEDaoImpl implements MODULEDao {
    @Autowired
    protected LiteOrm liteOrm;
}
