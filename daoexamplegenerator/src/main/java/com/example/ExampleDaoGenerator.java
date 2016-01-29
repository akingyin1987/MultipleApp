package com.example;

import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Created by zlcd on 2016/1/17.
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        System.out.println("ExampleDaoGenerator->>>>>2222222222111");
         byte[]  data = ConversionUtil.hexStringToBytes("2000000023000000104010F1D04010B2");

         byte[]  data2= ConversionUtil.hexStringToBytes("12474051991485C1000488F09BB061BA");
         for(int i=0;i<data.length;i++){
             System.out.println("data1="+data[i]+":data2="+data2[i]);
         }

         System.out.println("hex="+ConversionUtil.decode("2000000023000000104010F1D04010B2"));
//        Schema schema = new Schema(1000, "com.md.db");
//
//        addNote(schema);
//        addCustomerOrder(schema);
//
//        new DaoGenerator().generateAll(schema, "./appdemo/src/main/java-gen");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addContentProvider();
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addContentProvider();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.addContentProvider();
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
