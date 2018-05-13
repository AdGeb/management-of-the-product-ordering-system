package com.app;

import com.app.dao.*;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.EGuarantee;
import com.app.model.Payment;
import com.app.model.Product;
import com.app.model.dto.*;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {



    public static void main(String[] args) {
        ErrorService errorService = new ErrorServiceImpl();
        try {

            System.out.println(EGuarantee.HELP_DESK);
            System.out.println(Product.recognizeEGuarantee("HELP DESK"));
            menu();

        }catch (Exception e){
            errorService.addErrror(DbTables.valueOf(e.getMessage().split(";")[0]), e.getMessage(), LocalDateTime.now());
        }
        DbConnection.getInstance().close();
    }







    public static void menu() {
        ErrorService errorService = new ErrorServiceImpl();
        MyService myService = new MyServiceImpl();
        CountryDao countryDao = new CountryDaoImpl();
        Converter converter = new Converter();
        boolean turnOff = false;
        Scanner sc = new Scanner(System.in);
        Long id = 0L;
        LocalDateTime ldt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<String> lines = new ArrayList<>();

        do {
            System.out.println("1. CUSTOMER");
            System.out.println("2. SHOP");
            System.out.println("3. PRODUCER");
            System.out.println("4. PRODUCT");
            System.out.println("5. ORDERS");
            System.out.println("7. CATEGORY");
            System.out.println("8. TRADE");
            System.out.println("9. COUNTRY");
            System.out.println("10. FILTER");
            System.out.println("11. CONFIG");
            System.out.println("0. EXIT");
            System.out.print("YOUR CHOICE: ");
            Integer choice = sc.nextInt();
            try {
                switch (choice) {

                    //CUSTOMER
                    case 1:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice1 = sc.nextInt();

                        switch (choice1) {
                            case 1:
                                lines.clear();
                                System.out.println("CUSTOMER NAME: ");
                                lines.add(sc.next());
                                System.out.println("CUSTOMER SURNAME: ");
                                lines.add(sc.next());
                                System.out.println("CUSTOMER AGE: ");
                                lines.add(sc.next());
                                sc.nextLine();
                                System.out.println("CUSTOMER COUNTRY: ");
                                lines.add(sc.nextLine());
                                myService.addNewCustomer(lines.get(0), lines.get(1), Integer.parseInt(lines.get(2)), lines.get(3));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("TO REMOVE CUSTOMER ENTER HIS ID: ");
                                lines.add(sc.next());
                                myService.removeCustomer(Long.parseLong(lines.get(0)));
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("ENTER CUSTOMER'S ID: ");
                                lines.add(sc.next());
                                System.out.println("CUSTOMER NAME: ");
                                lines.add(sc.next());
                                System.out.println("CUSTOMER SURNAME: ");
                                lines.add(sc.next());
                                System.out.println("CUSTOMER AGE: ");
                                lines.add(sc.next());
                                sc.nextLine();
                                System.out.println("CUSTOMER COUNTRY: ");
                                lines.add(sc.nextLine());
                                myService.updateCustomer(Long.parseLong(lines.get(0)), lines.get(1), lines.get(2), Integer.parseInt(lines.get(3)), lines.get(4));
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME, SURNAME, COUNTRY");
                                Integer choice14 = sc.nextInt();
                                switch (choice14) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCustomerUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        System.out.println("CUSTOMER NAME: ");
                                        lines.add(sc.next());
                                        System.out.println("CUSTOMER SURNAME: ");
                                        lines.add(sc.next());
                                        sc.nextLine();
                                        System.out.println("CUSTOMER COUNTRY: ");
                                        lines.add(sc.nextLine());
                                        System.out.println(myService.findCustomerUsingNameSurnameCountry(lines.get(0), lines.get(1), lines.get(2)));
                                        break;
                                }
                                break;
                        }
                        break;


                    //SHOP
                    case 2:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice2 = sc.nextInt();
                        switch (choice2) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("SHOP NAME : ");
                                lines.add(sc.nextLine());
                                System.out.println("SHOP'S COUNTRY");
                                lines.add(sc.nextLine());
                                myService.addNewShop(lines.get(0), lines.get(1));
                                break;
                            case 2:
                                lines.clear();
                                System.out.println("TO REMOVE SHOP ENTER HIS ID: ");
                                lines.add(sc.next());
                                myService.removeShop(Long.parseLong(lines.get(0)));
                                break;
                            case 3:
                                lines.clear();
                                System.out.println("ENTER SHOP'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("SHOP NAME : ");
                                lines.add(sc.nextLine());
                                System.out.println("SHOP'S COUNTRY");
                                lines.add(sc.nextLine());
                                myService.updateShop(id, lines.get(0), lines.get(1));
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME, SURNAME, COUNTRY");
                                Integer choice14 = sc.nextInt();
                                switch (choice14) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findShopUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        sc.nextLine();
                                        System.out.println("SHOP NAME: ");
                                        lines.add(sc.nextLine());
                                        System.out.println(myService.findShopUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;

                    //PRODUCER
                    case 3:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice3 = sc.nextInt();
                        switch (choice3) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("PRODUCER NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S TRADE: ");
                                lines.add(sc.nextLine());
                                myService.addNewProducer(lines.get(0), lines.get(1), lines.get(2));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("PRODUCER'S ID: ");
                                id = sc.nextLong();
                                myService.removeProducer(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("PRODUCER'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("PRODUCER NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S TRADE: ");
                                lines.add(sc.nextLine());
                                myService.updateProducer(id, lines.get(0), lines.get(1), lines.get(2));
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME");
                                Integer choice34 = sc.nextInt();
                                switch (choice34) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findProducerUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        sc.nextLine();
                                        System.out.println("PRODUCER NAME: ");
                                        lines.add(sc.nextLine());
                                        System.out.println(myService.findProducerUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;


                    //PRODUCT
                    case 4:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice4 = sc.nextInt();
                        switch (choice4) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("PRODUCT NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRICE: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT'S CATEGORY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("SEVICE / MONEY BACK / HELP DESK / EXCHANGE");
                                sc.nextLine();
                                System.out.println("GUARANTEE: ");
                                lines.add(sc.nextLine());
                                myService.addNewProduct(lines.get(0), lines.get(1), lines.get(2), lines.get(3), lines.get(4));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("PRODUCT'S ID: ");
                                id = sc.nextLong();
                                myService.removeProduct(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("PRODUCT'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("PRODUCT NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRICE: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT'S CATEGORY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCER'S COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("SEVICE / MONEY BACK / HELP DESK / EXCHANGE");
                                sc.nextLine();
                                System.out.println("GUARANTEE: ");
                                lines.add(sc.nextLine());
                                myService.updateProduct(id, lines.get(0), lines.get(1), lines.get(2), lines.get(3), lines.get(4));
                                break;
                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME");
                                Integer choice44 = sc.nextInt();
                                switch (choice44) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findProductUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        sc.nextLine();
                                        System.out.println("PRODUCT NAME: ");
                                        lines.add(sc.nextLine());
                                        System.out.println(myService.findProductUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;

                    //ORDER
                    case 5:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice5 = sc.nextInt();
                        switch (choice5) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("CUSTOMER NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER SURNAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT'S CATEGORY: ");
                                lines.add(sc.nextLine());
                                System.out.println("QUANTITY: ");
                                lines.add(sc.nextLine());
                                System.out.println("DATE(YYYY-MM-DD HH): ");
                                ldt = LocalDateTime.parse(sc.nextLine() + ":00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                System.out.println(ldt);
                                System.out.println("DISCOUNT: ");
                                lines.add(sc.nextLine());
                                System.out.println("METHOD OF PAYMENT: ");
                                lines.add(sc.nextLine());
                                myService.addNewCustomerOrder(lines.get(0), lines.get(1), lines.get(2), lines.get(3), lines.get(4), Integer.parseInt(lines.get(5)), ldt, Integer.parseInt(lines.get(6)), Payment.recognizePaymentMethod(lines.get(7)));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("PRODUCT'S ID: ");
                                id = sc.nextLong();
                                myService.removeOrder(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("ORDER'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("CUSTOMER NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER SURNAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT'S CATEGORY: ");
                                lines.add(sc.nextLine());
                                System.out.println("QUANTITY: ");
                                lines.add(sc.nextLine());
                                System.out.println("DATE(YYYY-MM-DD HH): ");
                                ldt = LocalDateTime.parse(sc.nextLine() + ":00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                System.out.println("DISCOUNT: ");
                                lines.add(sc.nextLine());
                                System.out.println("METHOD OF PAYMENT: ");
                                lines.add(sc.nextLine());
                                myService.updateCustomerOrder(id, lines.get(0), lines.get(1), lines.get(2), lines.get(3), lines.get(4), Integer.parseInt(lines.get(5)), ldt, Integer.parseInt(lines.get(6)), Payment.recognizePaymentMethod(lines.get(7)));
                                break;
                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                Integer choice54 = sc.nextInt();
                                switch (choice54) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCustomerOrderUsingID(Long.parseLong(lines.get(0))));
                                        break;
                                }
                                break;
                        }
                        break;



                    //STOCK
                    case 6:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. FIND BY...");
                        Integer choice6 = sc.nextInt();
                        switch (choice6) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("PRODUCT NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("PRODUCT'S CATEGORY: ");
                                lines.add(sc.nextLine());
                                System.out.println("SHOP NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("SHOP'S COUNTRY: ");
                                lines.add(sc.nextLine());
                                System.out.println("QUANTITY: ");
                                lines.add(sc.nextLine());
                                myService.addNewStock(lines.get(0), lines.get(1), lines.get(2), lines.get(3), Integer.parseInt(lines.get(4)));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("STOCK'S ID: ");
                                id = sc.nextLong();
                                myService.removeStock(id);
                                break;

                            case 3:
                                System.out.println("1. FIND ONE USING ID");
                                Integer choice64 = sc.nextInt();
                                switch (choice64) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findStockUsingID(Long.parseLong(lines.get(0))));
                                        break;
                                }
                                break;
                        }
                        break;

                    //CATEGORY
                    case 7:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice7 = sc.nextInt();
                        switch (choice7) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("CATEGORY NAME: ");
                                lines.add(sc.nextLine());
                                myService.addNewCategory(lines.get(0));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("CATEGORY'S ID: ");
                                id = sc.nextLong();
                                myService.removeCategory(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("CATEGORY'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("CATEGORY'S NAME: ");
                                myService.updateCategory(id, sc.nextLine());
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME");
                                Integer choice74 = sc.nextInt();
                                switch (choice74) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCategoryUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        System.out.println("ENTER NAME: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCategoryUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;

                    //TRADE
                    case 8:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice8 = sc.nextInt();
                        switch (choice8) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("TRADE NAME: ");
                                lines.add(sc.nextLine());
                                myService.addNewTrade(lines.get(0));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("TRADE'S ID: ");
                                id = sc.nextLong();
                                myService.removeTrade(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("TRADE'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("TRADE'S NAME: ");
                                myService.updateCategory(id, sc.nextLine());
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME");
                                Integer choice84 = sc.nextInt();
                                switch (choice84) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findTradeUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        System.out.println("ENTER NAME: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findTradeUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;


                    //TRADE
                    case 9:
                        System.out.println("1. ADD");
                        System.out.println("2. REMOVE");
                        System.out.println("3. UPDATE");
                        System.out.println("4. FIND BY...");
                        Integer choice9 = sc.nextInt();
                        switch (choice9) {
                            case 1:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("COUNTRY NAME: ");
                                lines.add(sc.nextLine());
                                myService.addNewCountry(lines.get(0));
                                break;

                            case 2:
                                lines.clear();
                                System.out.println("COUNTRY'S ID: ");
                                id = sc.nextLong();
                                myService.removeCountry(id);
                                break;

                            case 3:
                                lines.clear();
                                System.out.println("COUNTRY'S ID: ");
                                id = sc.nextLong();
                                sc.nextLine();
                                System.out.println("COUNTRY'S NAME: ");
                                myService.updateCountry(id, sc.nextLine());
                                break;

                            case 4:
                                System.out.println("1. FIND ONE USING ID");
                                System.out.println("2. FIND ONE USING NAME");
                                Integer choice94 = sc.nextInt();
                                switch (choice94) {
                                    case 1:
                                        lines.clear();
                                        System.out.println("ENTER ID: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCountryUsingID(Long.parseLong(lines.get(0))));
                                        break;

                                    case 2:
                                        lines.clear();
                                        System.out.println("ENTER NAME: ");
                                        lines.add(sc.next());
                                        System.out.println(myService.findCountryUsingName(lines.get(0)));
                                        break;
                                }
                                break;
                        }
                        break;


                    //FILTER
                    case 10:
                        System.out.println("1. GET MOST EXPENSIVE PRODUCTS IN EACH CATEGORY");
                        System.out.println("2. GET ALL PRODUCTS ORDERED BY CLIENTS FROM GIVEN COUNTRY IN THE AGE RANGE");
                        System.out.println("3. GET ALL PRODUCTS GUARANTEED WITH");
                        System.out.println("4. GET SHOPS THAT SELLS PRODUCTS FROM FOREIGN COUNTRIES");
                        System.out.println("5. GET PRODUCERS FROM GIVEN TRADE WHO PRODUCED MORE THAN GIVEN AMOUNT");
                        System.out.println("6. GET ORDERS PLACED IN GIVEN TIME PERIOD");
                        System.out.println("7. GET PRODUCTS ORDERED BY GIVEN CUSTOMER");
                        System.out.println("YOUR CHOICE: ");
                        Integer choice101 = sc.nextInt();
                        switch (choice101) {
                            case 1:
                                System.out.println(myService.getMostExpensiveProductsInEachCategory());
                                break;
                            case 2:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("COUNTRY NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("FROM AGE: ");
                                lines.add(sc.nextLine());
                                System.out.println("TO AGE: ");
                                lines.add(sc.nextLine());
                                myService.getProductsOrderedByClientsFrom(lines.get(0), Integer.parseInt(lines.get(1)), Integer.parseInt(lines.get(2))).forEach(p -> System.out.println(p.getId() + "\t" + p.getName()));
                                break;

                            case 3:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("SEVICE / MONEY BACK / HELP DESK / EXCHANGE");
                                System.out.println("GUARANTEED WITH: ");
                                myService.getProductsGuaranteedWith(Product.recognizeEGuarantee(sc.nextLine())).forEach(p -> System.out.println(p.getId() + "\t" + p.getName() + "\t" + p.getEGuarantees()));
                                break;

                            case 4:
                                myService.getShopsContainingForeignProducts().forEach(System.out::println);
                                break;

                            case 5:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("TRADE NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("AMOUNT: ");
                                lines.add(sc.nextLine());
                                myService.getProducersWhoProducedMoreThan(lines.get(0), Integer.parseInt(lines.get(1))).forEach((key, value) -> System.out.println(key.getId() + "\t" + key.getName() + "\t" + key.getTrade().getName() + "\t" + value));
                                break;

                            case 6:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("FROM DATE (YYYY-MM-DD HH): ");
                                LocalDateTime ldt1 = LocalDateTime.parse(sc.nextLine() + ":00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                sc.nextLine();
                                System.out.println("TO DATE (YYYY-MM-DD HH): ");
                                LocalDateTime ldt2 = LocalDateTime.parse(sc.nextLine() + ":00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                myService.getOrdersOrderedIn(ldt1, ldt2).forEach(o -> System.out.println(o.getId() + "\t" + o.getDate() + "\t" + o.getProduct().getName() + "\t" + o.getCustomer().getName() + "\t" + o.getPayment().getPayment()));
                                break;

                            case 7:
                                lines.clear();
                                sc.nextLine();
                                System.out.println("CUSTOMER NAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER SURNAME: ");
                                lines.add(sc.nextLine());
                                System.out.println("CUSTOMER'S COUNTRY NAME: ");
                                lines.add(sc.nextLine());
                                myService.getProductsOrderedByCustomer(lines.get(0), lines.get(1), lines.get(2)).forEach((key, value) -> System.out.println(key.getName() + value));
                                break;
                        }
                        break;



                    //CONFIG
                    case 11:
                        System.out.println("1. INITIALIZE DATABASE");
                        System.out.println("2. MAKE LOG FILE");
                        System.out.println("3. CLEAR ALL TABLES");
                        Integer choice11 = sc.nextInt();
                        switch (choice11){
                            case 1:
                                System.out.println("INITIALIZING...");
                                myService.initializeDataBase();
                                System.out.println("DONE!");
                                break;
                            case 2:
                                System.out.println("CREATING LOG FILE");
                                myService.makeLogFile();
                                break;
                            case 3:
                                System.out.println("REMOVING ALL DATA FROM TABLES...");
                                myService.dropDataFromAllTables();
                                break;
                        }
                        break;
                    case 0:
                        turnOff = true;
                        break;

                    default:
                        System.out.println("1. CUSTOMER");
                        System.out.println("2. SHOP");
                        System.out.println("3. PRODUCER");
                        System.out.println("4. PRODUCT");
                        System.out.println("5. ORDERS");
                        System.out.println("7. CATEGORY");
                        System.out.println("8. TRADE");
                        System.out.println("9. COUNTRY");
                        System.out.println("10. FILTER");
                        System.out.println("11. CONFIG");
                        System.out.println("0. EXIT");
                        System.out.print("YOUR CHOICE: ");
                        choice = sc.nextInt();
                }
            } catch (Exception e) {
                errorService.addErrror(DbTables.valueOf(e.getMessage().split(";")[0]), e.getMessage(), LocalDateTime.now());
            }
        }while (!turnOff);
    }
}
