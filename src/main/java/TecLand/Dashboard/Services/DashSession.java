package TecLand.Dashboard.Services;

import TecLand.Logger.LogService;
import TecLand.ORM.Model.DashUserLogin;
import TecLand.ORM.Model.DashUserLoginHistorical;
import TecLand.ORM.Repository.DashUserLoginHistoricalRepository;
import TecLand.ORM.Repository.DashUserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dashSession")
public class DashSession {
    @Autowired
    private LogService logger;

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    @Autowired
    private DashUserLoginHistoricalRepository dashUserLoginHistoricalRepository;

    public void moveLoginToHistorical(DashUserLogin login) {
        if (null == login) {
            this.logger.info("NullPointer prevented on moveLoginToHistorical()");
            return;
        }
        DashUserLoginHistorical loginHistorical = new DashUserLoginHistorical();
        loginHistorical.setCoordsLat(login.getCoordsLat());
        loginHistorical.setCoordsLng(login.getCoordsLng());
        loginHistorical.setExpended(login.getExpended());
        loginHistorical.setExpires(login.getExpires());
        loginHistorical.setJwt(login.getJwt());
        loginHistorical.setUser(login.getDashUser());
        loginHistorical.setHash(login.getHash());
        if(null == this.dashUserLoginHistoricalRepository.findByJwt(login.getJwt())) {
            this.dashUserLoginHistoricalRepository.save(loginHistorical);
        }
        this.dashUserLoginRepository.delete(login);
    }

    public void setNewLogin(DashUserLogin newLogin) {
        if (null == newLogin) {
            this.logger.info("NullPointer prevented on setNewLogin()");
            return;
        }
        List<DashUserLogin> otherLogins = this.dashUserLoginRepository.findAllByDashUser(newLogin.getDashUser());
        for (DashUserLogin login : otherLogins) {
            this.moveLoginToHistorical(login);
        }
    }
}
