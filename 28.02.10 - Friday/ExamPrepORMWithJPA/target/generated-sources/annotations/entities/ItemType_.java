package entities;

import entities.OrderLine;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-28T14:46:49")
@StaticMetamodel(ItemType.class)
public class ItemType_ { 

    public static volatile ListAttribute<ItemType, OrderLine> orderLines;
    public static volatile SingularAttribute<ItemType, Integer> price;
    public static volatile SingularAttribute<ItemType, String> name;
    public static volatile SingularAttribute<ItemType, String> description;
    public static volatile SingularAttribute<ItemType, Integer> id;

}