package com.api.api.modals;

public class Request {
    public int id;
    public String account_sid;
    public String api_version;
    public String body;
    public String date_created;
    public String date_sent;
    public String date_updated;
    public String direction;
    public String error_code;
    public String error_message;
    public String from;
    public String messaging_service_sid;
    public String num_media;
    public String num_segments;
    public String price;
    public String price_unit;
    public String sid;
    public String status;
    public String subresource_uris;
    public String to;
    public String uri;


     public void add(
             int id,
             String account_sid,
             String api_version,
             String body,
             String date_created,
             String date_sent,
             String date_updated,
             String direction,
             String error_code,
             String error_message,
             String from,
             String messaging_service_sid,
             String num_media,
             String num_segments,
             String price,
             String price_unit,
             String sid,
             String status,
             String subresource_uris,
             String to,
             String uri
     )
     {
        this.id = id;
        this.account_sid = account_sid;
        this.api_version = api_version;
        this.body = body;
        this.date_created = date_created;
        this.date_sent = date_sent;
        this.date_updated = date_updated;
        this.direction = direction;
        this.error_code = error_code;
        this.error_message =  error_message;
        this.from = from;
        this.messaging_service_sid = messaging_service_sid;
        this.num_media = num_media;
        this.num_segments = num_segments;
        this.price = price;
        this.price_unit = price_unit;
        this.sid = sid;
        this.status = status;
        this.subresource_uris = subresource_uris;
        this.to = to;
        this.uri = uri;
     }

}
