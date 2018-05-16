package com.app.service;

import com.app.App;
import com.app.dao.*;
import com.app.dao.generic.DbStatus;
import com.app.dao.generic.DbTables;
import com.app.model.*;
import com.app.model.dto.*;

import javax.persistence.EntityManager;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyServiceImpl implements MyService {

    private Converter converter = new Converter();
    private CustomerDao customerDao = new CustomerDaoImpl();
    private CountryDao countryDao = new CountryDaoImpl();
    private ShopDao shopDao = new ShopDaoImpl();
    private TradeDao tradeDao = new TradeDaoImpl();
    private ErrorsDao errorsDao = new ErrorsDaoImpl();
    private ProducerDao producerDao = new ProducerDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private StockDao stockDao = new StockDaoImpl();
    private PaymentDao paymentDao = new PaymentDaoImpl();
    private CustomerOrderDao customerOrderDao = new CustomerOrderDaoImpl();


    @Override
    public DbStatus addNewCustomer(String name, String surname, Integer age, String countryName) throws Exception {
        if(name == null || surname == null || age == null || countryName == null){
            throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH NULL NAME/SURNAME/AGE/COUNTRY");
        }else if(age < 18){
            throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH AGE <18");
        }
        if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH NON EXISTING COUNTRY IN DB");
        Country country = countryDao.findOneByName(countryName).get();
        Customer customer = converter.fromCustomerDtoToCustomer(new CustomerDto(name, surname, age, converter.fromCountryToCountryDto(country), new HashSet<>()));

        Predicate<Customer> test1 = c -> c.getName().equals(customer.getName());
        Predicate<Customer> test2 = c -> c.getSurname().equals(customer.getSurname());
        Predicate<Customer> test3 = c -> c.getCountry().getCountryName().equals(customer.getCountry().getCountryName());
        if(country.getCustomers().stream().anyMatch(test1.and(test2).and(test3))){
            throw new Exception(DbTables.Customer + ";TRIED TO ADD DUPLICATE CUSTOMER");
        }

        customerDao.add(customer);
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeCustomer(Long id) throws Exception {
        if(!customerDao.findOne(id).isPresent()) throw new Exception(DbTables.Customer + ";TRIED TO REMOVE NON-EXISTENT CUSTOMER");
        customerDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateCustomer(Long id, String name, String surname, Integer age, String countryName) throws Exception {
        if(name == null || surname == null || age == null || countryName == null){
            throw new Exception(DbTables.Customer + ";TRIED TO UPDATE CUSTOMER WITH NULL NAME/SURNAME/AGE/COUNTRY");
        }else if(age < 18){
            throw new Exception(DbTables.Customer + ";TRIED TO UPDATE CUSTOMER WITH AGE <18");
        }
        if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH NON EXISTING COUNTRY IN DB");
        if(!customerDao.findOne(id).isPresent()) throw new Exception((DbTables.Customer + ";TRIED TO UPDATE NON EXISTING CUSTOMER"));
        Country country = countryDao.findOneByName(countryName).get();
        Customer customer = converter.fromCustomerDtoToCustomer(new CustomerDto(name, surname, age, converter.fromCountryToCountryDto(country), new HashSet<>()));

        customer.setId(id);
        customerDao.update(customer);
        return DbStatus.OK;
    }

    @Override
    public String findCustomerUsingID(Long id) throws Exception {
        if(!customerDao.findOne(id).isPresent()) throw new Exception((DbTables.Customer + ";TRIED TO FIND NON EXISTING CUSTOMER"));
        Customer temp = customerDao.findOne(id).get();
        return temp.getId() + "\t" + temp.getName() + "\t" + temp.getSurname() + "\t" + temp.getAge() + "\t" + temp.getCountry().getCountryName();
    }

    @Override
    public String findCustomerUsingNameSurnameCountry(String name, String surname, String countryName) throws Exception {
        if(!customerDao.findOneByNameSurnameCountry(name, surname, countryName).isPresent()) throw new Exception((DbTables.Customer + ";TRIED TO FIND NON EXISTING CUSTOMER"));
        Customer temp = customerDao.findOneByNameSurnameCountry(name, surname, countryName).get();
        return temp.getId() + "\t" + temp.getName() + "\t" + temp.getSurname() + "\t" + temp.getAge() + "\t" + temp.getCountry().getCountryName();
    }

    @Override
    public DbStatus addNewShop(String shopName, String countryName) throws Exception {
        if(shopName == null || countryName == null){
            throw new Exception(DbTables.Shop + ";TRIED TO ADD NEW SHOP WITH NULL SHOPNAME/COUNTRY");
        }else if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Shop + ";TRIED TO ADD NEW SHOP WITH NON EXISTING COUNTRY IN DB");

        CountryDto countryDto = converter.fromCountryToCountryDto(countryDao.findOneByName(countryName).get());
        Shop shopToAdd = converter.fromShopDtoToShop(new ShopDto(shopName, countryDto));
        Predicate<Shop> test1 = s -> s.getName().equals(shopToAdd.getName());
        Predicate<Shop> test2 = s -> s.getCountry().getCountryName().equals(shopToAdd.getCountry().getCountryName());
        if(countryDao.findOneByName(countryName).get().getShops().stream().anyMatch(test1.and(test2))){
            throw new Exception(DbTables.Shop + ";TRIED TO ADD DUPLICATE SHOP");
        }
        shopDao.add(shopToAdd);
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeShop(Long id) throws Exception {
        if(!shopDao.findOne(id).isPresent()) throw new Exception(DbTables.Shop + ";TRIED TO REMOVE NON-EXISTENT SHOP");
        shopDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateShop(Long id, String shopName, String countryName) throws Exception {
        if(shopName == null || countryName == null){
            throw new Exception(DbTables.Shop + ";TRIED TO UPDATE NEW SHOP WITH NULL SHOPNAME/COUNTRY");
        }else if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Shop + ";TRIED TO ADD UPDATE SHOP WITH NON EXISTING COUNTRY IN DB");
        if(!shopDao.findOne(id).isPresent()) throw new Exception("TRIED TO UPDATE NON EXISTING SHOP");
        CountryDto countryDto = converter.fromCountryToCountryDto(countryDao.findOneByName(countryName).get());
        Shop shopToAdd = converter.fromShopDtoToShop(new ShopDto(shopName, countryDto));
        shopToAdd.setId(id);
        shopDao.update(shopToAdd);
        return DbStatus.OK;
    }

    @Override
    public String findShopUsingID(Long id) throws Exception {
        if(!shopDao.findOne(id).isPresent()) throw new Exception(DbTables.Shop + ";TRIED TO FIND NON EXISTING SHOP");
        Shop temp = shopDao.findOne(id).get();
        return temp.getId() + "\t" + temp.getName() + "\t" + temp.getCountry().getCountryName();
    }

    @Override
    public String findShopUsingName(String name) throws Exception {
        if(!shopDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Shop + ";TRIED TO FIND NON EXISTING SHOP");
        Shop temp = shopDao.findOneByName(name).get();
        return temp.getId() + "\t" + temp.getName() + "\t" + temp.getCountry().getCountryName();
    }

    @Override
    public DbStatus addNewProducer(String producerName, String countryName, String tradeName) throws Exception {
        if(producerName == null || countryName == null || tradeName == null){
            throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NULL PRODUCERNAME/COUNTRYNAME/TRADENAME");
        }
        if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NON EXISTING COUNTRY IN DB = " + countryName);
        if(!tradeDao.findOneByName(tradeName).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NON EXISTING TRADE IN DB = " +tradeName);

        Producer producerToAdd = converter.fromProducerDtoToProducer(new ProducerDto(producerName, converter.fromCountryToCountryDto(countryDao.findOneByName(countryName).get()), converter.fromTradeToTradeDto(tradeDao.findOneByName(tradeName).get()), new HashSet<>()));

        Predicate<Producer> test1 = p -> p.getName().equals(producerToAdd.getName());
        Predicate<Producer> test2 = p -> p.getTrade().getName().equals(producerToAdd.getTrade().getName());
        Predicate<Producer> test3 = p -> p.getCountry().getCountryName().equals(producerToAdd.getCountry().getCountryName());
        if(producerDao.findAll().stream().anyMatch(test1.and(test2.and(test3)))){
            throw new Exception(DbTables.Producer + ";TRIED TO ADD DUPLICATE PRODUCER");
        }
        producerDao.add(producerToAdd);
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeProducer(Long id) throws Exception {
        if(!producerDao.findOne(id).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO REMOVE NON-EXISTENT PRODUCER");
        producerDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateProducer(Long id, String producerName, String countryName, String tradeName) throws Exception {
        if(producerName == null || countryName == null || tradeName == null){
            throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NULL PRODUCERNAME/COUNTRYNAME/TRADENAME");
        }
        if(!countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NON EXISTING COUNTRY IN DB = " + countryName);
        if(!tradeDao.findOneByName(tradeName).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO ADD NEW PRODUCER WITH NON EXISTING TRADE IN DB = " +tradeName);
        if(!producerDao.findOne(id).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO UPDATE NON EXISTING PRODUCER");
        Producer producerToAdd = converter.fromProducerDtoToProducer(new ProducerDto(producerName, converter.fromCountryToCountryDto(countryDao.findOneByName(countryName).get()), converter.fromTradeToTradeDto(tradeDao.findOneByName(tradeName).get()), new HashSet<>()));
        producerToAdd.setId(id);
        producerDao.update(producerToAdd);
        return DbStatus.OK;    }

    @Override
    public String findProducerUsingID(Long id) throws Exception {
        if(!producerDao.findOne(id).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO FIND NON EXISTING PRODUCER");
        Producer temp = producerDao.findOne(id).get();
        return temp.getName() + "\t" + temp.getCountry().getCountryName() + "\t" + temp.getTrade().getName();
    }

    @Override
    public String findProducerUsingName(String name) throws Exception {
        if(!producerDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Producer + ";TRIED TO FIND NON EXISTING PRODUCER");
        Producer temp = producerDao.findOneByName(name).get();
        return temp.getName() + "\t" + temp.getCountry().getCountryName() + "\t" + temp.getTrade().getName();
    }

    @Override
    public DbStatus addNewProduct(String productName, String price, String categoryName, String producerName, String producersCountryName, String ... eGuarantee) throws Exception {
        if(productName == null || price == null || producerName == null || producersCountryName == null || categoryName == null){
            throw new Exception(DbTables.Product + ";TRIED TO ADD NEW PRODUCT WITH GIVEN NULL OBJECTS");
        }
        if(!categoryDao.findOneByName(categoryName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING CATEGORY = " + categoryName);
        if(!producerDao.findOneByName(producerName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING PRODUCER = " + producerName);
        if(!countryDao.findOneByName(producersCountryName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING COUNTRY = " + producersCountryName);

        Product product = converter.fromProductDtoToProduct(new ProductDto(productName, new BigDecimal(price),
                converter.fromProducerToProducerDto(producerDao.findOneByName(producerName).get()),
                converter.fromCategoryToCategoryDto(categoryDao.findOneByName(categoryName).get())));
        for (String s: eGuarantee) {
            if(Product.recognizeEGuarantee(s) != null){
                product.addGuarantees(Product.recognizeEGuarantee(s));
            }
        }
        Predicate<Product> test1 = p -> p.getName().equals(product.getName());
        Predicate<Product> test2 = p -> p.getCategory().getName().equals(product.getCategory().getName());
        Predicate<Product> test3 = p -> p.getProducer().getName().equals(product.getProducer().getName());
        if(productDao.findAll().stream().anyMatch(test1.and(test2.and(test3)))) {
            throw new Exception(DbTables.Product + ";TRIED TO ADD DUPLICATE PRODUCT");
        }
        productDao.add(product);
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeProduct(Long id) throws Exception {
        if(!productDao.findOne(id).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO REMOVE NON-EXISTENT PRODUCT");
        productDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateProduct(Long id, String productName, String price, String categoryName, String producerName, String producersCountryName, String... eGuarantee) throws Exception {
        if(producerName == null || price == null || producerName == null || producersCountryName == null || categoryName == null){
            throw new Exception(DbTables.Product + ";TRIED TO UPDATE NEW PRODUCT WITH GIVEN NULL OBJECTS");
        }
        if(!categoryDao.findOneByName(categoryName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO UPDATE NEW PRODUCT WITH NO EXISTING CATEGORY = " + categoryName);
        if(!producerDao.findOneByName(producerName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO UPDATE NEW PRODUCT WITH NO EXISTING PRODUCER = " + producerName);
        if(!countryDao.findOneByName(producersCountryName).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO UPDATE NEW PRODUCT WITH NO EXISTING COUNTRY = " + producersCountryName);

        Product product = converter.fromProductDtoToProduct(new ProductDto(productName, new BigDecimal(price),
                converter.fromProducerToProducerDto(producerDao.findOneByName(producerName).get()),
                converter.fromCategoryToCategoryDto(categoryDao.findOneByName(categoryName).get())));

        for (String s : eGuarantee) {
            if(Product.recognizeEGuarantee(s) != null){
                product.addGuarantee(Product.recognizeEGuarantee(s));
            }
        }
        product.setId(id);
        productDao.update(product);
        return DbStatus.OK;
    }

    @Override
    public String findProductUsingID(Long id) throws Exception {
        if(!productDao.findOne(id).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO FIND NON EXISTING PRODUCT");
        Product temp = productDao.findOne(id).get();
        return temp.getName() + "\t" + temp.getProducer().getName() + "\t" + temp.getPrice() + "\t" + temp.getCategory().getName() + "\tQuantity = " + temp.getQuantityFromStocks();
    }

    @Override
    public String findProductUsingName(String name) throws Exception {
        if(!productDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Product + ";TRIED TO FIND NON EXISTING PRODUCT");
        Product temp = productDao.findOneByName(name).get();
        return temp.getName() + "\t" + temp.getProducer().getName() + "\t" + temp.getPrice() + "\t" + temp.getCategory().getName() + "\tQuantity = " + temp.getQuantityFromStocks();
    }

    @Override
    public DbStatus addNewStock(String productName, String categoryName, String shopName, String countryName, Integer quantity) throws Exception {
        if(!categoryDao.findOneByName(categoryName).isPresent()) throw new Exception(DbTables.Stock + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING CATEGORY = " + categoryName);
        if(!productDao.findOneByName(productName).isPresent()) throw new Exception(DbTables.Stock + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING PRODUCT = " + productName);
        if(!shopDao.findOneByName(shopName).isPresent()) throw new Exception(DbTables.Stock + ";TRIED TO ADD NEW PRODUCT WITH NO EXISTING SHOP = " + shopName);
        if(!productDao.findOneByName(productName).get().getCategory().getName().equals(categoryName)) throw  new Exception(DbTables.Stock + ";TRIED TO ADD NEW STOCK WITH INCORRECT PRODUCT'S CATEGORY = " + categoryName);
            if(!shopDao.findOneByName(shopName).get().getCountry().getCountryName().equals(countryName)) throw  new Exception(DbTables.Stock + ";TRIED TO ADD NEW STOCK WITH INCORRECT SHOP'S COUNTRY = " + countryName + "SHOP NAME = " + shopName);
        if(quantity < 0) throw new Exception(DbTables.Stock + ";TRIED TO ADD NEW PRODUCT WITH INVALID QUANTITY = " +quantity);

        //creating stock from given data
        Stock stock = converter.fromStockDtoToStock(new StockDto(quantity, converter.fromProductToProductDto(productDao.findOneByName(productName).get()), converter.fromShopToShopDto(shopDao.findOneByName(shopName).get())));

        //looking for already existing stock
        Predicate<Stock> test1 = s -> s.getProduct().getName().equals(stock.getProduct().getName());
        Predicate<Stock> test2 = s -> s.getShop().getName().equals(stock.getShop().getName());
        if(stockDao.findAll().stream().anyMatch(test1.and(test2))){
            //if stock exists in DB just increase its quantity
            Stock stockToEdit = stockDao.findAll().stream().filter(test1.and(test2)).findFirst().get();
            stockToEdit.setQuantity(stockToEdit.getQuantity() + quantity);
            stockDao.update(stockToEdit);
            System.out.println("DELIVERY OF " + productName + " IN STOCK OF " + shopName + " | " + countryName + "\nSUPPLIED: " + quantity);
        }else {
            stockDao.add(stock);
        }
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeStock(Long id) throws Exception {
        if(!stockDao.findOne(id).isPresent()) throw new Exception(DbTables.Stock + ";TRIED TO REMOVE UN-EXISTING STOCK");
        stockDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public String findStockUsingID(Long id) throws Exception {
        if(!stockDao.findOne(id).isPresent()) throw new Exception(DbTables.Stock + ";TRIED TO FIND UN-EXISTING STOCK");
        Stock temp = stockDao.findOne(id).get();
        return "QUANTITY = " + temp.getQuantity() + "\t" + temp.getProduct().getName() + "\t" + temp.getShop().getName();
    }

    @Override
    public DbStatus addNewCustomerOrder(String customerName, String customerSurname, String customersCountryName, String productName, String productsCategoryName, Integer quantity, LocalDateTime orderDate, Integer discount, EPayment ePayment) throws Exception {
        Predicate<Customer> test1 = c -> c.getName().equals(customerName);
        Predicate<Customer> test2 = c -> c.getSurname().equals(customerSurname);
        Predicate<Customer> test3 = c -> c.getCountry().getCountryName().equals(customersCountryName);
        if(!customerDao.findAll().stream().anyMatch(test1.and(test2.and(test3)))) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH NO EXISTING CUSTOMER IN DB");
        if(!productDao.findOneByName(productName).isPresent()) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH NO EXISTING PRODUCT");
        if(!productDao.findOneByName(productName).get().getCategory().getName().equals(productsCategoryName)) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH INCORRECT GIVEN PRODUCT'S CATEGORY");

        CustomerOrder customerOrder = converter.fromCustomerOrderDtoToCustomerOrder(
                        new CustomerOrderDto(
                                orderDate,
                                new BigDecimal(discount.toString()),
                                quantity,
                                converter.fromCustomerToCustomerDto(customerDao.findAll().stream().filter(test1.and(test2.and(test3))).findFirst().get()),
                                converter.fromPaymentToPaymentDto(paymentDao.findOneByName(ePayment).get()),
                                converter.fromProductToProductDto(productDao.findOneByName(productName).get())
                        ));

        customerOrderDao.add(customerOrder);
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeOrder(Long id) throws Exception {
        if(!customerOrderDao.findOne(id).isPresent()) throw new Exception(DbTables.Customer_Order + ";TRIED TO REMOVE NON-EXISTENT CUSTOMER_ORDER");
        customerOrderDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateCustomerOrder(Long id, String customerName, String customerSurname, String customersCountryName, String productName, String productsCategoryName, Integer quantity, LocalDateTime orderDate, Integer discount, EPayment ePayment) throws Exception {
        Predicate<Customer> test1 = c -> c.getName().equals(customerName);
        Predicate<Customer> test2 = c -> c.getSurname().equals(customerSurname);
        Predicate<Customer> test3 = c -> c.getCountry().getCountryName().equals(customersCountryName);
        if(!customerDao.findAll().stream().anyMatch(test1.and(test2.and(test3)))) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH NO EXISTING CUSTOMER IN DB");
        if(!productDao.findOneByName(productName).isPresent()) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH NO EXISTING PRODUCT");
        if(!productDao.findOneByName(productName).get().getCategory().getName().equals(productsCategoryName)) throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH INCORRECT GIVEN PRODUCT'S CATEGORY");

        CustomerOrder customerOrder = converter.fromCustomerOrderDtoToCustomerOrder(
                new CustomerOrderDto(
                        orderDate,
                        new BigDecimal(discount.toString()),
                        quantity,
                        converter.fromCustomerToCustomerDto(customerDao.findAll().stream().filter(test1.and(test2.and(test3))).findFirst().get()),
                        converter.fromPaymentToPaymentDto(paymentDao.findOneByName(ePayment).get()),
                        converter.fromProductToProductDto(productDao.findOneByName(productName).get())
                ));

        customerOrder.setId(id);
        customerOrderDao.update(customerOrder);
        return DbStatus.OK;
    }

    @Override
    public String findCustomerOrderUsingID(Long id) throws Exception {
        if(!customerOrderDao.findOne(id).isPresent()) throw new Exception(DbTables.Customer_Order + ";TRIED TO FIND NON EXISTING CUSTOMER_ORDER");
        CustomerOrder temp = customerOrderDao.findOne(id).get();
        return temp.getCustomer().getId() + "\t" + temp.getProduct().getName() + "\t" + temp.getDate() + "\tQuantity: " + temp.getQuantity();
    }

    @Override
    public DbStatus addNewCountry(String countryName) throws Exception {
        if(countryDao.findOneByName(countryName).isPresent()) throw new Exception(DbTables.Country + ";TRIED TO ADD DUPLICATE COUNTRY");
        CountryDto countryDto = new CountryDto(countryName, new HashSet<>(), new HashSet<>(), new HashSet<>());
        countryDao.add(converter.fromCountryDtoToCountry(countryDto));
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeCountry(Long id) throws Exception {
        if(!countryDao.findOne(id).isPresent()) throw new Exception(DbTables.Country + ";TRIED TO REMOVE NON-EXISTENT COUNTRY");
        countryDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateCountry(Long id, String countryName) throws Exception {
        Country country  = converter.fromCountryDtoToCountry(new CountryDto(countryName, new HashSet<>(), new HashSet<>(), new HashSet<>()));
        country.setId(id);
        countryDao.add(country);
        return DbStatus.OK;
    }

    @Override
    public Country findCountryUsingID(Long id) throws Exception {
        if(!countryDao.findOne(id).isPresent()) throw new Exception(DbTables.Country + ";TRIED TO FIND UN-EXISTING COUNTRY");
        Country country = countryDao.findOne(id).get();
        System.out.println(id + "\t" + country.getCountryName());
        return country;
    }

    @Override
    public Country findCountryUsingName(String name) throws Exception {
        if(!countryDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Country + ";TRIED TO FIND UN-EXISTING COUNTRY");
        Country country = countryDao.findOneByName(name).get();
        System.out.println(country.getId() + "\t" + country.getCountryName());
        return country;
    }

    @Override
    public DbStatus addNewTrade(String tradeName) throws Exception {
        if(tradeDao.findOneByName(tradeName).isPresent()) throw new Exception(DbTables.Trade + ";TRIED TO ADD DUPLICATE TRADE");
        TradeDto tradeDto = new TradeDto(tradeName, new HashSet<>());
        tradeDao.add(converter.fromTradeDtoToTrade(tradeDto));
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeTrade(Long id) throws Exception {
        if(!tradeDao.findOne(id).isPresent()) throw new Exception(DbTables.Trade + ";TRIED TO REMOVE NON-EXISTENT Trade ");
        tradeDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateTrade(Long id, String tradeName) throws Exception {
        Trade trade = converter.fromTradeDtoToTrade(new TradeDto(tradeName, new HashSet<>()));
        trade.setId(id);
        tradeDao.add(trade);
        return DbStatus.OK;
    }

    @Override
    public Trade findTradeUsingID(Long id) throws Exception {
        if(!tradeDao.findOne(id).isPresent()) throw new Exception(DbTables.Trade + ";TRIED TO FIND UNEXISTING TRADE");
        Trade trade = tradeDao.findOne(id).get();
        System.out.println(id + "\t" + trade.getName());
        return trade;
    }

    @Override
    public Trade findTradeUsingName(String name) throws Exception {
        if(!tradeDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Trade + ";TRIED TO FIND UNEXISTING TRADE");
        Trade trade = tradeDao.findOneByName(name).get();
        System.out.println(trade.getId() + "\t" + name);
        return trade;
    }

    @Override
    public DbStatus addNewCategory(String categoryName) throws Exception {
        if(categoryDao.findOneByName(categoryName).isPresent()) throw new Exception(DbTables.Category + ";TRIED TO ADD DUPLICATE CATEGORY");
        CategoryDto categoryDto = new CategoryDto(categoryName);
        categoryDao.add(converter.fromCategoryDtoToCategory(categoryDto));
        return DbStatus.OK;
    }

    @Override
    public DbStatus removeCategory(Long id) throws Exception {
        if(!categoryDao.findOne(id).isPresent()) throw new Exception(DbTables.Category + ";TRIED TO REMOVE NON-EXISTENT CATEGORY");
        categoryDao.delete(id);
        return DbStatus.OK;
    }

    @Override
    public DbStatus updateCategory(Long id, String categoryName) throws Exception {
        Category category = converter.fromCategoryDtoToCategory(new CategoryDto(categoryName));
        category.setId(id);
        categoryDao.update(category);
        return DbStatus.OK;
    }

    @Override
    public String findCategoryUsingID(Long id) throws Exception {
        if(!categoryDao.findOne(id).isPresent()) throw new Exception(DbTables.Category + ";TRIED TO FIND UN-EXISTING CATEGORY");
        return categoryDao.findOne(id).get().getId()+ "\t" + categoryDao.findOne(id).get().getName();
    }

    @Override
    public String findCategoryUsingName(String name) throws Exception {
        if(!categoryDao.findOneByName(name).isPresent()) throw new Exception(DbTables.Category + ";TRIED TO FIND UN-EXISTING CATEGORY");
        return categoryDao.findOneByName(name).get().getId()+ "\t" + categoryDao.findOneByName(name).get().getName();
    }



    @Override
    public String getMostExpensiveProductsInEachCategory() {

        StringBuilder sb = new StringBuilder();

        List<Product> items = categoryDao.findAll()
                .stream()
                .map(c -> c.getProducts()
                        .stream()
                        .max(Comparator.comparing(Product::getPrice))
                        .isPresent() ? c.getProducts()
                        .stream()
                        .max(Comparator.comparing(Product::getPrice))
                        .get() : null
                )
                .collect(Collectors.toList());

        for (Product p: items) {
            if(p == null) break;
            sb.append(p.getName() + "|\t" + p.getPrice() +
                    "|\t" + p.getCategory().getName() + "|\t" +
                    p.getProducer().getName() + "|\t" +
                    p.getProducer().getCountry().getCountryName() +
                    "|\tNUMBER OF ORDERS: " + p.getCustomer_order().size() + "\n");
        }
        return sb.toString();
    }

    @Override
    public List<Product> getProductsOrderedByClientsFrom(String countryName, int fromAge, int toAge) throws Exception {
        if(!countryDao.findOneByName(countryName).isPresent() || toAge < fromAge || toAge < 18) {
            throw new Exception("XXXXXX");
        }

        Predicate<CustomerOrder> test1 = o -> o.getCustomer().getCountry().getCountryName().equals(countryName);
        Predicate<CustomerOrder> test2 = o -> o.getCustomer().getAge() >= fromAge && o.getCustomer().getAge() <= toAge;
        Predicate<CustomerOrder> test3 = o -> !o.getCustomer().getCustomer_order().isEmpty();


        List<Product> items;
        items = customerOrderDao
        .findAll()
        .stream()
        .filter(test1.and(test2).and(test3))
        .map(CustomerOrder::getProduct)
        .distinct()
        .collect(Collectors.toList());

        //unhandled exception!?
        //List<ProductDto> itemsDto = items.forEach(p -> converter.fromProductToProductDto(p));

        return items;
    }

    @Override
    public List<Product> getProductsGuaranteedWith(EGuarantee eGuarantee) {

        return productDao.findAll()
                .stream()
                .filter(p -> p.getEGuarantees().contains(eGuarantee))
                .collect(Collectors.toList());
    }

    @Override
    public List<Shop> getShopsContainingForeignProducts() {
        Predicate<Stock> test1 = s -> !s.getShop().getCountry().getCountryName().equals(s.getProduct().getProducer().getCountry().getCountryName());
        return stockDao.findAll()
                .stream()
                .filter(test1)
                .map(Stock::getShop)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<Producer, Integer> getProducersWhoProducedMoreThan(String tradeName, Integer amount) {
        Map<Producer, Integer> producersMap = new HashMap<>();
        //getting all producers with given trade
        //tradeDao.findOneByName(tradeName).get().getProducers().stream().forEach(p -> producersMap.put(p, 0));
        Predicate<Product> test1 = p -> p.getProducer().getTrade().getName().equals(tradeName);
        Predicate<Product> test2 = p -> p.getQuantityFromStocks() + p.getNumberOfSoldProducts() > amount;
        productDao.findAll()
                .stream()
                .filter(test1.and(test2))
                .forEach(p -> producersMap.put(p.getProducer(), p.getQuantityFromStocks() + p.getNumberOfSoldProducts()));


        return producersMap;
    }

    @Override
    public List<CustomerOrder> getOrdersOrderedIn(LocalDateTime fromTime, LocalDateTime toTime) {
        Predicate<CustomerOrder> test1 = c -> c.getDate().isBefore(toTime);
        Predicate<CustomerOrder> test2 = c -> c.getDate().isAfter(fromTime);

        return customerOrderDao.findAll().stream().filter(test1.and(test2)).collect(Collectors.toList());
    }

    @Override
    public Map<Producer, List<Product>> getProductsOrderedByCustomer(String customerName, String customerSurname, String customersCountryName) {
        Predicate<CustomerOrder> test1 = co -> co.getCustomer().getName().equals(customerName) && co.getCustomer().getSurname().equals(customerSurname) && co.getCustomer().getCountry().getCountryName().equals(customersCountryName);
        Map<Producer, List<Product>> groupedProducts = customerOrderDao.findAll()
                .stream()
                .filter(test1)
                .map(CustomerOrder::getProduct)
                .distinct()
                .collect(Collectors.groupingBy(Product::getProducer));
        return groupedProducts;
    }

    @Override
    public String getCustomersWhoOrderedCountryProducts() {
        Predicate<CustomerOrder> test1 = co -> co.getCustomer().getCountry().getCountryName().equals(co.getProduct().getProducer().getCountry().getCountryName());
        Predicate<CustomerOrder> test2 = co -> !co.getCustomer().getCountry().getCountryName().equals(co.getProduct().getProducer().getCountry().getCountryName());
        Map<Customer, List<CustomerOrder>> customerMap = customerOrderDao.findAll()
                .stream()
                .filter(test2)
                .collect(Collectors.groupingBy(CustomerOrder::getCustomer));

        StringBuilder sb = new StringBuilder();


        Customer customer1 = customerDao.findOne(74L).get();
        System.out.println("C-------> " + customer1);
        for (Customer key : customerMap.keySet()) {
            System.out.println("------> " + key);
        }
        Customer customer = new Customer(customer1.getId(), customer1.getName(), customer1.getSurname(), customer1.getAge(), null, null);

        System.out.println(customerMap.containsKey(customer));


        List<Customer> customers = customerOrderDao.findAll()
                .stream()
                .filter(test1)
                .map(CustomerOrder::getCustomer)
                .distinct()
                .collect(Collectors.toList());

        customers.forEach(
                c -> sb.append(c.getName() + " " + c.getSurname() + " " + c.getAge() + " " + c.getCountry().getCountryName() + "\nNUBMER OF ORDERED PRODUCTS FROM FOREIGN COUNTRY: " + (customerMap.containsKey(c) ? customerMap.get(c).size() : 0) + "\n")
        );
        return sb.toString();
    }

    @Override
    public DbStatus dropDataFromAllTables() {
        stockDao.findAll().forEach(stock -> stockDao.delete(stock.getId()));
        customerOrderDao.findAll().forEach(customerOrder -> customerOrderDao.delete(customerOrder.getId()));
        shopDao.findAll().forEach(shop -> shopDao.delete(shop.getId()));
        paymentDao.findAll().forEach(payment -> paymentDao.delete(payment.getId()));
        productDao.findAll().forEach(product -> productDao.delete(product.getId()));
        customerDao.findAll().forEach(customer -> customerDao.delete(customer.getId()));
        producerDao.findAll().forEach(producer -> producerDao.delete(producer.getId()));
        tradeDao.findAll().forEach(trade -> tradeDao.delete(trade.getId()));
        categoryDao.findAll().forEach(category -> categoryDao.delete(category.getId()));
        countryDao.findAll().forEach(country -> countryDao.delete(country.getId()));
        return DbStatus.DELETED;
    }

    @Override
    public DbStatus initializeDataBase() {
        if(paymentDao.findAll().isEmpty()) {
            Payment.generate(true);
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Countries.csv"); Scanner sc = new Scanner(reader)) {
            System.out.println("PROCESSING...");
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                addNewCountry(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Customers.csv"); Scanner sc = new Scanner(reader)) {
            System.out.println("PROCESSING...");
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                addNewCustomer(lines.get(0), lines.get(1), Integer.parseInt(lines.get(2)), lines.get(3));
                lines.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Trades.csv"); Scanner sc = new Scanner(reader)) {
            System.out.println("PROCESSING...");
            while (sc.hasNextLine()){
                addNewTrade(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Producers.csv"); Scanner sc = new Scanner(reader)) {
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                addNewProducer(lines.get(0), lines.get(1), lines.get(2));
                lines.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Categories.csv"); Scanner sc = new Scanner(reader)) {
            while (sc.hasNextLine()){
                addNewCategory(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Products.csv"); Scanner sc = new Scanner(reader)) {
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                addNewProduct(lines.get(0), lines.get(1), lines.get(2), lines.get(3), lines.get(4), lines.get(5));
                lines.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Shops.csv"); Scanner sc = new Scanner(reader)) {
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                addNewShop(lines.get(0), lines.get(1));
                lines.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Stocks.csv"); Scanner sc = new Scanner(reader)) {
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                addNewStock(lines.get(0), lines.get(1), lines.get(2), lines.get(3), Integer.parseInt(lines.get(4)));
                lines.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\Users\\PER01 data\\Orders.csv"); Scanner sc = new Scanner(reader)) {
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()){
                lines.addAll(Arrays.asList(sc.nextLine().split(";")));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if(!lines.isEmpty()) {
                    addNewOrderFromCSV(lines.get(0), lines.get(1), lines.get(2), lines.get(3), Integer.parseInt(lines.get(4)), LocalDateTime.parse(lines.get(5), dtf), Integer.parseInt(lines.get(6)), lines.get(7));
                    lines.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DbStatus.OK;
    }

    @Override
    public void makeLogFile() {
        try(FileWriter writer = new FileWriter("C:\\Users\\PER01 data\\log.txt"); BufferedWriter bw = new BufferedWriter(writer)) {
            List<String> messages = errorsDao.findAll().stream().map(e -> e.getMessage() + "\t" + e.getDate()).collect(Collectors.toList());
            for (String s: messages) {
                bw.write(s);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
