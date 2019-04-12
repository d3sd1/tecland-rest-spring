package TecLand.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


@Repository
public class LogRepository {
    @Autowired
    private MongoOperations mongoOperations;

    public LogRepository(MongoOperations mongoOperations) {
        Assert.notNull(mongoOperations, "");
        this.mongoOperations = mongoOperations;

    }

    public List<Log> findAll() {
        List<Log> logs = this.mongoOperations.find(new Query(), Log.class);
        return logs;
    }

    public List<Log> findByLevel(LogLevel level) {
        List<Log> log = this.mongoOperations.find(new Query(Criteria.where("level").is(level)), Log.class);
        return log;
    }

    public void saveLog(Log log) {
        this.mongoOperations.save(log);
    }
}
