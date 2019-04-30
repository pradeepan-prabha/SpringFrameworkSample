/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chakra.rest.test;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;



import org.json.JSONException;

import org.json.JSONObject;
/**
 *
 * @author PRADEEPAN.G
 */
public class TestFCM {
   
final static private String FCM_URL = "https://fcm.googleapis.com/fcm/send";
/**
* Method to send push notification to Android FireBased Cloud messaging Server.
* @param tokenId Generated and provided from Android Client Developer
* @param server_key Key which is Generated in FCM Server 
* @param message which contains actual information.
*/

static void send_FCM_Notification(String tokenId, String server_key, String message){
try{
// Create URL instance.
URL url = new URL(FCM_URL);
// create connection.
HttpURLConnection conn;
conn = (HttpURLConnection) url.openConnection();
conn.setUseCaches(false);
conn.setDoInput(true);
conn.setDoOutput(true);
//set method as POST or GET
conn.setRequestMethod("POST");
//pass FCM server key
conn.setRequestProperty("Authorization","key="+server_key);
//Specify Message Format
conn.setRequestProperty("Content-Type","application/json");
//Create JSON Object & pass value
JSONObject infoJson = new JSONObject();
infoJson.put("title","Here is your notification.");
infoJson.put("body", message);
JSONObject json = new JSONObject();
json.put("to",tokenId.trim());
json.put("notification", infoJson);
OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
wr.write(json.toString());
wr.flush();
int status = 0;
if( null != conn ){
status = conn.getResponseCode();
}
if( status != 0){
if( status == 200 ){
//SUCCESS message
BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
System.out.println("Android Notification Response : " + reader.readLine());
}else if(status == 401){
//client side error
System.out.println("Notification Response : TokenId : " + tokenId + " Error occurred :");
}else if(status == 501){
//server side error
System.out.println("Notification Response : [ errorCode=ServerError ] TokenId : " + tokenId);
}else if( status == 503){
//server side error
System.out.println("Notification Response : FCM Service is Unavailable  TokenId : " + tokenId);
}
}
}catch(MalformedURLException mlfexception){
// Prototcal Error
System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
}catch(IOException mlfexception){
//URL problem
System.out.println("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
}catch(JSONException jsonexception){
//Message format error
System.out.println("Message Format, Error occurred while sending push Notification!.." + jsonexception.getMessage());
}catch (Exception exception) {
//General Error or exception.
System.out.println("Error occurred while sending push Notification!.." + exception.getMessage());
}
}


public static void main(String[] args) {
//Just I am passed dummy information
String tokenId = "e8GJZn1wEMk:APA91bHwMFwa5oJoBd-ygshBMNCRfSXdnNTr0J06OLhFvN3Va5QvoFQIM3L9OG2tRVg6pY4Y8OMi--oUa9iVg2Bl3N4m-Id_CkIG3Fx-tgu-A_dn91Od63K5ugA5maXqZBbaeTeXoSO7";
String server_key = "AIzaSyCGfPxekLfqjDNYBFhO5XfBd0atOu96wXk" ;
String message = "Welcome to FCM Server push notification!.";
//Method to send Push Notification
send_FCM_Notification( tokenId,server_key,message);
}
}
