package com.example;

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
        Schema schema = new Schema(1000, "com.md.db");

        addNote(schema);
        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "./appdemo/src/main/java-gen");
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
