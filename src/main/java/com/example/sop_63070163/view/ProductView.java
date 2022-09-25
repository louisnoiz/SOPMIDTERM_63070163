package com.example.sop_63070163.view;

import com.example.sop_63070163.pojo.Product;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Route(value = "/ProductView.it")
public class ProductView extends VerticalLayout {
    private ComboBox<String> productList;
    private TextField name;
    private NumberField cost, profit, price;
    private Button add, update, delete, clear;

    Product present;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ProductView () {
        productList = new ComboBox<>("Product List");
        productList.setWidth("600px");
        name = new TextField("Product Name");
        name.setWidth("600px");
        cost = new NumberField("Product Cost");
        cost.setWidth("600px");
        cost.setValue(0.0);
        profit = new NumberField("Product Profit");
        profit.setWidth("600px");
        profit.setValue(0.0);
        price = new NumberField("Product Price");
        price.setWidth("600px");
        price.setEnabled(false);
        price.setValue(0.0);
        add = new Button("Add Product");
        update = new Button("Update Product");
        delete = new Button("Delete Product");
        clear = new Button("Clear Product");

        HorizontalLayout btn = new HorizontalLayout(add, update, delete, clear);

        this.add(productList, name, cost, profit, price, btn);

        productList.addFocusListener(event -> {
            List<Product> productListShow = (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange","getall","");
//            productList.setItems(productListShow);
            List<String> productsName = new ArrayList<>();
            for (Product pn:productListShow) {
                productsName.add(pn.getProductName());
            }
            productList.setItems(productsName);
        });

        productList.addValueChangeListener(event -> {
            String selectProduct = productList.getValue();
            Product selected = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", selectProduct);
            name.setValue(selected.getProductName());
            cost.setValue(selected.getProductCost());
            profit.setValue(selected.getProductProfit());
            price.setValue(selected.getProductPrice());
        });


        add.addClickListener(event -> {
            double out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/" + cost.getValue() + "/" + profit.getValue())
                    .retrieve()
                    .bodyToMono((double.class))
                    .block();
            price.setValue(out);

            Product newProduct = (new Product(null,name.getValue(), cost.getValue(), profit.getValue(), price.getValue()));
            boolean checkAdd = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "add", newProduct);
            if (checkAdd == true) {
                new Notification("Add Completed", 500).open();
            }
        });

        cost.addKeyPressListener(keyPressEvent -> {
            if (keyPressEvent.getKey().toString().equals("Enter")){
                double out = WebClient.create()
                        .get()
                        .uri("http://localhost:8080/getPrice/" + cost.getValue() + "/" + profit.getValue())
                        .retrieve()
                        .bodyToMono((double.class))
                        .block();
                price.setValue(out);
            }
        });

        profit.addKeyPressListener(keyPressEvent -> {
            if (keyPressEvent.getKey().toString().equals("Enter")){
                double out = WebClient.create()
                        .get()
                        .uri("http://localhost:8080/getPrice/" + cost.getValue() + "/" + profit.getValue())
                        .retrieve()
                        .bodyToMono((double.class))
                        .block();
                price.setValue(out);
            }
        });

        update.addClickListener( event -> {
            double out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/" + cost.getValue() + "/" + profit.getValue())
                    .retrieve()
                    .bodyToMono((double.class))
                    .block();
            price.setValue(out);

            String selectedUpdate = name.getValue();
            Product selected = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", selectedUpdate);
            Product updateProduct = new Product(selected.get_id(), selected.getProductName(), cost.getValue(), profit.getValue(), price.getValue());
//            Product updateProduct = (new Product(present.get_id(),name.getValue(), cost.getValue(), profit.getValue(), price.getValue()));
            boolean checkUpdate = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "update", updateProduct);
            if (checkUpdate == true) {
                new Notification("Update Completed", 500).open();
            }
        });


        delete.addClickListener( event -> {

            String selectedProduct = productList.getValue();
            Product selected = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", selectedProduct);
            boolean checkUpdate = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "delete", selected);
            if (checkUpdate){
                new Notification("delete completed", 500).open();
            }
        });

        clear.addClickListener(event -> {
            name.setValue("");
            cost.setValue(0.0);
            profit.setValue(0.0);
            price.setValue(0.0);
            new Notification("clear completed", 500).open();
        });


    }
}
