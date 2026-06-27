//package cz.hornakova.barbora.tennisclub;
//
//import cz.hornakova.barbora.tennisclub.model.entity.Customer;
//import cz.hornakova.barbora.tennisclub.customer.CustomerRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean
//    CommandLineRunner initDatabase(CustomerRepository repository) {
//
//        return args -> {
//            log.info("Preloading " + repository.save(new Customer("Bilbo Baggins", "987654321")));
//            log.info("Preloading " + repository.save(new Customer("Frodo Baggins", "123456789")));
//        };
//    }
//}