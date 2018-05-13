package com.app.model.dto;


import com.app.dao.generic.DbTables;
import com.app.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Converter {

    public CustomerDto fromCustomerToCustomerDto(Customer customer) throws Exception {
        if(customer != null) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setSurname(customer.getSurname());
            customerDto.setAge(customer.getAge());
            customerDto.setCountryDto(fromCountryToCountryDto(customer.getCountry()));
            customerDto.setCustomerOrdersDto(new HashSet<>());
            return customerDto;
        } else throw new Exception(DbTables.Customer + ";TRIED TO CONVERT FROM GIVEN NULL CUSTOMER");
    }

    public Customer fromCustomerDtoToCustomer(CustomerDto customerDto) throws Exception {
        if(customerDto != null) {
            Customer customer = new Customer();
            customer.setId(customerDto.getId());
            customer.setName(customerDto.getName());
            customer.setSurname(customerDto.getSurname());
            customer.setAge(customerDto.getAge());
            customer.setCountry(fromCountryDtoToCountry(customerDto.getCountryDto()));
            customer.setCustomer_order(new HashSet<>());
            return customer;
        }else throw new Exception(DbTables.Customer + ";TRIED TO CONVERT FROM NULL CUSTOMERDTO");
    }

    public CountryDto fromCountryToCountryDto(Country country) throws Exception {
        if(country != null) {
            CountryDto countryDto = new CountryDto();
            countryDto.setId(country.getId());
            countryDto.setCountryName(country.getCountryName());
            countryDto.setCustomersDto(new HashSet<>());
            countryDto.setCustomersDto(new HashSet<>());
            countryDto.setShopsDto(new HashSet<>());
            return countryDto;
        } else throw new Exception(DbTables.Country +";TRIED TO CONVERT FROM NULL COUNTRYDTO");
    }

    public Country fromCountryDtoToCountry(CountryDto countryDto) throws Exception {


        if(countryDto != null){
            Country country = new Country();
            country.setId(countryDto.getId());
            country.setCountryName(countryDto.getCountryName());
            country.setCustomers(new HashSet<>());
            country.setProducers(new HashSet<>());
            country.setShops(new HashSet<>());
            return country;
        }else throw new Exception(DbTables.Country + ";TRIED DO CONVERT FROM NULL COUNTRYDTO");
    }

    public CustomerOrderDto fromCustomerOrderToCustomerOrderDto(CustomerOrder customerOrder) throws Exception {
        if(customerOrder != null){
            CustomerOrderDto customerOrderDto = new CustomerOrderDto();
            customerOrderDto.setId(customerOrder.getId());
            customerOrderDto.setDate(customerOrder.getDate());
            customerOrderDto.setQuantity(customerOrder.getQuantity());
            customerOrderDto.setDiscount(customerOrder.getDiscount());
            customerOrderDto.setPaymentDto(fromPaymentToPaymentDto(customerOrder.getPayment()));
            customerOrderDto.setCustomerDto(fromCustomerToCustomerDto(customerOrder.getCustomer()));
            customerOrderDto.setProductDto(fromProductToProductDto(customerOrder.getProduct()));
            return customerOrderDto;
        }else throw new Exception(DbTables.Customer_Order + ";TRIED TO CONVERT FROM GIVEN NULL CUSTOMERORDER");
    }

    public CustomerOrder fromCustomerOrderDtoToCustomerOrder(CustomerOrderDto customerOrderDto) throws Exception {
        if(customerOrderDto != null){
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setId(customerOrderDto.getId());
            customerOrder.setDate(customerOrderDto.getDate());
            customerOrder.setProduct(fromProductDtoToProduct(customerOrderDto.getProductDto()));
            customerOrder.setQuantity(customerOrderDto.getQuantity());
            customerOrder.setDiscount(customerOrderDto.getDiscount());
            customerOrder.setPayment(fromPaymentDtoToPayment(customerOrderDto.getPaymentDto()));
            customerOrder.setCustomer(fromCustomerDtoToCustomer(customerOrderDto.getCustomerDto()));
            return customerOrder;
        }else throw new Exception(DbTables.Customer_Order + ";TRIED TO CONVERT FROM GIVEN NULL CUSTOMERORDERDTO");

    }

    public PaymentDto fromPaymentToPaymentDto(Payment payment) throws Exception {
        if(payment != null){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setePayment(payment.getPayment());
            paymentDto.setPaymentHistory(new HashSet<>());
            return paymentDto;
        } else throw new Exception(DbTables.Payment + ";TRIED TO CONVERT FROM GIVEN NULL PAYMENT");
    }

    public Payment fromPaymentDtoToPayment(PaymentDto paymentDto) throws Exception {
        if(paymentDto != null){
            Payment payment = new Payment();
            payment.setId(paymentDto.getId());
            payment.setPayment(paymentDto.getEPayment());
            payment.setCustomer_order(new HashSet<>());
            return payment;
        }else throw new Exception(DbTables.Payment + ";TRIED TO CONVERT FROM GIVEN NULL PAYMENTDTO");
    }

    public ProductDto fromProductToProductDto(Product product) throws Exception {
            if (product != null) {
                ProductDto productDto = new ProductDto();
                productDto.setId(product.getId());
                productDto.setName(product.getName());
                productDto.setPrice(product.getPrice());
                productDto.setProducerDto(fromProducerToProducerDto(product.getProducer()));
                productDto.setCategoryDto(fromCategoryToCategoryDto(product.getCategory()));
                productDto.addStocksDto(prepareStockDtoList(product.getStocks(), productDto));
                return productDto;
            } else throw new Exception(DbTables.Product + ";TRIED TO CONVERT WITH GIVEN NULL PRODUCT");
    }

    public Product fromProductDtoToProduct(ProductDto productDto) throws Exception {
        if(productDto != null){
            Product product = new Product();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setProducer(fromProducerDtoToProducer(productDto.getProducerDto()));
            product.setCategory(fromCategoryDtoToCategory(productDto.getCategoryDto()));
            product.addStocks(prepareStockList(productDto.getStocksDto(), product));
            return product;
        } else throw new Exception(DbTables.Product + ";TRIED TO CONVERT WITH GIVEN NULL PRODUCTDTO");
    }

    public List<StockDto> prepareStockDtoList(Set<Stock> stocks, ProductDto productDto) throws Exception {
        List<StockDto> stocksDto = null;
        if(stocks != null && !stocks.isEmpty()){
            stocksDto = new ArrayList<>();
            for (Stock s: stocks) {
                StockDto stockDto = new StockDto();
                stockDto.setId(s.getId());
                stockDto.setQuantity(s.getQuantity());
                stockDto.setShopDto(fromShopToShopDto(s.getShop()));
                stockDto.setProductDto(productDto);
                stocksDto.add(stockDto);
            }
        }
        return stocksDto;
    }

    public List<Stock> prepareStockList(Set<StockDto> stocksDto, Product product) throws Exception {
        List<Stock> stocks = null;
        if(stocksDto != null && !stocksDto.isEmpty()){
            stocks = new ArrayList<>();
            for (StockDto s: stocksDto) {
                Stock stock = new Stock();
                stock.setId(s.getId());
                stock.setQuantity(s.getQuantity());
                stock.setShop(fromShopDtoToShop(s.getShopDto()));
                stock.setProduct(product);
                stocks.add(stock);
            }
        }
        return stocks;
    }

    public ProducerDto fromProducerToProducerDto(Producer producer) throws Exception {
        if(producer != null){
            ProducerDto producerDto = new ProducerDto();
            producerDto.setId(producer.getId());
            producerDto.setName(producer.getName());
            producerDto.setCountryDto(fromCountryToCountryDto(producer.getCountry()));
            producerDto.setTradeDto(fromTradeToTradeDto(producer.getTrade()));
            producerDto.setProductsDto(new HashSet<>());
            return producerDto;
        } else throw new Exception(DbTables.Producer + ";TRIED TO CONVERT FROM GIVEN NULL PRODUCER");

    }

    public Producer fromProducerDtoToProducer(ProducerDto producerDto) throws Exception {
        if(producerDto != null){
            Producer producer = new Producer();
            producer.setId(producerDto.getId());
            producer.setName(producerDto.getName());
            producer.setCountry(fromCountryDtoToCountry(producerDto.getCountryDto()));
            producer.setTrade(fromTradeDtoToTrade(producerDto.getTradeDto()));
            producer.setProducts(new HashSet<>());
            return producer;
        } else throw new Exception(DbTables.Producer + ";TRIED TO CONVERT FROM GIVEN NULL PRODUCERDTO");

    }

    public TradeDto fromTradeToTradeDto(Trade trade) throws Exception {
        if(trade != null){
            TradeDto tradeDto = new TradeDto();
            tradeDto.setId(trade.getId());
            tradeDto.setName(trade.getName());
            tradeDto.setProducersDto(new HashSet<>());
            return tradeDto;
        }else throw new Exception(DbTables.Trade + ";TRIED TO CONVERT FROM GIVEN NULL TRADE");
    }

    public Trade fromTradeDtoToTrade(TradeDto tradeDto) throws Exception {

        if(tradeDto != null){
            Trade trade = new Trade();
            trade.setId(tradeDto.getId());
            trade.setName(tradeDto.getName());
            trade.setProducers(new HashSet<>());
            return trade;
        }else throw new Exception(DbTables.Trade + ";TRIED TO CONVERT FROM GIVEN NULL TRADEDTO");
    }

    public CategoryDto fromCategoryToCategoryDto(Category category) throws Exception {
        if(category != null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            return categoryDto;
        } else throw new Exception(DbTables.Category + ";TRIED TO CONVERT FROM GIVEN NULL CATEGORY");
    }

    public Category fromCategoryDtoToCategory(CategoryDto categoryDto) throws Exception {
        if(categoryDto != null){
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            return category;
        } else throw new Exception(DbTables.Category + ";TRIED TO CONVERT FROM GIVEN NULL CATEGORYDTO");
    }

    public StockDto fromStockToStockDto(Stock stock) throws Exception {
        if(stock != null){
            StockDto stockDto = new StockDto();
            stockDto.setId(stock.getId());
            stockDto.setQuantity(stock.getQuantity());
            stockDto.setShopDto(fromShopToShopDto(stock.getShop()));
            stockDto.setProductDto(fromProductToProductDto(stock.getProduct()));
            return stockDto;
        }else throw new Exception(DbTables.Stock +";TRIED TO CONVERT FROM GIVEN NULL STOCK");
    }

    public Stock fromStockDtoToStock(StockDto stockDto) throws Exception {
        if(stockDto != null){
            Stock stock = new Stock();
            stock.setId(stockDto.getId());
            stock.setQuantity(stockDto.getQuantity());
            stock.setShop(fromShopDtoToShop(stockDto.getShopDto()));
            stock.setProduct(fromProductDtoToProduct(stockDto.getProductDto()));
            return stock;
        }else throw new Exception(DbTables.Stock +";TRIED TO CONVERT FROM GIVEN NULL STOCKDTO");
    }

    public ShopDto fromShopToShopDto(Shop shop) throws Exception {
        if(shop != null){
            ShopDto shopDto = new ShopDto();
            shopDto.setId(shop.getId());
            shopDto.setName(shop.getName());
            shopDto.setCountryDto(fromCountryToCountryDto(shop.getCountry()));
            return shopDto;
        } else throw new Exception(DbTables.Shop + ";TRIED TO CONVERT FROM GIVEN NULL SHOP");
    }

    public Shop fromShopDtoToShop(ShopDto shopDto) throws Exception {
        if(shopDto != null){
            Shop shop = new Shop();
            shop.setId(shopDto.getId());
            shop.setName(shopDto.getName());
            shop.setCountry(fromCountryDtoToCountry(shopDto.getCountryDto()));
            return shop;
        } else throw new Exception(DbTables.Shop + ";TRIED TO CONVERT FROM GIVEN NULL SHOPDTO");
    }
}
