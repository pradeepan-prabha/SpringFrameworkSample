/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chakra.rest.test;

import static com.chakra.rest.test.TestFCM.send_FCM_Notification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.springframework.messaging.Message;

/**
 * REST Web Service
 *
 * @author PRADEEPAN.G
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.chakra.rest.test.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("getText")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {

        try {

            //Just I am passed dummy information
            String tokenId = "e8GJZn1wEMk:APA91bHwMFwa5oJoBd-ygshBMNCRfSXdnNTr0J06OLhFvN3Va5QvoFQIM3L9OG2tRVg6pY4Y8OMi--oUa9iVg2Bl3N4m-Id_CkIG3Fx-tgu-A_dn91Od63K5ugA5maXqZBbaeTeXoSO7";
            String server_key = "AIzaSyCGfPxekLfqjDNYBFhO5XfBd0atOu96wXk";
            String message = "Welcome to FCM Server push notification!.";
            //Method to send Push Notification
            send_FCM_Notification(tokenId, server_key, message);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restfullwebtestdb", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from studentdetails");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
                return rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //TODO return proper representation object
        return "Restful web service is successfull";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("putText")
    @Consumes(MediaType.TEXT_PLAIN)
    public void putXml(String content) {
    }

}
