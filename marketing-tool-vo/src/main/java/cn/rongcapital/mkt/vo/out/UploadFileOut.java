package cn.rongcapital.mkt.vo.out;

public class UploadFileOut {

    private String file_path;
    
    private String file_name;
    
    private Long record_count;

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Long getRecord_count() {
        return record_count;
    }

    public void setRecord_count(Long record_count) {
        this.record_count = record_count;
    }
    
    
}
