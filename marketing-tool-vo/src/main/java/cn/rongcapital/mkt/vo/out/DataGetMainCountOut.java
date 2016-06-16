package cn.rongcapital.mkt.vo.out;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetMainCountOut extends BaseOutput {

    public DataGetMainCountOut() {
    }

    public DataGetMainCountOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

}
