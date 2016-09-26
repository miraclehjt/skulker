package PACKAGE4.PACKAGE3.PACKAGE2.PACKAGE1.PACKAGE0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(MODULEModel.NAME + ".service")
public class MODULEServiceImpl implements MODULEService {
    @Autowired
    protected MODULEDao mODULEDao;
}
