//package cn.rongcapital.mkt.job;
//
//import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
//import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
//
//public class JobDemo extends AbstractSimpleElasticJob {
//
//	@Override
//	public void process(JobExecutionMultipleShardingContext context) {
//		
//		context.createJobExecutionSingleShardingContext(1);
//		System.out.println("job starts running....");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("job finished");
//	}
//
//}
