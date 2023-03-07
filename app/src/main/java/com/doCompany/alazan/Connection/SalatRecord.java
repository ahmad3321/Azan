package com.doCompany.alazan.Connection;

public class SalatRecord {
    String Date, Fajr, Duha, Dhuhor, Asr, Moghrib, Eshaa;

    public SalatRecord(String date, String fajr, String duha, String dhuhor, String asr, String moghrib, String eshaa) {
        Date = date;
        Fajr = fajr;
        Duha = duha;
        Dhuhor = dhuhor;
        Asr = asr;
        Moghrib = moghrib;
        Eshaa = eshaa;
    }

    public String getDate() {
        return Date;
    }

    public String getFajr() {
        return Fajr;
    }

    public String getDuha() {
        return Duha;
    }

    public String getDhuhor() {
        return Dhuhor;
    }

    public String getAsr() {
        return Asr;
    }

    public String getMoghrib() {
        return Moghrib;
    }

    public String getEshaa() {
        return Eshaa;
    }
}
