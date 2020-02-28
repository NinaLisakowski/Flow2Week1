package entities;

import entities.Customer;
import entities.OrderLine;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-28T14:46:49")
@StaticMetamodel(PurchaseOrder.class)
public class PurchaseOrder_ { 

    public static volatile ListAttribute<PurchaseOrder, OrderLine> orderLines;
    public static volatile SingularAttribute<PurchaseOrder, Integer> orderId;
    public static volatile SingularAttribute<PurchaseOrder, Integer> id;
    public static volatile SingularAttribute<PurchaseOrder, Customer> customer;

}