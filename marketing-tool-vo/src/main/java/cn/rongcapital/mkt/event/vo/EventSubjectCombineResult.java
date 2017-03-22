/*************************************************
 * @功能简述: 合并主数据返回结果
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo;

public class EventSubjectCombineResult {
    /**
     * 事件ID
     */
    private String id;

    /**
     * 主数据mid
     */
    private String mid;

    /**
     * 插入或者更新
     */
    private boolean inserted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    @Override
    public String toString() {
        return "EventSubjectCombineResult [id=" + id + ", mid=" + mid + ", inserted=" + inserted + "]";
    }
    
}
