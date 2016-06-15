package cn.rongcapital.mkt.vo.out;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetMainListOut extends BaseOutput {

    public DataGetMainListOut() {}

    public DataGetMainListOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

    private int md_type;

    public int getMd_type() {
        return md_type;
    }

    public void setMd_type(int md_type) {
        this.md_type = md_type;
    }

}

