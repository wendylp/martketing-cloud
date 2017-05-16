/**
 * 
 */
package cn.rongcapital.mkt.job.service.base;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.DBObject;

/**
 * @author zhaohai
 *
 */
public class PatchAggregationOperation implements AggregationOperation {
    private DBObject operation;

    public PatchAggregationOperation(DBObject operation) {
        this.operation = operation;
    }

    @Override
    public DBObject toDBObject(AggregationOperationContext context) {
        return context.getMappedObject(operation);
    }
}
