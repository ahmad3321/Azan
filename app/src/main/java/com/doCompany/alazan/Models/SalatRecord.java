package com.doCompany.alazan.Models;

public class SalatRecord {
    String Date, Imsak, Fajr, Duha, Dhuhor, Asr, Moghrib, Eshaa;

    public SalatRecord(String date, String imsak, String fajr, String duha, String dhuhor, String asr, String moghrib, String eshaa) {
        Date = date;
        Imsak = imsak;
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
