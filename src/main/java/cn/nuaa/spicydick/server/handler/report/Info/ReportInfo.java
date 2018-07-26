package cn.nuaa.spicydick.server.handler.report.Info;

public class ReportInfo {
    public int reportId;        // 报表id
    public String createTime;   // 创建时间
    public String reportUrl;    // 报表pdf的url地址

    public int getReportId() {
        return reportId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

}
