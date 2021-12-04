package com.rmoralessolo2016.dcrypt;

public class cryptoMG {
    private String coin_Name, coin_short_name, coin_price, coin_icon_url;

    public cryptoMG(){

    }

    public cryptoMG (String coin_Name, String coin_short_name, String coin_price, String coin_icon_url ){
        this.coin_Name = coin_Name;
        this.coin_short_name = coin_short_name;
        this.coin_price = coin_price;
        this.coin_icon_url = coin_icon_url;
    }

    public String getCoin_Name() {
        return coin_Name;
    }

    public void setCoin_Name(String coin_Name) {
        this.coin_Name = coin_Name;
    }

    public String getCoin_short_name() {
        return coin_short_name;
    }

    public void setCoin_short_name(String coin_short_name) {
        this.coin_short_name = coin_short_name;
    }

    public String getCoin_price() {
        return coin_price;
    }

    public void setCoin_price(String coin_price) {
        this.coin_price = coin_price;
    }

    public String getCoin_icon_url() {
        return coin_icon_url;
    }

    public void setCoin_icon_url(String coin_icon_url) {
        this.coin_icon_url = coin_icon_url;
    }
} //end of cryptoMG
