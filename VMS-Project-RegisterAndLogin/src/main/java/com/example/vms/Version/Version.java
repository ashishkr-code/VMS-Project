package com.example.vms.Version;

public class Version {
    private String versionstart;
    private String versionend;

    public Version() {}

    public Version(String versionstart, String versionend) {
        this.versionstart = versionstart;
        this.versionend = versionend;
    }

    public String getVersionstart() { return versionstart; }
    public void setVersionstart(String versionstart) { this.versionstart = versionstart; }

    public String getVersionend() { return versionend; }
    public void setVersionend(String versionend) { this.versionend = versionend; }

    @Override
    public String toString() {
        return "Version{" +
                "versionStart='" + versionstart + '\'' +
                ", versionEnd='" + versionend + '\'' +
                '}';
    }
}

